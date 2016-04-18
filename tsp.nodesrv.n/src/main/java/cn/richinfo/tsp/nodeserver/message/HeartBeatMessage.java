package cn.richinfo.tsp.nodeserver.message;

/**
 * ������������Ϣ��
 * 
 * @ClassName: HeartBeatMessage
 * @Description: TODO()
 * @author ��ȫ
 * @date 2014-11-18 ����5:40:11
 */
public class HeartBeatMessage extends HeadMessage {
	public HeartBeatMessage() {
		super(Messages.TERMINAL_HEART_REQ);
	}

	// no body

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("HeartBeatMessage:\r\n");
		sb.append(super.toString()).append("Body:\r\n");
		return sb.toString();
	}
}
