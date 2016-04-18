package cn.richinfo.tsp.nodeserver.base;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName: PersistenceBaseTest
 * @Description: TODO()
 * @author ÍõÈ«
 * @date 2014-8-1 ÉÏÎç11:03:58
 */
public class PersistenceBaseTest extends NodeServerBaseTest {
	public ApplicationContext context;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}

}
