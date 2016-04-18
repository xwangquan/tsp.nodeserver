package cn.richinfo.tsp.nodeserver.message;

import org.apache.log4j.Logger;

import cn.richinfo.tsp.nodeserver.message.respond.PlatformCommonRespondMessage;
import cn.richinfo.tsp.nodeserver.message.respond.TerminalCommonRespondMessage;
import cn.richinfo.tsp.nodeserver.message.serialization.SerializationMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.CarDiagnosisMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.CarStatusMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.DriveCustomMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.DriveDataMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.MultiUploadTransparentTransmission;
import cn.richinfo.tsp.nodeserver.message.sutrans.ObdFaultCodeMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.ObdLocationMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.ObdLocationMileageMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.ObdStatusMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.PretreatmentTransparentTransmissionMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.TripReportDataMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.UploadTransparentTransmissionMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.WakeUpMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.WarnDataMessage;
import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;
import cn.richinfo.tsp.nodeserver.utils.HexConvert;
import cn.richinfo.tsp.nodeserver.utils.SignUtils;

/**
 * 
 * @ClassName: MessageProcessor
 * @Description: 分包解析
 * @author 王全
 * @date 20MARK4-8-MARK 下午3:05:37
 */
public class MessageProcessor {
	private static final Logger log = Logger.getLogger(MessageProcessor.class
			.getName());

	/**
	 * skip mark(标识位)
	 */
	private static int MARK = 1;

	/**
	 * 完整的数据包，结构为: [标识位,消息头,消息体,校验码] .
	 * 
	 * @param receivedData
	 *            bytes
	 * @param headLen
	 *            头长度(分包6MARK or 57不分包)
	 * @return
	 */
	public static HeadMessage processReceive(byte[] receivedData, int headLen)
			throws Exception {

		HeadMessage message = null;
		int offset = MARK;
		// 消息id
		int messageId = SignUtils.getUnsignedByte(ConversionUtil.byte2short(
				receivedData, offset));
		offset += 2;
		log.debug("Received message id = " + messageId + ", hexid = "
				+ HexConvert.toHex(messageId));
		switch (messageId) {
		/**
		 * heart
		 */
		case Messages.TERMINAL_HEART_REQ: {
			message = new HeartBeatMessage();
			message.setHeadLength(headLen);
			message.read(receivedData, MARK);
			break;
		}
		/**
		 * common respond
		 */
		case Messages.COMMON_PLANTFORM_RESP: {
			message = new PlatformCommonRespondMessage();
			message.setHeadLength(headLen);
			message.read(receivedData, MARK);
			break;
		}
		case Messages.COMMON_TERMINAL_RESP: {
			message = new TerminalCommonRespondMessage();
			message.setHeadLength(headLen);
			message.read(receivedData, MARK);
			break;
		}
		/**
		 * for serialization
		 */
		case Messages.SERVER_OBJECT_SERIALIZE_MESSAGE: {
			message = new SerializationMessage();
			message.setHeadLength(headLen);
			message.read(receivedData, MARK);
			break;
		}
		/**
		 * for tt upload
		 */
		case Messages.TT_UPLOAD_REQ: {
			// first decrypt
			int type = PretreatmentTransparentTransmissionMessage.getType(
					receivedData, MARK);
			int _ttType = -1;
			if (type == -100) {//no need decreypt
				_ttType = SignUtils.getUnsignedByte(ConversionUtil.byte2short(
						receivedData, headLen + MARK + 1));
			} else {
				_ttType = type;
			}
			log.debug("inner type ="+_ttType);
			message = selector(_ttType);
			if (message == null)
				return null;
			message.setHeadLength(headLen);
			message.read(receivedData, MARK);
			break;
		}
		case Messages.MULTI_UPLOAD_REQ: {
			log.info("Unsupport warning!");
			return null;
			//message = new MultiUploadTransparentTransmission();
			//message.setHeadLength(headLen);
			//message.read(receivedData, MARK);
			//break;
		}
		default:
			message = new EchoMessage();
		}
		return message;
	}

	private static UploadTransparentTransmissionMessage selector(int _ttType) {
		switch (_ttType) {
		case 0x00:
			return new DriveDataMessage();
		case 0x01:
			return new TripReportDataMessage();
		case 0x02:
			return new ObdFaultCodeMessage();
		case 0x03:
			return new ObdLocationMessage();
		case 0x04:
			return new DriveCustomMessage();
		case 0x05:
			return new CarStatusMessage();
		case 0x06:
			return new WarnDataMessage();
		case 0x07:
			return new WakeUpMessage();
		case 0x08:
			return new ObdStatusMessage();
		case 0x09:
			return new CarDiagnosisMessage();
		case 0x10:
			return new ObdLocationMileageMessage();
		default:
			break;
		}
		return null;
	}
}
