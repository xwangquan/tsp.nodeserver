package cn.richinfo.tsp.nodeserver.security.tea;

import java.util.Random;

/**
 * 
 * @ClassName: Tea加密解密算法
 * @Description: TODO()
 * @author 王全
 * @date 2014-9-4 上午10:16:14
 */
public class Tea {

	private final static int TIMES = 32;// 加密次数

	// 加密
	private static byte[] encrypt(byte[] content, int offset, int[] key,
			int times) {// times为加密轮数
		int[] tempInt = byteToInt(content, offset);
		int y = tempInt[0], z = tempInt[1], sum = 0, i;
		int delta = 0x9e3779b9; // 这是算法标准给的值
		int a = key[0], b = key[1], c = key[2], d = key[3];

		for (i = 0; i < times; i++) {

			sum += delta;
			y += ((z << 4) + a) ^ (z + sum) ^ ((z >>> 5) + b);
			z += ((y << 4) + c) ^ (y + sum) ^ ((y >>> 5) + d);
		}
		tempInt[0] = y;
		tempInt[1] = z;
		return intToByte(tempInt, 0);
	}

	// 解密
	private static byte[] decrypt(byte[] encryptContent, int offset, int[] key,
			int times) {
		int[] tempInt = byteToInt(encryptContent, offset);
		int y = tempInt[0], z = tempInt[1], sum = 0, i;
		int delta = 0x9e3779b9; // 这是算法标准给的值
		int a = key[0], b = key[1], c = key[2], d = key[3];
		if (times == 32)
			sum = 0xC6EF3720; /* delta << 5 */
		else if (times == 16)
			sum = 0xE3779B90; /* delta << 4 */
		else
			sum = delta * times;

		for (i = 0; i < times; i++) {
			z -= ((y << 4) + c) ^ (y + sum) ^ ((y >>> 5) + d);
			y -= ((z << 4) + a) ^ (z + sum) ^ ((z >>> 5) + b);
			sum -= delta;
		}
		tempInt[0] = y;
		tempInt[1] = z;

		return intToByte(tempInt, 0);
	}

	// byte[]型数据转成int[]型数据
	private static int[] byteToInt(byte[] content, int offset) {

		int[] result = new int[content.length >> 2];// 除以2的n次方 == 右移n位 即
													// content.length / 4 ==
													// content.length >> 2
		for (int i = 0, j = offset; j < content.length; i++, j += 4) {
			result[i] = transform(content[j + 3])
					| transform(content[j + 2]) << 8
					| transform(content[j + 1]) << 16 | (int) content[j] << 24;
		}
		return result;

	}

	// int[]型数据转成byte[]型数据
	private static byte[] intToByte(int[] content, int offset) {
		byte[] result = new byte[content.length << 2];// 乘以2的n次方 == 左移n位 即
														// content.length * 4 ==
														// content.length << 2
		for (int i = 0, j = offset; j < result.length; i++, j += 4) {
			result[j + 3] = (byte) (content[i] & 0xff);
			result[j + 2] = (byte) ((content[i] >> 8) & 0xff);
			result[j + 1] = (byte) ((content[i] >> 16) & 0xff);
			result[j] = (byte) ((content[i] >> 24) & 0xff);
		}
		return result;
	}

	// 若某字节为负数则需将其转成无符号正数
	private static int transform(byte temp) {
		int tempInt = (int) temp;
		if (tempInt < 0) {
			tempInt += 256;
		}
		return tempInt;
	}

	// 通过TEA算法加密信息
	public static byte[] encryptByTeaCommon(byte[] content, int[] key) {
		int n = 8 - content.length % 8;// 若temp的位数不足8的倍数,需要填充的位数
		byte[] encryptStr = new byte[content.length + n];
		encryptStr[0] = (byte) n;
		System.arraycopy(content, 0, encryptStr, n, content.length);
		byte[] result = new byte[encryptStr.length];
		for (int offset = 0; offset < result.length; offset += 8) {
			byte[] tempEncrpt = encrypt(encryptStr, offset, key, TIMES);
			System.arraycopy(tempEncrpt, 0, result, offset, 8);
		}
		return result;
	}

	// 通过TEA算法加密信息 qq (前3后7)
	public static byte[] encryptByTea(byte[] content, int[] key) {
		int n = 8 - (content.length + 10) % 8;// 若temp的位数不足8的倍数,需要填充的位数
		byte[] encryptStr = new byte[content.length + n + 10];
		encryptStr[0] = (byte) n;
		byte t = (byte) (new Random().nextInt());
		int nIndex = 0;
		for (nIndex = 0; nIndex < n; nIndex++) {
			encryptStr[nIndex + 1] = t;
		}

		for (; nIndex < 2; nIndex++) {
			encryptStr[nIndex + 1] = (byte) (new Random().nextInt());
		}

		System.arraycopy(content, 0, encryptStr, n + 3, content.length);
		byte[] result = new byte[encryptStr.length];
		for (int offset = 0; offset < result.length; offset += 8) {
			byte[] tempEncrpt = encrypt(encryptStr, offset, key, TIMES);
			System.arraycopy(tempEncrpt, 0, result, offset, 8);
		}
		return result;
	}

	// 通过TEA算法解密信息 qq (前3后7)
	public static byte[] decryptByTea(byte[] secret, int[] key) {
		byte[] decryptStr = null;
		byte[] tempDecrypt = new byte[secret.length];
		for (int offset = 0; offset < secret.length; offset += 8) {
			decryptStr = decrypt(secret, offset, key, TIMES);
			System.arraycopy(decryptStr, 0, tempDecrypt, offset, 8);
		}
		int n = tempDecrypt[0];
		byte[] result = new byte[decryptStr.length - n - 10];
		System.arraycopy(tempDecrypt, n + 3, result, 0, decryptStr.length - n
				- 10);
		return result;
	}

	// 通过TEA算法解密信息
	public static byte[] decryptByTeaCommon(byte[] secret, int[] key) {
		byte[] decryptStr = null;
		byte[] tempDecrypt = new byte[secret.length];
		for (int offset = 0; offset < secret.length; offset += 8) {
			decryptStr = decrypt(secret, offset, key, TIMES);
			System.arraycopy(decryptStr, 0, tempDecrypt, offset, 8);
		}
		int n = tempDecrypt[0];
		byte b[] = new byte[decryptStr.length - n];
		System.arraycopy(tempDecrypt, n, b, 0, tempDecrypt.length - n);
		return b;
		// return new String(tempDecrypt, n, decryptStr.length - n);
	}
}
