package cn.richinfo.tsp.nodeserver.message;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;
import cn.richinfo.tsp.nodeserver.utils.SignUtils;

/**
 * 消息包的接口类，只包含消息头， 所有的接口消息必须实现write or read 其中一个方法
 * 
 * @ClassName: HeadMessage
 * @Description: TSP消息头,参考 中国移动车载终端数据上传接口规范 ==>5.4.3. 消息头
 * @author 王全
 * @date 2014-7-30 下午12:41:11
 */
public class HeadMessage {
	/** 默认string 编码模式 */
	public final static String CHARSET = "GBK";

	/** 消息头head长度 91 or 95 */
	private int HEADLENTH = 91;

	/** 消息Id(类型) */
	private int messageID;

	/** 设备类型 */
	private byte termType;

	/** 消息体属性2字节 */
	private byte[] bodyProp = new byte[2];

	/** 设备编号 BYTE[40] 设备编号，全球唯一 */
	private String tuid = new String(new byte[40]);

	/** 消息流水号 */
	private int sequenceNumber;

	/** 消息长度(head+body) */
	private int messageLength = 91;

	/** 会话标识 */
	private byte[] session = new byte[40];

	/** 版本标识 2个字节 */
	private byte[] version = new byte[2];

	/** 消息封装项 4字节 ,可有可无 */
	// private String msgPack = new String(new byte[4]);
	private byte[] msgPack = new byte[4];

	public HeadMessage() {
	}

	/**
	 * 注册消息类型
	 * 
	 * @param messageID
	 *            消息类型
	 */
	public HeadMessage(int messageID) {
		this.messageID = messageID;
	}

	/**
	 * 注册消息类型,终端type
	 * 
	 * @param messageID
	 *            消息id
	 * @param termType
	 *            terminal type 0=obd;1=ca engine
	 */
	public HeadMessage(int messageID, byte termType) {
		this.messageID = messageID;
		this.termType = termType;
	}

	/**
	 * 将包的数据写到一个byte数组并返回
	 * 
	 * @return 完整消息的byte数组
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
	 * 从byte数组读取包的内容
	 * 
	 * @param receivedData
	 *            输入的byte数组
	 * @throws Exception
	 */
	public int read(byte[] receivedData) throws Exception {
		return read(receivedData, 0);
	}

	/**
	 * 从byte数组读取包的内容
	 * 
	 * @param receivedData
	 *            输入的byte数组
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
	 * check bodyProp 是否有分包(移位运算)
	 * 
	 * @param bodyProp
	 * @return true=has sub package
	 */
	public final boolean hasSubPackage() {
		// 是否分包
		boolean subPackage = false;
		int body = SignUtils.getUnsignedByte(ConversionUtil
				.byte2short(bodyProp));
		if (((body >> 13) & 1) == 1)
			subPackage = true;
		return subPackage;
	}

	/**
	 * check bodyProp 是否TEA加密
	 * 
	 * @param bodyProp
	 * @return true=tea
	 */
	@Deprecated
	public final boolean _hasTea() {
		// 是否tea加密
		boolean tea = false;
		byte[] bit = ConversionUtil.bytes2BinaryByte(bodyProp);
		if (bit[5] == 49)
			tea = true;
		return tea;
	}

	/**
	 * check bodyProp 是否tea加密 (移位运算)
	 * 
	 * @param bodyProp
	 * @return true=rsa
	 */
	public final boolean hasTea() {
		// 是否tea加密
		boolean tea = false;
		int body = SignUtils.getUnsignedByte(ConversionUtil
				.byte2short(bodyProp));
		if (((body >> 10) & 7) == 1)
			tea = true;
		return tea;
	}

	/**
	 * 转换成string的方法
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

	/** ************--Get-set 方法--******************** */
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
	 * 返回消息头长度
	 * 
	 * @return
	 */
	public int getHeadLength() {
		if (hasSubPackage())
			setHeadLength(95);
		return HEADLENTH;
	}

	/**
	 * 返回消息头长度
	 * 
	 * @return
	 */
	public void setHeadLength(int len) {
		this.HEADLENTH = len;
	}
}
