package cn.richinfo.tsp.nodeserver.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * NameServer ����
 * 
 * @ClassName: Config
 * @Description: TODO()
 * @author ��ȫ
 * @date 2014-8-14 ����3:21:48
 */
public class Config {

	private Logger log = Logger.getLogger(Config.class);

	private Properties prop = null;

	/**
	 * @param configFileName
	 *            �����ļ�
	 * @param byClassPath
	 *            �����ļ��Ƿ�����·���£�����Ϊ����·��
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
	 * ��������ѡ���Key���ض�Ӧ��ֵ
	 * 
	 * @param name
	 *            ����ѡ��Key
	 * @return ����ѡ���ֵ
	 */
	public String getConfig(String name) {
		return this.getConfig(name, null);
	}

	/**
	 * ��������ѡ���Key���ض�Ӧ��ֵ���Ҳ����򷵻�Ĭ��ֵ
	 * 
	 * @param name
	 *            ����ѡ��Key
	 * @param defaultValue
	 *            Ĭ��ֵ
	 * @return ����ѡ���ֵ
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
