package cn.richinfo.tsp.nodeserver.message.sutrans;

import cn.richinfo.tsp.nodeserver.persistence.info.Json;
import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;
import cn.richinfo.tsp.nodeserver.utils.SignUtils;

/**
 * @ClassName: WarnDataMessage
 * @Description: 告警数据
 * @author 王全
 * @date 2014-11-19 下午4:30:11
 */
public class WarnDataMessage extends UploadTransparentTransmissionMessage {

	// 时间 BCD[6] 时间格式：YYMMDDhhmmss
	public byte[] time = new byte[6];
	// 上传类型 0：主动上传；1：查询上传
	public byte type;
	// 报警标识 BYTE 终端至平台报警标识见表41.
	public byte alarm_type;
	// 报警状态 BYTE 0x01:报警产生；0x02:报警解除；
	public byte status;
	// GPS状态 WORD GPS状态位定义见表42.
	public int gsp_status;
	// 经度 DWORD 以度为单位的纬度值乘以10的6次方，精确到百万分之一度；
	public int longitude;
	// 纬度 DWORD 以度为单位的纬度值乘以10的6次方，精确到百万分之一度；
	public int lattitude;
	// 报警附加信息 BYTE[n] 附加信息项列表由各报警项决定，也可没有，根据消息头中的长度字段确定。格式见表41。
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
		sb.append("保留").append(SIGN);
		if ((status >> 2 & 1) == 1)
			sb.append("西经").append(SIGN);
		else
			sb.append("东经").append(SIGN);

		if ((status >> 1 & 1) == 1)
			sb.append("南纬").append(SIGN);
		else
			sb.append("北纬").append(SIGN);

		if ((status & 1) == 1)
			sb.append("定位").append(SIGN);
		else
			sb.append("未定位").append(SIGN);
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

	// 碰撞报警 ---碰撞快照参数
	public class INFO_02 extends Json {
		// 车速 WORD 碰着前车速,单位：0.1km/h
		public int speed;
		// 转速 WORD 数值范围：0 - 30000rpm
		public int rpm;
		// 碰撞前5s时车辆转向 BYTE 0x00:无；0x01:左；0x02:右
		public byte turn5s;
		// 碰撞前5s时车辆转角 WORD 单位：度
		public int angle5s;
		// 碰撞前4s时车辆转向 BYTE 0x00:无；0x01:左；0x02:右
		public byte turn4s;
		// 碰撞前4s时车辆转角 WORD 单位：度
		public int angle4s;
		// 碰撞前3s时车辆转向 BYTE 0x00:无；0x01:左；0x02:右
		public byte turn3s;
		// 碰撞前3s时车辆转角 WORD 单位：度
		public int angle3s;
		// 碰撞前2s时车辆转向 BYTE 0x00:无；0x01:左；0x02:右
		public byte turn2s;
		// 碰撞前2s时车辆转角 WORD 单位：度
		public int angle2s;
		// 碰撞前1s时车辆转向 BYTE 0x00:无；0x01:左；0x02:右
		public byte turn1s;
		// 碰撞前1s时车辆转角 WORD 单位：度
		public int angle1s;
		// 安全气囊是否打开 BYTE 0x00：未打开，0x01：打开
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
		// 0x01 设备断电报警 无
		// 0x02 碰撞报警 进行碰撞快照参数见表43。
		// 0x03 超速报警 超速参数见表44。
		// 0x04 怠速报警 怠速参数见表45。
		// 0x05 故障码报警 无
		// 0x06 长时间连续驾驶报警 无
		// 0x07 电子围栏 无
		// 0x08 保养提醒报警 无
		// 0x09 驻车时车身状态报警 无
		// 0x0A 冷却液温度过高报警 冷却液参数见表47
		// 0x0B 设备换车报警 换车参数见表48
		// 0x0C 蓄电池电压过低报警 蓄电池电压参数见表49

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
