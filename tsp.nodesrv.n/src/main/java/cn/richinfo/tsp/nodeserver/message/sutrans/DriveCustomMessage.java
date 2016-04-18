package cn.richinfo.tsp.nodeserver.message.sutrans;

import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;
import cn.richinfo.tsp.nodeserver.utils.SignUtils;

/**
 * @ClassName: DriveCustomMessage
 * @Description: ��ʻϰ��
 * @author ��ȫ
 * @date 2014-11-19 ����4:27:04
 */
public class DriveCustomMessage extends UploadTransparentTransmissionMessage {
	// ʱ�� BCD[6] ʱ���ʽ��YYMMDDhhmmss
	public byte[] time = new byte[6];
	// �ϴ����� 0�������ϴ���1����ѯ�ϴ�
	public byte type;
	// ��ʻ�¼����� WORD ��ʻ�¼�������Ϣ�������ͼ8
	public int driving_event_type;
	// �������¼� BYTE[2] ��λ��0.1m/s2
	public byte[] acceleration = new byte[2];
	// �������¼� BYTE[2] ��λ��0.1m/s2
	public byte[] deceleration = new byte[2];
	// ��ת���¼� BYTE[4] ���ݸ�ʽ����29
	public byte[] suddenTurn = new byte[4];
	// ��ת�� BYTE[5] ���ݸ�ʽ����30
	public byte[] highSpeed = new byte[5];
	// ������ת�ٲ�ƥ�� BYTE[7] ���ݸ�ʽ����31
	public byte[] speedNotMatch = new byte[7];
	// ������ BYTE[3] ���ݸ�ʽ����32
	public byte[] longDuration = new byte[3];
	// �յ����� BYTE[5] ���ݸ�ʽ����33
	public byte[] sliding = new byte[5];
	// ƣ�ͼ�ʻ DWORD �����г�ƣ�ͼ�ʻ��ʱ������λ�루�޴��¼�ȡֵΪ0��
	public int fatigue_driving_time;

	// for inner
	private int SUBLEN = 41;

