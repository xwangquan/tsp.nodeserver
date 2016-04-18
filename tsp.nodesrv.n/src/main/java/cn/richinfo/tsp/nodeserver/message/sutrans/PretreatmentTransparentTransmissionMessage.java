package cn.richinfo.tsp.nodeserver.message.sutrans;

import cn.richinfo.tsp.nodeserver.message.Messages;
import cn.richinfo.tsp.nodeserver.message.SecurityMessage;
import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;
import cn.richinfo.tsp.nodeserver.utils.SignUtils;

/**
 * @ClassName: PretreatmentTransparentTransmissionMessage
 * @Description: pretreatment decrypt
 * @author ÍõÈ«
 * @date 2015-8-10 ÏÂÎç4:44:14
 */
public class PretreatmentTransparentTransmissionMessage extends SecurityMessage {

	public PretreatmentTransparentTransmissionMessage(int messageID) {
		super(messageID);
	}

	public static int getType(byte[] receivedData, int offset) throws Exception {
		PretreatmentTransparentTransmissionMessage message = new PretreatmentTransparentTransmissionMessage(
				Messages.TT_UPLOAD_REQ);
		return message.read(receivedData, offset);
	}

	@Override
	public int read(byte[] receivedData, int offset) throws Exception {
		offset = super.read(receivedData, offset);
		if (hasTea()) {
			// tea decrypt
			byte[] _body = new byte[getMessageLength() - getHeadLength()];
			System.arraycopy(receivedData, offset, _body, 0, _body.length);
			String tuid = getTuid().trim();
			// set base key,check from db;
			// setBaseSecret("");
			byte[] body = decrypt(_body);
			return getTtype(body, 0);
		}
		return -100;
	}

	private int getTtype(byte[] receivedData, int offset) throws Exception {
		int _ttType = SignUtils.getUnsignedByte(ConversionUtil.byte2short(
				receivedData, offset + 1));
		return _ttType;
	}

	@Override
	public byte[] writeBody() {
		// just ignore
		return null;
	}
}
