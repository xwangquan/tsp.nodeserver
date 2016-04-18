package cn.richinfo.tsp.nodeserver.hbase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.PropertyConfigurator;

public class HBase1 {
	static {
		// init log4j
		PropertyConfigurator.configure("./conf/log4j.properties");
	}

	public static String TABLE = "wangquan";

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Configuration HBASE_CONFIG = new Configuration();
		HBASE_CONFIG.set("hbase.zookeeper.quorum", "192.168.34.138");
		HBASE_CONFIG.set("hbase.zookeeper.property.clientPort", "2181");
		Configuration configuration = HBaseConfiguration.create(HBASE_CONFIG);
		create(configuration);
	}

	public static void create(Configuration configuration) throws Exception {
		HBaseAdmin hAdmin = new HBaseAdmin(configuration);
		HTableDescriptor t = new HTableDescriptor(TableName.valueOf(TABLE));
		t.addFamily(new HColumnDescriptor("f1"));
		t.addFamily(new HColumnDescriptor("f2"));
		t.addFamily(new HColumnDescriptor("f3"));
		t.addFamily(new HColumnDescriptor("f4"));
		//drop(configuration);
		//hAdmin.createTable(t);
		//insert(configuration);
		//list(configuration);
		get(configuration);
		delete(configuration);
		System.out.println("-----------------------");
		get(configuration);
	}
	public static void delete(Configuration configuration) throws Exception {
		HTable table = new HTable(configuration, TABLE);
		Delete d = new Delete("row000000001".getBytes());
		table.delete(d);
		
	}
	public static void get(Configuration configuration) throws Exception {
		HTable table = new HTable(configuration, TABLE);
		Get g = new Get("row000000001".getBytes());
		g.addFamily(Bytes.toBytes("f1"));
		Result rs = table.get(g);
		for (KeyValue kv : rs.raw()) {
			System.out.println("--------------------" + new String(kv.getRow())
					+ "----------------------------");
			System.out.println("Column Family: " + new String(kv.getFamily()));
			System.out
					.println("Column       :" + new String(kv.getQualifier()));
			System.out.println("value        : " + new String(kv.getValue()));
		}
	}

	public static void list(Configuration configuration) throws Exception {
		Scan s = new Scan();
		s.setMaxVersions();
		ResultScanner ss = new HTable(configuration, TABLE).getScanner(s);
		for (Result r : ss) {
			System.out.println(new String(r.getRow()));
			for (KeyValue kv : r.raw()) {
				System.out.println(new String(kv.getValue()));
			}
		}
	}

	public static void drop(Configuration configuration) throws Exception {
		HBaseAdmin hAdmin = new HBaseAdmin(configuration);
		if (hAdmin.tableExists(TABLE)) {
			hAdmin.disableTable(TABLE);
			hAdmin.deleteTable(TABLE);
		}
	}

	public static void insert(Configuration configuration) throws Exception {
		HTable table = new HTable(configuration, TABLE);
		// table.setAutoFlush(autoFlush);
		List lp = new ArrayList();
		int count = 10;
		byte[] buffer = new byte[5];
		Random r = new Random();
		for (int i = 1; i <= count; ++i) {
			Put p = new Put(String.format("row%09d", i).getBytes());
			r.nextBytes(buffer);
			p.add("f1".getBytes(), null, buffer);
			p.add("f2".getBytes(), null, buffer);
			p.add("f3".getBytes(), null, buffer);
			p.add("f4".getBytes(), null, buffer);
			// p.setWriteToWAL(true);
			lp.add(p);
			table.put(lp);
			lp.clear();
		}
	}

}
