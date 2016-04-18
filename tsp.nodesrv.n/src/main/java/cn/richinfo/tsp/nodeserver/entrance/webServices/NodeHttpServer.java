package cn.richinfo.tsp.nodeserver.entrance.webServices;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.apache.log4j.Logger;

import cn.richinfo.tsp.nodeserver.codec.webservice.WebServicesInitializer;
import cn.richinfo.tsp.nodeserver.entrance.Constants;
import cn.richinfo.tsp.nodeserver.entrance.NodeServerContext;

/**
 * 
 * @ClassName: NodeHttpServer
 * @Description: An HTTP server that sends back the content of the received HTTP
 *               request in a pretty plaintext form.
 * @author ÍõÈ«
 * @date 2015-5-15 ÏÂÎç3:34:10
 */
public class NodeHttpServer {

	private static Logger log = Logger.getLogger(NodeHttpServer.class);

	private static NodeHttpServer server = null;

	private NodeHttpServer() {

	}

	// only one instance
	public static NodeHttpServer getInstance() {
		if (server == null) {
			synchronized (NodeHttpServer.class) {
				if (server == null) {
					server = new NodeHttpServer();
				}
			}
		}
		return server;
	}

	public void start() throws Exception {
		// Configure the server.
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.option(ChannelOption.SO_BACKLOG, 1024);
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new WebServicesInitializer());
			int port = Integer.parseInt(NodeServerContext.getContext()
					.getConfig()
					.getConfig(Constants.SERVER_MONITOR_PORT, "8422"));
			Channel ch = b.bind(port).sync().channel();
			log.info("#####################>>>>>"
					+ "Node Server Monitor listen on " + port);
			ch.closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) {

	}
}