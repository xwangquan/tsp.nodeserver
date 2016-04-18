package cn.richinfo.tsp.nodeserver.client;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

import cn.richinfo.tsp.nodeserver.message.HeadMessage;
import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;

/**
 * server respond processor
 * 
 * @ClassName: NodeClientProcessor
 * @Description: TODO()
 * @author ÍõÈ«
 * @date 2014-11-19 ÉÏÎç11:29:20
 */
public class NodeClientProcessor extends ClientProcessor {

	private ConcurrentHashMap<Integer, Object> commands;

	private CallbackProcessor callback;

	Logger logger = Logger.getLogger(NodeClientProcessor.class);

	public NodeClientProcessor() {
	}

	@Override
	@SuppressWarnings("unchecked")
	public void process(HeadMessage msg, Channel session) {
		if (commands == null)
			commands = (ConcurrentHashMap<Integer, Object>) session.attr(
					AttributeKey.valueOf(SessionStatusItem.MESSAGEQUEE
							.getName())).get();
		boolean pack = msg.hasSubPackage();
		if (session.attr(
				AttributeKey.valueOf(SessionStatusItem.CALLBACK.getName()))
				.get() != null) {
			callback = (CallbackProcessor) session.attr(
					AttributeKey.valueOf(SessionStatusItem.CALLBACK.getName()))
					.get();
		}
		if (pack) {
			if (callback != null) {
				if (packageOver(msg.getMsgPack()))
					callback.messsageReceived(msg, true);
				else
					callback.messsageReceived(msg, false);
			}
		} else {
			if (callback != null) {
				callback.messsageReceived(msg, true);
				return;
			}
		}

		// client time out, silence
		if (commands.get(msg.getSequenceNumber()) == null)
			return;
		CountDownLatch latch = (CountDownLatch) commands.get(msg
				.getSequenceNumber());
		commands.put(msg.getSequenceNumber(), msg);
		latch.countDown();
	}

	private boolean packageOver(byte[] msgpack) {
		short all = ConversionUtil.byte2short(new byte[] { msgpack[0],
				msgpack[1] });
		short seq = ConversionUtil.byte2short(new byte[] { msgpack[2],
				msgpack[3] });
		return all == seq ? true : false;
	}

	@Override
	public HeadMessage process(HeadMessage msg) {
		// ignore
		return null;
	}

}
