package cn.richinfo.tsp.nodeserver.message.serialization;

import io.netty.buffer.Unpooled;
import cn.richinfo.tsp.nodeserver.message.HeadMessage;
import cn.richinfo.tsp.nodeserver.message.Messages;

/**
 * @ClassName: SerializationMessage todo!
 * @Description: serialization message
 * @author ÍõÈ«
 * @date 2014-11-3 ÉÏÎç11:25:08
 */
public abstract class AbstractSerializationMessage extends HeadMessage {
	private int maxObjectSize = 1048576 * 100; // 100MB
	private int LEN = 0;

	public AbstractSerializationMessage() {
		super(Messages.SERVER_OBJECT_SERIALIZE_MESSAGE);
	}

	@Override
	public int read(byte[] receivedData) throws Exception {
		return super.read(receivedData);
	}

	@Override
	public final int read(byte[] receivedData, int offset) throws Exception {
		// read message head
		super.read(receivedData, offset);
		// offset
		offset += getHeadLength();
		byte[] decode = new byte[receivedData.length - offset];
		System.arraycopy(receivedData, offset, decode, 0, decode.length);
		decode(decode);
		return offset + decode.length;
	}

	@Override
	public final byte[] write() {
		byte[] encode = encode();
		setLEN(getHeadLength() + encode.length);
		setMessageLength(getLEN());
		byte send[] = new byte[getLEN()];
		// write message head
		System.arraycopy(super.write(), 0, send, 0, getHeadLength());
		// write message body
		System.arraycopy(encode, 0, send, getHeadLength(), encode.length);
		return send;
	}

	/**
	 * serialize
	 * 
	 * @return
	 */
	private byte[] encode() {
		Unpooled.buffer(64);
		return null;
	}

	/**
	 * un serialize
	 */
	private Object decode(byte[] source) throws Exception {

		return getMessage();
	}

	// ---------------------get /set method

	public int getLEN() {
		return LEN;
	}

	private void setLEN(int lEN) {
		LEN = lEN;
	}

	public abstract Object getMessage();

	public abstract void setMessage(Object message);

	// -----------------super messsage

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	@Override
	public byte[] getBodyProp() {
		// TODO Auto-generated method stub
		return super.getBodyProp();
	}

	@Override
	public void setBodyProp(byte[] bodyProp) {
		// TODO Auto-generated method stub
		super.setBodyProp(bodyProp);
	}

	@Override
	public int getMessageLength() {
		// TODO Auto-generated method stub
		return super.getMessageLength();
	}

	@Override
	public void setMessageLength(int messageLength) {
		// TODO Auto-generated method stub
		super.setMessageLength(messageLength);
	}

	@Override
	public String getTuid() {
		// TODO Auto-generated method stub
		return super.getTuid();
	}

	@Override
	public void setTuid(String tuid) {
		// TODO Auto-generated method stub
		super.setTuid(tuid);
	}

	@Override
	public int getMessageID() {
		// TODO Auto-generated method stub
		return super.getMessageID();
	}

	@Override
	public void setMessageID(int messageID) {
		// TODO Auto-generated method stub
		super.setMessageID(messageID);
	}

	@Override
	public byte getTermType() {
		// TODO Auto-generated method stub
		return super.getTermType();
	}

	@Override
	public void setTermType(byte termType) {
		// TODO Auto-generated method stub
		super.setTermType(termType);
	}

	@Override
	public int getSequenceNumber() {
		// TODO Auto-generated method stub
		return super.getSequenceNumber();
	}

	@Override
	public void setSequenceNumber(int sequenceNumber) {
		// TODO Auto-generated method stub
		super.setSequenceNumber(sequenceNumber);
	}

	@Override
	public byte[] getSession() {
		// TODO Auto-generated method stub
		return super.getSession();
	}

	@Override
	public void setSession(byte[] session) {
		// TODO Auto-generated method stub
		super.setSession(session);
	}

	@Override
	public byte[] getVersion() {
		// TODO Auto-generated method stub
		return super.getVersion();
	}

	@Override
	public void setVersion(byte[] version) {
		// TODO Auto-generated method stub
		super.setVersion(version);
	}

	@Override
	public byte[] getMsgPack() {
		// TODO Auto-generated method stub
		return super.getMsgPack();
	}

	@Override
	public void setMsgPack(byte[] msgPack) {
		// TODO Auto-generated method stub
		super.setMsgPack(msgPack);
	}

	@Override
	public int getHeadLength() {
		// TODO Auto-generated method stub
		return super.getHeadLength();
	}

	@Override
	public void setHeadLength(int len) {
		// TODO Auto-generated method stub
		super.setHeadLength(len);
	}
}
