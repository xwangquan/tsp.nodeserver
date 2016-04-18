package cn.richinfo.tsp.nodeserver.message.sutrans;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.richinfo.tsp.nodeserver.MessageBaseTest;
import cn.richinfo.tsp.nodeserver.persistence.info.CarStatus;
import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;

/**
 * @ClassName: CarStatusMessageTest
 * @Description: for CarStatusMessage
 * @author ÍõÈ«
 * @date 2014-11-20 ÉÏÎç8:37:34
 */
public class CarStatusMessageTest extends MessageBaseTest {
	CarStatusMessage message = new CarStatusMessage();

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		copyHead(message);
		// for bcd time
		SimpleDateFormat sdfShortTime = new SimpleDateFormat("yyMMddhhmmss");
		String _time = sdfShortTime.format(new Date());
		message.setTime(ConversionUtil.long2bcd(Long.parseLong(_time)));
		message.type = 1;
		message.a = 83;
		message.b = 110;
		message.c = 35;
		message.d = -112;
		message.e = -50;
		message.f = 96;
		message.g = -15;
		message.h = 17;
		message.i = 3;
		message.j = 50;
		message.k = 120;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.richinfo.tsp.nodeserver.MessageBaseTest#testWrite()
	 */
	@Override
	public void testWrite() throws Exception {
		byte[] by = message.write();
		assertEquals(by.length, message.getMessageLength());
	}
	public void testJson() throws Exception {
		CarStatus status= new CarStatus(message);
		log.info(status.toString());
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.richinfo.tsp.nodeserver.MessageBaseTest#testRead()
	 */
	@Override
	public void testRead() throws Exception {
		byte[] by = message.write();
		CarStatusMessage _message = new CarStatusMessage();
		int pos = _message.read(by);
		assertEquals(_message.getMessageLength(), pos);

		log.info(_message.toString());
		log.info(message.toString());
	}

}
