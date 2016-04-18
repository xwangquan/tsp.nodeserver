package cn.richinfo.tsp.nodeserver.mq;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import cn.richinfo.tsp.nodeserver.entrance.Constants;

/**
 * @ClassName: KafkaProducer
 * @Description: TODO()
 * @author ÍõÈ«
 * @date 2015-11-26 ÉÏÎç10:35:30
 */
public class KafkaFacade {
	private Producer<String, String> producer;
	private static String TOPIC = "car_position_info";
	private static KafkaFacade stance;

	private KafkaFacade() {
	}

	public static KafkaFacade getKafkaHook() {
		if (stance == null) {
			stance = new KafkaFacade();
		}
		return stance;
	}

	public KafkaFacade init() {
		try {
			Properties props = new Properties();
			props.load(new FileInputStream(new File(Constants.KAFKA_CONFIG)));
			ProducerConfig config = new ProducerConfig(props);
			producer = new Producer<String, String>(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	public void put(String json) throws Exception {
		KeyedMessage<String, String> data = new KeyedMessage<String, String>(
				TOPIC, json);
		producer.send(data);
	}

	public static void main(String[] args) throws Exception {
		KafkaFacade.getKafkaHook().init().put("wangquan123");

	}
}
