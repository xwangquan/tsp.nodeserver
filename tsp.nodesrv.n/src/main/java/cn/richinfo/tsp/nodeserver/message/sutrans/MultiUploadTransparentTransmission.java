package cn.richinfo.tsp.nodeserver.message.sutrans;

import java.util.ArrayList;

import cn.richinfo.tsp.nodeserver.message.HeadMessage;
import cn.richinfo.tsp.nodeserver.message.Messages;
import cn.richinfo.tsp.nodeserver.message.SecurityMessage;

/**
 * @ClassName: MultiUploadTransparentTransmission
 * @Description: )
 * @author 王全
 * @date 2015-10-30 下午5:44:06
 */
public class MultiUploadTransparentTransmission extends SecurityMessage {
	// 透传消息类型(id)
	private int ttID = (byte) 0xAB;
	// all tt messages
	private ArrayList<HeadMessage> messages = new ArrayList<HeadMessage>();

	public MultiUploadTransparentTransmission() {
		super(Messages.MULTI_UPLOAD_REQ);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.richinfo.tsp.nodeserver.message.SecurityMessage#writeBody()
	 */
	@Override
	public byte[] writeBody() {
		return null;
	}

}
