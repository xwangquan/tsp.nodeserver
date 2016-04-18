package cn.richinfo.tsp.nodeserver.message.sutrans;

import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;
import cn.richinfo.tsp.nodeserver.utils.SignUtils;

/**
 * @ClassName: CarStatusMessage
 * @Description: ����״̬
 * @author ��ȫ
 * @date 2014-11-19 ����4:28:24
 */
public class CarStatusMessage extends UploadTransparentTransmissionMessage {
	// ʱ�� BCD[6] ʱ���ʽ��YYMMDDhhmmss
	public byte[] time = new byte[6];
	// �ϴ����� 0�������ϴ���1����ѯ�ϴ�
	public byte type;

	// ��״̬(Զ���) BYTE(bit1:0) 0:�ر�,1:��,2:����,3:δ֪
	// ��״̬(�����) BYTE(bit3:2) 0:�ر�,1:��,2:����,3:δ֪
	// ��״̬(ʾ���) BYTE(bit5:4) 0:�ر�,1:��,2:����,3:δ֪
	// ��״̬(���) BYTE(bit7:6) 0:�ر�,1:��,2:����,3:δ֪
	public byte a;

	// ��״̬(��ת��) BYTE(bit1:0) 0:�ر�,1:��,2:����,3:δ֪
	// ��״̬(��ת��) BYTE(bit3:2) 0:�ر�,1:��,2:����,3:δ֪
	// ��״̬(Σ�յ�) BYTE(bit5:4) 0:�ر�,1:��,2:����,3:δ֪
	// ��״̬(��ǰ��) BYTE(bit7:6) 0:�ر�,1:��,2:����,3:δ֪
	public byte b;

	// ��״̬(��ǰ��) BYTE(bit1:0) 0:�ر�,1:��,2:����,3:δ֪
	// ��״̬(�����) BYTE(bit3:2) 0:�ر�,1:��,2:����,3:δ֪
	// ��״̬(�Һ���) BYTE(bit5:4) 0:�ر�,1:��,2:����,3:δ֪
	// ��״̬(����\β��) BYTE(bit7:6) 0:�ر�,1:��,2:����,3:δ֪
	public byte c;
	// ����(ȫ��) BYTE(bit1:0) 0:����,1:����,2:����,3:δ֪
	// ����(��ǰ��) BYTE(bit3:2) 0:����,1:����,2:����,3:δ֪
	// ����(��ǰ��) BYTE(bit5:4) 0:����,1:����,2:����,3:δ֪
	// ����(�����) BYTE(bit7:6) 0:����,1:����,2:����,3:δ֪
	public byte d;
	// ����(�Һ���) BYTE(bit1:0) 0:����,1:����,2:����,3:δ֪
	// ����(����\β��) BYTE(bit3:2) 0:����,1:����,2:����,3:δ֪
	// ��״̬(��ǰ��) BYTE(bit5:4) 0:�ر�,1:��,2:����,3:δ֪
	// ��״̬(��ǰ��) BYTE(bit7:6) 0:�ر�,1:��,2:����,3:δ֪
	public byte e;
	// ��״̬(���) BYTE(bit1:0) 0:�ر�,1:��,2:����,3:δ֪
	// ��״̬(�Һ�) BYTE(bit3:2) 0:�ر�,1:��,2:����,3:δ֪
	// ��״̬(�촰) BYTE(bit5:4) 0:�ر�,1:��,2:����,3:δ֪
	// �����ź�(ECM) BYTE(bit7:6) 0:����,1:����,2:����,3:δ֪
	public byte f;
	// �����ź�(ABS) BYTE(bit1:0) 0:����,1:����,2:����,3:δ֪
	// �����ź�(SRS) BYTE(bit3:2) 0:����,1:����,2:����,3:δ֪
	// �����ź�(����) BYTE(bit5:4) 0:����,1:����,2:����,3:δ֪
	// �����ź�(̥ѹ) BYTE(bit7:6) 0:����,1:����,2:����,3:δ֪
	public byte g;
	// �����ź�(����) BYTE(bit1:0) 0:����,1:����,2:����,3:δ֪
	// ��ȫ����״̬ BYTE(bit3:2) 0:����,1:����,2:����,3:δ֪
	// ��ɲ״̬ BYTE(bit5:4) 0:�ɿ�,1:����,2:����,3:δ֪
	// ɲ��״̬(��ɲ) BYTE(bit7:6) 0:�ɿ�,1:̤��,2:����,3:δ֪
	public byte h;
	// ��ȫ��(��ʻԱ) BYTE(bit1:0) 0:δ��,1:�ѿ�,2:����,3:δ֪
	// ��ȫ��(����) BYTE(bit3:2) 0:δ��,1:�ѿ�,2:����,3:δ֪
	// ACC �ź� BYTE(bit5:4) 0:��Ч,1:��Ч,2:����,3:δ֪
	// Կ��״̬ BYTE(bit7:6) 0:��Ч,1:��Ч,2:����,3:δ֪
	public byte i;
	// ң���ź� BYTE(bit3:0) 0��δ����1��������2��������3��β������
	// 4������������5������������6:������7��δ֪
	// ���״̬ BYTE(bit5:4) 0:�ر�,1:��,2:����,3:δ֪
	// �յ����� BYTE(bit7:6) 0:�ر�,1:��,2:����,3:δ֪
	public byte j;
	// ��λ BYTE(3:0) 1��P����2��R����3��N����4��D����
	// ���� BYTE(bit7:4)
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
