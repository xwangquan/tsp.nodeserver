package cn.richinfo.tsp.nodeserver.message.sutrans;

import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;
import cn.richinfo.tsp.nodeserver.utils.SignUtils;

/**
 * 
 * @ClassName: ObdLocationMessage
 * @Description: λ�û�����Ϣ��OBD����GPSģ��ʱ��ָ����Ч
 * @author ��ȫ
 * @date 2014-11-19 ����4:26:04
 */
public class ObdLocationMessage extends UploadTransparentTransmissionMessage {

	// ʱ�� BCD[6] ʱ���ʽ��YYMMDDhhmmss
	public byte[] time = new byte[6];

	// �ϴ����� BYTE 0�������ϴ���1����ѯ�ϴ�
	public byte type;

	// ״̬ DWORD ״̬λ�����ͼ7
	public int status;

	// γ�� DWORD �Զ�Ϊ��λ��γ��ֵ����10�����η�����ȷ�������֮һ��
	public int lattitude;

	// ���� DWORD �Զ�Ϊ��λ�ľ���ֵ����10�����η�����ȷ�������֮һ��
	public int longitude;

	// �߳� WORD ���θ߶ȣ���λΪ��
	public int height;

	// �ٶ� WORD 1/10km/h
	public int speed;

	// ���� WORD 0-359�㣬����Ϊ0��˳ʱ��
	public int direction;

	// ��γN37��25��43.38�� ����E118��09��46.05��
	public ObdLocationMessage() {
		super(SuTransTypes.OBDLOCATION);
	}

	// --inner
	private int SUBLEN = 25;

	@Override
	public int hei(byte[] receivedData, int offset) throws Exception {
		System.arraycopy(receivedData, offset, time, 0, 6);
		type = receivedData[offset + 6];
		status = ConversionUtil.byte2int(receivedData, offset + 7);
		lattitude = ConversionUtil.byte2int(receivedData, offset + 11);
		longitude = ConversionUtil.byte2int(receivedData, offset + 15);
		height = SignUtils.getUnsignedByte(ConversionUtil.byte2short(
				receivedData, offset + 19));
		speed = SignUtils.getUnsignedByte(ConversionUtil.byte2short(
				receivedData, offset + 21));
		direction = SignUtils.getUnsignedByte(ConversionUtil.byte2short(
				receivedData, offset + 23));
		return offset + SUBLEN;
	}

	@Override
	public byte[] xiu() {
		byte[] send = new byte[SUBLEN];
		System.arraycopy(time, 0, send, 0, 6);
		send[6] = type;
		System.arraycopy(ConversionUtil.int2byte(status), 0, send, +7, 4);
		System.arraycopy(ConversionUtil.int2byte(lattitude), 0, send, +11, 4);
		System.arraycopy(ConversionUtil.int2byte(longitude), 0, send, +15, 4);
		System.arraycopy(SignUtils.getUnsingedShort(height), 0, send, +19, 2);
		System.arraycopy(SignUtils.getUnsingedShort(speed), 0, send, +21, 2);
		System.arraycopy(SignUtils.getUnsingedShort(direction), 0, send, +23, 2);
		return send;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString()).append("],BODY[");
		sb.append("time=").append(getTime()).append("| ");
		sb.append("type=").append(type).append("| ");
		sb.append("status=").append(getStatus()).append("| ");
		sb.append("statusDes=").append(STATUS()).append("| ");
		sb.append("lattitude=").append(getLattitude()).append("| ");
		sb.append("longitude=").append(getLongitude()).append("| ");
		sb.append("height=").append(getHeight()).append("| ");
		sb.append("speed=").append(getSpeed()).append("| ");
		sb.append("direction=").append(getDirection()).append("]}");
		return sb.toString();
	}

	public String STATUS() {
		StringBuffer sb = new StringBuffer();
		sb.append("����").append(SIGN);
		if ((status >> 5 & 1) == 1)
			sb.append("����").append(SIGN);
		else
			sb.append("δ����").append(SIGN);

		sb.append("����").append(SIGN);
		if ((status >> 3 & 1) == 1)
			sb.append("����").append(SIGN);
		else
			sb.append("����").append(SIGN);

		if ((status >> 2 & 1) == 1)
			sb.append("��γ").append(SIGN);
		else
			sb.append("��γ").append(SIGN);

		if ((status >> 1 & 1) == 1)
			sb.append("��λ").append(SIGN);
		else
			sb.append("δ��λ").append(SIGN);
		sb.append("����");
		return sb.toString();
	}

	// --------------------------set/get

	public long getTime() {
		return ConversionUtil.bcd2long(time);
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	private double UNIT = 1000000f;

	public double getLattitude() {
		return lattitude / UNIT;
	}

	public void setLattitude(int lattitude) {
		this.lattitude = lattitude;
	}

	public double getLongitude() {
		return longitude / UNIT;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

}
