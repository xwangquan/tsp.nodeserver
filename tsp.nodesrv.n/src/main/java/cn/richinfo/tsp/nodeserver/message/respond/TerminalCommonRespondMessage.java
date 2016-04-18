package cn.richinfo.tsp.nodeserver.message.respond;

import cn.richinfo.tsp.nodeserver.message.Messages;

/**
 * @ClassName: TerminalCommonRespondMessage
 * @Description: 9.1. 终端通用应答
 * @author 王全
 * @date 2014-9-18 下午3:30:29
 */
public class TerminalCommonRespondMessage extends CommonRespondMessage {

	public TerminalCommonRespondMessage() {
		super(Messages.COMMON_TERMINAL_RESP);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("TerminalCommonRespondMessage:\r\n");
		sb.append(super.toString());
		return sb.toString();
	}
}
