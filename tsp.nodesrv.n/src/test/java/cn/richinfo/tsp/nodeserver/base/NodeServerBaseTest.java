package cn.richinfo.tsp.nodeserver.base;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * @ClassName: NameServerBaseTest
 * @Description: Test unit base class
 * @author ��ȫ
 * @date 2014-7-31 ����9:03:18
 */
public class NodeServerBaseTest extends TestCase {

	public Logger log = Logger.getLogger(getClass());
	
	static {
		// init log4j
		PropertyConfigurator.configure("./conf/log4j.properties");
	}

	@Override
	protected void setUp() throws Exception {
	}
}
