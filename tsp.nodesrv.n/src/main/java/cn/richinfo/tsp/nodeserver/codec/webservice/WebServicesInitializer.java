package cn.richinfo.tsp.nodeserver.codec.webservice;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @ClassName: NodeServerWebServicesInitializer
 * @Description: for web service codec initializer
 * @author ��ȫ
 * @date 2014-12-3 ����9:14:01
 */
public class WebServicesInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new HttpServerCodec());
		pipeline.addLast(new WebServicesHandler());
	}

}
