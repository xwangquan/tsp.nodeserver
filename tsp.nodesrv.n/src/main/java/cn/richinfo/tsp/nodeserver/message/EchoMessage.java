package cn.richinfo.tsp.nodeserver.message;

/**
 * 
 * @ClassName: EchoMessage
 * @Description: Wall me!
 * @author ��ȫ
 * @date 2014-11-20 ����3:54:17
 */
public class EchoMessage extends HeadMessage {

	private String fuck = "Well,wall me!";

	public EchoMessage() {
		super(Messages.SERVER_ECHO_MESAGE);
	}

	@Override
	public byte[] write() {
		return fuck.getBytes();
	}
}
