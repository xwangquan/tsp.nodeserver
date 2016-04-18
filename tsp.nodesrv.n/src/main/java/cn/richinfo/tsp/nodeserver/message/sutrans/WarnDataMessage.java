package cn.richinfo.tsp.nodeserver.message.sutrans;

import cn.richinfo.tsp.nodeserver.persistence.info.Json;
import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;
import cn.richinfo.tsp.nodeserver.utils.SignUtils;

/**
 * @ClassName: WarnDataMessage
 * @Description: �澯����
 * @author ��ȫ
 * @date 2014-11-19 ����4:30:11
 */
public class WarnDataMessage extends UploadTransparentTransmissionMessage {

	// ʱ�� BCD[6] ʱ���ʽ��YYMMDDhhmmss
	public byte[] time = new byte[6];
	// �ϴ����� 0�������ϴ���1����ѯ�ϴ�
	public byte type;
	// ������ʶ BYTE �ն���ƽ̨������ʶ����41.
	public byte alarm_type;
	// ����״̬ BYTE 0x01:����������0x02:���������
	public byte status;
	// GPS״̬ WORD GPS״̬λ�������42.
	public int gsp_status;
	// ���� DWORD �Զ�Ϊ��λ��γ��ֵ����10��6�η�����ȷ�������֮һ�ȣ�
	public int longitude;
	// γ�� DWORD �Զ�Ϊ��λ��γ��ֵ����10��6�η�����ȷ�������֮һ�ȣ�
	public int lattitude;
	// ����������Ϣ BYTE[n] ������Ϣ���б��ɸ������������Ҳ��û�У�������Ϣͷ�еĳ����ֶ�ȷ������ʽ����41��
	public byte[] information;

	// --inner
	private int SUBLEN = 19;

