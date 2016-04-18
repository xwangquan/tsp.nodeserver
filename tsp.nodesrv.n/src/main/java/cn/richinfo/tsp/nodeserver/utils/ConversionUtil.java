package cn.richinfo.tsp.nodeserver.utils;

/**
 * �ֽ�ת��,������ת��
 * 
 * @ClassName: ConversionUtil
 * @Description: TODO()
 * @author ��ȫ
 * @date 2014-7-30 ����3:20:28
 */

public class ConversionUtil {

	private static String hexStr = "0123456789ABCDEF";
	private static String[] binaryArray = { "0000", "0001", "0010", "0011",
			"0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011",
			"1100", "1101", "1110", "1111" };

	/**
	 * ���طְ���Ϣ��
	 * @param packs �ְ�����
	 * @param seq ��ǰ�ְ���ˮ
	 * @return
	 */
	public static byte[] getMsgPack(int packs,int seq){
		byte[] msgPack= new byte[4];
		System.arraycopy(SignUtils.getUnsingedShort(packs), 0, msgPack,
				0, 2);
		System.arraycopy(SignUtils.getUnsingedShort(seq), 0, msgPack,
				2, 2);
		return msgPack;
	}
	/**
	 * ��ȡ��Ϣ������
	 * 
	 * @param hasPackage
	 *            �Ƿ�ְ�
	 * @param hasEncry
	 *            �Ƿ�rsa����
	 * @param bodyLen
	 *            ��Ϣ�峤��
	 * @return
	 */
	public static int getBodyPropety(boolean hasPackage, boolean hasEncry,
			int bodyLen) {
		// Ĭ�ϲ��ְ�
		int pack = 0;
		// �ְ�
		if (hasPackage)
			pack = 0x2000;
		// Ĭ�ϲ�����
		int encry = 0;
		// ����
		if (hasEncry)
			encry = 0x400;
		return pack | encry | bodyLen;
	}

	/**
	 * ת��Ϊ�������ַ���
	 * 
	 * @param bArray
	 * @return bit string
	 */
	public static String bytes2BinaryStr(byte[] bArray) {
		String outStr = "";
		int pos = 0;
		for (byte b : bArray) {
			// ����λ
			pos = (b & 0xF0) >> 4;
			outStr += binaryArray[pos];
			// ����λ
			pos = b & 0x0F;
			outStr += binaryArray[pos];
		}
		return outStr;
	}

	/**
	 * ת��Ϊ������ �ֽ�����
	 * 
	 * @param bArray
	 * @return bit byte[]
	 */
	public static byte[] bytes2BinaryByte(byte[] bArray) {
		return bytes2BinaryStr(bArray).getBytes();
	}

	/**
	 * 
	 * @param bytes
	 * @return ��������ת��Ϊʮ�������ַ����
	 */
	public static String BinaryToHexString(byte[] bytes) {

		String result = "";
		String hex = "";
		for (int i = 0; i < bytes.length; i++) {
			// �ֽڸ�4λ
			hex = String.valueOf(hexStr.charAt((bytes[i] & 0xF0) >> 4));
			// �ֽڵ�4λ
			hex += String.valueOf(hexStr.charAt(bytes[i] & 0x0F));
			result += hex + " ";
		}
		return result;
	}

	/**
	 * 
	 * @param hexString
	 * @return ��ʮ������ת��Ϊ�ֽ�����
	 */
	public static byte[] HexStringToBinary(String hexString) {
		// hexString�ĳ��ȶ�2ȡ������Ϊbytes�ĳ���
		int len = hexString.length() / 2;
		byte[] bytes = new byte[len];
		byte high = 0;// �ֽڸ���λ
		byte low = 0;// �ֽڵ���λ

		for (int i = 0; i < len; i++) {
			// ������λ�õ���λ
			high = (byte) ((hexStr.indexOf(hexString.charAt(2 * i))) << 4);
			low = (byte) hexStr.indexOf(hexString.charAt(2 * i + 1));
			bytes[i] = (byte) (high | low);// �ߵ�λ��������
		}
		return bytes;
	}

	public static byte[] int2byte(int n) {
		byte[] b = new byte[4];
		b[0] = (byte) (n >> 24);
		b[1] = (byte) (n >> 16);
		b[2] = (byte) (n >> 8);
		b[3] = (byte) n;
		return b;
	}

	public static int byte2int(byte[] b) {
		return (b[3] & 0xFF | (b[2] & 0xFF) << 8 | (b[1] & 0xFF) << 16 | (b[0] & 0xFF) << 24);
	}

