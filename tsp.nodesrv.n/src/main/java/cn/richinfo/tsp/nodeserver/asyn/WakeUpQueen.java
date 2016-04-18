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
import cn.richinfo.tsp.nodeserver.message.sutrans.WakeUpMessage;
import cn.richinfo.tsp.nodeserver.persistence.bean.WakeUpBean;
import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;

/**
 * 
 * @ClassName: DriveCustomQueen
 * @Description: async processer
 * @author ÍõÈ«
 * @date 2014-11-20 ÏÂÎç1:52:19
 * @param <WakeUpMessage>
 */
public class WakeUpQueen extends NodeServerQueen<WakeUpMessage> {

	private static Logger log = Logger.getLogger(ObdLocationQueen.class);
	private static final long serialVersionUID = -2062036043163585987L;
	private NodeServerContext context;
	private int WORKERS = 1;
	private String HBASE_TABLE = "CarAutoAwakenInfo";

	public WakeUpQueen(NodeServerContext contex) {
		this.context = contex;
		init();
	}

	public void init() {
		for (int i = 0; i < WORKERS; i++) {

			new HbaseWakeUpWorker(context.getWorkGroups(),
					Constants.SERVER_WAKEUP_QUEEN + i).start();
		}
		// new Printer().start();
	}

	class Printer extends Thread {

		@Override
		public void run() {
			while (true) {
				try {
					TimeUnit.SECONDS.sleep(10);
					System.out.println("WakeUp size:" + size());
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

	class HbaseWakeUpWorker extends Thread {

		public HbaseWakeUpWorker(ThreadGroup group, String name) {
			super(group, name);
		}

		HTable table = null;

		public void store(WakeUpMessage message) throws Exception {
			WakeUpBean bean = new WakeUpBean();
			byte[] _uin = new byte[4];
			System.arraycopy(message.getSession(), 0, _uin, 0, 4);
			bean.setUin(ConversionUtil.byte2int(_uin));
			bean.setNodeid(Integer.parseInt(context.getNodeId()));			bean.setTrip_id("I don't know what it is!");// todo
			bean.setVtu_id(message.getTuid().trim());
			bean.setCreate_time(format.format(System.currentTimeMillis()));
			bean.setId(123456789);
			bean.setReport_time(format.format(new Date(message.getTime())));
			bean.setStatus(message.getGsp_status());
			bean.setLattitude(message.getLattitude());
			bean.setLongitude(message.getLongitude());
			bean.setBatteryVoltage(message.getBatteryVoltage());
			bean.setDuration(message.getDuration());
			if (table == null) {
				table = new HTable(context.getHbase().getConfiguration(),
						HBASE_TABLE);
				// table.setWriteBufferSize(6 * 1024 * 1024);
				// table.setAutoFlush(false, false);
			}
			Put put = new Put(Bytes.toBytes(message.getTuid().trim()));
			//
			put.add("info".getBytes(), Bytes.toBytes("gsp_status"),
					Bytes.toBytes(bean.getGsp_status() + ""));
			put.add("info".getBytes(), Bytes.toBytes("longitude"),
					Bytes.toBytes(bean.getLongitude() + ""));
			put.add("info".getBytes(), Bytes.toBytes("lattitude"),
					Bytes.toBytes(bean.getLattitude() + ""));
			put.add("info".getBytes(), Bytes.toBytes("type"),
					Bytes.toBytes(bean.getType() + ""));
			put.add("info".getBytes(), Bytes.toBytes("batteryVoltage"),
					Bytes.toBytes(bean.getBatteryVoltage() + ""));
			put.add("info".getBytes(), Bytes.toBytes("duration"),
					Bytes.toBytes(bean.getDuration() + ""));
			// row_key= carid_reporttime_tuid

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
		}

		public void safeAndExit() {
		}

		@Override
		public void run() {
			while (true) {
				try {
					WakeUpMessage message = (WakeUpMessage) poll();
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
