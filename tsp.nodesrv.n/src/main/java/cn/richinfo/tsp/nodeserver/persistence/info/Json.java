package cn.richinfo.tsp.nodeserver.persistence.info;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @ClassName: Info
 * @Description: TODO()
 * @author ��ȫ
 * @date 2014-11-21 ����4:41:33
 */
public abstract class Json {

	public static ObjectMapper mapper = new ObjectMapper(); // create once,
															// reuse

	public abstract String toString();
}
