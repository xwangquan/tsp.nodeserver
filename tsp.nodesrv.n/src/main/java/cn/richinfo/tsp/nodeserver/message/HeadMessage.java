package cn.richinfo.tsp.nodeserver.message;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;
import cn.richinfo.tsp.nodeserver.utils.SignUtils;

/**
 * ��Ϣ���Ľӿ��ֻ࣬������Ϣͷ�� ���еĽӿ���Ϣ����ʵ��write or read ����һ������
 * 
 * @ClassName: HeadMessage
 * @Description: TSP��Ϣͷ,�ο� �й��ƶ������ն������ϴ��ӿڹ淶 ==>5.4.3. ��Ϣͷ
 * @author ��ȫ
 * @date 2014-7-30 ����12:41:11
 */
public class HeadMessage {
	/** Ĭ��string ����ģʽ */
	public final static String CHARSET = "GBK";

	/** ��Ϣͷhead���� 91 or 95 */
	private int HEADLENTH = 91;

	/** ��ϢId(����) */
	private int messageID;

	/** �豸���� */
	private byte termType;

	/** ��Ϣ������2�ֽ� */
	private byte[] bodyProp = new byte[2];

	/** �豸��� BYTE[40] �豸��ţ�ȫ��Ψһ */
	private String tuid = new String(new byte[40]);

	/** ��Ϣ��ˮ�� */
	private int sequenceNumber;

	/** ��Ϣ����(head+body) */
	private int messageLength = 91;

	/** �Ự��ʶ */
	private byte[] session = new byte[40];

	/** �汾��ʶ 2���ֽ� */
	private byte[] version = new byte[2];

	/** ��Ϣ��װ�� 4�ֽ� ,���п��� */
	// private String msgPack = new String(new byte[4]);
	private byte[] msgPack = new byte[4];

	public HeadMessage() {
	}

	/**
	 * ע����Ϣ����
	 * 
	 * @param messageID
	 *            ��Ϣ����
	 */
	public HeadMessage(int messageID) {
		this.messageID = messageID;
	}

	/**
	 * ע����Ϣ����,�ն�type
	 * 
	 * @param messageID
	 *            ��Ϣid
	 * @param termType
	 *            terminal type 0=obd;1=ca engine
	 */
	public HeadMessage(int messageID, byte termType) {
		this.messageID = messageID;
		this.termType = termType;
	}

	/**
	 * ����������д��һ��byte���鲢����
	 * 
	 * @return ������Ϣ��byte����
	 */
	public byte[] write() {
		byte send[] = new byte[HEADLENTH];
		System.arraycopy(SignUtils.getUnsingedShort(messageID), 0, send, 0, 2);
		send[2] = termType;
		System.arraycopy(bodyProp, 0, send, 3, 2);
		tuidStuff();
		System.arraycopy(tuid.getBytes(), 0, send, 5, 40);
		System.arraycopy(SignUtils.getUnsingedShort(sequenceNumber), 0, send,
				45, 2);
		System.arraycopy(SignUtils.getUnsingedShort(messageLength), 0, send,
				47, 2);
		System.arraycopy(session, 0, send, 49, 40);
		System.arraycopy(version, 0, send, 89, 2);
		if (HEADLENTH == 95)
			System.arraycopy(msgPack, 0, send, 91, 4);
		return send;
	}

	/**
	 * ��byte�����ȡ��������
	 * 
	 * @param receivedData
	 *            �����byte����
	 * @throws Exception
	 */
	public int read(byte[] receivedData) throws Exception {
		return read(receivedData, 0);
	}

	/**
	 * ��byte�����ȡ��������
	 * 
	 * @param receivedData
	 *            �����byte����
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	public int read(byte[] receivedData, int offset) throws Exception {
		ByteArrayInputStream byteStream = new ByteArrayInputStream(
				receivedData, offset, getHeadLength());
		DataInputStream in = new DataInputStream(byteStream);
		messageID = in.readUnsignedShort();
		termType = in.readByte();
		in.read(bodyProp);
		byte[] termPhoneByte = new byte[40];
		in.read(termPhoneByte);
		setTuid(new String(termPhoneByte));
		sequenceNumber = in.readUnsignedShort();
		messageLength = in.readUnsignedShort();
		in.read(session);
		in.read(version);
		if (HEADLENTH == 95) {
			byte[] msgPackByte = new byte[4];
			in.read(msgPackByte);
			setMsgPack(msgPackByte);
		}
		return offset + getHeadLength();
	}

	/**
	 * check bodyProp �Ƿ��зְ�(��λ����)
	 * 
	 * @param bodyProp
	 * @return true=has sub package
	 */
	public final boolean hasSubPackage() {
		// �Ƿ�ְ�
		boolean subPackage = false;
		int body = SignUtils.getUnsignedByte(ConversionUtil
				.byte2short(bodyProp));
		if (((body >> 13) & 1) == 1)
			subPackage = true;
		return subPackage;
	}

