package cn.richinfo.tsp.nodeserver.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import org.apache.log4j.Logger;

import cn.richinfo.tsp.nodeserver.handler.Processor;
import cn.richinfo.tsp.nodeserver.message.HeadMessage;
import cn.richinfo.tsp.nodeserver.message.Messages;

/**
 * ClientProcessor
 * 
 * @ClassName: ClientProcessor
 * @author 王全
 * @date 2014-11-18 下午5:49:28
 */
public abstract class ClientProcessor implements Processor {

	Logger logger = Logger.getLogger(ClientProcessor.class);

	// @Override
	public HeadMessage _processMessage(HeadMessage msg, Channel session) {
		return process(msg);
	}

	/**
	 * 业务处理拦截到user thread
	 * 
	 * @param msg
	 * @return
	 */
	public abstract HeadMessage process(HeadMessage msg);

	/**
	 * 业务处理拦截到user thread
	 * 
	 * @param msg
	 * @return
	 */
	public abstract void process(HeadMessage msg, Channel session);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.richinfo.tsp.nameserver.hander.Processor#processMessage(cn.richinfo
	 * .tsp.nameserver.message.HeadMessage,
	 * org.apache.mina.core.session.Channel)
	 */
	/**
	 * silent process response
	 * 
	 * @param msg
	 * @param session
	 * @return
	 */
	@Override
	public HeadMessage processMessage(HeadMessage msg,
			ChannelHandlerContext _session) {
		Channel session = _session.channel();
		HeadMessage retr = null;
		switch (msg.getMessageID()) {
		case Messages.TERMINAL_CAR_VTU_RESP:// message verfiycode
			retr = processTerminalCarVtuResponse(msg, session);
			break;
		case Messages.TERMINAL_REGISTER_RESP:// OBD or car engine
			retr = processTerminalRegisterResponse(msg, session);
			break;
		case Messages.TERMINAL_GET_NODES_RESP:// get node
			retr = processTerminalGetNodesResponse(msg, session);
			break;
		/**
		 * for update
		 */
		case Messages.UPDATE_PLATFORM_RESP_INFO:
			retr = processUpdateTerminalInfoResponse(msg, session);
			break;
		case Messages.UPDATE_PLATFORM_RESP_DATA:
			retr = processUpdateTerminalDataResponse(msg, session);
			break;
		/**
		 * for admin
		 */
		case Messages.ADMIN_PROPERTIES_RESP:
			retr = processAdminPropertiesResp(msg, session);
			break;
		/**
		 * for set terminal item
		 */
		case Messages.PLATFORM_SET_TERMINAL_ITEM_REQ:
			retr = processSetTerminalItemResp(msg, session);
			break;
		/**
		 * for TERMINAL_CONTROL_REQ
		 */
		case Messages.TERMINAL_CONTROL_REQ:
			retr = processSetTerminalControlReq(msg, session);
			break;
		/**
		 * for tsp inner
		 */

		/**
		 * *Transparent transmission
		 */
		case Messages.TT_DOWNLOAD_REQ:
			retr = processDownLoadTTReq(msg, session);
			break;
		default:
			process(msg, session);
			return null;
		}
		return retr;
	}

	private HeadMessage processDownLoadTTReq(HeadMessage msg, Channel session) {
		process(msg, session);
		logger.info(msg);
		return null;
	}

	private HeadMessage processSetTerminalControlReq(HeadMessage msg,
			Channel session) {
		logger.info(msg);
		return null;
	}

	private HeadMessage processSetTerminalItemResp(HeadMessage msg,
			Channel session) {
		// logger.info(msg);
		process(msg, session);
		return null;
	}

	private HeadMessage processAdminPropertiesResp(HeadMessage msg,
			Channel session) {
		// TODO Auto-generated method stub
		process(msg, session);
		return null;
	}

	private HeadMessage processUpdateTerminalDataResponse(HeadMessage msg,
			Channel session) {
		process(msg, session);
		return null;
	}

	private HeadMessage processUpdateTerminalInfoResponse(HeadMessage msg,
			Channel session) {
		process(msg, session);
		return null;
	}

	private HeadMessage processTerminalGetNodesResponse(HeadMessage msg,
			Channel session) {
		// TODO Auto-generated method stub
		process(msg, session);
		return null;
	}

	private HeadMessage processTerminalRegisterResponse(HeadMessage msg,
			Channel session) {
		process(msg, session);
		return null;
	}

	private HeadMessage processTerminalCarVtuResponse(HeadMessage msg,
			Channel session) {
		process(msg, session);
		return null;
	}

}
