package cn.richinfo.tsp.nodeserver.message.sutrans;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;
import cn.richinfo.tsp.nodeserver.utils.SignUtils;

/**
 * DummyTripReportDataMessage
 * 
 * @ClassName: DummyTripReportDataMessage
 * @Description: Dummy for mock DummyTripReportDataMessage
 * @author 王全
 * @date 2014-11-27 下午5:01:43
 */
public class DummyTripReportDataMessage extends TripReportDataMessage {

	public DummyTripReportDataMessage() {
		String TUID = "wangquan obd test";
		// message.setBodyProp(new byte[2]);
		setTermType((byte) 1);
		setSequenceNumber(1);
		setSession("Sessions!!Sessions!!Sessions!!Sessions!!".getBytes());
		setVersion("aa".getBytes());
		setMsgPack(new byte[4]);// 缺省
		setMessageLength(57);
		setHeadLength(91);
		setTuid(TUID);
		// body-----------------------------------------
		// for bcd time
		SimpleDateFormat sdfShortTime = new SimpleDateFormat("yyMMddhhmmss");
		String _time = sdfShortTime.format(new Date());
		setStart_time(ConversionUtil.long2bcd(Long.parseLong(_time)));
		setEnd_time(ConversionUtil.long2bcd(Long.parseLong(_time)));
		type = 1;
		setTrip_mileage(SignUtils.getUnsingedShort(505));
		setTotal_mileage(ConversionUtil.int2byte(100345));
		setTrip_fuel_consumption(SignUtils.getUnsingedShort(8883));
		setTotal_fuel_consumption(ConversionUtil.int2byte(385));
		setAvg_fuel_consumption(SignUtils.getUnsingedShort(356));
		setOverspeed_time(ConversionUtil.int2byte(999999));
		setEngine_highspeed_times(SignUtils.getUnsingedShort(28));
		setHighspeed_driving_time(ConversionUtil.int2byte(88888));
		setLong_idle_times(SignUtils.getUnsingedShort(15));
		setTotal_idle_time(ConversionUtil.int2byte(37));
		setTotal_idle_consumption(SignUtils.getUnsingedShort(88));
		setTotal_fatigue_duration(ConversionUtil.int2byte(5555));
		setAvg_speed(SignUtils.getUnsingedShort(705));
		setMax_speed(SignUtils.getUnsingedShort(1220));
		setMax_4otationl(SignUtils.getUnsingedShort(3666));
		setMax_water_temperature(ConversionUtil.short2byte(-89));
	}

	public static TripReportDataMessage getMessage() {
		TripReportDataMessage message = new TripReportDataMessage();
		String TUID = "wangquan obd test";
		// message.setBodyProp(new byte[2]);
		message.setTermType((byte) 1);
		message.setSequenceNumber(1);
		message.setSession("Sessions!!Sessions!!Sessions!!Sessions!!"
				.getBytes());
		message.setVersion("aa".getBytes());
		message.setMsgPack(new byte[4]);// 缺省
		message.setMessageLength(57);
		message.setHeadLength(91);
		message.setTuid(TUID);
		// for bcd time
		SimpleDateFormat sdfShortTime = new SimpleDateFormat("yyMMddhhmmss");
		String _time = sdfShortTime.format(new Date());
		message.setStart_time(ConversionUtil.long2bcd(Long.parseLong(_time)));
		message.setEnd_time(ConversionUtil.long2bcd(Long.parseLong(_time)));
		message.type = 1;
		message.setTrip_mileage(SignUtils.getUnsingedShort(505));
		message.setTotal_mileage(ConversionUtil.int2byte(100345));
		message.setTrip_fuel_consumption(SignUtils.getUnsingedShort(8883));
		message.setTotal_fuel_consumption(ConversionUtil.int2byte(385));
		message.setAvg_fuel_consumption(SignUtils.getUnsingedShort(356));
		message.setOverspeed_time(ConversionUtil.int2byte(999999));
		message.setEngine_highspeed_times(SignUtils.getUnsingedShort(28));
		message.setHighspeed_driving_time(ConversionUtil.int2byte(88888));
		message.setLong_idle_times(SignUtils.getUnsingedShort(15));
		message.setTotal_idle_time(ConversionUtil.int2byte(37));
		message.setTotal_idle_consumption(SignUtils.getUnsingedShort(88));
		message.setTotal_fatigue_duration(ConversionUtil.int2byte(5555));
		message.setAvg_speed(SignUtils.getUnsingedShort(705));
		message.setMax_speed(SignUtils.getUnsingedShort(1220));
		message.setMax_4otationl(SignUtils.getUnsingedShort(3666));
		message.setMax_water_temperature(ConversionUtil.short2byte(-89));
		return message;
	}
}
