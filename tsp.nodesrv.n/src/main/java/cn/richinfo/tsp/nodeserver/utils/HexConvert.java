package cn.richinfo.tsp.nodeserver.utils;

/**
 * 常用进制 16 8 10 进制转换
 * 
 * @ClassName: HexTest
 * @Description: TODO()
 * @author 王全
 * @date 2014-7-31 上午9:47:28
 */
public class HexConvert {

	public static void main(String[] args) {
		int num = 13;
		// 用Java内置函数与自定义函数输出结果进行对比
		System.out.println(Integer.toHexString(num));
		System.out.println(toHex(num));

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println(Integer.toBinaryString(num));
		System.out.println(toBin(num));
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println(Integer.toOctalString(num));
		System.out.println(toOct(num));
	}

	// 查表发返回查询16进制的字符串
	public static char getCharHex(int index) {
		char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		return hexChars[index];
	}

	// 查表发返回查询2进制的字符串
	public static char getCharBin(int index) {
		char[] binChars = { '0', '1' };
		return binChars[index];
	}

	// 转换成16进制，用的是移位4
	public static String toHex(int num) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 8; i++) {
			int temp = num & 15;// 进行&操作（1111）2 =15
			num = num >>> 4;
			sb.append(getCharHex(temp));

		}
		// 将反转之后的结果返回
		return new String(sb.reverse());
	}

	// 转换成2进制，用的也是移位1
	private static String toBin(int num) {
		StringBuffer sb = new StringBuffer();
		while (num != 0) {
			int index = num & 1;// 进行&操作（1）2 =1
			sb.append(getCharBin(index));
			num = num >>> 1;
		}
		return new String(sb.reverse());
	}

	// 转换成8进制，用移位3；
	private static String toOct(int num) {
		StringBuffer sb = new StringBuffer();
		while (num != 0) {
			int index = num & 7;// 进行&操作（111）2 =7
			sb.append(index);
			num = num >>> 3;
		}
		return new String(sb.reverse());
	}

}