	public DriveCustomMessage() {
		super(SuTransTypes.DRIVECOSTOM);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.richinfo.tsp.nodeserver.message.sutrans.
	 * UploadTransparentTransmissionMessage#hei(byte[], int)
	 */
	@Override
	public int hei(byte[] receivedData, int offset) throws Exception {
		System.arraycopy(receivedData, offset, time, 0, 6);
		type = receivedData[offset + 6];
		driving_event_type = SignUtils.getUnsignedByte(ConversionUtil
				.byte2short(receivedData, offset + 7));
		System.arraycopy(receivedData, offset + 9, acceleration, 0, 2);
		System.arraycopy(receivedData, offset + 11, deceleration, 0, 2);
		System.arraycopy(receivedData, offset + 13, suddenTurn, 0, 4);
		System.arraycopy(receivedData, offset + 17, highSpeed, 0, 5);
		System.arraycopy(receivedData, offset + 22, speedNotMatch, 0, 7);
		System.arraycopy(receivedData, offset + 29, longDuration, 0, 3);
		System.arraycopy(receivedData, offset + 32, sliding, 0, 5);
		fatigue_driving_time = ConversionUtil.byte2int(receivedData,
				offset + 37);
		return offset + SUBLEN;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.richinfo.tsp.nodeserver.message.sutrans.
	 * UploadTransparentTransmissionMessage#xiu()
	 */
	@Override
	public byte[] xiu() {
		byte[] send = new byte[SUBLEN];
		System.arraycopy(time, 0, send, 0, 6);
		send[6] = type;
		System.arraycopy(SignUtils.getUnsingedShort(driving_event_type), 0,
				send, 7, 2);
		System.arraycopy(acceleration, 0, send, 9, 2);
		System.arraycopy(deceleration, 0, send, 11, 2);
		System.arraycopy(suddenTurn, 0, send, 13, 4);
		System.arraycopy(highSpeed, 0, send, 17, 5);
		System.arraycopy(speedNotMatch, 0, send, 22, 7);
		System.arraycopy(longDuration, 0, send, 29, 3);
		System.arraycopy(sliding, 0, send, 32, 5);
		System.arraycopy(ConversionUtil.int2byte(fatigue_driving_time), 0,
				send, 37, 4);
		return send;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString()).append("],BODY[");
		sb.append("time=").append(getTime()).append("| ");
		sb.append("type=").append(getType()).append("| ");
		sb.append("driving_event_type=").append(getDriving_event_type())
				.append("| ");
		sb.append("acceleration=").append(getAcceleration()).append("| ");
		sb.append("deceleration=").append(getDeceleration()).append("| ");
		sb.append("suddenTurn=").append(getSuddenTurn()).append("| ");
		sb.append("highSpeed=").append(getHighSpeed()).append("| ");
		sb.append("speedNotMatch=").append(getSpeedNotMatch()).append("| ");
		sb.append("longDuration=").append(getLongDuration()).append("| ");
		sb.append("sliding=").append(getSliding()).append("| ");
		sb.append("fatigue_driving_time=").append(fatigue_driving_time)
				.append("]}");
		return sb.toString();
	}

	// ----------------------------get/set method

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

	public int getDriving_event_type() {
		return driving_event_type;
	}

	public void setDriving_event_type(int driving_event_type) {
		this.driving_event_type = driving_event_type;
	}

	
	public float getAcceleration() {
		return SignUtils.getUnsignedByte(ConversionUtil.byte2short(
				acceleration, 0)) / 10f;
	}

	public void setAcceleration(byte[] acceleration) {
		this.acceleration = acceleration;
	}

	public float getDeceleration() {
		return SignUtils.getUnsignedByte(ConversionUtil.byte2short(
				deceleration, 0)) / 10f;
	}

	public void setDeceleration(byte[] deceleration) {
		this.deceleration = deceleration;
	}

	public suddenTurnB getSuddenTurn() {
		suddenTurnB bean = new suddenTurnB();
		bean.turn_direction = suddenTurn[0];
		bean.palstance = SignUtils.getUnsignedByte(suddenTurn[1]);
		bean.acceleration = SignUtils.getUnsignedByte(ConversionUtil
				.byte2short(suddenTurn, 2));
		return bean;
	}

	public void setSuddenTurn(suddenTurnB bean) {
		suddenTurn[0] = bean.turn_direction;
		suddenTurn[1] = (byte) bean.palstance;
		System.arraycopy(SignUtils.getUnsingedShort(bean.acceleration), 0,
				suddenTurn, 2, 2);
	}

	public highSpeedB getHighSpeed() {
		highSpeedB bean = new highSpeedB();
		bean.type = highSpeed[0];
		bean.rpm = SignUtils.getUnsignedByte(ConversionUtil.byte2short(
				highSpeed, 1));
		bean.duration = SignUtils.getUnsignedByte(ConversionUtil.byte2short(
				highSpeed, 3));
		return bean;
	}

	public void setHighSpeed(highSpeedB bean) {
		highSpeed[0] = bean.type;
		System.arraycopy(SignUtils.getUnsingedShort(bean.rpm), 0, highSpeed, 1,
				2);
		System.arraycopy(SignUtils.getUnsingedShort(bean.duration), 0,
				highSpeed, 3, 2);
	}

	public speedNotMatchB getSpeedNotMatch() {
		speedNotMatchB bean = new speedNotMatchB();
		bean.type = speedNotMatch[0];
		bean.speed = SignUtils.getUnsignedByte(ConversionUtil.byte2short(
				speedNotMatch, 1));
		bean.rpm = SignUtils.getUnsignedByte(ConversionUtil.byte2short(
				speedNotMatch, 3));
		bean.duration = SignUtils.getUnsignedByte(ConversionUtil.byte2short(
				speedNotMatch, 5));
		return bean;
	}

	public void setSpeedNotMatch(speedNotMatchB bean) {
		speedNotMatch[0] = bean.type;
		System.arraycopy(SignUtils.getUnsingedShort(bean.speed), 0,
				speedNotMatch, 1, 2);
		System.arraycopy(SignUtils.getUnsingedShort(bean.rpm), 0,
				speedNotMatch, 3, 2);
		System.arraycopy(SignUtils.getUnsingedShort(bean.duration), 0,
				speedNotMatch, 5, 2);
	}

	public longDurationB getLongDuration() {
		longDurationB bean = new longDurationB();
		bean.type = longDuration[0];
		bean.duration = SignUtils.getUnsignedByte(ConversionUtil.byte2short(
				longDuration, 1));
		return bean;
	}

	public void setLongDuration(longDurationB bean) {
		longDuration[0] = bean.type;
		System.arraycopy(SignUtils.getUnsingedShort(bean.duration), 0,
				longDuration, 1, 2);
	}

	public slidingB getSliding() {
		slidingB bean = new slidingB();
		bean.type = sliding[0];
		bean.speed = SignUtils.getUnsignedByte(ConversionUtil.byte2short(
				sliding, 1));
		bean.duration = SignUtils.getUnsignedByte(ConversionUtil.byte2short(
				sliding, 3));
		return bean;
	}

	public void setSliding(slidingB bean) {
		sliding[0] = bean.type;
		System.arraycopy(SignUtils.getUnsingedShort(bean.speed), 0, sliding, 1,
				2);
		System.arraycopy(SignUtils.getUnsingedShort(bean.duration), 0, sliding,
				3, 2);
	}

	public int getFatigue_driving_time() {
		return fatigue_driving_time;
	}

	public void setFatigue_driving_time(int fatigue_driving_time) {
		this.fatigue_driving_time = fatigue_driving_time;
	}

	// ----------------------------inner class for bean
	public class suddenTurnB {
		// ת�䷽�� BYTE 0����ת��1����ת
		public byte turn_direction;
		// ���ٶ� BYTE ��λ����/��
		public int palstance;
		// ������ٶ� WORD ��λ��0.1m/s2
		public int acceleration;

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder("deceleration [");
			sb.append("turn_direction=").append(turn_direction).append("| ");
			sb.append("palstance=").append(palstance).append("| ");
			sb.append("acceleration=").append(acceleration).append("]");
			return sb.toString();
		}
	}

