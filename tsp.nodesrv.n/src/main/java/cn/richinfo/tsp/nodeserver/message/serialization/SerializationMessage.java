package cn.richinfo.tsp.nodeserver.message.serialization;

import java.io.NotSerializableException;
import java.io.Serializable;

/**
 * @ClassName: SerializationMessage
 * @Description: TODO()
 * @author ÍõÈ«
 * @date 2014-11-3 ÏÂÎç1:02:31
 */
public class SerializationMessage extends AbstractSerializationMessage {

	private Object message;

	@Override
	public Object getMessage() {
		return message;
	}

	@Override
	public void setMessage(Object message) {
		try {
			if (!(message instanceof Serializable)) {
				throw new NotSerializableException();
			}
			this.message = message;
		} catch (Exception e) {
			// just ignore
			e.printStackTrace();
		}
	}
}
