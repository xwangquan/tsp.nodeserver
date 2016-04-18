package cn.richinfo.tsp.nodeserver.message.sutrans;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;

/**
 * Dummy for mock WarnDataMessage
 * 
 * @ClassName: DummyWarnDataMessage
 * @Description:
 * @author 王全
 * @date 2014-11-27 下午5:49:01
 */
public class DummyWarnDataMessage extends WarnDataMessage {

	public DummyWarnDataMessage() {
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
		alarm_type = 5;
		status = 2;
		// 37.4295815873,118.1688418409
		lattitude = 37429581;
		longitude = 118418409;
		information = "afasd设备断电报警".getBytes();

	}

	public static WarnDataMessage getMessage() {
		WarnDataMessage message = new WarnDataMessage();
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
		message.alarm_type = 5;
		message.status = 2;
		// 37.4295815873,118.1688418409
		message.lattitude = 37429581;
		message.longitude = 118418409;
		message.information = "afasd设备断电报警".getBytes();

		return message;
	}
}
