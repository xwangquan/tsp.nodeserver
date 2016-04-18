package cn.richinfo.tsp.nodeserver;

import cn.richinfo.tsp.nodeserver.base.NodeServerBaseTest;
import cn.richinfo.tsp.nodeserver.message.HeadMessage;

/**
 * @ClassName: MessageBaseTest
 * @Description: TODO()
 * @author 王全
 * @date 2014-7-31 下午12:27:54
 */
public abstract class MessageBaseTest extends NodeServerBaseTest {

	public HeadMessage head = new HeadMessage();

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		initHead();
	}

	private void initHead() {
		head.setBodyProp(new byte[2]);
		head.setSequenceNumber(1);
		head.setTuid("myphonesssmyphonesssmyphonesssmyphonesss");
		head.setSession("Sessions!!Sessions!!Sessions!!Sessions!!".getBytes());
		head.setVersion("10".getBytes());
		head.setMsgPack(new byte[4]);// 缺省
		head.setMessageLength(57);
		System.out.println("Head test! helloWorld1111");

	}

	/**
	 * copy message head
	 * 
	 * @param message
	 */
	protected void copyHead(HeadMessage message) {
		message.setTermType(head.getTermType());
		message.setBodyProp(head.getBodyProp());
		message.setTuid(head.getTuid());
		message.setSequenceNumber(head.getSequenceNumber());
		message.setHeadLength(head.getHeadLength());
		message.setSession(head.getSession());
		message.setVersion(head.getVersion());
		message.setMsgPack(head.getMsgPack());
	}

	public HeadMessage getHead() {
		return head;
	}

	public void setHead(HeadMessage head) {
		this.head = head;
	}

	public abstract void testWrite() throws Exception;

	public abstract void testRead() throws Exception;

}
