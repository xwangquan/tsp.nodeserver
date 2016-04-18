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
import cn.richinfo.tsp.nodeserver.message.sutrans.ObdFaultCodeMessage;
import cn.richinfo.tsp.nodeserver.persistence.bean.ObdFaultCodeBean;
import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;

/**
 * @ClassName: ObdFaultCodeQueen
 * @Description: TODO()
 * @author ÍõÈ«
 * @date 2015-11-6 ÏÂÎç4:00:39
 */
public class ObdFaultCodeQueen extends NodeServerQueen<ObdFaultCodeMessage> {

	private static Logger log = Logger.getLogger(ObdLocationQueen.class);
	private static final long serialVersionUID = -2062036043163585987L;
	private NodeServerContext context;
	private int WORKERS = 1;
	private String HBASE_TABLE = "ObdFaultInfo";

	public ObdFaultCodeQueen(NodeServerContext contex) {
		this.context = contex;
		init();
	}

	public void init() {
		for (int i = 0; i < WORKERS; i++) {

			new HbaseObdFaultCodeWorker(context.getWorkGroups(),
					Constants.SERVER_OBD_FAULT_CODE_QUEEN + i).start();
		}
		// new Printer().start();
	}

	class Printer extends Thread {

		@Override
		public void run() {
			while (true) {
				try {
					TimeUnit.SECONDS.sleep(10);
					System.out.println("ObdFaultCode size:" + size());
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

	class HbaseObdFaultCodeWorker extends Thread {

		public HbaseObdFaultCodeWorker(ThreadGroup group, String name) {
			super(group, name);
		}

		HTable table = null;

		public void store(ObdFaultCodeMessage message) throws Exception {
			ObdFaultCodeBean bean = new ObdFaultCodeBean();
			byte[] _uin = new byte[4];
			System.arraycopy(message.getSession(), 0, _uin, 0, 4);
			bean.setUin(ConversionUtil.byte2int(_uin));
			bean.setNodeid(Integer.parseInt(context.getNodeId()));			bean.setTrip_id("I don't know what it is!");// todo
			bean.setVtu_id(message.getTuid().trim());
			// bean.setCreate_time(format.format(System.currentTimeMillis()));
			bean.setId(123456789);
			bean.setFault_code(message.getFault_code());
			bean.setFault_count(message.getCodeNum());
			bean.setReport_time(format.format(new Date(message.getTime())));

			if (table == null) {
				table = new HTable(context.getHbase().getConfiguration(),
						HBASE_TABLE);
				// table.setWriteBufferSize(6 * 1024 * 1024);
				// table.setAutoFlush(false, false);
			}

			Put put = new Put(Bytes.toBytes(message.getTuid().trim()));
			// row_key= carid_reporttime_tuid
			put.add("info".getBytes(), Bytes.toBytes("id"),
					Bytes.toBytes(bean.getId() + ""));
			put.add("info".getBytes(), Bytes.toBytes("fault_code"),
					Bytes.toBytes(bean.getFault_code() + ""));
			put.add("info".getBytes(), Bytes.toBytes("fault_count"),
					Bytes.toBytes(bean.getFault_count() + ""));
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
					ObdFaultCodeMessage message = (ObdFaultCodeMessage) poll();
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
