package cn.richinfo.tsp.nodeserver.message.respond;

import cn.richinfo.tsp.nodeserver.message.HeadMessage;
import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;
import cn.richinfo.tsp.nodeserver.utils.SignUtils;

/**
 * @ClassName: CommonRespondMessage
 * @Description: 通用应答
 * @author 王全
 * @date 2014-9-18 下午3:35:01
 */
public class CommonRespondMessage extends HeadMessage {

	private int LEN = getHeadLength() + 5;

	// 应答流水号 2bytes
	private int ansSeq;

	// 对应的(平台/终端)id (2bytes)
	private int ansId;

	// 0：成功/确认；1：失败；2：消息有误；3：不支持
	private byte result;

	/**
	 * @param messageID
	 */
	public CommonRespondMessage(int messageID) {
		super(messageID);
	}

	/**
	 * only for test
	 */
	@Override
	public int read(byte[] receivedData, int offset) throws Exception {
		// read message head
		super.read(receivedData, offset);
		// offset
		offset += getHeadLength();
		ansSeq = SignUtils.getUnsignedByte(ConversionUtil.byte2short(
				receivedData, offset));
		ansId = SignUtils.getUnsignedByte(ConversionUtil.byte2short(
				receivedData, offset + 2));
		result = receivedData[offset + 4];
		return offset + 5;
	}

	@Override
	public byte[] write() {
		setMessageLength(getLEN());
		byte send[] = new byte[getLEN()];
		// write message head
		System.arraycopy(super.write(), 0, send, 0, getHeadLength());
		// write message body
		System.arraycopy(SignUtils.getUnsingedShort(ansSeq), 0, send,
				getHeadLength(), 2);
		System.arraycopy(SignUtils.getUnsingedShort(ansId), 0, send,
				getHeadLength() + 2, 2);
		send[getHeadLength() + 4] = result;
		return send;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString()).append("Body:\r\n");
		sb.append("\t ansSeq=").append(ansSeq).append("\r\n");
		sb.append("\t ansId=").append(ansId).append("\r\n");
		sb.append("\t result=").append(result).append("\r\n");
		return sb.toString();
	}

	// -----------------get / set method

	public int getAnsSeq() {
		return ansSeq;
	}

	public CommonRespondMessage setAnsSeq(int ansSeq) {
		this.ansSeq = ansSeq;
		return this;
	}

	public int getAnsId() {
		return ansId;
	}

	public CommonRespondMessage setAnsId(int ansId) {
		this.ansId = ansId;
		return this;
	}

	public byte getResult() {
		return result;
	}

	public CommonRespondMessage setResult(byte result) {
		this.result = result;
		return this;
	}

	public int getLEN() {
		return LEN;
	}

}
