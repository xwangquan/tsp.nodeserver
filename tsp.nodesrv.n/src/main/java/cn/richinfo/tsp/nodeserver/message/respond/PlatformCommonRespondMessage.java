package cn.richinfo.tsp.nodeserver.message.respond;

import cn.richinfo.tsp.nodeserver.message.Messages;

/**
 * @ClassName: PlatformCommonRespondMessage
 * @Description: 9.2. 平台通用应答
 * @author 王全
 * @date 2014-9-18 下午3:33:44
 */
public class PlatformCommonRespondMessage extends CommonRespondMessage {

	public PlatformCommonRespondMessage() {
		super(Messages.COMMON_PLANTFORM_RESP);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("TerminalCommonRespondMessage:\r\n");
		sb.append(super.toString());
		return sb.toString();
	}

}
