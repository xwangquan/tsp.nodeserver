package cn.richinfo.tsp.nodeserver.message.serialization;

/**
 * @ClassName: ShellMessage
 * @Description: Common linux shell command , netstat -an ,ps ux and so on!
 * @author ÍõÈ«
 * @date 2014-11-3 ÏÂÎç2:58:19
 */
public class ShellMessage extends NameServerSerializeMessage {

	private static final long serialVersionUID = 1L;
	public String command;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

}