	public class highSpeedB {
		// ��ʶ BYTE 0����ʼ��1������
		public byte type;
		// ת�����ֵ WORD ��λ��rpm
		public int rpm;
		// ����ʱ�� WORD ��λ��s
		public int duration;

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder("highSpeed [");
			sb.append("type=").append(type).append("| ");
			sb.append("rpm=").append(rpm).append("| ");
			sb.append("duration=").append(duration).append("]");
			return sb.toString();
		}
	}

	public class speedNotMatchB {
		// ��ʶ BYTE 0����ʼ��1������
		public byte type;
		// �ٶ� WORD ��λ��0.1km/h
		public int speed;
		// ת�� WORD ��λ��rpm
		public int rpm;
		// ����ʱ�� WORD ��λ��s
		public int duration;

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder("speedNotMatch [");
			sb.append("type=").append(type).append("| ");
			sb.append("speed=").append(speed).append("| ");
			sb.append("rpm=").append(rpm).append("| ");
			sb.append("duration=").append(duration).append("]");
			return sb.toString();
		}
	}

	public class longDurationB {
		// ��ʶ BYTE 0����ʼ��1������
		public byte type;
		// ����ʱ�� WORD ��λ��s
		public int duration;

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder("longDuration [");
			sb.append("type=").append(type).append("| ");
			sb.append("duration=").append(duration).append("]");
			return sb.toString();
		}
	}

	public class slidingB {
		// ��ʶ BYTE 0����ʼ��1������
		public byte type;
		// ��������ٶ� WORD ��λ��0.1km/h��
		public int speed;
		// ����ʱ�� WORD ��λ��s
		public int duration;

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder("sliding [");
			sb.append("type=").append(type).append("| ");
			sb.append("speed=").append(speed).append("| ");
			sb.append("duration=").append(duration).append("]");
			return sb.toString();
		}
	}
}
