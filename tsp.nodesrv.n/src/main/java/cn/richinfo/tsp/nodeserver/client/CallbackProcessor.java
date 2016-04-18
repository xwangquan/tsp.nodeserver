package cn.richinfo.tsp.nodeserver.client;

import cn.richinfo.tsp.nodeserver.message.HeadMessage;

/**
 * @ClassName: CallbackProcessor
 * @Description: call back
 * @author ÍõÈ«
 * @date 2014-10-13 ÏÂÎç1:40:28
 */
public interface CallbackProcessor {

	/**
	 * server respond event
	 * 
	 * @param message
	 *            {@link HeadMessage}
	 * @param finished
	 *            {@link Boolean}
	 */
	public void messsageReceived(HeadMessage message, boolean finished);
}
