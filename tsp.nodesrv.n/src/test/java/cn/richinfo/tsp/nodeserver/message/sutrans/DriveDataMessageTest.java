package cn.richinfo.tsp.nodeserver.message.sutrans;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.richinfo.tsp.nodeserver.MessageBaseTest;
import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;
import cn.richinfo.tsp.nodeserver.utils.SignUtils;

/**
 * @ClassName: DriveDataMessageTest
 * @Description: TODO()
 * @author ÍõÈ«
 * @date 2014-11-20 ÉÏÎç8:37:34
 */
public class DriveDataMessageTest extends MessageBaseTest {
	DriveDataMessage message = new DriveDataMessage();

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		copyHead(message);
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
		//tea
		message.setBodyProp(SignUtils.getUnsingedShort(ConversionUtil
				.getBodyPropety(false, true, 0)));
		message.setBaseSecret("aaaaaaaaaaaaaaaa");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.richinfo.tsp.nodeserver.MessageBaseTest#testWrite()
	 */
	@Override
	public void testWrite() throws Exception {
		//byte[] by = message.write();
		//assertEquals(by.length, message.getMessageLength());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.richinfo.tsp.nodeserver.MessageBaseTest#testRead()
	 */
	@Override
	public void testRead() throws Exception {
		byte[] by = message.write();
		DriveDataMessage _message = new DriveDataMessage();
		int pos = _message.read(by);
		//assertEquals(_message.getMessageLength(), pos);

		log.info(_message.toString());
		log.info(message.toString());
	}

}
