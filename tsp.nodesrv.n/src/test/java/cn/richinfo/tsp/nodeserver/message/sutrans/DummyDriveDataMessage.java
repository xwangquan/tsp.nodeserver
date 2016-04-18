package cn.richinfo.tsp.nodeserver.message.sutrans;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;
import cn.richinfo.tsp.nodeserver.utils.SignUtils;

/**
 * 
 * @ClassName: DummyDriveDataMessage
 * @Description: Dummy for mock DriveDataMessage!
 * @author 王全
 * @date 2014-11-20 上午11:31:09
 */
public class DummyDriveDataMessage extends DriveDataMessage {

	private DriveDataMessage message = new DriveDataMessage();

	public DummyDriveDataMessage() {
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
		// body-----------------------------------------
		SimpleDateFormat sdfShortTime = new SimpleDateFormat("yyMMddhhmmss");
		String _time = sdfShortTime.format(new Date());
		setTime(ConversionUtil.long2bcd(Long.parseLong(_time)));
		type = 1;
		obdWarn = ConversionUtil.int2byte(10);
		obdStatus = ConversionUtil.int2byte(0x80000000);
		speed = SignUtils.getUnsingedShort(125);
		engineRotationRate = SignUtils.getUnsingedShort(2500);
		batteryVoltage = SignUtils.getUnsingedShort(35);
		totalMileage = ConversionUtil.int2byte(555);
		idleConsumption = SignUtils.getUnsingedShort(15);
		fuelConsumption = SignUtils.getUnsingedShort(44);
		engineLoad = 80;
		waterTemperature = ConversionUtil.short2byte(-25);
		fuelPressure = SignUtils.getUnsingedShort(888);
		manifoldPressure = SignUtils.getUnsingedShort(435);
		inletTemperature = ConversionUtil.short2byte(-40);
		inletTlow = SignUtils.getUnsingedShort(666);
		tthrottlePosition = 55;
		status = 1;
		hourGallon = SignUtils.getUnsingedShort(8);
		pedalPosition = SignUtils.getUnsingedShort(333);
		pedalStatus = 3;
		restOil = SignUtils.getUnsingedShort(34);
		
		//for tea test
		message.setBodyProp(SignUtils.getUnsingedShort(ConversionUtil
				.getBodyPropety(false, true, 0)));
		message.setBaseSecret("aaaaaaaaaaaaaaaa");
	}

	public static DriveDataMessage getMessage() {
		DriveDataMessage message = new DummyDriveDataMessage();
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
		message.setTime(ConversionUtil.long2bcd(Long.parseLong(_time)));
		message.type = 1;
		message.obdWarn = ConversionUtil.int2byte(10);
		message.obdStatus = ConversionUtil.int2byte(5);
		message.speed = SignUtils.getUnsingedShort(125);
		message.engineRotationRate = SignUtils.getUnsingedShort(2500);
		message.batteryVoltage = SignUtils.getUnsingedShort(35);
		message.totalMileage = ConversionUtil.int2byte(555);
		message.idleConsumption = SignUtils.getUnsingedShort(15);
		message.fuelConsumption = SignUtils.getUnsingedShort(44);
		message.engineLoad = 80;
		message.waterTemperature = ConversionUtil.short2byte(-25);
		message.fuelPressure = SignUtils.getUnsingedShort(888);
		message.manifoldPressure = SignUtils.getUnsingedShort(435);
		message.inletTemperature = ConversionUtil.short2byte(-40);
		message.inletTlow = SignUtils.getUnsingedShort(666);
		message.tthrottlePosition = 55;
		message.status = 1;
		message.hourGallon = SignUtils.getUnsingedShort(8);
		message.pedalPosition = SignUtils.getUnsingedShort(333);
		message.pedalStatus = 3;
		message.restOil = SignUtils.getUnsingedShort(34);
		return message;
	}
}
