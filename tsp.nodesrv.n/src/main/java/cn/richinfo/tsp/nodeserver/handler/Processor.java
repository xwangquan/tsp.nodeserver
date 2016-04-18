package cn.richinfo.tsp.nodeserver.handler;

import io.netty.channel.ChannelHandlerContext;
import cn.richinfo.tsp.nodeserver.message.HeadMessage;

/**
 * Real process on the fly;
 * 
 * @ClassName: Processor
 * @author ��ȫ
 * @date 2014-11-18 ����4:53:29
 */
public interface Processor {
	/**
	 * 
	 * @param msg
	 * @param session
	 * @return
	 */
	public HeadMessage processMessage(HeadMessage msg,
			ChannelHandlerContext session);
}
