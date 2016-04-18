package cn.richinfo.tsp.nodeserver.message.sutrans;

import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;
import cn.richinfo.tsp.nodeserver.utils.SignUtils;

/**
 * @ClassName: CarStatusMessage
 * @Description: 车身状态
 * @author 王全
 * @date 2014-11-19 下午4:28:24
 */
public class CarStatusMessage extends UploadTransparentTransmissionMessage {
	// 时间 BCD[6] 时间格式：YYMMDDhhmmss
	public byte[] time = new byte[6];
	// 上传类型 0：主动上传；1：查询上传
	public byte type;

	// 灯状态(远光灯) BYTE(bit1:0) 0:关闭,1:打开,2:保留,3:未知
	// 灯状态(近光灯) BYTE(bit3:2) 0:关闭,1:打开,2:保留,3:未知
	// 灯状态(示宽灯) BYTE(bit5:4) 0:关闭,1:打开,2:保留,3:未知
	// 灯状态(雾灯) BYTE(bit7:6) 0:关闭,1:打开,2:保留,3:未知
	public byte a;

	// 灯状态(左转向) BYTE(bit1:0) 0:关闭,1:打开,2:保留,3:未知
	// 灯状态(右转向) BYTE(bit3:2) 0:关闭,1:打开,2:保留,3:未知
	// 灯状态(危险灯) BYTE(bit5:4) 0:关闭,1:打开,2:保留,3:未知
	// 门状态(左前门) BYTE(bit7:6) 0:关闭,1:打开,2:保留,3:未知
	public byte b;

	// 门状态(右前门) BYTE(bit1:0) 0:关闭,1:打开,2:保留,3:未知
	// 门状态(左后门) BYTE(bit3:2) 0:关闭,1:打开,2:保留,3:未知
	// 门状态(右后门) BYTE(bit5:4) 0:关闭,1:打开,2:保留,3:未知
	// 门状态(后备箱\尾箱) BYTE(bit7:6) 0:关闭,1:打开,2:保留,3:未知
	public byte c;
	// 门锁(全车) BYTE(bit1:0) 0:解锁,1:上锁,2:保留,3:未知
	// 门锁(左前门) BYTE(bit3:2) 0:解锁,1:上锁,2:保留,3:未知
	// 门锁(右前门) BYTE(bit5:4) 0:解锁,1:上锁,2:保留,3:未知
	// 门锁(左后门) BYTE(bit7:6) 0:解锁,1:上锁,2:保留,3:未知
	public byte d;
	// 门锁(右后门) BYTE(bit1:0) 0:解锁,1:上锁,2:保留,3:未知
	// 门锁(后备箱\尾箱) BYTE(bit3:2) 0:解锁,1:上锁,2:保留,3:未知
	// 窗状态(左前窗) BYTE(bit5:4) 0:关闭,1:打开,2:保留,3:未知
	// 窗状态(右前窗) BYTE(bit7:6) 0:关闭,1:打开,2:保留,3:未知
	public byte e;
	// 窗状态(左后窗) BYTE(bit1:0) 0:关闭,1:打开,2:保留,3:未知
	// 窗状态(右后窗) BYTE(bit3:2) 0:关闭,1:打开,2:保留,3:未知
	// 窗状态(天窗) BYTE(bit5:4) 0:关闭,1:打开,2:保留,3:未知
	// 故障信号(ECM) BYTE(bit7:6) 0:正常,1:故障,2:保留,3:未知
	public byte f;
	// 故障信号(ABS) BYTE(bit1:0) 0:正常,1:故障,2:保留,3:未知
	// 故障信号(SRS) BYTE(bit3:2) 0:正常,1:故障,2:保留,3:未知
	// 报警信号(机油) BYTE(bit5:4) 0:正常,1:故障,2:保留,3:未知
	// 报警信号(胎压) BYTE(bit7:6) 0:正常,1:故障,2:保留,3:未知
	public byte g;
	// 报警信号(保养) BYTE(bit1:0) 0:正常,1:故障,2:保留,3:未知
	// 安全气囊状态 BYTE(bit3:2) 0:正常,1:故障,2:保留,3:未知
	// 手刹状态 BYTE(bit5:4) 0:松开,1:拉紧,2:保留,3:未知
	// 刹车状态(脚刹) BYTE(bit7:6) 0:松开,1:踏下,2:保留,3:未知
	public byte h;
	// 安全带(驾驶员) BYTE(bit1:0) 0:未扣,1:已扣,2:保留,3:未知
	// 安全带(副驾) BYTE(bit3:2) 0:未扣,1:已扣,2:保留,3:未知
	// ACC 信号 BYTE(bit5:4) 0:无效,1:有效,2:保留,3:未知
	// 钥匙状态 BYTE(bit7:6) 0:无效,1:有效,2:保留,3:未知
	public byte i;
	// 遥控信号 BYTE(bit3:0) 0：未按；1：开锁；2：关锁；3：尾箱锁；
	// 4：长按开锁；5：长按关锁；6:保留；7：未知
	// 雨刮状态 BYTE(bit5:4) 0:关闭,1:打开,2:保留,3:未知
	// 空调开关 BYTE(bit7:6) 0:关闭,1:打开,2:保留,3:未知
	public byte j;
	// 档位 BYTE(3:0) 1：P档；2：R档；3：N档；4：D档；
	// 保留 BYTE(bit7:4)
	public byte k;

