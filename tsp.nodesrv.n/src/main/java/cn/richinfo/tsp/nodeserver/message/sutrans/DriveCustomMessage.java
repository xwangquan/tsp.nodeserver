package cn.richinfo.tsp.nodeserver.message.sutrans;

import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;
import cn.richinfo.tsp.nodeserver.utils.SignUtils;

/**
 * @ClassName: DriveCustomMessage
 * @Description: 驾驶习惯
 * @author 王全
 * @date 2014-11-19 下午4:27:04
 */
public class DriveCustomMessage extends UploadTransparentTransmissionMessage {
	// 时间 BCD[6] 时间格式：YYMMDDhhmmss
	public byte[] time = new byte[6];
	// 上传类型 0：主动上传；1：查询上传
	public byte type;
	// 驾驶事件类型 WORD 驾驶事件类型信息，定义见图8
	public int driving_event_type;
	// 急加速事件 BYTE[2] 单位：0.1m/s2
	public byte[] acceleration = new byte[2];
	// 急减速事件 BYTE[2] 单位：0.1m/s2
	public byte[] deceleration = new byte[2];
	// 急转弯事件 BYTE[4] 数据格式见表29
	public byte[] suddenTurn = new byte[4];
	// 高转速 BYTE[5] 数据格式见表30
	public byte[] highSpeed = new byte[5];
	// 车速与转速不匹配 BYTE[7] 数据格式见表31
	public byte[] speedNotMatch = new byte[7];
	// 长怠速 BYTE[3] 数据格式见表32
	public byte[] longDuration = new byte[3];
	// 空档滑行 BYTE[5] 数据格式见表33
	public byte[] sliding = new byte[5];
	// 疲劳驾驶 DWORD 本次行程疲劳驾驶总时长，单位秒（无此事件取值为0）
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
		// 转弯方向 BYTE 0：左转；1：右转
		public byte turn_direction;
		// 角速度 BYTE 单位：度/秒
		public int palstance;
		// 横向加速度 WORD 单位：0.1m/s2
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
		// 标识 BYTE 0：开始；1：结束
		public byte type;
		// 转速最大值 WORD 单位：rpm
		public int rpm;
		// 持续时间 WORD 单位：s
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
		// 标识 BYTE 0：开始；1：结束
		public byte type;
		// 速度 WORD 单位：0.1km/h
		public int speed;
		// 转速 WORD 单位：rpm
		public int rpm;
		// 持续时间 WORD 单位：s
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
		// 标识 BYTE 0：开始；1：结束
		public byte type;
		// 持续时间 WORD 单位：s
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
		// 标识 BYTE 0：开始；1：结束
		public byte type;
		// 滑行最大速度 WORD 单位：0.1km/h。
		public int speed;
		// 持续时间 WORD 单位：s
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
