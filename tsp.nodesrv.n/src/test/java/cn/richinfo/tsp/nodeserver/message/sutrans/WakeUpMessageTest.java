package cn.richinfo.tsp.nodeserver.message.sutrans;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.richinfo.tsp.nodeserver.MessageBaseTest;
import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;

/**
 * @ClassName: DriveDataMessageTest
 * @Description: ObdLocationMessage
 * @author ��ȫ
 * @date 2014-11-20 ����8:37:34
 */
public class WakeUpMessageTest extends MessageBaseTest {
	WakeUpMessage message = new WakeUpMessage();

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		copyHead(message);
		// for bcd time
		SimpleDateFormat sdfShortTime = new SimpleDateFormat("yyMMddhhmmss");
		String _time = sdfShortTime.format(new Date());
		message.setTime(ConversionUtil.long2bcd(Long.parseLong(_time)));
		message.type = 0;
		// 37.4295815873,118.1688418409
		message.lattitude = 37429581;
		message.longitude = 118418409;
		message.batteryVoltage=630;
		message.duration=555;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.richinfo.tsp.nodeserver.MessageBaseTest#testRead()
	 */
	@Override
	public void testRead() throws Exception {
		byte[] _by = message.write();
		byte[] by = new byte[_by.length + 2];
		System.arraycopy(_by, 0, by, 0, _by.length);
		WakeUpMessage _message = new WakeUpMessage();
		int pos = _message.read(by);
		assertEquals(_message.getMessageLength(), pos);

		log.info(_message.toString());
		log.info(message.toString());
	}

}
