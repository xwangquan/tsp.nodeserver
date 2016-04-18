package cn.richinfo.tsp.nodeserver.async;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import cn.richinfo.tsp.nodeserver.asyn.CircularQueue;

public class CirculaQueenTest {
	static CircularQueue<String> s = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		s = new CircularQueue<>();
		new W(s).start();
		new W1(s).start();
	}

}

class W1 extends Thread {
	CircularQueue<String> q;

	public W1(CircularQueue<String> s) {
		q = s;
		setName("Write");
	}

	@Override
	public void run() {
		try {
			AtomicLong count = new AtomicLong();
			while (true) {
				String str = "in:" + count.incrementAndGet();
				q.offer(count.get() + "");
				System.out.println(getName() + "----- " + str);
				TimeUnit.MICROSECONDS.sleep(5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class W extends Thread {
	CircularQueue<String> q;

	public W(CircularQueue<String> s) {
		q = s;
		setName("Read");
	}

	@Override
	public void run() {
		try {
			while (true) {
				System.out.println(getName() + "----- " + q.poll());
				if (q.isEmpty())
					TimeUnit.MICROSECONDS.sleep(5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}