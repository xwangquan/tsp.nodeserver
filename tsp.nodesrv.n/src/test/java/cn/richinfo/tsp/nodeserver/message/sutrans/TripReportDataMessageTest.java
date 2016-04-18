package cn.richinfo.tsp.nodeserver.message.sutrans;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.richinfo.tsp.nodeserver.MessageBaseTest;
import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;
import cn.richinfo.tsp.nodeserver.utils.SignUtils;

/**
 * 
 * @ClassName: TripReportDataMessageTest
 * @Description: TODO()
 * @author ÍõÈ«
 * @date 2014-11-27 ÏÂÎç4:22:27
 */
public class TripReportDataMessageTest extends MessageBaseTest {
	TripReportDataMessage message = new TripReportDataMessage();

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		copyHead(message);
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
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.richinfo.tsp.nodeserver.MessageBaseTest#testWrite()
	 */
	@Override
	public void testWrite() throws Exception {
		byte[] by = message.write();
		assertEquals(by.length, message.getMessageLength());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.richinfo.tsp.nodeserver.MessageBaseTest#testRead()
	 */
	@Override
	public void testRead() throws Exception {
		byte[] by = message.write();
		TripReportDataMessage _message = new TripReportDataMessage();
		int pos = _message.read(by);
		assertEquals(_message.getMessageLength(), pos);

		log.info(_message.toString());
		log.info(message.toString());
	}

}
