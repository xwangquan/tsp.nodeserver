package cn.richinfo.tsp.nodeserver.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

/**
 * @ClassName: Md5Utils
 * @Description: 计算md5
 * @author 王全
 * @date 2014-8-6 下午5:30:20
 */
public class Md5Utils {

	private static Logger log = Logger.getLogger(Md5Utils.class);

	private static ThreadLocal<MessageDigest> md5Local = new ThreadLocal<MessageDigest>();

	public static final String DEFAULT_CHARSET_NAME = "utf-8";

	/**
	 * 计算文件md5
	 * @param file
	 * @return
	 */
	public static String getMd5ByFile(File file) {
		String value = null;
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			MappedByteBuffer byteBuffer = in.getChannel().map(
					FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			BigInteger bi = new BigInteger(1, md5.digest());
			value = bi.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	public static String getMD5Str(String str) {
		byte[] byteArray = computeMd5(str);
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}

	/**
	 * Get the md5 of the given key.
	 */
	private static byte[] computeMd5(String k) {
		MessageDigest md5 = md5Local.get();
		if (md5 == null) {
			try {
				md5 = MessageDigest.getInstance("MD5");
				md5Local.set(md5);
			} catch (NoSuchAlgorithmException e) {
				log.error(e.getMessage(), e);
				throw new RuntimeException("MD5 not supported", e);
			}
		}
		md5.reset();
		md5.update(getBytes(k));
		return md5.digest();
	}

	private static final byte[] getBytes(String k) {
		if (k == null || k.length() == 0) {
			throw new IllegalArgumentException("Key must not be blank");
		}
		try {
			return k.getBytes(DEFAULT_CHARSET_NAME);
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
}
