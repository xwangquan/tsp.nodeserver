package cn.richinfo.tsp.nodeserver.client.sutrans;

import org.junit.Test;

import cn.richinfo.tsp.nodeserver.base.SocketBaseBuilder;
import cn.richinfo.tsp.nodeserver.client.NodeServerSession;
import cn.richinfo.tsp.nodeserver.message.HeadMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.DummyTripReportDataMessage;

/**
 * @ClassName: TripReportDataMessageClient
 * @Description: TODO()
 * @author 王全
 * @date 2014-11-27 下午5:03:36
 */
public class TripReportDataMessageClient extends SocketBaseBuilder {

	@Test
	public void test1() throws Exception {
		NodeServerSession session = getSession();
		DummyTripReportDataMessage message = new DummyTripReportDataMessage();
		copyHead(message);
		String TUID = "CDB8AAE3-D76D-F495-8A5D-4C-YUANZHENG02"
				+ new String(new byte[2]);
		// message.setBodyProp(new byte[2]);
		message.setTermType((byte) 1);
		message.setSequenceNumber(1);
		message.setSession("Sessions!!Sessions!!Sessions!!Sessions!!"
				.getBytes());
		message.setVersion("aa".getBytes());
		message.setMsgPack(new byte[4]);// 缺省
		message.setMessageLength(57);
		message.setHeadLength(91);
		message.setTuid(TUID);
		log.info(message);
		for (int i = 0; i < 100; i++) {
			HeadMessage ret = session.write(message);
			log.info(ret);
		}
		shutdown();
	}
}
