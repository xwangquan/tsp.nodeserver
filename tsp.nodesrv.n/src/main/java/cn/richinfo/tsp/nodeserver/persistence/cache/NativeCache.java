package cn.richinfo.tsp.nodeserver.persistence.cache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: NativeCache
 * @Description: TODO()
 * @author ��ȫ
 * @date 2014-11-24 ����12:26:31
 */
public class NativeCache {
	private static NativeCache cache;

	private NativeCache() {
	}

	public static NativeCache getCache() {
		if (cache == null)
			cache = new NativeCache();
		return cache;
	}

	private ConcurrentHashMap<String, String> key = new ConcurrentHashMap<String, String>();

	public String getKey(String tuid) {
		return key.get(tuid);
	}
}
