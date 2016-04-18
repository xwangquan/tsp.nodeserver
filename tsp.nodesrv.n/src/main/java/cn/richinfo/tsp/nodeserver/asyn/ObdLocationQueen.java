package cn.richinfo.tsp.nodeserver.asyn;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import cn.richinfo.tsp.nodeserver.entrance.Constants;
import cn.richinfo.tsp.nodeserver.entrance.NodeServerContext;
import cn.richinfo.tsp.nodeserver.message.sutrans.ObdLocationMessage;
import cn.richinfo.tsp.nodeserver.persistence.bean.ObdLocationBean;
import cn.richinfo.tsp.nodeserver.persistence.info.ObdLocation;
import cn.richinfo.tsp.nodeserver.persistence.info.ObdLocationKafka;
import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;

/**
 * @ClassName: ObdLocationMessage
 * @Description: ObdLocationMessage worker
 * @author ÍõÈ«
 * @date 2014-11-20 ÏÂÎç1:50:04
 */
public class ObdLocationQueen extends NodeServerQueen<ObdLocationMessage> {
	private static Logger log = Logger.getLogger(ObdLocationQueen.class);
	private static final long serialVersionUID = -2062036043163585987L;
	private NodeServerContext context;
	private int WORKERS = 1;
	private String HBASE_TABLE = "CarPositionInfo";

	public ObdLocationQueen(NodeServerContext contex) {
		this.context = contex;
		init();
	}

	public void init() {
		for (int i = 0; i < WORKERS; i++) {

			new HbaseObdLocationWorker(context.getWorkGroups(),
					Constants.SERVER_OBD_LOCATION_QUEEN + i).start();
		}
		// new Printer().start();
	}

	class Printer extends Thread {

		@Override
		public void run() {
			while (true) {
				try {
					TimeUnit.SECONDS.sleep(10);
					System.out.println("ObdLocation size:" + size());
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

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	class HbaseObdLocationWorker extends Thread {

		public HbaseObdLocationWorker(ThreadGroup group, String name) {
			super(group, name);
		}

		HTable table = null;

		public void store(ObdLocationMessage message) throws Exception {
			ObdLocationBean bean = new ObdLocationBean();
			byte[] _uin = new byte[4];
			System.arraycopy(message.getSession(), 0, _uin, 0, 4);
			bean.setUin(ConversionUtil.byte2int(_uin));
			byte[] _carId = new byte[4];
			int carId = 0;
			System.arraycopy(message.getSession(), 0, _carId, 4, 4);
			try {
				carId = ConversionUtil.byte2int(_carId);
			} catch (Exception e) {// ignore
			}
			bean.setNodeid(Integer.parseInt(context.getNodeId()));
			bean.setTrip_id("I don't know what it is!");// todo
			bean.setVtu_id(message.getTuid().trim());
			// bean.setCreate_time(format.format(System.currentTimeMillis()));
			bean.setId(123456789);
			bean.setReport_time(format.format(new Date(message.getTime())));
			ObdLocation data = new ObdLocation(message);
			bean.setCar_position_info(data.toString());

			if (table == null) {
				table = new HTable(context.getHbase().getConfiguration(),
						HBASE_TABLE);
				// table.setWriteBufferSize(6 * 1024 * 1024);
				// table.setAutoFlush(false, false);
			}

			Put put = new Put(Bytes.toBytes(message.getTuid().trim()));
			// row_key= carid_reporttime_tuid
			put.add("info".getBytes(), Bytes.toBytes("car_position_info"),
					Bytes.toBytes(bean.getCar_position_info() + ""));
			put.add("info".getBytes(), Bytes.toBytes("create_time"),
					Bytes.toBytes(bean.getCreate_time() + ""));
			put.add("info".getBytes(), Bytes.toBytes("id"),
					Bytes.toBytes(bean.getId() + ""));
			put.add("info".getBytes(), Bytes.toBytes("nodeid"),
					Bytes.toBytes(bean.getNodeid() + ""));
			put.add("info".getBytes(), Bytes.toBytes("report_time"),
					Bytes.toBytes(bean.getReport_time() + ""));
			put.add("info".getBytes(), Bytes.toBytes("trip_id"),
					Bytes.toBytes(bean.getTrip_id() + ""));
			put.add("info".getBytes(), Bytes.toBytes("uin"),
					Bytes.toBytes(bean.getUin() + ""));
			put.add("info".getBytes(), Bytes.toBytes("vtu_id"),
					Bytes.toBytes(bean.getVtu_id() + ""));
			table.put(put);

			// kafka
			if (carId != 0 && isCompanyCar(carId + "")) {
				// {uin:10000,carId:0,vtuId:00001000-0000-1000-8000-00805F9B34FB,status:-1,lat:4294.967295,lon:4294.967295,height:65535,speed:6553.5,direction:65535,reportTime:20140808160434}
				ObdLocationKafka kafka = new ObdLocationKafka(message,
						bean.getUin(), carId);
				setKafka(kafka.toString());
			}
		}

		// todo
		public boolean isCompanyCar(String carId) {
			String result = context.getJedis().hget("VEHBINDRULES", carId);
			if (result != null)
				return true;
			return false;
		}

		// todo
		public void setKafka(String json) {
			try {
				context.getKafkaFacade().put(json);
			} catch (Exception e) {
				log.error(e);
			}
		}

		public void safeAndExit() {
		}

		@Override
		public void run() {
			while (true) {
				try {
					ObdLocationMessage message = (ObdLocationMessage) poll();
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

}
