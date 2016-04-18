package cn.richinfo.tsp.nodeserver.message.sutrans;

import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;
import cn.richinfo.tsp.nodeserver.utils.SignUtils;

/**
 * @ClassName: WakeUpMessage
 * @Description: 待机自唤醒数据
 * @author 王全
 * @date 2014-11-19 下午4:31:20
 */
public class WakeUpMessage extends UploadTransparentTransmissionMessage {

	// 时间 BCD[6] 时间格式：YYMMDDhhmmss
	public byte[] time = new byte[6];
	// 上传类型 0：主动上传；1：查询上传
	public byte type;
	// GPS状态 WORD GPS状态位定义见表42.
	public int gsp_status;
	// 经度 DWORD 以度为单位的纬度值乘以10的6次方，精确到百万分之一度；
	public int longitude;
	// 纬度 DWORD 以度为单位的纬度值乘以10的6次方，精确到百万分之一度；
	public int lattitude;
	// 电瓶电压 WORD 单位：0.1V
	public int batteryVoltage;
	// 熄火时长 DWORD 单位：秒
	public int duration;

	// --inner
	private int SUBLEN = 23;

	public WakeUpMessage() {
		super(SuTransTypes.WAKEUP);
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
		gsp_status = SignUtils.getUnsignedByte(ConversionUtil.byte2short(
				receivedData, offset + 7));
		longitude = ConversionUtil.byte2int(receivedData, offset + 9);
		lattitude = ConversionUtil.byte2int(receivedData, offset + 13);
		batteryVoltage = SignUtils.getUnsignedByte(ConversionUtil.byte2short(
				receivedData, offset + 17));
		duration = ConversionUtil.byte2int(receivedData, offset + 19);
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
		System.arraycopy(SignUtils.getUnsingedShort(gsp_status), 0, send, +7, 2);
		System.arraycopy(ConversionUtil.int2byte(longitude), 0, send, +9, 4);
		System.arraycopy(ConversionUtil.int2byte(lattitude), 0, send, +13, 4);
		System.arraycopy(SignUtils.getUnsingedShort(batteryVoltage), 0, send,
				+17, 2);
		System.arraycopy(ConversionUtil.int2byte(duration), 0, send, +19, 4);
		return send;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString()).append("],BODY[");
		sb.append("time=").append(getTime()).append("| ");
		sb.append("type=").append(getType()).append("| ");
		sb.append("gsp_status=").append(gsp_status).append("| ");
		sb.append("longitude=").append(longitude).append("| ");
		sb.append("lattitude=").append(lattitude).append("| ");
		sb.append("batteryVoltage=").append(getBatteryVoltage()).append("| ");
		sb.append("duration=").append(duration).append("]}");
		return sb.toString();
	}

	// ------------------------------------------------------get/set

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

	public int getGsp_status() {
		return gsp_status;
	}

	public void setGsp_status(int gsp_status) {
		this.gsp_status = gsp_status;
	}

	public int getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public int getLattitude() {
		return lattitude;
	}

	public void setLattitude(int lattitude) {
		this.lattitude = lattitude;
	}

	public float getBatteryVoltage() {
		return batteryVoltage * 0.1f;
	}

	public void setBatteryVoltage(int batteryVoltage) {
		this.batteryVoltage = batteryVoltage;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

}
