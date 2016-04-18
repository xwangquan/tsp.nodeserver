package cn.richinfo.tsp.nodeserver.message.sutrans;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.richinfo.tsp.nodeserver.MessageBaseTest;
import cn.richinfo.tsp.nodeserver.message.sutrans.DriveCustomMessage.highSpeedB;
import cn.richinfo.tsp.nodeserver.message.sutrans.DriveCustomMessage.longDurationB;
import cn.richinfo.tsp.nodeserver.message.sutrans.DriveCustomMessage.slidingB;
import cn.richinfo.tsp.nodeserver.message.sutrans.DriveCustomMessage.speedNotMatchB;
import cn.richinfo.tsp.nodeserver.message.sutrans.DriveCustomMessage.suddenTurnB;
import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;

/**
 * @ClassName: DriveDataMessageTest
 * @Description: DriveCustomMessage
 * @author ÍõÈ«
 * @date 2014-11-20 ÉÏÎç8:37:34
 */
public class DriveCustomMessageTest extends MessageBaseTest {
	DriveCustomMessage message = new DriveCustomMessage();

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		copyHead(message);
		// for bcd time
		SimpleDateFormat sdfShortTime = new SimpleDateFormat("yyMMddhhmmss");
		String _time = sdfShortTime.format(new Date());
		message.setTime(ConversionUtil.long2bcd(Long.parseLong(_time)));
		message.type = 0;
		message.driving_event_type = 65530;
		message.setAcceleration(new byte[] { 1, 2 });
		message.setDeceleration(new byte[] { 5, 8 });
		// suddenTurnB
		suddenTurnB suddenTurn = message.new suddenTurnB();
		suddenTurn.acceleration = 111;
		suddenTurn.palstance = 80;
		suddenTurn.turn_direction = 1;
		message.setSuddenTurn(suddenTurn);
		// highSpeedB
		highSpeedB highSpeed = message.new highSpeedB();
		highSpeed.duration = 155;
		highSpeed.rpm = 36;
		highSpeed.type = 1;
		message.setHighSpeed(highSpeed);
		// speedNotMatchB
		speedNotMatchB speedNotMatch = message.new speedNotMatchB();
		speedNotMatch.duration = 135;
		speedNotMatch.rpm = 38;
		speedNotMatch.speed = 55;
		speedNotMatch.type = 1;
		message.setSpeedNotMatch(speedNotMatch);
		// longDurationB
		longDurationB longDuration = message.new longDurationB();
		longDuration.duration = 120;
		longDuration.type = 1;
		message.setLongDuration(longDuration);
		// slidingB
		slidingB sliding = message.new slidingB();
		sliding.duration = 120;
		sliding.type = 1;
		sliding.speed = 60;
		message.setSliding(sliding);
		message.fatigue_driving_time=88;
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
		byte[] _by = message.write();
		byte[] by = new byte[_by.length + 2];
		System.arraycopy(_by, 0, by, 0, _by.length);
		DriveCustomMessage _message = new DriveCustomMessage();
		int pos = _message.read(by);
		assertEquals(_message.getMessageLength(), pos);

		log.info(_message.toString());
		log.info(message.toString());
	}

}
