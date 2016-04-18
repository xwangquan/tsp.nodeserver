package cn.richinfo.tsp.nodeserver.asyn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import cn.richinfo.tsp.nodeserver.entrance.Constants;
import cn.richinfo.tsp.nodeserver.entrance.NodeServerContext;
import cn.richinfo.tsp.nodeserver.message.sutrans.DriveDataMessage;
import cn.richinfo.tsp.nodeserver.persistence.bean.CarDrivingBean;
import cn.richinfo.tsp.nodeserver.persistence.info.DriveData;
import cn.richinfo.tsp.nodeserver.persistence.service.CarDrivingService;
import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;

/**
 * @ClassName: DriveDataQueen
 * @Description: DriveData worker
 * @author ÍõÈ«
 * @date 2014-11-20 ÏÂÎç1:50:04
 */
public class DriveDataQueen extends NodeServerQueen<DriveDataMessage> {
	private static Logger log = Logger.getLogger(DriveDataQueen.class);
	private static final long serialVersionUID = -2062036043163585987L;
	private NodeServerContext context;
	private int WORKERS = 1;
	private String HBASE_TABLE = "CarDrivingInfo";

	public DriveDataQueen(NodeServerContext contex) {
		this.context = contex;
		init();
	}

	public void init() {
		for (int i = 0; i < WORKERS; i++) {
			// new Worker(context.getWorkGroups(),
			// Constants.SERVER_DRIVEDATA_QUEEN + i).start();
			new HbaseDriveDataWorker(context.getWorkGroups(),
					Constants.SERVER_DRIVEDATA_QUEEN + i).start();
			// flush = new Flush();

		}
		//new Printer().start();
	}

	class Printer extends Thread {

		@Override
		public void run() {
			while (true) {
				try {
					TimeUnit.SECONDS.sleep(10);
					System.out.println("DriveData size:" + size());
				} catch (InterruptedException e) {
					log.warn(getName() + " thread was interrupted!");
					return;
				} catch (Exception e) {
					log.error(getName() + " was stoped!");
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e1) {
						// ignore
					}
				}
			}
		}
	}

	class HbaseDriveDataWorker extends Thread {

		public HbaseDriveDataWorker(ThreadGroup group, String name) {
			super(group, name);
		}

		HTable table = null;
		ArrayList<Put> list;

		public void store(DriveDataMessage message) throws Exception {
			CarDrivingBean bean = new CarDrivingBean();
			byte[] _uin = new byte[4];
			System.arraycopy(message.getSession(), 0, _uin, 0, 4);
			bean.setUin(ConversionUtil.byte2int(_uin));
			bean.setNodeid(Integer.parseInt(context.getNodeId()));			bean.setTrip_id("I don't know what it is!");// todo
			bean.setVtu_id(message.getTuid().trim());

			DriveData data = new DriveData(message);
			bean.setCar_driving_info(data.toString());

			if (table == null) {
				table = new HTable(context.getHbase().getConfiguration(),
						HBASE_TABLE);
				// table.setWriteBufferSize(6 * 1024 * 1024);
				// table.setAutoFlush(false, false);
			}

			Put put = new Put(Bytes.toBytes(message.getTuid().trim()));
			// row_key= carid_reporttime_tuid
			put.add("info".getBytes(), Bytes.toBytes("uin"),
					Bytes.toBytes(bean.getUin() + ""));
			put.add("info".getBytes(), Bytes.toBytes("trip_id"),
					Bytes.toBytes(bean.getTrip_id() + ""));
			put.add("info".getBytes(), Bytes.toBytes("car_driving_info"),
					Bytes.toBytes(bean.getCar_driving_info() + ""));
			put.add("info".getBytes(), Bytes.toBytes("vtu_id"),
					Bytes.toBytes(bean.getVtu_id() + ""));
			put.add("info".getBytes(), Bytes.toBytes("nodeid"),
					Bytes.toBytes(bean.getNodeid() + ""));
			table.put(put);
		}

		public void safeAndExit() {
		}

		@Override
		public void run() {
			while (true) {
				try {
					DriveDataMessage message = (DriveDataMessage) poll();
					if (message == null) {
						TimeUnit.SECONDS.sleep(3);
						continue;
					}
					// do something
					message.toString();
					// log.info(message.toString());
					store(message);
				} catch (InterruptedException e) {
					log.warn(getName() + " thread was interrupted!");
					safeAndExit();
					return;
				} catch (Exception e) {
					log.error(getName() + " was stoped!", e);
					safeAndExit();
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e1) {
						// ignore
					}
				}
			}
		}
	}

	// row_key= carid_reporttime_tuid
	@Deprecated
	class Worker extends Thread {

		public Worker(ThreadGroup group, String name) {
			super(group, name);
		}

		public void store(DriveDataMessage message) {
			CarDrivingService userService = (CarDrivingService) context
					.getSpringContext().getBean("carDrivingService");
			CarDrivingBean bean = new CarDrivingBean();
			bean.setUin(11111);
			bean.setNodeid(1);
			bean.setTrip_id("aaaaaaaaa");
			bean.setVtu_id(message.getTuid().trim());

			DriveData data = new DriveData(message);
			bean.setCar_driving_info(data.toString());

			Map<String, Object> map = new HashMap<>();
			map.put("uin", bean.getUin());
			map.put("trip_id", bean.getTrip_id());
			map.put("car_driving_info", bean.getCar_driving_info());
			map.put("vtu_id", bean.getVtu_id());
			map.put("nodeid", bean.getNodeid());
			userService.storeDriveData(map);
		}

		public void safeAndExit() {
		}

		@Override
		public void run() {
			while (true) {
				try {
					DriveDataMessage message = (DriveDataMessage) poll();
					if (message == null) {
						TimeUnit.SECONDS.sleep(3);
						continue;
					}
					// do something
					message.toString();
					// log.info(message.toString());
					// store(message);
				} catch (InterruptedException e) {
					log.warn(getName() + " thread was interrupted!");
					safeAndExit();
					return;
				} catch (Exception e) {
					log.error(getName() + " was stoped!");
					safeAndExit();
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e1) {
						// ignore
					}
				}
			}
		}
	}
}
