package cn.richinfo.tsp.nodeserver.message.sutrans;

import java.io.UnsupportedEncodingException;

import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;
import cn.richinfo.tsp.nodeserver.utils.SignUtils;

/**
 * @ClassName: ObdFaultCodeMessage
 * @Description: OBD故障码
 * @author 王全
 * @date 2014-11-19 下午4:24:59
 */
public class ObdFaultCodeMessage extends UploadTransparentTransmissionMessage {

	// 时间
	public byte[] time = new byte[6];
	// 上传类型
	public byte type;

	// 故障码数量
	private int codeNum;
	// 故障码记录集
	private String fault_code;

	// --inner
	private int SUBLEN = 9;

	public ObdFaultCodeMessage() {
		super(SuTransTypes.OBDFAULTCODE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.richinfo.tsp.nodeserver.message.sutrans.
	 * UploadTransparentTransmissionMessage#hei(byte[], int)
	 */
	@Override
	public int hei(byte[] receivedData, int offset) throws Exception {
		int codeNumLen = receivedData.length - offset - SUBLEN - 2;
		System.arraycopy(receivedData, offset, time, 0, 6);
		type = receivedData[offset + 6];
		codeNum = SignUtils.getUnsignedByte(ConversionUtil.byte2short(
				receivedData, offset + 7));
		if (codeNum != 0)
			fault_code = new String(receivedData, offset + 9, codeNumLen);
		return offset + 9 + codeNumLen;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.richinfo.tsp.nodeserver.message.sutrans.
	 * UploadTransparentTransmissionMessage#xiu()
	 */
	@Override
	public byte[] xiu() {
		byte[] send = new byte[getSUBLEN()];
		System.arraycopy(time, 0, send, 0, 6);
		send[6] = type;
		System.arraycopy(SignUtils.getUnsingedShort(codeNum), 0, send, 7, 2);
		if (codeNum != 0 && fault_code != null)
			System.arraycopy(fault_code.getBytes(), 0, send, 9, fault_code.length());
		return send;
	}

	public int getSUBLEN() {
		int sub = 0;
		if (fault_code != null && codeNum != 0) {
			try {
				sub = fault_code.getBytes(CHARSET).length;
			} catch (UnsupportedEncodingException e) {
			}
		}
		return 9 + sub;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString()).append("],BODY[");
		sb.append("time=").append(getTime()).append("| ");
		sb.append("type=").append(getType()).append("| ");
		sb.append("codeNum=").append(getCodeNum()).append("| ");
		sb.append("fault_code=").append(getFault_code()).append("]}");
		return sb.toString();
	}

	// ---------------------set/get method

	public long getTime() {
		return ConversionUtil.bcd2long(time);
	}

	public void setTime(byte[] time) {
		this.time = time;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public int getCodeNum() {
		return codeNum;
	}

	public void setCodeNum(int codeNum) {
		this.codeNum = codeNum;
	}

	public String getFault_code() {
		return fault_code;
	}

	public void setFault_code(String fault_code) {
		this.fault_code = fault_code;
	}

}
