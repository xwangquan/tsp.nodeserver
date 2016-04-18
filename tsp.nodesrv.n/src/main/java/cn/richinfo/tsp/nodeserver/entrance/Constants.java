package cn.richinfo.tsp.nodeserver.entrance;

/**
 * @ClassName: Constants
 * @Description: NodeServer Constants
 * @author 王全
 * @date 2014-8-14 下午3:27:44
 */
public class Constants {

	/** 配置文件目录 */
	public static final String SERVER_CONFIG_DIR = "./conf/";

	/** nameserver配置文件 */
	public static final String SERVER_CONFIG_FILE_NAME = "nodeserver.properties";
	/** kafka prop**/
	public static final String KAFKA_FILE_NAME = "kafka.properties";
	public static final String KAFKA_CONFIG = SERVER_CONFIG_DIR+KAFKA_FILE_NAME;

	/** nameserver配置文件路径 */
	public static final String SERVER_CONFIG_FILE = SERVER_CONFIG_DIR
			+ SERVER_CONFIG_FILE_NAME;

	public static final String SERVER_CLUSTER_BROADCAST_UDP = SERVER_CONFIG_DIR
			+ "tcp-nio.xml";

	/** db配置文件 */
	public static final String DB_CONFIG_FILE_NAME = "dbConfig.properties";
	/** db配置文件路径 */
	public static final String DB_CONFIG_FILE = SERVER_CONFIG_DIR
			+ DB_CONFIG_FILE_NAME;

	/** 服务端口 */
	public static final String SERVER_PORT = "server.port";

	/** 服务监控端口 */
	public static final String SERVER_MONITOR_PORT = "server.monitor.port";

	/** terminal idle time */
	public static final String SERVER_TERMINAL_SESSION_IDLE = "server.terminal.session.idle";

	/** terminal login ,server respond tea encryption or not */
	public static final String SERVER_TERMINAL_LOGIN_TEA = "server.terminal.login.tea";

	/** server print debug send messageg */
	public static final String SERVER_ENCODER_DEBUG = "server.encoder.debug";

	/** Sping conf */
	public static final String SPRING_PROPERTIES = "server.spring.context";

	/** rmi server name */
	public static final String SERVER_JMX_NAME = "server.jmx.name";

	/** RMI port */
	public static final String SERVER_JMX_PORT = "server.jmx.port";

	/** is jmx enable */
	public static final String SERVER_JMX_ENABLE = "server.jmx.enable";

	/** is jmx enable */
	public static final String SERVER_JETTY_ENABLE = "server.monitor.enable";
	
	/**
	 * node server tag
	 */
	public static final String SERVER_NODE_ID = "server.node.id";


	/**
	 * Jetty webapps path
	 */
	public static final String JETTY_WEBAPPS = "server.monitor.webDefDesc";
	public static final String JETTY_WEBAPPS_BASES = "server.monitor.resourceBase";
	public static final String JETTY_PORT = "server.monitor.port";

	public static final String LOG4J_PROPERTIES_NAME = "log4j.properties";
	/**
	 * LOG4J properties file
	 */
	public static final String LOG4J_PROPERTIES = SERVER_CONFIG_DIR
			+ LOG4J_PROPERTIES_NAME;

	/** 检查终端到nameserver会话线程 */
	public static final String TERM_SESSION_CHECK = "TermSessionCheckWorker";

	/** nameserver到nodeserver heart */
	public static final String NODE_SESSION_HEART = "NodeSessionHeartWorker";

	/** DriveDataQueen */
	public static final String SERVER_DRIVEDATA_QUEEN = "DriveDataQueen";
	public static final String SERVER_OBD_LOCATION_QUEEN = "ObdLocationQueen";
	public static final String SERVER_TRIP_REPORT_DATA_QUEEN= "TripReportDataQueen";
	public static final String SERVER_OBD_FAULT_CODE_QUEEN= "ObdFaultCodeQueen";
	public static final String SERVER_DRIVE_CUSTOM_QUEEN= "DriveCustomQueen";
	public static final String SERVER_WARN_DATA_QUEEN= "WarnDataQueen";
	public static final String SERVER_WAKEUP_QUEEN= "WakeUpQueen";
	public static final String SERVER_OBD_LOCATION_MILEAGE_QUEEN= "ObdLocationMileage";
	public static final String SERVER_OBD_STATUS_QUEEN= "ObdStatus";
	
	/** 设置终端参数,异步推送到终端 */
	public static final String SERVER_SET_TERMINAL_ITEM_Q2 = "ServerSetTerminalItemQ2";
	public static final String SERVER_SET_TERMINAL_ITEM_Q1 = "ServerSetTerminalItemQ1";
	/** SERVER_LOGIN_CACHE_CHECKER */
	public static final String SERVER_LOGIN_CACHE_CHECKER = "ServerLoginChecker";

	/** 车机异步激活队列 */
	public static final String SERVER_REGISTER_CAR_ENGINE_QUEEN = "ServerRegisterCarEngineQueen";

	/** 配置监控线程 */
	public static final String SERVER_PROPERTIES_THREAD = "ServerPropertiesThread";

	/** Jetty Thread Name */
	public static final String JETTY_THREAD = "JettyWorker";

	//
	public static final String REDIS_SERVER_IP="redis.server.address";
	public static final String REDIS_SERVER_PORT="redis.server.port";
	// ----------------------------------------------------session
	public static final String SESSION_TERMINAL_LOGIN_IN = "terminal_login_in";
}
