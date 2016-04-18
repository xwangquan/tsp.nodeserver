package cn.richinfo.tsp.nodeserver.message.sutrans;

/**
 * Do nothing,just ignore now!
 * 
 * @ClassName: ObdStatusMessage
 * @Description: �ն�״̬
 * @author ��ȫ
 * @date 2014-11-19 ����4:31:51
 */
public class ObdStatusMessage extends UploadTransparentTransmissionMessage {

	// ��ǰ������ʽ
	public byte net;
	// ��ǰ�źŵȼ�
	public byte sign;
	// WIFI״̬
	public byte wifi;

	private int SUBLEN = 3;

	public ObdStatusMessage() {
		super(SuTransTypes.OBDSTATUS);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.richinfo.tsp.nodeserver.message.sutrans.
	 * UploadTransparentTransmissionMessage#hei(byte[], int)
	 */
	@Override
	public int hei(byte[] receivedData, int offset) throws Exception {
		net = receivedData[offset];
		sign = receivedData[offset + 1];
		wifi = receivedData[offset + 2];
		return SUBLEN + offset;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.richinfo.tsp.nodeserver.message.sutrans.
	 * UploadTransparentTransmissionMessage#xiu()
	 */
	@Override
	public byte[] xiu() {
		byte[] send = new byte[SUBLEN];
		send[0] = net;
		send[1] = sign;
		send[2] = wifi;
		return send;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString()).append("],BODY[");
		sb.append("net=").append(net).append("| ");
		sb.append("sign=").append(sign).append("| ");
		sb.append("wifi=").append(wifi).append("]}");
		return sb.toString();
	}

	// set/get method-----------------------

	public byte getNet() {
		return net;
	}

	public void setNet(byte net) {
		this.net = net;
	}

	public byte getSign() {
		return sign;
	}

	public void setSign(byte sign) {
		this.sign = sign;
	}

	public byte getWifi() {
		return wifi;
	}

	public void setWifi(byte wifi) {
		this.wifi = wifi;
	}

}
