package cn.richinfo.tsp.nodeserver.entrance;

import io.netty.channel.Channel;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import redis.clients.jedis.Jedis;
import cn.richinfo.tsp.nodeserver.asyn.CarStatusQueen;
import cn.richinfo.tsp.nodeserver.asyn.DriveCustomQueen;
import cn.richinfo.tsp.nodeserver.asyn.DriveDataQueen;
import cn.richinfo.tsp.nodeserver.asyn.ObdFaultCodeQueen;
import cn.richinfo.tsp.nodeserver.asyn.ObdLocationMileageQueen;
import cn.richinfo.tsp.nodeserver.asyn.ObdLocationQueen;
import cn.richinfo.tsp.nodeserver.asyn.ObdStatusQueen;
import cn.richinfo.tsp.nodeserver.asyn.TripReportDataQueen;
import cn.richinfo.tsp.nodeserver.asyn.WakeUpQueen;
import cn.richinfo.tsp.nodeserver.asyn.WarnDataQueen;
import cn.richinfo.tsp.nodeserver.mq.KafkaFacade;
import cn.richinfo.tsp.nodeserver.utils.Config;

/**
 * NodServer application context
 * 
 * @ClassName: NodeServerContext
 * @Description: TODO()
 * @author ÍõÈ«
 * @date 2014-11-20 ÏÂÎç2:51:40
 */
public class NodeServerContext {

	public Logger logger = Logger.getLogger(NodeServerContext.class);
	public String nodeId = "0";

	/** Name server properties */
	private static Config config;

	/** All NameServer Woker Thread group */
	private static ThreadGroup workGroups = new ThreadGroup("WorkerGroup");

	/**
	 * single instance
	 */
	private static NodeServerContext context;

	/**
	 * all connect session
	 */
	private ConcurrentHashMap<Long, Channel> sessions = new ConcurrentHashMap<Long, Channel>();

	private HbaseHook hbase;
	private Jedis jedis;
	private KafkaFacade kafkaFacade;

	private DriveDataQueen drive;
	private ObdLocationQueen obdLocation;
	private CarStatusQueen carStatus;
	private TripReportDataQueen tripReport;
	private ObdFaultCodeQueen obdFaultCode;
	private DriveCustomQueen driveCustom;
	private WarnDataQueen warnData;
	private WakeUpQueen wakeUp;
	private ObdLocationMileageQueen obdLocationMileage;
	private ObdStatusQueen obdStatus;

	private NodeServerContext() {
		// start worker thread

		// new CachedWorker(getWorkGroups(),
		// Constants.SERVER_LOGIN_CACHE_CHECKER)
		// .start();
		drive = new DriveDataQueen(this);
		obdLocation = new ObdLocationQueen(this);
		carStatus = new CarStatusQueen(this);
		tripReport = new TripReportDataQueen(this);
		obdFaultCode = new ObdFaultCodeQueen(this);
		driveCustom = new DriveCustomQueen(this);
		warnData = new WarnDataQueen(this);
		wakeUp = new WakeUpQueen(this);
		obdLocationMileage = new ObdLocationMileageQueen(this);
		obdStatus = new ObdStatusQueen(this);
	}

	public void setKafkaFacade(KafkaFacade kafkaFacade) {
		this.kafkaFacade = kafkaFacade;
	}

	public KafkaFacade getKafkaFacade() {
		return kafkaFacade;
	}

	public Jedis getJedis() {
		return jedis;
	}

	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}

	public ObdStatusQueen getObdStatus() {
		return obdStatus;
	}

	public ObdLocationMileageQueen getObdLocationMileage() {
		return obdLocationMileage;
	}

	public WakeUpQueen getWakeUp() {
		return wakeUp;
	}

	public WarnDataQueen getWarnData() {
		return warnData;
	}

	public DriveCustomQueen getDriveCustom() {
		return driveCustom;
	}

	public ObdFaultCodeQueen getObdFaultCode() {
		return obdFaultCode;
	}

	public TripReportDataQueen getTripReport() {
		return tripReport;
	}

	public String getNodeId() {
		return nodeId;
	}

	public CarStatusQueen getCarStatus() {
		return carStatus;
	}

	public HbaseHook getHbase() {
		return hbase;
	}

	public void setHbase(HbaseHook hbase) {
		this.hbase = hbase;
	}

	public ObdLocationQueen getObdLocation() {
		return obdLocation;
	}

	public DriveDataQueen getDrive() {
		return drive;
	}

	/**
	 * get {@link NodeServerContext}
	 * 
	 * @return
	 */
	public static NodeServerContext getContext() {
		if (context == null)
			context = new NodeServerContext();
		return context;
	}

	private ApplicationContext springContext;

	public ApplicationContext getSpringContext() {
		return springContext;
	}

	public void setSpringContext(ApplicationContext springContext) {
		this.springContext = springContext;
	}

	public ThreadGroup getWorkGroups() {
		return workGroups;
	}

	public ConcurrentHashMap<Long, Channel> getSessions() {
		return sessions;
	}

	public cn.richinfo.tsp.nodeserver.utils.Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		NodeServerContext.config = config;
		nodeId = config.getConfig(Constants.SERVER_NODE_ID);
	}

	public void dispose() {
		stopAllSession();
		stopAllInternalThread();
	}

	/**
	 * close all channel
	 */
	public void stopAllSession() {
		try {
			Iterator<Channel> sessionIter = sessions.values().iterator();
			while (sessionIter.hasNext()) {
				Channel se = sessionIter.next();
				if (se != null && se.isActive())
					se.close();
			}
		} catch (Exception e) {
			// just ignore
		}

	}

	/**
	 * shut down all nameserver user internal thread
	 */
	public void stopAllInternalThread() {
		try {
			workGroups.interrupt();
		} catch (Exception e) {
		}
	}

	/**
	 * shut down special thread
	 * 
	 * @param threadName
	 *            name of thread will be shutdown
	 */
	public boolean stopWorkerThread(String threadName) {
		try {
			int alive = workGroups.activeCount();
			Thread[] list = new Thread[alive];
			workGroups.enumerate(list);
			for (int i = 0; i < list.length; i++) {
				Thread t = list[i];
				if (t.getName().equals(threadName)) {
					if (t.isAlive())
						t.interrupt();
					if (t == null || !t.isAlive())
						return true;
				}
			}
		} catch (Exception e) {
			// just ignore
		}
		return false;
	}

}
