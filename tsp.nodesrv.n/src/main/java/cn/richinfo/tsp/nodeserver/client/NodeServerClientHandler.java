package cn.richinfo.tsp.nodeserver.client;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import org.apache.log4j.Logger;

import cn.richinfo.tsp.nodeserver.handler.Processor;
import cn.richinfo.tsp.nodeserver.message.HeadMessage;

/**
 * 客户端 业务处理
 * 
 * @ClassName: NodeServerClentHandler
 * @Description: TODO()
 * @author 王全
 * @date 2014-11-18 下午6:09:25
 */
public class NodeServerClientHandler extends ChannelHandlerAdapter {

	private Logger log = Logger.getLogger(getClass());
	/**
	 * 解包处理
	 */
	private Processor processor;

	public NodeServerClientHandler(Processor processor) {
		this.processor = processor;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelInactive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext session, Object message)
			throws Exception {
		HeadMessage msg = (HeadMessage) message;
		log.debug("Client Received Message：" + msg.toString());
		try {
			HeadMessage retr = processor.processMessage(msg, session);
			if (retr != null) {
				session.writeAndFlush(retr);
			}
		} catch (Exception e) {
			log.error("Process message error", e);
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelReadComplete(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
	}

}
