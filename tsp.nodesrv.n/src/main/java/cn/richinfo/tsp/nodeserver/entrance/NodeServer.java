package cn.richinfo.tsp.nodeserver.entrance;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.security.AccessControlException;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.Jedis;
import cn.richinfo.tsp.nodeserver.codec.NodeServerDecoder;
import cn.richinfo.tsp.nodeserver.codec.NodeServerEncoder;
import cn.richinfo.tsp.nodeserver.codec.NodeServerWallEncoder;
import cn.richinfo.tsp.nodeserver.entrance.webServices.NodeHttpServer;
import cn.richinfo.tsp.nodeserver.handler.NodeServerHandler;
import cn.richinfo.tsp.nodeserver.handler.NodeServerProcessor;
import cn.richinfo.tsp.nodeserver.mq.KafkaFacade;
import cn.richinfo.tsp.nodeserver.utils.Config;
import cn.richinfo.tsp.nodeserver.utils.ConfigFactory;
import cn.richinfo.tsp.nodeserver.utils.PortCheckUtils;

/**
 * @ClassName: NodeServer
 * @Description: Node Server entrance here!
 * @author ÍõÈ«
 * @date 2014-11-18 ÏÂÎç3:48:02
 */
public class NodeServer {
	private static NodeServer server = null;

	private NodeServerContext context;
	private static Logger log = Logger.getLogger(NodeServer.class);
	// Node Server properties
	public static Config config = ConfigFactory
			.getConfigByWorkingPath(Constants.SERVER_CONFIG_FILE);
	// you know this
	public ApplicationContext springContext;
	private EventLoopGroup boss;
	private EventLoopGroup worker;
	public static Date STARTTIME = new Date();

	// only one instance
	public static NodeServer getInstance() {
		if (server == null) {
			synchronized (NodeServer.class) {
				if (server == null) {
					server = new NodeServer();
				}
			}
		}
		return server;
	}

	/**
	 * init node server context
	 */
	public void context() throws Exception {
		PropertyConfigurator.configure(Constants.LOG4J_PROPERTIES);
		// spring context
		springContext = new ClassPathXmlApplicationContext(config.getConfig(
				Constants.SPRING_PROPERTIES, "applicationContext.xml"));
		context.setSpringContext(springContext);
		context.setConfig(config);
	}

	/**
	 * program entrance
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		getInstance().start();
		if (shutdownHook == null) {
			shutdownHook = server.new NodeServerShutdownHook();
		}
		Runtime.getRuntime().addShutdownHook(shutdownHook);
		server.await();
		server.stopAwait();
		System.exit(1);
	}

	/**
	 * start all
	 * 
	 * @throws Exception
	 */
	public void start() throws Exception {
		context = NodeServerContext.getContext();
		context();
		redis();
		kafka();
		net();
		hbase();
		// start web services
		starthttp();
	}

	public void redis() {
		Jedis jedis = new Jedis(config.getConfig(Constants.REDIS_SERVER_IP),
				Integer.parseInt(config.getConfig(Constants.REDIS_SERVER_PORT)));
		context.setJedis(jedis);
		log.info(PREFIX + "Redis init,address = "
				+ config.getConfig(Constants.REDIS_SERVER_IP) + ":"
				+ config.getConfig(Constants.REDIS_SERVER_PORT));
	}

	public void kafka() {
		context.setKafkaFacade(KafkaFacade.getKafkaHook().init());
		log.info(PREFIX + "Kafka init!");
	}

	public void starthttp() throws Exception {
		NodeHttpServer.getInstance().start();
	}

	/**
	 * Hadoop store init
	 * 
	 * @throws Exception
	 */
	public void hbase() throws Exception {
		HbaseHook hook = new HbaseHook();
		hook.init();
		context.setHbase(hook);
		log.info(PREFIX + "Hbase init!");
	}

	/**
	 * for shutdown port
	 */

