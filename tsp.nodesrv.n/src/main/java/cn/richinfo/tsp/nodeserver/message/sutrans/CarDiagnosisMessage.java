package cn.richinfo.tsp.nodeserver.message.sutrans;

/**
 * ������Ϲ��������ݸ�ʽ
 * 
 * @ClassName: CarDiagnosisMessage
 * @Description: For roland diagnosis
 * @author ��ȫ
 * @date 2015-8-24 ����5:35:04
 */
public class CarDiagnosisMessage extends UploadTransparentTransmissionMessage {

	// ʱ�� BCD[6] ʱ���ʽ��YYMMDDhhmmss
	public byte[] time = new byte[6];
	// �ϴ����� 0�������ϴ���1����ѯ�ϴ�
	public byte type;
	public String codes;

	public CarDiagnosisMessage() {
		super(SuTransTypes.CARDIAGNOSIS);
	}

	@Override
	public int hei(byte[] receivedData, int offset) throws Exception {
		System.arraycopy(receivedData, offset, time, 0, 6);
		type = receivedData[offset + 6];
		int _codesLen = getMessageLength(getBodyProp()) - 7 - 3;
		codes = new String(receivedData, offset + 7, _codesLen);
		return offset + 7 + _codesLen;
	}

	// for test
	@Override
	public byte[] xiu() {
		byte[] send = new byte[7 + codes.length()];// max=256
		System.arraycopy(time, 0, send, 0, 6);
		send[6] = type;
		System.arraycopy(codes.getBytes(), 0, send, 7, codes.length());
		return send;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString()).append("],BODY[");
		sb.append("time=").append(getTime()).append("| ");
		sb.append("type=").append(type).append("| ");
		sb.append("codes=").append(codes).append(" ]}");
		return sb.toString();
	}

	public byte[] getTime() {
		return time;
	}

	public void setTime(byte[] time) {
		this.time = time;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public String getCodes() {
		return codes;
	}

	public void setCodes(String codes) {
		this.codes = codes;
	}
}
