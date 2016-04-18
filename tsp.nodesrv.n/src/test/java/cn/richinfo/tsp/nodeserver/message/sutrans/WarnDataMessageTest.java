package cn.richinfo.tsp.nodeserver.message.sutrans;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.richinfo.tsp.nodeserver.MessageBaseTest;
import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;

/**
 * @ClassName: DriveDataMessageTest
 * @Description: ObdLocationMessage
 * @author 王全
 * @date 2014-11-20 上午8:37:34
 */
public class WarnDataMessageTest extends MessageBaseTest {
	WarnDataMessage message = new WarnDataMessage();

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		copyHead(message);
		// for bcd time
		SimpleDateFormat sdfShortTime = new SimpleDateFormat("yyMMddhhmmss");
		String _time = sdfShortTime.format(new Date());
		message.setTime(ConversionUtil.long2bcd(Long.parseLong(_time)));
		message.type = 0;
		message.alarm_type = 5;
		message.status=2;
		//37.4295815873,118.1688418409
		message.lattitude=37429581;
		message.longitude=118418409;
		message.information = "afasd设备断电报警".getBytes();
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
		WarnDataMessage _message = new WarnDataMessage();
		int pos = _message.read(by);
		assertEquals(_message.getMessageLength(), pos);

		log.info(_message.toString());
		log.info(message.toString());
	}

}
