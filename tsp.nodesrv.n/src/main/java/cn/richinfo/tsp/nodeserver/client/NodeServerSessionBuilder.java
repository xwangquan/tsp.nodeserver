package cn.richinfo.tsp.nodeserver.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.AttributeKey;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import cn.richinfo.tsp.nodeserver.codec.NodeServerDecoder;
import cn.richinfo.tsp.nodeserver.codec.NodeServerEncoder;

/**
 * @ClassName: NameServerClientBuilder
 * @Description: Session build factory
 * @author ÍõÈ«
 * @date 2014-9-11 ÏÂÎç5:14:25
 */
public final class NodeServerSessionBuilder {

	Logger logger = Logger.getLogger(NodeServerSessionBuilder.class);

	/**
	 * name server address
	 */
	private InetSocketAddress address;

	/**
	 * name server session
	 */
	private Channel session;

	private NodeServerSession nameServerSession;

	private long maxRespondTime;

	private CallbackProcessor callbackProcessor;

	public NodeServerSessionBuilder(InetSocketAddress address) {
		this.address = address;
	}

	/**
	 * Session operate max wait time (in ms)
	 */
	public NodeServerSessionBuilder setMaxRespondTime(long time) {
		if (time <= 0) {
			throw new IllegalArgumentException("maxRespondTime<=0");
		}
		this.maxRespondTime = time;
		return this;
	}

	public NodeServerSessionBuilder setCallbackHook(
			CallbackProcessor callbackProcessor) {
		if (callbackProcessor == null)
			throw new IllegalArgumentException("callbackProcessor is null!");

		if (session != null && session.isActive())
			throw new IllegalArgumentException("On the fly ,not support!");

		this.callbackProcessor = callbackProcessor;
		return this;
	}

	private ConcurrentHashMap<Integer, Object> commands;

	public NodeServerSession build() throws IOException {
		if (session == null || !session.isActive())
			connect();
		if (session == null || !session.isActive()) {
			throw new IllegalStateException("Can't connect to " + address);
		}
		if (nameServerSession == null)
			nameServerSession = new NodeServerSession(session, maxRespondTime,
					commands);
		logger.info("Connected to " + address.getHostString() + ":"
				+ address.getPort());
		return nameServerSession;
	}

	EventLoopGroup group;

	/**
	 * connect to name server
	 */
	private void connect() {
		try {
			if (session == null || !session.isActive()) {
				// Configure the client.
				group = new NioEventLoopGroup();
				try {
					Bootstrap bootstrap = new Bootstrap();
					bootstrap.group(group).channel(NioSocketChannel.class)
							.option(ChannelOption.TCP_NODELAY, true)
							// .handler(new LoggingHandler(LogLevel.INFO))
							.handler(new ChannelInitializer<SocketChannel>() {
								@Override
								public void initChannel(SocketChannel ch)
										throws Exception {
									ChannelPipeline p = ch.pipeline();
									//p.addLast(new LoggingHandler(LogLevel.INFO));
									p.addLast(new NodeServerDecoder());
									p.addLast(new NodeServerEncoder());
									p.addLast(new NodeServerClientHandler(
											new NodeClientProcessor()));
								}
							});
					if (callbackProcessor != null)
						bootstrap.attr(AttributeKey
								.valueOf(SessionStatusItem.CALLBACK.getName()),
								callbackProcessor);

					commands = new ConcurrentHashMap<Integer, Object>();
					bootstrap.attr(AttributeKey
							.valueOf(SessionStatusItem.MESSAGEQUEE.getName()),
							commands);
					// Start the client.
					// ChannelFuture future = bootstrap.connect(address).sync();
					ChannelFuture future = bootstrap.connect(address);
					if (future.await().isSuccess()) {
						session = future.channel();
						return;
					}
					// Wait until the connection is closed.
					// future.closeFuture().sync();
				} finally {
					// Shut down the event loop to terminate all threads.
					// group.shutdownGracefully();
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * shutdown the session builder
	 */
	public void shutdown() {
		if (group == null || group.isShutdown())
			throw new IllegalStateException("Builder was already disposed!");
		group.shutdownGracefully();
	}
}
