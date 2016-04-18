package cn.richinfo.tsp.nodeserver.web;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * @ClassName: DiagnosisFacade
 * @Description: For roland diagnosis utils
 * @author ÍõÈ«
 * @date 2015-8-24 ÏÂÎç4:41:44
 */
public class DiagnosisFacade {
	public static Logger log = Logger.getLogger(DiagnosisFacade.class);

	private static String TOKEN = "0c1b90d00554438386d8059a189e5613";
	private static String DEFAULT_HOST = "http://cmccapi.roistar.net/diagnosis/fault/"
			+ TOKEN + "/";
	public static int DEFAULT_PORT = 80;

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		String s = get("TEZWM0EyNEc5QzMwMzk0OTgDBQcJCwkOA1JPSUxBTkQDBQcJCwkOA1JPSUxBTkQDBQcJCwkOA1JPSUxBTkQDBQcJCwkOA1JPSUxBTkQDBQcJCwkOA1JPSUxBTkQDBQcJCwkOA1JPSUxBTkQDBQcJCwkOA1JPSUxBTkQDBQcJCwkOA1JPSUxBTkQDBQcJCwkOA1JPSUxBTkQDBQcJCwkOA1JPSUxBTkQDBQcJCwkOA1JPSUxBTkQDBQcJCwkOA1JPSUxBTkQDBQcJCwkOA1JPSUxBTkQDBQcJCwkOA1JPSUxBTkQDBQcJCwkOA1JPSUxBTkQDBQcJCwkOA1JPSUxBTg==");
		System.out.println(s);
	}

	public static String get(String code) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(DEFAULT_HOST + code);
		CloseableHttpResponse response1 = null;
		try {
			response1 = httpclient.execute(httpGet);
			int stat = response1.getStatusLine().getStatusCode();
			if (stat != 200) {
				log.error(DEFAULT_HOST + code + ",Get status = " + stat);
				return null;
			}
			HttpEntity entity1 = response1.getEntity();
			// do something useful with the response body
			// and ensure it is fully consumed
			// EntityUtils.consume(entity1);
			return EntityUtils.toString(entity1);
		} catch (Exception e) {
			log.error(DEFAULT_HOST + code + ",Get error!", e);
		} finally {
			try {
				response1.close();
			} catch (IOException e) {//ingore
			}
		}
		return null;
	}
}
