package cn.richinfo.tsp.nodeserver.entrance;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.log4j.Logger;

import cn.richinfo.tsp.nodeserver.asyn.DriveDataQueen;

/**
 * @ClassName: HbaseHook
 * @Description: hadoop distributed persistence facade!
 * @author ÍõÈ«
 * @date 2015-8-5 ÏÂÎç3:53:51
 */
public class HbaseHook {
	private static Logger log = Logger.getLogger(DriveDataQueen.class);
	private Configuration configuration;
	private HBaseAdmin hBaseAdmin;

	public void init() {

		try {
			Configuration HBASE_CONFIG = new Configuration();
			HBASE_CONFIG.set(
					"hbase.zookeeper.quorum",
					NodeServerContext
							.getContext()
							.getConfig()
							.getConfig("hbase.zookeeper.quorum",
									"192.168.9.219"));
			// HBASE_CONFIG.set("hbase.zookeeper.quorum", "192.168.34.138");
			HBASE_CONFIG.set(
					"hbase.zookeeper.property.clientPort",
					NodeServerContext
							.getContext()
							.getConfig()
							.getConfig("hbase.zookeeper.property.clientPort",
									"2181"));
			configuration = HBaseConfiguration.create(HBASE_CONFIG);
			hBaseAdmin = new HBaseAdmin(configuration);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public HBaseAdmin gethBaseAdmin() {
		return hBaseAdmin;
	}

	public Configuration getConfiguration() {
		return configuration;
	}
}
