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
		System.out.println("��ʼ�����ļ�-----------------------");
		parse.parse();
		System.out.println("�ļ�����������������ɡ�����ļ�Ϊ��"+parse.destFilePath);
	}

	void parse() throws IOException {
		System.out.println("��Ŀ¼�µ��ļ�����:"+files.length + ",����ֻ�����ļ�������Ŀ¼����" );
		System.out.println("	�����ļ�����ʼ:");
		FileWriter fw = new FileWriter(destFilePath);
		for (int i = 0; i < files.length; i++) {
			File orgFile = files[i];
			System.out.println("		��"+i+"��"+orgFile+"�ļ�����ʼ<<<");
			if(!orgFile.isDirectory()) {
				readAndWrite(orgFile, fw);
			} else {
				System.out.println("		��"+i+"��"+orgFile+"�ļ���Ŀ¼������");
			}
			System.out.println("		��"+i+"��"+orgFile+"�ļ��������>>>");
		}
		System.out.println("	�����ļ��������");
		
		fw.flush();
		fw.close();
	}

	// �����ж�д����2G���ı��ļ�ʱ�Ƽ�ʹ�����´���
	void readAndWrite(File inputFile, FileWriter fw) {
		int count = 0;
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inputFile));
			BufferedReader in = new BufferedReader(new InputStreamReader(bis, "utf-8"), 10 * 1024 * 1024);// 10M����
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
	 * ��ȡ���ò���
	 * 
	 * @param args
	 */
	boolean readParam(String[] args) {
		if (args != null && args.length > 0) {
			readFilePath = args[0];
			File readRootFile = new File(readFilePath);
			if ((!readRootFile.isDirectory()) || (!readRootFile.exists())) {
				System.err.println("Դ�ļ�·�������ڴ���");
			} else {
				files = readRootFile.listFiles();
				if (files.length == 0) {
					System.err.println("ָ��Դ�ļ�����·��(��·��)�£����ļ�");
				} else {
					destFilePath = readFilePath + File.separator + "dest";
					new File(destFilePath).mkdir();
					destFilePath = destFilePath	+ File.separator + "dest.txt";
					File destFile = new File(destFilePath);
					try {
						destFile.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println("�����������ļ�:+" + destFilePath+"ʧ�ܣ������ֹ�����������������");
					}
					System.out.println("�����������ļ�Ϊ:" + destFilePath);
					return true;
				}

			}
		} else {
			System.err.println("���������к�ָ��Դ�ļ�����·��(��·��)");
		}
		return false;
	}

}
