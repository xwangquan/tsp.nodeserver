package cn.richinfo.tsp.nodeserver.message.sutrans;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.richinfo.tsp.nodeserver.base.MessageBaseTest;
import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;
import cn.richinfo.tsp.nodeserver.utils.SignUtils;

public class CarDiagnosisMessageTest extends MessageBaseTest {
	CarDiagnosisMessage message = new CarDiagnosisMessage();

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		copyHead(message);
		// for bcd time
		SimpleDateFormat sdfShortTime = new SimpleDateFormat("yyMMddhhmmss");
		String _time = sdfShortTime.format(new Date());
		message.setTime(ConversionUtil.long2bcd(Long.parseLong(_time)));
		message.type = 1;
		message.codes="1234567890";
		//tea
		message.setBodyProp(SignUtils.getUnsingedShort(ConversionUtil
				.getBodyPropety(false, true, 7+10)));
		message.setBaseSecret("aaaaaaaaaaaaaaaa");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.richinfo.tsp.nodeserver.MessageBaseTest#testWrite()
	 */
	@Override
	public void testWrite() throws Exception {
//		byte[] by = message.write();
//		assertEquals(by.length, message.getMessageLength());
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.richinfo.tsp.nodeserver.MessageBaseTest#testRead()
	 */
	@Override
	public void testRead() throws Exception {
		byte[] by = message.write();
		CarDiagnosisMessage _message = new CarDiagnosisMessage();
		int pos = _message.read(by);
		assertEquals(_message.getMessageLength(), pos);

		log.info(_message.toString());
		log.info(message.toString());
	}

}
