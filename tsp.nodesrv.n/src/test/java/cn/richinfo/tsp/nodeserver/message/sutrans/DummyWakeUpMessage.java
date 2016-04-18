package cn.richinfo.tsp.nodeserver.message.sutrans;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;

/**
 * Dummy for mock WakeUpMessage
 * 
 * @ClassName: DummyWakeUpMessage
 * @Description:
 * @author ��ȫ
 * @date 2014-11-27 ����5:49:01
 */
public class DummyWakeUpMessage extends WakeUpMessage {

	public DummyWakeUpMessage() {
		String TUID = "wangquan obd test";
		// message.setBodyProp(new byte[2]);
		setTermType((byte) 1);
		setSequenceNumber(1);
		setSession("Sessions!!Sessions!!Sessions!!Sessions!!".getBytes());
		setVersion("aa".getBytes());
		setMsgPack(new byte[4]);// ȱʡ
		setMessageLength(57);
		setHeadLength(91);
		setTuid(TUID);
		// body-----------------------------------------
		// for bcd time
		SimpleDateFormat sdfShortTime = new SimpleDateFormat("yyMMddhhmmss");
		String _time = sdfShortTime.format(new Date());
		setTime(ConversionUtil.long2bcd(Long.parseLong(_time)));
		type = 0;
		// 37.4295815873,118.1688418409
		lattitude = 37429581;
		longitude = 118418409;
		batteryVoltage = 630;
		duration = 555;

	}

	public static WakeUpMessage getMessage() {
		WakeUpMessage message = new WakeUpMessage();
		String TUID = "wangquan obd test";
		// message.setBodyProp(new byte[2]);
		message.setTermType((byte) 1);
		message.setSequenceNumber(1);
		message.setSession("Sessions!!Sessions!!Sessions!!Sessions!!"
				.getBytes());
		message.setVersion("aa".getBytes());
		message.setMsgPack(new byte[4]);// ȱʡ
		message.setMessageLength(57);
		message.setHeadLength(91);
		message.setTuid(TUID);
		// for bcd time
		SimpleDateFormat sdfShortTime = new SimpleDateFormat("yyMMddhhmmss");
		String _time = sdfShortTime.format(new Date());
		message.setTime(ConversionUtil.long2bcd(Long.parseLong(_time)));
		message.type = 0;
		// 37.4295815873,118.1688418409
		message.lattitude = 37429581;
		message.longitude = 118418409;
		message.batteryVoltage = 630;
		message.duration = 555;
		return message;
	}
}
