package cn.richinfo.tsp.nodeserver.client.sutrans;

import java.net.InetSocketAddress;

import cn.richinfo.tsp.nodeserver.client.NodeServerSession;
import cn.richinfo.tsp.nodeserver.client.NodeServerSessionBuilder;
import cn.richinfo.tsp.nodeserver.message.HeadMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.DummyObdLocationMessage;
import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;
import cn.richinfo.tsp.nodeserver.utils.SignUtils;

/**
 * @ClassName: ObdLocationSimulator
 * @Description: TODO()
 * @author ÍõÈ«
 * @date 2015-9-23 ÏÂÎç4:12:36
 */
public class ObdLocationSimulator {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		//58.61.30.35 1021
		NodeServerSessionBuilder builder = new NodeServerSessionBuilder(
				new InetSocketAddress("192.168.9.52", 8317));
		builder.setMaxRespondTime(30 * 1000);
		NodeServerSession session = builder.build();
		String TUID = "otuid444";
		int uin = 34800;
		int carid = 11700;
		byte[] re = new byte[40];
		System.arraycopy(ConversionUtil.int2byte(uin), 0, re, 0, 4);
		System.arraycopy(ConversionUtil.int2byte(carid), 0, re, 4, 4);
		DummyObdLocationMessage msg = new DummyObdLocationMessage();// 3000
		msg.setHeadLength(91);
		msg.setMessageLength(91+25+3);
		msg.setSession(re);
		msg.setTuid(TUID);
		// 113.930432,22.545778
		msg.lattitude = 22545778;
		msg.longitude = 113930432;
		// set body prop
		System.out.println(msg.getMessageLength());
		msg.setBodyProp(SignUtils.getUnsingedShort(ConversionUtil
				.getBodyPropety(false, false, 25+3)));
		//msg.setBodyProp(SignUtils.getUnsingedShort(msg.getMessageLength()));

		for (int i = 0; i < 10; i++) {
			HeadMessage verifyCodeRespMesage = null;
			try {
				verifyCodeRespMesage = session.write(msg);
			} catch (Exception e) {
				System.out.println("time out");
				continue;
			}
			if (verifyCodeRespMesage != null
					&& verifyCodeRespMesage.getSequenceNumber() == msg
							.getSequenceNumber()) {
				msg.lattitude = msg.lattitude + 20;
				msg.longitude = msg.longitude + 10;
				System.out.println(verifyCodeRespMesage);
			}
		}
	}

}
