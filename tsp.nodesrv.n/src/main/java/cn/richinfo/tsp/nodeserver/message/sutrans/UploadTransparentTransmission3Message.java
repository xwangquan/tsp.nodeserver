package cn.richinfo.tsp.nodeserver.message.sutrans;

import cn.richinfo.tsp.nodeserver.message.HeadMessage;
import cn.richinfo.tsp.nodeserver.message.Messages;
import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;
import cn.richinfo.tsp.nodeserver.utils.SignUtils;

/**
 * 9.18. ��������͸��----old back!
 * 
 * @ClassName: UploadTTMessage
 * @Description: Received terminal msg then stroe;
 * @author ��ȫ
 * @date 2014-11-19 ����4:05:17
 */
@Deprecated
public abstract class UploadTransparentTransmission3Message extends HeadMessage {
	// ͸����Ϣ����(id)
	private int ttID = (byte) 0xAB;

	// ͸����Ϣ����(type)
	private SuTransTypes ttType;

	private int LEN = getHeadLength() + 3;

	public enum SuTransTypes {
		DRIVEDATA(0x00, "DriveData"), TRIPREPORT(0x01, "TripReport"), OBDFAULTCODE(
				0x02, "ObdFaultCode"), OBDLOCATION(0x03, "ObdLocation"), DRIVECOSTOM(
				0x04, "DriveCustom"), CARSTATUS(0x5, "CarStatus"), WARNDATA(
				0x6, "WarnData"), WAKEUP(0x7, "Wakeup"), OBDSTATUS(0x8,
				"ObdStatus");

		int type;
		String name;

		SuTransTypes(int type, String name) {
			setType(type);
			setName(name);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}
	}

	public UploadTransparentTransmission3Message(SuTransTypes ttType) {
		super(Messages.TT_UPLOAD_REQ);
		this.ttType = ttType;
	}

	@Override
	public final int read(byte[] receivedData, int offset) throws Exception {
		offset = super.read(receivedData, offset);
		ttID = receivedData[offset];
		int _ttType = SignUtils.getUnsignedByte(ConversionUtil.byte2short(
				receivedData, offset + 1));
		ttType = selector(_ttType);
		return hei(receivedData, offset + 3);
	}

	@Override
	public final int read(byte[] receivedData) throws Exception {
		return super.read(receivedData);
	}

	@Override
	public final byte[] write() {
		byte[] sub = xiu();
		setMessageLength(getLEN() + sub.length);
		byte send[] = new byte[getMessageLength()];
		// write message head
		System.arraycopy(super.write(), 0, send, 0, getHeadLength());
		// write message body
		send[getHeadLength()] = (byte) ttID;
		System.arraycopy(SignUtils.getUnsingedShort(ttType.getType()), 0, send,
				getHeadLength() + 1, 2);
		// write sub body
		System.arraycopy(sub, 0, send, getHeadLength() + 3, sub.length);
		return send;
	}

	public SuTransTypes getTtType() {
		return ttType;
	}

	/**
	 * ������Ϣ����
	 * 
	 * @return
	 */
	public int getLEN() {
		return LEN;
	}

	public String SIGN = ",";
	public abstract int hei(byte[] receivedData, int offset) throws Exception;

	public abstract byte[] xiu();

	public SuTransTypes selector(int _ttType) {
		switch (_ttType) {
		case 0x00:
			return SuTransTypes.DRIVEDATA;
		case 0x01:
			return SuTransTypes.TRIPREPORT;
		case 0x02:
			return SuTransTypes.OBDFAULTCODE;
		case 0x03:
			return SuTransTypes.OBDLOCATION;
		case 0x04:
			return SuTransTypes.DRIVECOSTOM;
		case 0x05:
			return SuTransTypes.CARSTATUS;
		case 0x06:
			return SuTransTypes.WARNDATA;
		case 0x07:
			return SuTransTypes.WAKEUP;
		case 0x08:
			return SuTransTypes.OBDSTATUS;
		default:
			break;
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(
				"UploadTransparentTransmissionMessage:{ ");
		sb.append(super.toString()).append("Body:| ");
		sb.append("ttId=").append(ttID).append("| ");
		sb.append("ttType=").append(ttType.getName()).append("| ");
		return sb.toString();
	}
}
