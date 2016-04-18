package cn.richinfo.tsp.nodeserver.utils;

/**
 * NameServer ��ȡ�������
 * 
 * @ClassName: ConfigFactory
 * @Description: TODO()
 * @author ��ȫ
 * @date 2014-8-14 ����3:21:40
 */
public class ConfigFactory {

	public static Config getConfigByClassPath(String configFile) {
		return new Config(configFile, true);
	}

	public static Config getConfigByWorkingPath(String configFile) {
		return new Config(configFile, false);
	}
}
