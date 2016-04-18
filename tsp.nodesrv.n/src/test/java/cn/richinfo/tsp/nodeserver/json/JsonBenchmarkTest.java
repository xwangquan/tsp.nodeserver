package cn.richinfo.tsp.nodeserver.json;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Date;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import cn.richinfo.tsp.nodeserver.client.NodeServerSession;
import cn.richinfo.tsp.nodeserver.message.sutrans.DummyDriveDataMessage;
import cn.richinfo.tsp.nodeserver.persistence.info.DriveData;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Client entrance benchmark test!
 * 
 * @ClassName: MockTerminalBenchmarkTest
 * @Description: for json write test
 * @author 王全
 * @date 2014-8-1 下午6:03:00
 */
public class JsonBenchmarkTest {
	public ObjectMapper mapper = new ObjectMapper();
	static	File  file = new File("result.json");
	static Writer writer;

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// cycle send message 8400万
		int thread = 30;
		// int count = 6000000;
		int count = 10000;
		writer = new FileWriter(file, true);
		CyclicBarrier cyclicBarrier = new CyclicBarrier(thread + 1);
		AtomicLong sendMessageCount = new AtomicLong(0);
		AtomicLong receiveMessageCount = new AtomicLong(0);
		AtomicLong timeoutMessageCount = new AtomicLong(0);
		NodeServerSession session = null;
		for (int i = 0; i < thread; i++) {
			new JsonBenchmarkTest().new Run(cyclicBarrier, session, count,
					sendMessageCount, receiveMessageCount, timeoutMessageCount)
					.start();
		}
		try {
			cyclicBarrier.await();
			// statistics send message count
			while (true) {
				System.out.println(new Date() + " Send : "
						+ sendMessageCount.get() + ",Receicve : "
						+ receiveMessageCount.get() + ",Timeout : "
						+ timeoutMessageCount.get());
				TimeUnit.SECONDS.sleep(5);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * Cycle send message
	 * 
	 * @author William Wang
	 * @version $Rev: $, Dec 5, 2011 11:32:00 PM
	 */
	class Run extends Thread {

		CyclicBarrier cyclicBarrier;

		NodeServerSession session;

		int num;

		AtomicLong sendMessageCount;
		AtomicLong receiveMessageCount;
		AtomicLong timeoutMessageCount;

		public Run() {
			// TODO Auto-generated constructor stub
		}

		/**
		 * 
		 * @param cyclicBarrier
		 * @param session
		 * @param num
		 * @param sendMessageCount
		 */
		public Run(CyclicBarrier cyclicBarrier, NodeServerSession session,
				int num, AtomicLong sendMessageCount,
				AtomicLong receiveMessageCount, AtomicLong timeoutMessageCount) {
			this.cyclicBarrier = cyclicBarrier;
			this.session = session;
			this.num = num;
			this.sendMessageCount = sendMessageCount;
			this.receiveMessageCount = receiveMessageCount;
			this.timeoutMessageCount = timeoutMessageCount;
		}

		// performance issue
		public void fos() throws Exception {
			FileOutputStream fs = new FileOutputStream(new File("result.json"),
					true);
			mapper.writeValue(fs,
					new DriveData(DummyDriveDataMessage.getMessage()));
		}

		// mutil thread issue
		public void fw() throws Exception {
			writer.write(new DriveData(DummyDriveDataMessage.getMessage())
					.toString() + "\n");
			//writer.flush();
			// writer.close();
		}

		public void bfw() throws Exception {
			File file = new File("result.json");
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			bw.flush();
			bw.close();
		}

		@Override
		public void run() {
			try {
				cyclicBarrier.await();
				for (int i = 0; i < num; i++) {
					try {
						fw();
					} catch (Exception e) {
						// e.printStackTrace();
						timeoutMessageCount.incrementAndGet();
						continue;
					} finally {
						sendMessageCount.incrementAndGet();
					}
					TimeUnit.MILLISECONDS.sleep(3);
				}
				cyclicBarrier.await();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
