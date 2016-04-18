package cn.richinfo.tsp.nodeserver.mq;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class KafkaTest extends TestCase {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAbc() throws Exception {
		KafkaFacade.getKafkaHook().put("test");
	}

}
