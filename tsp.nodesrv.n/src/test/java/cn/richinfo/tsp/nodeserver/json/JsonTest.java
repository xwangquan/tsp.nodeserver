package cn.richinfo.tsp.nodeserver.json;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URL;

import cn.richinfo.tsp.nodeserver.message.sutrans.DummyDriveDataMessage;
import cn.richinfo.tsp.nodeserver.persistence.info.DriveData;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @ClassName: JsonTest
 * @Description: TODO()
 * @author ÍõÈ«
 * @date 2014-11-21 ÏÂÎç2:56:24
 */
public class JsonTest {
	static ObjectMapper mapper = new ObjectMapper(); // create once, reuse

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		write();
	}

	static void read() throws Exception {
		MyValue value = mapper.readValue(new File("data.json"), MyValue.class);
		// or:
		value = mapper.readValue(new URL("http://some.com/api/entry.json"),
				MyValue.class);
		// or:
		value = mapper.readValue("{\"name\":\"Bob\", \"age\":13}",
				MyValue.class);
	}

	static void write() throws Exception {
		MyValue value = new MyValue();
		value.age = 15;
		value.name = "wangquan";
		DummyDriveDataMessage d = new DummyDriveDataMessage();
		File file = new File("result.json");
		//Writer writer = new FileWriter(file, true);
		mapper.writeValue(new FileOutputStream(file, true), new DriveData(
				DummyDriveDataMessage.getMessage()));
		mapper.writeValue(new FileOutputStream(file, true), new DriveData(
				DummyDriveDataMessage.getMessage()));
		// or:
		// byte[] jsonBytes = mapper.writeValueAsBytes(value);
		// or:
		String jsonString = mapper.writeValueAsString(value);
		System.out.println(jsonString);
		jsonString = mapper.writeValueAsString(d);
		System.out.println(jsonString);
		jsonString = mapper.writeValueAsString(new DriveData(
				DummyDriveDataMessage.getMessage()));
		System.out.println(jsonString);
	}

}
