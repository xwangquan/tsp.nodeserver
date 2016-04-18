package cn.richinfo.tsp.nodeserver.cache;

/**
 * 
 */

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

/**
 * @author xinzhou
 *
 */
public class Parse {

	String readFilePath = null;
	String destFilePath = null;
	File[] files = null;
	HashSet<String> uniqueMobile = new HashSet<String>();

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		Parse parse = new Parse();

		String[] strs = new String[1];
		strs[0] = "D:/abc";
		if (!parse.readParam(args)) {
			return;
		}
		System.out.println();
		System.out.println("开始解析文件-----------------------");
		parse.parse();
		System.out.println("文件解析结束，任务完成。结果文件为："+parse.destFilePath);
	}

	void parse() throws IOException {
		System.out.println("该目录下的文件数量:"+files.length + ",任务只处理文件，不对目录处理" );
		System.out.println("	挨个文件处理开始:");
		FileWriter fw = new FileWriter(destFilePath);
		for (int i = 0; i < files.length; i++) {
			File orgFile = files[i];
			System.out.println("		第"+i+"个"+orgFile+"文件处理开始<<<");
			if(!orgFile.isDirectory()) {
				readAndWrite(orgFile, fw);
			} else {
				System.out.println("		第"+i+"个"+orgFile+"文件是目录，跳过");
			}
			System.out.println("		第"+i+"个"+orgFile+"文件处理结束>>>");
		}
		System.out.println("	所有文件处理结束");
		
		fw.flush();
		fw.close();
	}

	// 当逐行读写大于2G的文本文件时推荐使用以下代码
	void readAndWrite(File inputFile, FileWriter fw) {
		int count = 0;
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inputFile));
			BufferedReader in = new BufferedReader(new InputStreamReader(bis, "utf-8"), 10 * 1024 * 1024);// 10M缓存
			while (in.ready()) {
				String line = in.readLine();
				if(line!=null &&!"".equals(line)) {
					String[] strs = line.split("\\|");
					if(strs.length>2) {
						String mobile = strs[2];
						if(uniqueMobile.contains(mobile)) {
							continue;
						} else {
							uniqueMobile.add(mobile);
							fw.append(mobile + "\n");
						}
					}
					System.out.println(count++);
				}
			}
			in.close();
			
		} catch (IOException ex) {
			ex.printStackTrace();
			System.out.println("count:"+count);
		}
	}

	/**
	 * 读取配置参数
	 * 
	 * @param args
	 */
	boolean readParam(String[] args) {
		if (args != null && args.length > 0) {
			readFilePath = args[0];
			File readRootFile = new File(readFilePath);
			if ((!readRootFile.isDirectory()) || (!readRootFile.exists())) {
				System.err.println("源文件路径不存在错误");
			} else {
				files = readRootFile.listFiles();
				if (files.length == 0) {
					System.err.println("指定源文件所在路径(父路径)下，无文件");
				} else {
					destFilePath = readFilePath + File.separator + "dest";
					new File(destFilePath).mkdir();
					destFilePath = destFilePath	+ File.separator + "dest.txt";
					File destFile = new File(destFilePath);
					try {
						destFile.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println("创建结果存放文件:+" + destFilePath+"失败，请先手工创建，再重新运行");
					}
					System.out.println("创建结果存放文件为:" + destFilePath);
					return true;
				}

			}
		} else {
			System.err.println("请在命令行后指定源文件所在路径(父路径)");
		}
		return false;
	}

}
