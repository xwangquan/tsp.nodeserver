package cn.richinfo.tsp.nodeserver.message.sutrans;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;

/**
 * Dummy for mock DriveCustomMessage
 * 
 * @ClassName: DummyDriveCustomMessage
 * @Description:
 * @author 王全
 * @date 2014-11-27 下午5:49:01
 */
public class DummyDriveCustomMessage extends DriveCustomMessage {

	public DummyDriveCustomMessage() {
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
		setTime(ConversionUtil.long2bcd(Long.parseLong(_time)));
		type = 0;
		driving_event_type = 65530;
		setAcceleration(new byte[] { 1, 2 });
		setDeceleration(new byte[] { 5, 8 });
		// suddenTurnB
		suddenTurnB suddenTurn = new suddenTurnB();
		suddenTurn.acceleration = 111;
		suddenTurn.palstance = 80;
		suddenTurn.turn_direction = 1;
		setSuddenTurn(suddenTurn);
		// highSpeedB
		highSpeedB highSpeed = new highSpeedB();
		highSpeed.duration = 155;
		highSpeed.rpm = 36;
		highSpeed.type = 1;
		setHighSpeed(highSpeed);
		// speedNotMatchB
		speedNotMatchB speedNotMatch = new speedNotMatchB();
		speedNotMatch.duration = 135;
		speedNotMatch.rpm = 38;
		speedNotMatch.speed = 55;
		speedNotMatch.type = 1;
		setSpeedNotMatch(speedNotMatch);
		// longDurationB
		longDurationB longDuration = new longDurationB();
		longDuration.duration = 120;
		longDuration.type = 1;
		setLongDuration(longDuration);
		// slidingB
		slidingB sliding = new slidingB();
		sliding.duration = 120;
		sliding.type = 1;
		sliding.speed = 60;
		setSliding(sliding);
		fatigue_driving_time = 88;

	}

	public static DriveCustomMessage getMessage() {
		DriveCustomMessage message = new DriveCustomMessage();
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
		message.fatigue_driving_time = 88;
		return message;
	}
}
