package cn.richinfo.tsp.nodeserver.utils;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.math.BigInteger;

/**
 * @ClassName: SignUtils
 * @Description: TODO()
 * @author 王全
 * @date 2014-7-29 下午3:58:02
 */
public final class SignUtils {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		int i = -2;
		short t = -1213;
		byte c = -19;
		System.out.println(getUnsignedIntt(i));
		System.out.println(getUnsignedByte(t));
		System.out.println(getUnsignedByte(c));
		int t1 = 64323;
		short t2 = getSingedShort(t1);
		System.out.println("t2=" + t2);
		System.out.println(ConversionUtil.short2byte(t2)[0] + ","
				+ ConversionUtil.short2byte(t2)[1]);
		ByteArrayInputStream byteStream = new ByteArrayInputStream(
				ConversionUtil.short2byte(t2));
		DataInputStream in = new DataInputStream(byteStream);
		System.out.println(in.readUnsignedShort());

		System.out.println("------------------------------");

		byte b[] = ConversionUtil.int2byte(-523423432);
		System.out.println(b.length);
		byteStream = new ByteArrayInputStream(b);
		in = new DataInputStream(byteStream);
		System.out.println(in.readInt());
	}

	/**
	 * 有符号字节转换为无符号字节
	 * 
	 * @param bytes
	 * @return
	 */
	public static int[] getUnsignedBytes(byte[] bytes) {
		int result[] = new int[bytes.length];
		for (int i = 0; i < bytes.length; i++) {
			// one get
			result[i] = getUnsignedByte(bytes[i]);
			// another get
			// result[i] = (bytes[i] < 0) ? 256 + bytes[i] : bytes[i];
		}
		// int newIPSegment = (ipSegment < 0) ? 256 + ipSegment : ipSegment;
		return result;
	}

	/**
	 * 有符号 转无符号short byte
	 * 
	 * @param unsinged
	 * @return
	 */
	public static byte[] getUnsingedShort(int unsinged) {
		return ConversionUtil.short2byte(getSingedShort(unsinged));
	}

	/**
	 * 无符号int 转换为有符号short
	 * 
	 * @param data
	 *            unsinged int
	 * @return
	 */
	private static short getSingedShort(int data) {
		return new BigInteger(ConversionUtil.int2byte(data)).shortValue();
	}

	/**
	 * 将data字节型数据转换为0~255 (0xFF 即BYTE)。
	 * 
	 * @param data
	 * @return
	 */
	public static int getUnsignedByte(byte data) {
		return data & 0x0FF;
	}

	/**
	 * 将data字节型数据转换为0~65535 (0xFFFF 即 WORD)
	 * 
	 * @param data
	 * @return
	 */
	public static int getUnsignedByte(short data) {
		return data & 0x0FFFF;
	}

	/**
	 * 将int数据转换为0~4294967295 (0xFFFFFFFF即DWORD)。
	 * 
	 * @param data
	 * @return
	 */
	public static long getUnsignedIntt(int data) {
		return data & 0x0FFFFFFFFl;
	}
}
