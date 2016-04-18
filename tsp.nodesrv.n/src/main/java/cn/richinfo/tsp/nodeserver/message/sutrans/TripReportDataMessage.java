package cn.richinfo.tsp.nodeserver.message.sutrans;

import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;
import cn.richinfo.tsp.nodeserver.utils.SignUtils;

/**
 * @ClassName: TripReportDataMessage
 * @Description: OBD行程报告
 * @author 王全
 * @date 2014-11-19 下午4:23:35
 */
public class TripReportDataMessage extends UploadTransparentTransmissionMessage {

	// 开始时间 BCD[6] 时间格式：YYMMDDhhmmss
	public byte[] start_time = new byte[6];
	// 结束时间 BCD[6] 时间格式：YYMMDDhhmmss
	public byte[] end_time = new byte[6];
	// 上传类型 BYTE 0：主动上传；1：查询上传
	public byte type;
	// 总里程 BYTE[2] 从点火到熄火（或熄火前的某记录时间点）的本次行程总里程（重新插拔后清零）,单位：0.1km
	public byte[] trip_mileage = new byte[2];

	// 累计行驶里程 BYTE[4] 此车辆插上终端至今的累计行驶里程（重新插拔后清零），单位：0.1km
	public byte[] total_mileage = new byte[4];

	// 总耗油量 BYTE[2] 从点火到熄火（或熄火前的某记录时间点）的本次行程总耗油量（重新插拔后清零），单位：0.1L
	public byte[] trip_fuel_consumption = new byte[2];

	// 累计耗油量 BYTE[4] 此车辆插上终端至今的累计耗油量（重新插拔后清零）,单位：0.1L
	public byte[] total_fuel_consumption = new byte[4];

	// 平均油耗 BYTE[2] 本次行程平均油耗,单位：0.1L/100km
	public byte[] avg_fuel_consumption = new byte[2];

	// 超速时长 BYTE[4] 本次行程超速时长：单位：秒
	public byte[] overspeed_time = new byte[4];

	// 发动机高转速次数 BYTE[2] 本次行程发动机高转速次数，高转速阀值可配置
	public byte[] engine_highspeed_times = new byte[2];

	// 高转速行驶时长 BYTE[4] 本次行程高转速行驶时长：单位：秒
	public byte[] highspeed_driving_time = new byte[4];

	// 超长怠速次数 BYTE[2] 本次行程超长怠速次数，超长怠速时间阀值可配置
	public byte[] long_idle_times = new byte[2];

	// 怠速总时长 BYTE[4] 本次行程怠速总时长（本次怠速时间），单位：秒
	public byte[] total_idle_time = new byte[4];

	// 怠速总耗油量 BYTE[2] 本次行程怠速总耗油量:单位0.1L
	public byte[] total_idle_consumption = new byte[2];

	// 疲劳驾驶总时长 BYTE[4] 本次行程疲劳驾驶总时长：单位秒
	public byte[] total_fatigue_duration = new byte[4];

	// 平均车速 BYTE[2] 本次行程平均车速,单位：0.1km/h
	public byte[] avg_speed = new byte[2];

	// 最大车速 BYTE[2] 本次行程最大车速，单位：0.1km/h
	public byte[] max_speed = new byte[2];

	// 最高转速 BYTE[2] 本次行程最高转速，单位：RPM ////////////////////////where is db?
	public byte[] max_4otationl = new byte[2];

	// 发动机最高水温 BYTE[2] 本次行程发动机最高水温，单位：℃，有符号整数
	public byte[] max_water_temperature = new byte[2];

	// --inner
	private float UNIT = 0.1f;

	private int SUBLEN = 57;