	/**
	 * check bodyProp �Ƿ�TEA����
	 * 
	 * @param bodyProp
	 * @return true=tea
	 */
	@Deprecated
	public final boolean _hasTea() {
		// �Ƿ�tea����
		boolean tea = false;
		byte[] bit = ConversionUtil.bytes2BinaryByte(bodyProp);
		if (bit[5] == 49)
			tea = true;
		return tea;
	}

	/**
	 * check bodyProp �Ƿ�tea���� (��λ����)
	 * 
	 * @param bodyProp
	 * @return true=rsa
	 */
	public final boolean hasTea() {
		// �Ƿ�tea����
		boolean tea = false;
		int body = SignUtils.getUnsignedByte(ConversionUtil
				.byte2short(bodyProp));
		if (((body >> 10) & 7) == 1)
			tea = true;
		return tea;
	}

	/**
	 * ת����string�ķ���
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer("Head:[");
		sb.append("headLength=").append(HEADLENTH).append("| ");
		sb.append("messageLength=").append(messageLength).append("| ");
		sb.append("messageID=").append(messageID).append("| ");
		sb.append("termType=").append(termType).append("| ");
		sb.append("sequenceNumber=").append(sequenceNumber).append("| ");
		sb.append("bodyProp=")
				.append(SignUtils.getUnsignedByte(ConversionUtil
						.byte2short(bodyProp))).append("| ");
		sb.append("tuid=").append(tuid.trim()).append("| ");
		sb.append("session=").append(new String(session).trim()).append("| ");
		String s = new String(version);
		sb.append("version=").append(s.charAt(0)).append(".")
				.append(s.charAt(1)).append("| ");
		if (HEADLENTH == 95)
			sb.append("msgPack={")
					.append("pNum=")
					.append(ConversionUtil.byte2short(new byte[] { msgPack[0],
							msgPack[1] }))
					.append("pSeq=")
					.append(ConversionUtil.byte2short(new byte[] { msgPack[2],
							msgPack[3] })).append("}]");
		return sb.toString();
	}

	/** ************--Get-set ����--******************** */
	public byte[] getBodyProp() {
		return bodyProp;
	}

	public void setBodyProp(byte[] bodyProp) {
		this.bodyProp = bodyProp;
	}

	public int getMessageLength() {
		return messageLength;
	}
	/**
	 * check bodyProp ,then return body length
	 * 
	 * @param bodyProp
	 * @return message length
	 */
	public int getMessageLength(byte[] bodyProp) {
		int prop = SignUtils.getUnsignedByte(ConversionUtil.byte2short(
				bodyProp, 0));
		int len = prop & 0x3FF;
		return len;
	}

	public void setMessageLength(int messageLength) {
		this.messageLength = messageLength;
	}

	public String getTuid() {
		return tuid;
	}

	private void tuidStuff() {
		try {
			if (tuid == null)
				return;
			else if (tuid.getBytes(CHARSET).length < 40) {
				int l = tuid.length();
				this.tuid = tuid + new String(new byte[40 - l]);
			} else if (tuid.getBytes(CHARSET).length > 40) {
				byte[] b = new byte[40];
				System.arraycopy(tuid.getBytes(CHARSET), 0, b, 0, 40);
				this.tuid = new String(b, CHARSET);
			}
		} catch (Exception e) {
		}
	}

	public void setTuid(String tuid) {
		this.tuid = tuid;
	}

	public int getMessageID() {
		return messageID;
	}

	public void setMessageID(int messageID) {
		this.messageID = messageID;
	}

	public byte getTermType() {
		return termType;
	}

	public void setTermType(byte termType) {
		this.termType = termType;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public byte[] getSession() {
		return session;
	}

	public void setSession(byte[] session) {
		this.session = session;
	}

	public byte[] getVersion() {
		return version;
	}

	public void setVersion(byte[] version) {
		this.version = version;
	}

	public byte[] getMsgPack() {
		return msgPack;
	}

	public void setMsgPack(byte[] msgPack) {
		this.msgPack = msgPack;
	}

	/**
	 * ������Ϣͷ����
	 * 
	 * @return
	 */
	public int getHeadLength() {
		if (hasSubPackage())
			setHeadLength(95);
		return HEADLENTH;
	}

	/**
	 * ������Ϣͷ����
	 * 
	 * @return
	 */
	public void setHeadLength(int len) {
		this.HEADLENTH = len;
	}
}
