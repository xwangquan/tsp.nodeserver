package cn.richinfo.tsp.nodeserver.client.sutrans;

import org.junit.Test;

import cn.richinfo.tsp.nodeserver.base.SocketBaseBuilder;
import cn.richinfo.tsp.nodeserver.client.NodeServerSession;
import cn.richinfo.tsp.nodeserver.message.HeadMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.DummyDriveDataMessage;
import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;
import cn.richinfo.tsp.nodeserver.utils.SignUtils;

public class DriveDataMessageClient extends SocketBaseBuilder {

	@Test
	public void test1() throws Exception {
		NodeServerSession session = getSession();
		DummyDriveDataMessage message = new DummyDriveDataMessage();
		// DummyObdFaultCodeMessage message = new DummyObdFaultCodeMessage();
		// DummyObdLocationMessage message = new DummyObdLocationMessage();
		copyHead(message);
		String TUID = "wangquantest";
		// message.setBodyProp(new byte[2]);
		message.setTermType((byte) 1);
		message.setSequenceNumber(1);
		message.setSession("Sessions!!Sessions!!Sessions!!Sessions!!"
				.getBytes());
		message.setVersion("aa".getBytes());
		message.setMsgPack(new byte[4]);// ȱʡ
		message.setMessageLength(57);
		message.setHeadLength(91);
		message.setTuid(TUID);
		
		//for tea test
		message.setBodyProp(SignUtils.getUnsingedShort(ConversionUtil
				.getBodyPropety(false, true, 0)));
		message.setBaseSecret("aaaaaaaaaaaaaaaa");
		log.info(message);
		for (int i = 0; i < 1000; i++) {
			message.setTuid(TUID+i);
			HeadMessage ret = session.write(message);
			log.info(ret);
		}
		shutdown();
	}
}