	public TripReportDataMessage() {
		super(SuTransTypes.TRIPREPORT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.richinfo.tsp.nodeserver.message.sutrans.
	 * UploadTransparentTransmissionMessage#hei(byte[], int)
	 */
	@Override
	public int hei(byte[] receivedData, int offset) throws Exception {
		System.arraycopy(receivedData, offset, start_time, 0, 6);
		System.arraycopy(receivedData, offset + 6, end_time, 0, 6);
		type = receivedData[offset + 12];
		System.arraycopy(receivedData, offset + 13, trip_mileage, 0, 2);
		System.arraycopy(receivedData, offset + 15, total_mileage, 0, 4);
		System.arraycopy(receivedData, offset + 19, trip_fuel_consumption, 0, 2);
		System.arraycopy(receivedData, offset + 21, total_fuel_consumption, 0,
				4);
		System.arraycopy(receivedData, offset + 25, avg_fuel_consumption, 0, 2);
		System.arraycopy(receivedData, offset + 27, overspeed_time, 0, 4);
		System.arraycopy(receivedData, offset + 31, engine_highspeed_times, 0,
				2);
		System.arraycopy(receivedData, offset + 33, highspeed_driving_time, 0,
				4);
		System.arraycopy(receivedData, offset + 37, long_idle_times, 0, 2);
		System.arraycopy(receivedData, offset + 39, total_idle_time, 0, 4);
		System.arraycopy(receivedData, offset + 43, total_idle_consumption, 0,
				2);
		System.arraycopy(receivedData, offset + 45, total_fatigue_duration, 0,
				4);
		System.arraycopy(receivedData, offset + 49, avg_speed, 0, 2);
		System.arraycopy(receivedData, offset + 51, max_speed, 0, 2);
		System.arraycopy(receivedData, offset + 53, max_4otationl, 0, 2);
		System.arraycopy(receivedData, offset + 55, max_water_temperature, 0, 2);
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
		System.arraycopy(start_time, 0, send, 0, 6);
		System.arraycopy(end_time, 0, send, 6, 6);
		send[12] = type;
		System.arraycopy(trip_mileage, 0, send, 13, 2);
		System.arraycopy(total_mileage, 0, send, 15, 4);
		System.arraycopy(trip_fuel_consumption, 0, send, 19, 2);
		System.arraycopy(total_fuel_consumption, 0, send, 21, 4);
		System.arraycopy(avg_fuel_consumption, 0, send, 25, 2);
		System.arraycopy(overspeed_time, 0, send, 27, 4);
		System.arraycopy(engine_highspeed_times, 0, send, 31, 2);
		System.arraycopy(highspeed_driving_time, 0, send, 33, 4);
		System.arraycopy(long_idle_times, 0, send, 37, 2);
		System.arraycopy(total_idle_time, 0, send, 39, 4);
		System.arraycopy(total_idle_consumption, 0, send, 43, 2);
		System.arraycopy(total_fatigue_duration, 0, send, 45, 4);
		System.arraycopy(avg_speed, 0, send, 49, 2);
		System.arraycopy(max_speed, 0, send, 51, 2);
		System.arraycopy(max_4otationl, 0, send, 53, 2);
		System.arraycopy(max_water_temperature, 0, send, 55, 2);
		return send;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString()).append("],BODY[");
		sb.append("start_time=").append(getStart_time()).append("| ");
		sb.append("end_time=").append(getEnd_time()).append("| ");
		sb.append("type=").append(getType()).append("| ");
		sb.append("trip_mileage=").append(getTrip_mileage()).append("| ");
		sb.append("total_mileage=").append(getTotal_mileage()).append("| ");
		sb.append("trip_fuel_consumption=").append(getTrip_fuel_consumption())
				.append("| ");
		sb.append("total_fuel_consumption=")
				.append(getTotal_fuel_consumption()).append("| ");
		sb.append("avg_fuel_consumption=").append(getAvg_fuel_consumption())
				.append("| ");
		sb.append("overspeed_time=").append(getOverspeed_time()).append("| ");
		sb.append("engine_highspeed_times=")
				.append(getEngine_highspeed_times()).append("| ");
		sb.append("highspeed_driving_time=")
				.append(getHighspeed_driving_time()).append("| ");
		sb.append("long_idle_times=").append(getLong_idle_times()).append("| ");
		sb.append("total_idle_time=").append(getTotal_idle_time()).append("| ");
		sb.append("total_idle_consumption=")
				.append(getTotal_idle_consumption()).append("| ");
		sb.append("total_fatigue_duration=")
				.append(getTotal_fatigue_duration()).append("| ");
		sb.append("avg_speed=").append(getAvg_speed()).append("| ");
		sb.append("max_speed=").append(getMax_speed()).append("| ");
		sb.append("max_4otationl=").append(getMax_4otationl()).append("| ");
		sb.append("max_water_temperature=").append(getMax_water_temperature())
				.append("]}");
		return sb.toString();
	}

	// -----------------------------------------------------------get/set method

	public long getStart_time() {
		return ConversionUtil.bcd2long(start_time);
	}

	public void setStart_time(byte[] start_time) {
		this.start_time = start_time;
	}

	public long getEnd_time() {
		return ConversionUtil.bcd2long(end_time);
	}

	public void setEnd_time(byte[] end_time) {
		this.end_time = end_time;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public float getTrip_mileage() {
		return SignUtils.getUnsignedByte(ConversionUtil
				.byte2short(trip_mileage)) * UNIT;
	}

	public void setTrip_mileage(byte[] trip_mileage) {
		this.trip_mileage = trip_mileage;
	}

	public float getTotal_mileage() {
		return SignUtils
				.getUnsignedIntt(ConversionUtil.byte2int(total_mileage)) * UNIT;
	}

	public void setTotal_mileage(byte[] total_mileage) {
		this.total_mileage = total_mileage;
	}

	public float getTrip_fuel_consumption() {
		return SignUtils.getUnsignedByte(ConversionUtil
				.byte2short(trip_fuel_consumption)) * UNIT;
	}

	public void setTrip_fuel_consumption(byte[] trip_fuel_consumption) {
		this.trip_fuel_consumption = trip_fuel_consumption;
	}

	public float getTotal_fuel_consumption() {
		return SignUtils.getUnsignedIntt(ConversionUtil
				.byte2int(total_fuel_consumption)) * UNIT;
	}

	public void setTotal_fuel_consumption(byte[] total_fuel_consumption) {
		this.total_fuel_consumption = total_fuel_consumption;
	}

	public float getAvg_fuel_consumption() {
		return SignUtils.getUnsignedByte(ConversionUtil
				.byte2short(avg_fuel_consumption)) * UNIT;
	}

	public void setAvg_fuel_consumption(byte[] avg_fuel_consumption) {
		this.avg_fuel_consumption = avg_fuel_consumption;
	}

	public long getOverspeed_time() {
		return SignUtils.getUnsignedIntt(ConversionUtil
				.byte2int(overspeed_time));
	}

	public void setOverspeed_time(byte[] overspeed_time) {
		this.overspeed_time = overspeed_time;
	}

	public int getEngine_highspeed_times() {
		return SignUtils.getUnsignedByte(ConversionUtil
				.byte2short(engine_highspeed_times));
	}

	public void setEngine_highspeed_times(byte[] engine_highspeed_times) {
		this.engine_highspeed_times = engine_highspeed_times;
	}

	public long getHighspeed_driving_time() {
		return SignUtils.getUnsignedIntt(ConversionUtil
				.byte2int(highspeed_driving_time));
	}

	public void setHighspeed_driving_time(byte[] highspeed_driving_time) {
		this.highspeed_driving_time = highspeed_driving_time;
	}

	public int getLong_idle_times() {
		return SignUtils.getUnsignedByte(ConversionUtil
				.byte2short(long_idle_times));
	}

	public void setLong_idle_times(byte[] long_idle_times) {
		this.long_idle_times = long_idle_times;
	}

	public long getTotal_idle_time() {
		return SignUtils.getUnsignedIntt(ConversionUtil
				.byte2int(total_idle_time));
	}

	public void setTotal_idle_time(byte[] total_idle_time) {
		this.total_idle_time = total_idle_time;
	}

	public float getTotal_idle_consumption() {
		return SignUtils.getUnsignedByte(ConversionUtil
				.byte2short(total_idle_consumption)) * UNIT;
	}

	public void setTotal_idle_consumption(byte[] total_idle_consumption) {
		this.total_idle_consumption = total_idle_consumption;
	}

	public long getTotal_fatigue_duration() {
		return SignUtils.getUnsignedIntt(ConversionUtil
				.byte2int(total_fatigue_duration));
	}

	public void setTotal_fatigue_duration(byte[] total_fatigue_duration) {
		this.total_fatigue_duration = total_fatigue_duration;
	}

	public float getAvg_speed() {
		return SignUtils.getUnsignedByte(ConversionUtil.byte2short(avg_speed))
				* UNIT;
	}

	public void setAvg_speed(byte[] avg_speed) {
		this.avg_speed = avg_speed;
	}

	public float getMax_speed() {
		return SignUtils.getUnsignedByte(ConversionUtil.byte2short(max_speed))
				* UNIT;
	}

	public void setMax_speed(byte[] max_speed) {
		this.max_speed = max_speed;
	}

	public int getMax_4otationl() {
		return SignUtils.getUnsignedByte(ConversionUtil
				.byte2short(max_4otationl));
	}

	public void setMax_4otationl(byte[] max_4otationl) {
		this.max_4otationl = max_4otationl;
	}

	public int getMax_water_temperature() {
		return ConversionUtil.byte2short(max_water_temperature);
	}

	public void setMax_water_temperature(byte[] max_water_temperature) {
		this.max_water_temperature = max_water_temperature;
	}

}
