package cn.richinfo.tsp.nodeserver.asyn;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import cn.richinfo.tsp.nodeserver.entrance.Constants;
import cn.richinfo.tsp.nodeserver.entrance.NodeServerContext;
import cn.richinfo.tsp.nodeserver.message.sutrans.TripReportDataMessage;
import cn.richinfo.tsp.nodeserver.persistence.bean.TripReportDataBeen;
import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;

/**
 * @ClassName: TripReportDataQueen
 * @Description: TODO()
 * @author ÍõÈ«
 * @date 2015-11-6 ÏÂÎç3:49:25
 */
public class TripReportDataQueen extends NodeServerQueen<TripReportDataMessage> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(TripReportDataQueen.class);
	private NodeServerContext context;
	private int WORKERS = 1;
	private String HBASE_TABLE = "CarTripInfo";

	public TripReportDataQueen(NodeServerContext contex) {
		this.context = contex;
		init();
	}

	public void init() {
		for (int i = 0; i < WORKERS; i++) {

			new HbaseTripReportWorker(context.getWorkGroups(),
					Constants.SERVER_TRIP_REPORT_DATA_QUEEN + i).start();
		}
		//new Printer().start();
	}

	class Printer extends Thread {

		@Override
		public void run() {
			while (true) {
				try {
					TimeUnit.SECONDS.sleep(10);
					System.out.println("TripReportData size:" + size());
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

	class HbaseTripReportWorker extends Thread {

		public HbaseTripReportWorker(ThreadGroup group, String name) {
			super(group, name);
		}

		HTable table = null;

		public void store(TripReportDataMessage message) throws Exception {
			TripReportDataBeen bean = new TripReportDataBeen();
			byte[] _uin = new byte[4];
			System.arraycopy(message.getSession(), 0, _uin, 0, 4);
			bean.setUin(ConversionUtil.byte2int(_uin));
			bean.setNodeid(Integer.parseInt(context.getNodeId()));			bean.setTrip_id("I don't know what it is!");// todo
			bean.setVtu_id(message.getTuid().trim());
			// bean.setCreate_time(format.format(System.currentTimeMillis()));
			bean.setId(123456789);
			bean.setMessage(message);
			if (table == null) {
				table = new HTable(context.getHbase().getConfiguration(),
						HBASE_TABLE);
				// table.setWriteBufferSize(6 * 1024 * 1024);
				// table.setAutoFlush(false, false);
			}

			Put put = new Put(Bytes.toBytes(message.getTuid().trim()));
			// row_key= carid_reporttime_tuid
			put.add("info".getBytes(), Bytes.toBytes("start_time"),
					Bytes.toBytes(bean.getMessage().getStart_time() + ""));
			put.add("info".getBytes(), Bytes.toBytes("end_time"),
					Bytes.toBytes(bean.getMessage().getEnd_time() + ""));
			put.add("info".getBytes(), Bytes.toBytes("type"),
					Bytes.toBytes(bean.getMessage().getType() + ""));
			put.add("info".getBytes(), Bytes.toBytes("trip_mileage"),
					Bytes.toBytes(bean.getMessage().getTrip_mileage() + ""));
			put.add("info".getBytes(), Bytes.toBytes("total_mileage"),
					Bytes.toBytes(bean.getMessage().getTotal_mileage() + ""));
			put.add("info".getBytes(),
					Bytes.toBytes("trip_fuel_consumption"),
					Bytes.toBytes(bean.getMessage().getTrip_fuel_consumption()
							+ ""));
			put.add("info".getBytes(),
					Bytes.toBytes("total_fuel_consumption"),
					Bytes.toBytes(bean.getMessage().getTotal_fuel_consumption()
							+ ""));
			put.add("info".getBytes(),
					Bytes.toBytes("avg_fuel_consumption"),
					Bytes.toBytes(bean.getMessage().getAvg_fuel_consumption()
							+ ""));
			put.add("info".getBytes(), Bytes.toBytes("overspeed_time"),
					Bytes.toBytes(bean.getMessage().getOverspeed_time() + ""));
			put.add("info".getBytes(),
					Bytes.toBytes("engine_highspeed_times"),
					Bytes.toBytes(bean.getMessage().getEngine_highspeed_times()
							+ ""));
			put.add("info".getBytes(),
					Bytes.toBytes("highspeed_driving_time"),
					Bytes.toBytes(bean.getMessage().getHighspeed_driving_time()
							+ ""));
			put.add("info".getBytes(), Bytes.toBytes("long_idle_times"),
					Bytes.toBytes(bean.getMessage().getLong_idle_times() + ""));
			put.add("info".getBytes(),
					Bytes.toBytes("max_water_temperature"),
					Bytes.toBytes(bean.getMessage().getMax_water_temperature()
							+ ""));

			put.add("info".getBytes(),
					Bytes.toBytes("total_idle_consumption"),
					Bytes.toBytes(bean.getMessage().getTotal_idle_consumption()
							+ ""));
			put.add("info".getBytes(),
					Bytes.toBytes("total_fatigue_duration"),
					Bytes.toBytes(bean.getMessage().getTotal_fatigue_duration()
							+ ""));
			put.add("info".getBytes(), Bytes.toBytes("avg_speed"),
					Bytes.toBytes(bean.getMessage().getAvg_speed() + ""));
			put.add("info".getBytes(), Bytes.toBytes("max_speed"),
					Bytes.toBytes(bean.getMessage().getMax_speed() + ""));
			put.add("info".getBytes(), Bytes.toBytes("max_4otationl"),
					Bytes.toBytes(bean.getMessage().getMax_4otationl() + ""));
			put.add("info".getBytes(), Bytes.toBytes("total_idle_time"),
					Bytes.toBytes(bean.getMessage().getTotal_idle_time() + ""));

			put.add("info".getBytes(), Bytes.toBytes("id"),
					Bytes.toBytes(bean.getId() + ""));
			put.add("info".getBytes(), Bytes.toBytes("nodeid"),
					Bytes.toBytes(bean.getNodeid() + ""));
			put.add("info".getBytes(), Bytes.toBytes("trip_id"),
					Bytes.toBytes(bean.getTrip_id() + ""));
			put.add("info".getBytes(), Bytes.toBytes("uin"),
					Bytes.toBytes(bean.getUin() + ""));
			put.add("info".getBytes(), Bytes.toBytes("vtu_id"),
					Bytes.toBytes(bean.getVtu_id() + ""));
			table.put(put);
		}

		public void safeAndExit() {
		}

		@Override
		public void run() {
			while (true) {
				try {
					TripReportDataMessage message = (TripReportDataMessage) poll();
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

}
