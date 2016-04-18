package cn.richinfo.tsp.nodeserver.message.sutrans;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.richinfo.tsp.nodeserver.MessageBaseTest;
import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;

/**
 * @ClassName: DriveDataMessageTest
 * @Description: TODO()
 * @author ÍõÈ«
 * @date 2014-11-20 ÉÏÎç8:37:34
 */
public class ObdFaultCodeMessageTest extends MessageBaseTest {
	ObdFaultCodeMessage message = new ObdFaultCodeMessage();

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		copyHead(message);
		// for bcd time
		SimpleDateFormat sdfShortTime = new SimpleDateFormat("yyMMddhhmmss");
		String _time = sdfShortTime.format(new Date());
		message.setTime(ConversionUtil.long2bcd(Long.parseLong(_time)));
		message.type = 1;
		message.setCodeNum(54);
		message.setFault_code("P1001|P1002|P1004|P1005");
		// message.setCodeNum(0);
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
		ObdFaultCodeMessage _message = new ObdFaultCodeMessage();
		int pos = _message.read(by);
		assertEquals(_message.getMessageLength(), pos);

		log.info(_message.toString());
		log.info(message.toString());
	}

}
