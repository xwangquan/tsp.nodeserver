package cn.richinfo.tsp.nodeserver.message;

import cn.richinfo.tsp.nodeserver.base.MessageBaseTest;

/**
 * @ClassName: HeadMessageTest
 * @Description: TODO()
 * @author ÍõÈ«
 * @date 2014-7-31 ÉÏÎç11:36:53
 */
public class HeadMessageTest extends MessageBaseTest {

	@Override
	public void testRead() throws Exception {
		byte[] headBytes = getHead().write();
		HeadMessage message = new HeadMessage();
		message.read(headBytes);
		System.out.println(message);
		HeadMessage m = getHead();
		m.setHeadLength(91);
		byte[] b = m.write();
		HeadMessage messag2 = new HeadMessage();
		messag2.setHeadLength(91);
		messag2.read(b);
		System.out.println(messag2);
	}

	@Override
	public void testWrite() throws Exception {
		HeadMessage headMesssage = getHead();
		byte[] headBytes = headMesssage.write();
		assertEquals(headBytes.length, headMesssage.getHeadLength());
	}
}
