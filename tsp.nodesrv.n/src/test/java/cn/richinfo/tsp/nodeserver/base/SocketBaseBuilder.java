package cn.richinfo.tsp.nodeserver.base;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import cn.richinfo.tsp.nodeserver.client.NodeServerSession;
import cn.richinfo.tsp.nodeserver.client.NodeServerSessionBuilder;

/**
 * @ClassName: SocketBaseBuilder
 * @Description: TODO()
 * @author ÍõÈ«
 * @date 2014-10-30 ÏÂÎç4:27:22
 */
public class SocketBaseBuilder extends MessageBaseTest {
	public static final Logger log = Logger.getLogger(SocketBaseBuilder.class);

	static {
		PropertyConfigurator.configure("./conf/log4j.properties");
	}
	public static NodeServerSessionBuilder builder;
	private static NodeServerSession session;

	private static NodeServerSessionBuilder getBuilder() {
		if (builder != null)
			return builder;

		// initialize session 192.168.34.138
		builder = new NodeServerSessionBuilder(new InetSocketAddress(
				"localhost", 8317));
		// the maximum wait time
		builder.setMaxRespondTime(300000l);
		return builder;
	}

	public static void clear() {
		try {
			String os = System.getProperty("os.name");
			if (os.contains("Windows")) {
				Runtime.getRuntime().exec("cls");
			} else {
				Runtime.getRuntime().exec("clear");
			}
		} catch (Exception e) {
		}

	}
	
	public static void  shutdown(){
		builder.shutdown();
	}

	public static NodeServerSession getSession() {
		try {
			if (session != null)
				return session;

			// try get session
			session = getBuilder().build();
			return session;
		} catch (Exception e) {
			log.equals(e);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.exit(0);
		}
		return null;
	}

	@Override
	public void testRead() throws Exception {
		
	}

	@Override
	public void testWrite() throws Exception {
		
	}
}
