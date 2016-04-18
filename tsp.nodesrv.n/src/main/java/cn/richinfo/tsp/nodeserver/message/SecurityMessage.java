package cn.richinfo.tsp.nodeserver.message;

import cn.richinfo.tsp.nodeserver.persistence.cache.NativeCache;
import cn.richinfo.tsp.nodeserver.security.tea.Tea;
import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;

/**
 * 加密消息体
 * 
 * @ClassName: SecurityMessage
 * @Description: for tea encrypt
 * @author 王全
 * @date 2014-12-1 下午5:22:53
 */
public abstract class SecurityMessage extends HeadMessage {

	// 密钥
	private String baseSecret;

	public SecurityMessage(int messageID) {
		super(messageID);
	}

	/**
	 * tea encrypt
	 * 
	 * @return
	 */
	public byte[] encrypt() {
		return Tea.encryptByTea(writeBody(), getKey());
	}

	/**
	 * tea decrypt
	 * 
	 * @return
	 */
	public byte[] decrypt(byte[] body) {
		// get key
		NativeCache.getCache().getKey(getTuid().trim());
		// for read
		if (baseSecret == null || "".equals(baseSecret))// just for test!!!!
			// setBaseSecret("aaaaaaaaaaaaaaaa");
			setBaseSecret("i3xPOcog2OE@0Z%B");
		return Tea.decryptByTea(body, getKey());
	}

	/**
	 * encrypt key
	 * 
	 * @return int[4]
	 */
	private int[] getKey() {
		// check secret
		if (baseSecret != null && baseSecret.length() == 16) {
			byte[] _baseSecret = baseSecret.getBytes();
			int[] key = new int[4];
			for (int i = 0; i < key.length; i++) {
				byte[] _int = new byte[4];
				System.arraycopy(_baseSecret, i * 4, _int, 0, 4);
				key[i] = ConversionUtil.byte2int(_int);
			}
			return key;
		}
		return null;
	}

	public abstract byte[] writeBody();

	public String getBaseSecret() {
		return baseSecret;
	}

	public void setBaseSecret(String baseSecret) {
		if (baseSecret == null || baseSecret.length() != 16)
			this.baseSecret = new String(new byte[16]);
		else
			this.baseSecret = baseSecret;
	}
}
