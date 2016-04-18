package cn.richinfo.tsp.nodeserver.handler;

import io.netty.channel.Channel;
import cn.richinfo.tsp.nodeserver.entrance.NodeServerContext;
import cn.richinfo.tsp.nodeserver.message.HeadMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.CarDiagnosisMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.CarStatusMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.DriveCustomMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.DriveDataMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.ObdFaultCodeMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.ObdLocationMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.ObdLocationMileageMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.ObdStatusMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.TripReportDataMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.UploadTransparentTransmissionMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.WakeUpMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.WarnDataMessage;
import cn.richinfo.tsp.nodeserver.web.DiagnosisFacade;

/**
 * All user event
 * 
 * @ClassName: NodeServerProcessor
 * @Description:
 * @author ÍõÈ«
 * @date 2014-7-31 ÏÂÎç5:11:03
 */
public class NodeServerProcessor extends ServerProcessor {

	// name server context
	private NodeServerContext context;

	public NodeServerProcessor(NodeServerContext context) {
		this.context = context;
	}

	@Override
	public HeadMessage processTTReq(UploadTransparentTransmissionMessage req,
			Channel session) {
		// session.attr(
		// AttributeKey.valueOf(SessionStatusItem.MESSAGEQUEE.getName()))
		// .set(new DriveCustomQueen<DriveCustomMessage>());
		return selector(req, session);

	}

	// done
	public HeadMessage processDriveData(DriveDataMessage req, Channel session) {
		// log.info(req.toString());
		// req.toString();
		context.getDrive().offer(req);
		return getPlatformRespondMessage(req).setResult((byte) 0);
	}

	// done
	public HeadMessage processTripReportData(TripReportDataMessage req,
			Channel session) {
		// log.info(req.toString());
		// req.toString();
		context.getTripReport().offer(req);
		return getPlatformRespondMessage(req).setResult((byte) 0);
	}

	// done
	public HeadMessage processObdFaultCode(ObdFaultCodeMessage req,
			Channel session) {
		log.info(req.toString());
		// req.toString();
		context.getObdFaultCode().offer(req);
		return getPlatformRespondMessage(req).setResult((byte) 0);
	}

	// done
	public HeadMessage processObdLocation(ObdLocationMessage req,
			Channel session) {
		log.info(req.toString());
		// req.toString();
		context.getObdLocation().offer(req);
		return getPlatformRespondMessage(req).setResult((byte) 0);
	}

	// done
	public HeadMessage processDriveCustom(DriveCustomMessage req,
			Channel session) {
		log.info(req.toString());
		// req.toString();
		context.getDriveCustom().offer(req);
		return getPlatformRespondMessage(req).setResult((byte) 0);
	}

	// done
	public HeadMessage processCarStatus(CarStatusMessage req, Channel session) {
		// log.info(req.toString());
		// req.toString();
		context.getCarStatus().offer(req);
		return getPlatformRespondMessage(req).setResult((byte) 0);
	}

	// done
	public HeadMessage processWarnData(WarnDataMessage req, Channel session) {
		log.info(req.toString());
		// req.toString();
		context.getWarnData().offer(req);
		return getPlatformRespondMessage(req).setResult((byte) 0);
	}

	public HeadMessage processObdMileageLocation(ObdLocationMileageMessage req,
			Channel session) {
		// log.info(req.toString());
		// req.toString();
		context.getObdLocationMileage().offer(req);
		return getPlatformRespondMessage(req).setResult((byte) 0);
	}

	// done
	public HeadMessage processWakeup(WakeUpMessage req, Channel session) {
		log.info(req.toString());
		// req.toString();
		context.getWakeUp().offer(req);
		return getPlatformRespondMessage(req).setResult((byte) 0);
	}

	//done
	public HeadMessage processObdStatus(ObdStatusMessage req, Channel session) {
		log.info(req.toString());
		// req.toString();
		context.getObdStatus().offer(req);
		return getPlatformRespondMessage(req).setResult((byte) 0);
	}

	public HeadMessage processCarDiagnosis(CarDiagnosisMessage req,
			Channel session) {
		log.info(req.toString());
		String codes = req.getCodes();
		// start diagnosis
		String result = DiagnosisFacade.get(codes);
		if (result == null) {
			log.info(req.getTuid().trim() + " diagnosis faild!");
			return getPlatformRespondMessage(req).setResult((byte) 1);
		}
		// flush to hbase
		log.info(result);
		return getPlatformRespondMessage(req).setResult((byte) 0);
	}

	private HeadMessage selector(UploadTransparentTransmissionMessage req,
			Channel session) {
		switch (req.getTtType().getType()) {
		case 0x00:
			return processDriveData((DriveDataMessage) req, session);
		case 0x01:
			return processTripReportData((TripReportDataMessage) req, session);
		case 0x02:
			return processObdFaultCode((ObdFaultCodeMessage) req, session);
		case 0x03:
			return processObdLocation((ObdLocationMessage) req, session);
		case 0x04:
			return processDriveCustom((DriveCustomMessage) req, session);
		case 0x05:
			return processCarStatus((CarStatusMessage) req, session);
		case 0x06:
			return processWarnData((WarnDataMessage) req, session);
		case 0x07:
			return processWakeup((WakeUpMessage) req, session);
		case 0x08:
			return processObdStatus((ObdStatusMessage) req, session);
		case 0x09:
			return processCarDiagnosis((CarDiagnosisMessage) req, session);
		case 0x10:
			return processObdMileageLocation((ObdLocationMileageMessage) req,
					session);
		default:
			break;
		}
		return null;
	}
}
