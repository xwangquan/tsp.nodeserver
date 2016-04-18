package cn.richinfo.tsp.nodeserver.utils;

/**
 * NameServer 读取配置相关
 * 
 * @ClassName: ConfigFactory
 * @Description: TODO()
 * @author 王全
 * @date 2014-8-14 下午3:21:40
 */
public class ConfigFactory {

	public static Config getConfigByClassPath(String configFile) {
		return new Config(configFile, true);
	}

	public static Config getConfigByWorkingPath(String configFile) {
		return new Config(configFile, false);
	}
}
