package cn.richinfo.tsp.nodeserver.asyn;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import cn.richinfo.tsp.nodeserver.entrance.Constants;
import cn.richinfo.tsp.nodeserver.entrance.NodeServerContext;
import cn.richinfo.tsp.nodeserver.message.sutrans.DriveCustomMessage;
import cn.richinfo.tsp.nodeserver.persistence.bean.DriveCustomBean;
import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;

/**
 * 
 * @ClassName: DriveCustomQueen
 * @Description: async processer
 * @author ÍõÈ«
 * @date 2014-11-20 ÏÂÎç1:52:19
 * @param <DriveCustomMessage>
 */
public class DriveCustomQueen extends NodeServerQueen<DriveCustomMessage> {

	private static Logger log = Logger.getLogger(ObdLocationQueen.class);
	private static final long serialVersionUID = -2062036043163585987L;
	private NodeServerContext context;
	private int WORKERS = 1;
	private String HBASE_TABLE = "ObdDriveHabit";

	public DriveCustomQueen(NodeServerContext contex) {
		this.context = contex;
		init();
	}

	public void init() {
		for (int i = 0; i < WORKERS; i++) {

			new HbaseDriveCustomWorker(context.getWorkGroups(),
					Constants.SERVER_DRIVE_CUSTOM_QUEEN + i).start();
		}
		// new Printer().start();
	}

	class Printer extends Thread {

		@Override
		public void run() {
			while (true) {
				try {
					TimeUnit.SECONDS.sleep(10);
					System.out.println("DriveCustom size:" + size());
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

	class HbaseDriveCustomWorker extends Thread {

		public HbaseDriveCustomWorker(ThreadGroup group, String name) {
			super(group, name);
		}

		HTable table = null;

		public void store(DriveCustomMessage message) throws Exception {
			DriveCustomBean bean = new DriveCustomBean();
			byte[] _uin = new byte[4];
			System.arraycopy(message.getSession(), 0, _uin, 0, 4);
			bean.setUin(ConversionUtil.byte2int(_uin));
			bean.setNodeid(Integer.parseInt(context.getNodeId()));			bean.setTrip_id("I don't know what it is!");// todo
			bean.setVtu_id(message.getTuid().trim());
			// bean.setCreate_time(format.format(System.currentTimeMillis()));
			bean.setId(123456789);
			bean.setReport_time(format.format(new Date(message.getTime())));
			int _type = message.getDriving_event_type();

			bean.setDirving_event_type(_type + "");

			if (table == null) {
				table = new HTable(context.getHbase().getConfiguration(),
						HBASE_TABLE);
				// table.setWriteBufferSize(6 * 1024 * 1024);
				// table.setAutoFlush(false, false);
			}
			ArrayList<Put> puts = new ArrayList<Put>();
			if ((_type & 1) == 1) {
				bean.setDirving_event_type("0");
				bean.setDirving_event_data(new StringBuilder()
						.append("{duration:")
						.append(message.getSliding().duration)
						.append(",speed:").append(message.getSliding().speed)
						.append("}").toString());
				Put put = put(message, bean);
				puts.add(put);
			}
			if ((_type >> 1 & 1) == 1) {
				bean.setDirving_event_type("1");
				bean.setDirving_event_data(new StringBuilder()
						.append("{duration:")
						.append(message.getLongDuration().duration).append("}")
						.toString());
				Put put = put(message, bean);
				puts.add(put);
			}
			if ((_type >> 2 & 1) == 1) {
				bean.setDirving_event_type("2");
				bean.setDirving_event_data(new StringBuilder()
						.append("{duration:")
						.append(message.getSpeedNotMatch().duration)
						.append(",speed:")
						.append(message.getSpeedNotMatch().speed)
						.append(",rpm:").append(message.getSpeedNotMatch().rpm)
						.append("}").toString());
				Put put = put(message, bean);
				puts.add(put);
			}
			if ((_type >> 3 & 1) == 1) {
				bean.setDirving_event_type("3");
				bean.setDirving_event_data(new StringBuilder()
						.append("{duration:")
						.append(message.getHighSpeed().duration)
						.append(",rpm:").append(message.getHighSpeed().rpm)
						.append("}").toString());
				Put put = put(message, bean);
				puts.add(put);
			}
			if ((_type >> 4 & 1) == 1) {
				bean.setDirving_event_type("4");
				bean.setDirving_event_data(new StringBuilder()
						.append("{turn_direction:")
						.append(message.getSuddenTurn().turn_direction)
						.append(",palstance:")
						.append(message.getSuddenTurn().palstance)
						.append(",acceleration:")
						.append(message.getSuddenTurn().acceleration)
						.append("}").toString());
				Put put = put(message, bean);
				puts.add(put);
			}
			if ((_type >> 5 & 1) == 1) {
				bean.setDirving_event_type("5");
				bean.setDeceleration(message.getDeceleration());
				bean.setDirving_event_data(new StringBuilder()
						.append("{deceleration:")
						.append(bean.getDeceleration()).append("}").toString());
				Put put = put(message, bean);
				puts.add(put);
			}
			if ((_type >> 6 & 1) == 1) {
				bean.setDirving_event_type("6");
				bean.setAcceleration(message.getAcceleration());
				bean.setDirving_event_data(new StringBuilder()
						.append("{acceleration:")
						.append(bean.getAcceleration()).append("}").toString());
				Put put = put(message, bean);
				puts.add(put);
			}
			if ((_type >> 7 & 1) == 1) {
				bean.setDirving_event_type("7");
				bean.setAcceleration(message.getAcceleration());
				bean.setDirving_event_data(new StringBuilder()
						.append("{fatigue_driving_time:")
						.append(message.getFatigue_driving_time()).append("}")
						.toString());
				Put put = put(message, bean);
				puts.add(put);
			}
			// flush together
			table.put(puts);
		}

		private Put put(DriveCustomMessage message, DriveCustomBean bean) {
			Put put = new Put(Bytes.toBytes(message.getTuid().trim()));
			// row_key= carid_reporttime_tuid
			put.add("info".getBytes(), Bytes.toBytes("dirving_event_type"),
					Bytes.toBytes(bean.getDirving_event_type()));
			put.add("info".getBytes(), Bytes.toBytes("dirving_event_data"),
					Bytes.toBytes(bean.getDirving_event_data()));
			// common
			put.add("info".getBytes(), Bytes.toBytes("type"),
					Bytes.toBytes(message.getType() + ""));
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
			return put;
		}

		public void safeAndExit() {
		}

		@Override
		public void run() {
			while (true) {
				try {
					DriveCustomMessage message = (DriveCustomMessage) poll();
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
