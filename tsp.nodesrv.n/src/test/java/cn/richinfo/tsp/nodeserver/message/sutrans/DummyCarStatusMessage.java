package cn.richinfo.tsp.nodeserver.message.sutrans;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;
import cn.richinfo.tsp.nodeserver.utils.SignUtils;

/**
 * Dummy for mock CarStatusMessage
 * 
 * @ClassName: DummyCarStatusMessage
 * @Description:
 * @author 王全
 * @date 2014-11-27 下午5:49:01
 */
public class DummyCarStatusMessage extends CarStatusMessage {

	public DummyCarStatusMessage() {
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
		type = 1;
		a = 83;
		b = 110;
		c = 35;
		d = -112;
		e = -50;
		f = 96;
		g = -15;
		h = 17;
		i = 3;
		j = 50;
		k = 120;
		
		//for tea test
		setBodyProp(SignUtils.getUnsingedShort(ConversionUtil
				.getBodyPropety(false, true, 0)));
		setBaseSecret("aaaaaaaaaaaaaaaa");
	}

	public static CarStatusMessage getMessage() {
		CarStatusMessage message = new CarStatusMessage();
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
		message.a = 83;
		message.b = 110;
		message.c = 35;
		message.d = -112;
		message.e = -50;
		message.f = 96;
		message.g = -15;
		message.h = 17;
		message.i = 3;
		message.j = 50;
		message.k = 120;
		return message;
	}
}
