package cn.richinfo.tsp.nodeserver.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @ClassName: ShellCommand
 * @Description: linux shell command
 * @author 王全
 * @date 2014-11-3 下午2:54:32
 */
public class ShellCommand {
	static Logger logger = Logger.getLogger(ShellCommand.class);

	/**
	 * 运行shell脚本
	 * 
	 * @param shell
	 *            需要运行的shell脚本
	 */
	public static void execShell(String shell) {
		try {
			Runtime rt = Runtime.getRuntime();
			rt.exec(shell);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 运行shell
	 * 
	 * @param shStr
	 *            需要执行的shell
	 * @return
	 * @throws IOException
	 */
	public static List<String> runShell(String shStr) {
		try {
			List<String> strList = new ArrayList<String>();
			Process process;
			process = Runtime.getRuntime().exec(
					new String[] { "/bin/sh", "-c", shStr }, null, null);
			InputStreamReader ir = new InputStreamReader(
					process.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			String line;
			process.waitFor();
			while ((line = input.readLine()) != null) {
				strList.add(line);
			}
			return strList;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

}