	/**
	 * for services port
	 */
	public void net() {
		boss = new NioEventLoopGroup(2);
		worker = new NioEventLoopGroup();
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			// Configure the server.
			bootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_TIMEOUT, 30)
					.option(ChannelOption.SO_BACKLOG, 100)
					// .handler(new LoggingHandler(LogLevel.DEBUG))
					.childHandler(new ChannelInitializer<Channel>() {
						protected void initChannel(Channel ch) throws Exception {
							ChannelPipeline p = ch.pipeline();
							p.addLast(new LoggingHandler(LogLevel.DEBUG));
							p.addLast(new NodeServerDecoder());
							p.addLast(new NodeServerEncoder());
							p.addLast(new NodeServerWallEncoder());
							p.addLast(new NodeServerHandler(
									new NodeServerProcessor(context)));

						};
					});
			// listen port
			int port = Integer.parseInt(config.getConfig(Constants.SERVER_PORT,
					"8317"));
			// Start the server.
			ChannelFuture future = bootstrap.bind(port).sync();
			if (future.isSuccess())
				log.info(PREFIX + "NodeServer listen on " + port + " .");
			// Wait until the server socket is closed.
			// future.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Shut down all event loops to terminate all threads.
			// boss.shutdownGracefully();
			// worker.shutdownGracefully();
		}
	}

	/**
	 * release node server
	 */
	public void stop() {
		try { // context dispose(thread interrupted)
			context.dispose();
			// net connection release
			boss.shutdownGracefully();
			worker.shutdownGracefully();
			// jmx release
			// monitor release
			log.info("NodeServer was stoped !");
		} catch (Exception e) {
		}
	}

	/**
	 * -----------------------------------shutdown hook
	 */

	protected static Thread shutdownHook = null;

	/**
	 * Shutdown hook which will perform a clean shutdown of name server if
	 * needed.
	 */
	private class NodeServerShutdownHook extends Thread {
		@Override
		public void run() {
			server.stop();
		}
	}

	/**
	 * Thread that currently is inside our await() method.
	 */
	private volatile Thread awaitThread = null;

	/**
	 * Server socket that is used to wait for the shutdown command.
	 */
	private volatile ServerSocket awaitSocket = null;

	private int port = 8318;
	private volatile boolean stopAwait = false;
	private String localhostListen = "localhost";
	/**
	 * The shutdown command string we are looking for.
	 */
	private String shutdown = "SHUTDOWN";

	/**
	 * A random number generator that is <strong>only</strong> used if the
	 * shutdown command string is longer than 1024 characters.
	 */
	private Random random = null;

	private void stopAwait() {
		stopAwait = true;
		Thread t = awaitThread;
		if (t != null) {
			ServerSocket s = awaitSocket;
			if (s != null) {
				awaitSocket = null;
				try {
					s.close();
				} catch (IOException e) {
					// Ignored
				}
			}
			t.interrupt();
			try {
				t.join(1000);
			} catch (InterruptedException e) {
				// Ignored
			}
		}
	}

	private void await() {
		if (port == -1) {
			try {
				awaitThread = Thread.currentThread();
				while (!stopAwait) {
					try {
						Thread.sleep(10000);
					} catch (InterruptedException ex) {
						// continue and check the flag
					}
				}
			} finally {
				awaitThread = null;
			}
			return;
		}

		// Set up a server socket to wait on
		try {
			while (PortCheckUtils.isLoclePortUsing(port)) {
				port++;
				continue;
			}
			awaitSocket = new ServerSocket(port, 1,
					InetAddress.getByName(localhostListen));
		} catch (IOException e) {
			log.error("NodeServer.await: create[" + localhostListen + ":"
					+ port + "]: ", e);
			return;
		}
		log.info("NodeServer safety exit port listen on " + port + " !");
		try {
			awaitThread = Thread.currentThread();

			// Loop waiting for a connection and a valid command
			while (!stopAwait) {
				ServerSocket serverSocket = awaitSocket;
				if (serverSocket == null) {
					break;
				}

				// Wait for the next connection
				Socket socket = null;
				StringBuilder command = new StringBuilder();
				try {
					InputStream stream;
					try {
						socket = serverSocket.accept();
						socket.setSoTimeout(10 * 1000); // Ten seconds
						stream = socket.getInputStream();
					} catch (SocketTimeoutException ste) {
						continue;
					} catch (AccessControlException ace) {
						log.warn(
								"NodeServer.accept security exception: "
										+ ace.getMessage(), ace);
						continue;
					} catch (IOException e) {
						if (stopAwait) {
							// Wait was aborted with socket.close()
							break;
						}
						log.error("NodeServer.await: accept: ", e);
						break;
					}

					// Read a set of characters from the socket
					int expected = 1024; // Cut off to avoid DoS attack
					while (expected < shutdown.length()) {
						if (random == null)
							random = new Random();
						expected += (random.nextInt() % 1024);
					}
					while (expected > 0) {
						int ch = -1;
						try {
							ch = stream.read();
						} catch (IOException e) {
							log.warn("NodeServer.await: read: ", e);
							ch = -1;
						}
						if (ch < 32) // Control character or EOF terminates loop
							break;
						command.append((char) ch);
						expected--;
					}
				} finally {
					// Close the socket now that we are done with it
					try {
						if (socket != null) {
							socket.close();
						}
					} catch (IOException e) {
						// Ignore
					}
				}

				// Match against our command string
				boolean match = command.toString().equals(shutdown);
				if (match) {
					log.info("NodeServer shutdownViaPort!");
					break;
				} else
					log.warn("NodeServer.await: Invalid command '"
							+ command.toString() + "' received");
			}
		} finally {
			ServerSocket serverSocket = awaitSocket;
			awaitThread = null;
			awaitSocket = null;

			// Close the server socket and return
			if (serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					// Ignore
				}
			}
		}
	}

	private static String PREFIX = "#####################>>>>>";
}
