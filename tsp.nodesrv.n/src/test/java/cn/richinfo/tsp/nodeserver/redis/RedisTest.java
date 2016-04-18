package cn.richinfo.tsp.nodeserver.redis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;

public class RedisTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Jedis jedis = new Jedis("192.168.9.63", 40011);
		jedis.set("foo", "bar");
		String value = jedis.get("foo");
		System.out.println(value);
		System.out.println(jedis.hget("VEHBINDRULES", "34166"));

		// System.out.println(jedis.scard("VEHBINDRULES"));
	}

}
