package cn.richinfo.tsp.nodeserver.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * NameServer 配置
 * 
 * @ClassName: Config
 * @Description: TODO()
 * @author 王全
 * @date 2014-8-14 下午3:21:48
 */
public class Config {

	private Logger log = Logger.getLogger(Config.class);

	private Properties prop = null;

	/**
	 * @param configFileName
	 *            配置文件
	 * @param byClassPath
	 *            配置文件是否在类路径下，否则为工作路径
	 */
	public Config(String configFileName, boolean byClassPath) {
		InputStream in = null;
		try {
			if (byClassPath) {
				in = Config.class.getClassLoader().getResourceAsStream(
						configFileName);
			} else {
				in = new FileInputStream(configFileName);
			}
			prop = new Properties();
			prop.load(in);
		} catch (IOException e) {
			log.error("Can not find config file:" + configFileName, e);
		} finally {
			if (null != in) {
				try {
					in.close();
					in = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 根据配置选项的Key返回对应的值
	 * 
	 * @param name
	 *            配置选项Key
	 * @return 配置选项的值
	 */
	public String getConfig(String name) {
		return this.getConfig(name, null);
	}

	/**
	 * 根据配置选项的Key返回对应的值，找不到则返回默认值
	 * 
	 * @param name
	 *            配置选项Key
	 * @param defaultValue
	 *            默认值
	 * @return 配置选项的值
	 */
	public String getConfig(String name, String defaultValue) {
		try {
			String value = prop.getProperty(name);
			return null == value ? defaultValue : value;
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public Properties getProp() {
		return prop;
	}

	public void setProp(Properties prop) {
		this.prop = prop;
	}

}