	public static int byte2int(byte[] b, int offset) {
		return (b[(3 + offset)] & 0xFF | (b[(2 + offset)] & 0xFF) << 8
				| (b[(1 + offset)] & 0xFF) << 16 | (b[(0 + offset)] & 0xFF) << 24);
	}

	public static byte[] short2byte(int n) {
		byte[] b = new byte[2];
		b[0] = (byte) (n >> 8);
		b[1] = (byte) n;
		return b;
	}

	public static short byte2short(byte[] b) {
		return (short) (b[1] & 0xFF | (b[0] & 0xFF) << 8);
	}

	public static short byte2short(byte[] b, int offset) {
		return (short) (b[(1 + offset)] & 0xFF | (b[offset] & 0xFF) << 8);
	}

	public static byte[] long2byte(long n) {
		byte[] b = new byte[8];
		b[0] = (byte) (int) (n >> 56);
		b[1] = (byte) (int) (n >> 48);
		b[2] = (byte) (int) (n >> 40);
		b[3] = (byte) (int) (n >> 32);
		b[4] = (byte) (int) (n >> 24);
		b[5] = (byte) (int) (n >> 16);
		b[6] = (byte) (int) (n >> 8);
		b[7] = (byte) (int) n;
		return b;
	}

	public static long byte2long(byte[] b) {
		return (b[7] & 0xFF | (b[6] & 0xFF) << 8 | (b[5] & 0xFF) << 16
				| (b[4] & 0xFF) << 24 | (b[3] & 0xFF) << 32
				| (b[2] & 0xFF) << 40 | (b[1] & 0xFF) << 48 | b[0] << 56);
	}

	public static long byte2long(byte[] b, int offset) {
		return (b[(offset + 7)] & 0xFF | (b[(offset + 6)] & 0xFF) << 8
				| (b[(offset + 5)] & 0xFF) << 16
				| (b[(offset + 4)] & 0xFF) << 24
				| (b[(offset + 3)] & 0xFF) << 32
				| (b[(offset + 2)] & 0xFF) << 40
				| (b[(offset + 1)] & 0xFF) << 48 | b[offset] << 56);
	}

	public static int getInt(String s) {
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
		}
		return 0;
	}

	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; ++n) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	public static byte[] hex2byte(byte[] b) {
		if (b.length % 2 != 0)
			throw new IllegalArgumentException(
					"the length of array is incorrect:" + b.length);

		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[(n / 2)] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	public static long bcd2long(byte[] bytes) {
		StringBuffer temp = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; ++i) {
			temp.append((byte) ((bytes[i] & 0xF0) >>> 4));
			temp.append((byte) (bytes[i] & 0xF));
		}
		return Long.parseLong((temp.toString().substring(0, 1)
				.equalsIgnoreCase("0")) ? temp.toString().substring(1) : temp
				.toString());
	}

	public static byte[] long2bcd(long data) {
		String asc = data + "";

		int len = asc.length();
		int mod = len % 2;

		if (mod != 0) {
			asc = asc + "0";
			len = asc.length();
		}

		byte[] bOriginalData = new byte[len];
		if (len >= 2) {
			len /= 2;
		}

		bOriginalData = asc.getBytes();

		byte[] bBCD = new byte[len];

		for (int p = 0; p < asc.length() / 2; ++p) {
			int sH;
			int sL;
			if ((bOriginalData[(2 * p)] >= 97)
					&& (bOriginalData[(2 * p)] <= 102))
				sH = bOriginalData[(2 * p)] - 97 + 10;
			else if ((bOriginalData[(2 * p)] >= 65)
					&& (bOriginalData[(2 * p)] <= 70))
				sH = bOriginalData[(2 * p)] - 65 + 10;
			else {
				sH = bOriginalData[(2 * p)] & 0xF;
			}

			if ((bOriginalData[(2 * p + 1)] >= 97)
					&& (bOriginalData[(2 * p + 1)] <= 102))
				sL = bOriginalData[(2 * p + 1)] - 97 + 10;
			else if ((bOriginalData[(2 * p + 1)] >= 65)
					&& (bOriginalData[(2 * p + 1)] <= 70))
				sL = bOriginalData[(2 * p + 1)] - 65 + 10;
			else {
				sL = bOriginalData[(2 * p + 1)] & 0xF;
			}

			bBCD[p] = (byte) ((sH << 4) + sL);
		}
		return bBCD;
	}
}
