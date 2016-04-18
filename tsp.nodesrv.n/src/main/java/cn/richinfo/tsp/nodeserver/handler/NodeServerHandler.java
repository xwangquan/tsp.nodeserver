package cn.richinfo.tsp.nodeserver.handler;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import org.apache.log4j.Logger;

import cn.richinfo.tsp.nodeserver.message.HeadMessage;

/**
 * @ClassName: NodeServerHandler
 * @Description: TODO()
 * @author ÍõÈ«
 * @date 2014-11-18 ÏÂÎç4:49:39
 */
@Sharable
public class NodeServerHandler extends ChannelHandlerAdapter {
	private Logger log = Logger.getLogger(getClass());

	private NodeServerProcessor processor;

	public NodeServerHandler(Processor processor) {
		this.processor = (NodeServerProcessor) processor;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.info("New Channle actived!");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		log.info("Channle inactive!");

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		log.error("Channle error!",cause);

	}

	@Override
	public void channelRead(ChannelHandlerContext session, Object message)
			throws Exception {
		HeadMessage msg = (HeadMessage) message;
		log.debug("Received Terminal Message£º" + msg.toString());
		try {
			HeadMessage retr = processor.processMessage(msg, session);
			if (retr != null) {
				session.channel().writeAndFlush(retr);
				// session.pipeline().write(msg);
			}
		} catch (Exception e) {
			log.error("Process terminal message error , " + msg, e);
		}
	}
}