	// for inner
	private int SUBLEN = 7 + 11;

	public CarStatusMessage() {
		super(SuTransTypes.CARSTATUS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.richinfo.tsp.nodeserver.message.sutrans.
	 * UploadTransparentTransmissionMessage#hei(byte[], int)
	 */
	@Override
	public int hei(byte[] receivedData, int offset) throws Exception {
		System.arraycopy(receivedData, offset, time, 0, 6);
		type = receivedData[offset + 6];
		a = (byte)SignUtils.getUnsignedByte(receivedData[offset + 7]);
		b = (byte)SignUtils.getUnsignedByte(receivedData[offset + 8]);
		c = (byte)SignUtils.getUnsignedByte(receivedData[offset + 9]);
		d = (byte)SignUtils.getUnsignedByte(receivedData[offset + 10]);
		e = (byte)SignUtils.getUnsignedByte(receivedData[offset + 11]);
		f = (byte)SignUtils.getUnsignedByte(receivedData[offset + 12]);
		g = (byte)SignUtils.getUnsignedByte(receivedData[offset + 13]);
		h = (byte)SignUtils.getUnsignedByte(receivedData[offset + 14]);
		i = (byte)SignUtils.getUnsignedByte(receivedData[offset + 15]);
		g = (byte)SignUtils.getUnsignedByte(receivedData[offset + 16]);
		k = (byte)SignUtils.getUnsignedByte(receivedData[offset + 17]);
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
		System.arraycopy(time, 0, send, 0, 6);
		send[6] = type;
		send[7] = a;
		send[8] = b;
		send[9] = c;
		send[10] = d;
		send[11] = e;
		send[12] = f;
		send[13] = g;
		send[14] = h;
		send[15] = i;
		send[16] = j;
		send[17] = k;
		return send;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString()).append("],BODY[");
		sb.append("time=").append(getTime()).append("| ");
		sb.append("type=").append(type).append("| ");
		sb.append("a=").append(a).append("| ");
		sb.append("b=").append(b).append("| ");
		sb.append("c=").append(c).append("| ");
		sb.append("d=").append(d).append("| ");
		sb.append("e=").append(e).append("| ");
		sb.append("f=").append(f).append("| ");
		sb.append("g=").append(g).append("| ");
		sb.append("h=").append(h).append("| ");
		sb.append("i=").append(i).append("| ");
		sb.append("j=").append(j).append("| ");
		sb.append("k=").append(k).append("]}");
		return sb.toString();
	}

	// ---------------------get/set
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

	public byte getA() {
		return a;
	}

	public void setA(byte a) {
		this.a = a;
	}

	public byte getB() {
		return b;
	}

	public void setB(byte b) {
		this.b = b;
	}

	public byte getC() {
		return c;
	}

	public void setC(byte c) {
		this.c = c;
	}

	public byte getD() {
		return d;
	}

	public void setD(byte d) {
		this.d = d;
	}

	public byte getE() {
		return e;
	}

	public void setE(byte e) {
		this.e = e;
	}

	public byte getF() {
		return f;
	}

	public void setF(byte f) {
		this.f = f;
	}

	public byte getG() {
		return g;
	}

	public void setG(byte g) {
		this.g = g;
	}

	public byte getH() {
		return h;
	}

	public void setH(byte h) {
		this.h = h;
	}

	public byte getI() {
		return i;
	}

	public void setI(byte i) {
		this.i = i;
	}

	public byte getJ() {
		return j;
	}

	public void setJ(byte j) {
		this.j = j;
	}

	public byte getK() {
		return k;
	}

	public void setK(byte k) {
		this.k = k;
	}

}
