package cn.richinfo.tsp.nodeserver.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import cn.richinfo.tsp.nodeserver.message.HeadMessage;
import cn.richinfo.tsp.nodeserver.message.Messages;
import cn.richinfo.tsp.nodeserver.message.respond.PlatformCommonRespondMessage;
import cn.richinfo.tsp.nodeserver.message.respond.TerminalCommonRespondMessage;
import cn.richinfo.tsp.nodeserver.message.serialization.CopyMessage;
import cn.richinfo.tsp.nodeserver.message.serialization.NameServerSerializeMessage;
import cn.richinfo.tsp.nodeserver.message.serialization.SerializationMessage;
import cn.richinfo.tsp.nodeserver.message.serialization.ShellMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.UploadTransparentTransmissionMessage;
import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;
import cn.richinfo.tsp.nodeserver.utils.ShellCommand;
import cn.richinfo.tsp.nodeserver.utils.SignUtils;

/**
 * 服务端数据包处理的抽象类
 * 
 * @ClassName: ServerProcessor
 * @Description: TODO()
 * @author 王全
 * @date 2014-11-18 下午4:57:19
 */
public abstract class ServerProcessor implements Processor {

	public Logger log = Logger.getLogger(ServerProcessor.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.richinfo.tsp.nameserver.hander.Processor#processMessage(cn.richinfo
	 * .tsp.nameserver.message.HeadMessage,
	 * org.apache.mina.core.session.IoSession)
	 */
	public HeadMessage processMessage(HeadMessage msg,
			ChannelHandlerContext _session) {
		Channel session = _session.channel();
		HeadMessage retr = null;
		switch (msg.getMessageID()) {
		case Messages.TERMINAL_HEART_REQ:// message verfiycode
			retr = processHeartbeatRequest(msg, session);
			break;
		/**
		 * for common respond
		 */
		case Messages.COMMON_PLANTFORM_RESP:
			retr = processPlantformResp(msg, session);
			break;
		case Messages.COMMON_TERMINAL_RESP:
			retr = processTerminalResp(msg, session);
			break;
		/**
		 * for serialize
		 */
		case Messages.SERVER_OBJECT_SERIALIZE_MESSAGE:
			retr = processSerializeMessage(msg, session);
			break;
		/**
		 * for tt
		 */
		case Messages.TT_UPLOAD_REQ:
			retr = processTTReq((UploadTransparentTransmissionMessage) msg,
					session);
			break;
		/**
		 * for other
		 */
		case Messages.SERVER_ECHO_MESAGE:
			retr = msg;
			break;
		}

		return retr;
	}

	/**
	 * 上行透传
	 * 
	 * @param msg
	 * @param session
	 * @return
	 */
	public abstract HeadMessage processTTReq(
			UploadTransparentTransmissionMessage msg, Channel session);

	/**
	 * {@link Serializable} message
	 * 
	 * @param msg
	 * @param session
	 * @return
	 */
	public HeadMessage processSerializeMessage(HeadMessage msg, Channel session) {
		SerializationMessage message = (SerializationMessage) msg;
		if (message.getMessage() instanceof NameServerSerializeMessage)
			return serializeProcessor(message, session);

		// do something
		ArrayList<String> list = new ArrayList<String>();
		list.add("What a fuck msg!?");
		list.add("What a fuck msg!?");
		message.setMessage(list);
		return message;
	}

	private HeadMessage serializeProcessor(SerializationMessage message,
			Channel session) {
		NameServerSerializeMessage msg = (NameServerSerializeMessage) message
				.getMessage();
		if (msg instanceof CopyMessage) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Hello Word!", "你好,世界!");
			message.setMessage(map);
			return message;
		}
		if (msg instanceof ShellMessage) {
			ShellMessage shellMessage = (ShellMessage) msg;
			List<String> result = ShellCommand.runShell(shellMessage
					.getCommand());
			if (result != null)
				message.setMessage(result);
			else
				message.setMessage("Wrong command!");
			return message;
		}
		return null;
	}

	/**
	 * 终端通用应答
	 * 
	 * @param msg
	 * @param session
	 * @return
	 */
	public HeadMessage processTerminalResp(HeadMessage msg, Channel session) {
		TerminalCommonRespondMessage res = (TerminalCommonRespondMessage) msg;
		log.info(res);
		return null;
	}

	/**
	 * 平台通用应答 (silence)
	 * 
	 * @param msg
	 * @param session
	 * @return
	 */
	public HeadMessage processPlantformResp(HeadMessage msg, Channel session) {
		// only silence print
		log.debug(msg);
		return null;
	}

	public HeadMessage processHeartbeatRequest(HeadMessage msg, Channel session) {
		// do nothing
		log.debug(msg);
		// return by the way it came
		return getPlatformRespondMessage(msg).setResult((byte) 0);
		// return msg;
	}

	/**
	 * Copy common message head
	 */
	public void copyHead(HeadMessage req, HeadMessage resp) {
		resp.setTermType(req.getTermType());
		resp.setTuid(req.getTuid());
		resp.setSequenceNumber(req.getSequenceNumber());
		resp.setHeadLength(req.getHeadLength());
		resp.setSession(req.getSession());
		resp.setVersion(req.getVersion());
		resp.setMsgPack(req.getMsgPack());
	}

	/**
	 * terminal control
	 * 
	 * @param msg
	 * @param session
	 * @return
	 */
	public HeadMessage process(HeadMessage msg, Channel session) {
		return null;
	}

	/**
	 * get platoform common respond message
	 * 
	 * @param req
	 * @return
	 */
	protected PlatformCommonRespondMessage getPlatformRespondMessage(
			HeadMessage req) {
		PlatformCommonRespondMessage respond = (PlatformCommonRespondMessage) new PlatformCommonRespondMessage()
				.setAnsId(req.getMessageID())
				.setAnsSeq(req.getSequenceNumber());
		copyHead(req, respond);
		respond.setBodyProp(SignUtils.getUnsingedShort(ConversionUtil
				.getBodyPropety(false, false,
						respond.getLEN() - respond.getHeadLength())));
		return respond;
	}

}