	public WarnDataMessage() {
		super(SuTransTypes.WARNDATA);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.richinfo.tsp.nodeserver.message.sutrans.
	 * UploadTransparentTransmissionMessage#hei(byte[], int)
	 */
	@Override
	public int hei(byte[] receivedData, int offset) throws Exception {
		int infoLen = receivedData.length - offset - SUBLEN - 2;
		System.arraycopy(receivedData, offset, time, 0, 6);
		type = receivedData[offset + 6];
		alarm_type = receivedData[offset + 7];
		status = receivedData[offset + 8];
		gsp_status = SignUtils.getUnsignedByte(ConversionUtil.byte2short(
				receivedData, offset + 9));
		longitude = ConversionUtil.byte2int(receivedData, offset + 11);
		lattitude = ConversionUtil.byte2int(receivedData, offset + 15);
		information = new byte[infoLen];
		System.arraycopy(receivedData, offset + 19, information, 0, infoLen);
		return offset + SUBLEN + infoLen;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.richinfo.tsp.nodeserver.message.sutrans.
	 * UploadTransparentTransmissionMessage#xiu()
	 */
	@Override
	public byte[] xiu() {
		byte[] send = new byte[getSUBLEN()];
		System.arraycopy(time, 0, send, 0, 6);
		send[6] = type;
		send[7] = alarm_type;
		send[8] = status;
		System.arraycopy(SignUtils.getUnsingedShort(gsp_status), 0, send, +9, 2);
		System.arraycopy(ConversionUtil.int2byte(longitude), 0, send, +11, 4);
		System.arraycopy(ConversionUtil.int2byte(lattitude), 0, send, +15, 4);
		if (information != null && information.length > 0) {
			System.arraycopy(information, 0, send, +19, information.length);
		}
		return send;
	}

	public int getSUBLEN() {
		int sub = 0;
		if (information != null && information.length > 0) {
			sub = information.length;
		}
		SUBLEN = 19 + sub;
		return SUBLEN;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString()).append("],BODY[");
		sb.append("time=").append(getTime()).append("| ");
		sb.append("type=").append(getType()).append("| ");
		sb.append("alarm_type=").append(alarm_type).append("| ");
		sb.append("status=").append(status).append("| ");
		sb.append("gsp_status=").append(gsp_status).append("| ");
		sb.append("longitude=").append(longitude).append("| ");
		sb.append("lattitude=").append(lattitude).append("| ");
		sb.append("information=").append(new String(information)).append("]}");
		return sb.toString();
	}

	public String GPS_STATUS_DESC() {
		StringBuffer sb = new StringBuffer();
		sb.append("����").append(SIGN);
		if ((status >> 2 & 1) == 1)
			sb.append("����").append(SIGN);
		else
			sb.append("����").append(SIGN);

		if ((status >> 1 & 1) == 1)
			sb.append("��γ").append(SIGN);
		else
			sb.append("��γ").append(SIGN);

		if ((status & 1) == 1)
			sb.append("��λ").append(SIGN);
		else
			sb.append("δ��λ").append(SIGN);
		return sb.toString();
	}


	public String getINFO_0C() {
		if (information == null && information.length == 0)
			return null;
		return "{batteryVoltage:"
				+ SignUtils.getUnsignedByte(ConversionUtil
						.byte2short(information)) * 0.1f + "}";
	}

	public String getINFO_0B() {
		if (information == null && information.length == 0)
			return null;
		try {
			INFO_0B info = new INFO_0B();
			info.fuck(information);
			return info.toString();
		} catch (Exception e) {// ignore
		}
		return null;
	}

	class INFO_0B extends Json {
		public String oldVIN;
		public String newVIN;

		public int fuck(byte[] info) throws Exception {
			oldVIN = new String(info, 0, 17);
			newVIN = new String(info, 16, 17);
			return 20;
		}

		@Override
		public String toString() {
			try {
				return mapper.writeValueAsString(this);
			} catch (Exception e) {
				// just ignore
			}
			return null;
		}

	}

	public String getINFO_0A() {
		if (information == null && information.length == 0)
			return null;
		return "{waterTemperature:" + ConversionUtil.byte2int(information)
				+ "}";
	}

	public String getINFO_04() {
		if (information == null && information.length == 0)
			return null;
		return "{duration:" + ConversionUtil.byte2int(information) + "}";

	}

	public String getINFO_03() {
		if (information == null && information.length == 0)
			return null;
		try {
			return "{speed:"
					+ SignUtils.getUnsignedByte(ConversionUtil.byte2short(
							information, 0)) * 0.1f + "}";
		} catch (Exception e) {// ignore
		}
		return null;
	}

	// -----------------------------------------02
	public String getINFO_02() {
		if (information == null && information.length == 0)
			return null;
		try {
			INFO_02 info = new INFO_02();
			info.fuck(information);
			return info.toString();
		} catch (Exception e) {// ignore
		}
		return null;
	}

	// ��ײ���� ---��ײ���ղ���
	public class INFO_02 extends Json {
		// ���� WORD ����ǰ����,��λ��0.1km/h
		public int speed;
		// ת�� WORD ��ֵ��Χ��0 - 30000rpm
		public int rpm;
		// ��ײǰ5sʱ����ת�� BYTE 0x00:�ޣ�0x01:��0x02:��
		public byte turn5s;
		// ��ײǰ5sʱ����ת�� WORD ��λ����
		public int angle5s;
		// ��ײǰ4sʱ����ת�� BYTE 0x00:�ޣ�0x01:��0x02:��
		public byte turn4s;
		// ��ײǰ4sʱ����ת�� WORD ��λ����
		public int angle4s;
		// ��ײǰ3sʱ����ת�� BYTE 0x00:�ޣ�0x01:��0x02:��
		public byte turn3s;
		// ��ײǰ3sʱ����ת�� WORD ��λ����
		public int angle3s;
		// ��ײǰ2sʱ����ת�� BYTE 0x00:�ޣ�0x01:��0x02:��
		public byte turn2s;
		// ��ײǰ2sʱ����ת�� WORD ��λ����
		public int angle2s;
		// ��ײǰ1sʱ����ת�� BYTE 0x00:�ޣ�0x01:��0x02:��
		public byte turn1s;
		// ��ײǰ1sʱ����ת�� WORD ��λ����
		public int angle1s;
		// ��ȫ�����Ƿ�� BYTE 0x00��δ�򿪣�0x01����
		public byte airbag;

		public int fuck(byte[] receivedData) throws Exception {
			speed = (SignUtils.getUnsignedByte(ConversionUtil.byte2short(
					receivedData, 0)));
			rpm = ConversionUtil.byte2int(receivedData, 2);
			turn5s = receivedData[4];
			angle5s = (SignUtils.getUnsignedByte(ConversionUtil.byte2short(
					receivedData, 5)));
			turn4s = receivedData[7];
			angle4s = (SignUtils.getUnsignedByte(ConversionUtil.byte2short(
					receivedData, 8)));
			turn3s = receivedData[10];
			angle3s = (SignUtils.getUnsignedByte(ConversionUtil.byte2short(
					receivedData, 11)));
			turn2s = receivedData[13];
			angle2s = (SignUtils.getUnsignedByte(ConversionUtil.byte2short(
					receivedData, 14)));
			turn1s = receivedData[16];
			angle1s = (SignUtils.getUnsignedByte(ConversionUtil.byte2short(
					receivedData, 17)));
			airbag = receivedData[19];
			return 20;
		}

		public byte[] you() {
			byte[] send = new byte[20];
			System.arraycopy(SignUtils.getUnsingedShort(speed), 0, send, 0, 2);
			System.arraycopy(SignUtils.getUnsingedShort(rpm), 2, send, 0, 2);
			send[4] = turn5s;
			System.arraycopy(SignUtils.getUnsingedShort(angle5s), 5, send, 0, 2);
			send[7] = turn4s;
			System.arraycopy(SignUtils.getUnsingedShort(angle4s), 8, send, 0, 2);
			send[10] = turn3s;
			System.arraycopy(SignUtils.getUnsingedShort(angle3s), 11, send, 0,
					2);
			send[13] = turn2s;
			System.arraycopy(SignUtils.getUnsingedShort(angle2s), 14, send, 0,
					2);
			send[16] = turn1s;
			System.arraycopy(SignUtils.getUnsingedShort(angle1s), 17, send, 0,
					2);
			send[19] = airbag;
			return send;
		}

		@Override
		public String toString() {
			try {
				return mapper.writeValueAsString(this);
			} catch (Exception e) {
				// just ignore
			}
			return null;
		}

	}

	enum ALARM_TYPE {
		// 0x01 �豸�ϵ籨�� ��
		// 0x02 ��ײ���� ������ײ���ղ�������43��
		// 0x03 ���ٱ��� ���ٲ�������44��
		// 0x04 ���ٱ��� ���ٲ�������45��
		// 0x05 �����뱨�� ��
		// 0x06 ��ʱ��������ʻ���� ��
		// 0x07 ����Χ�� ��
		// 0x08 �������ѱ��� ��
		// 0x09 פ��ʱ����״̬���� ��
		// 0x0A ��ȴҺ�¶ȹ��߱��� ��ȴҺ��������47
		// 0x0B �豸�������� ������������48
		// 0x0C ���ص�ѹ���ͱ��� ���ص�ѹ��������49

	}

	// -------------------------------set get

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

	public byte getAlarm_type() {
		return alarm_type;
	}

	public void setAlarm_type(byte alarm_type) {
		this.alarm_type = alarm_type;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public int getGsp_status() {
		return gsp_status;
	}

	public void setGsp_status(int gsp_status) {
		this.gsp_status = gsp_status;
	}

	private double UNIT = 1000000f;

	public double getLattitude() {
		return lattitude / UNIT;
	}

	public double getLongitude() {
		return longitude / UNIT;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public void setLattitude(int lattitude) {
		this.lattitude = lattitude;
	}

	public byte[] getInformation() {
		return information;
	}

	public void setInformation(byte[] information) {
		this.information = information;
	}

}
