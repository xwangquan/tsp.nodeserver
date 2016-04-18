package cn.richinfo.tsp.nodeserver.utils;

/**
 * ���ý��� 16 8 10 ����ת��
 * 
 * @ClassName: HexTest
 * @Description: TODO()
 * @author ��ȫ
 * @date 2014-7-31 ����9:47:28
 */
public class HexConvert {

	public static void main(String[] args) {
		int num = 13;
		// ��Java���ú������Զ��庯�����������жԱ�
		System.out.println(Integer.toHexString(num));
		System.out.println(toHex(num));

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println(Integer.toBinaryString(num));
		System.out.println(toBin(num));
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println(Integer.toOctalString(num));
		System.out.println(toOct(num));
	}

	// ������ز�ѯ16���Ƶ��ַ���
	public static char getCharHex(int index) {
		char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		return hexChars[index];
	}

	// ������ز�ѯ2���Ƶ��ַ���
	public static char getCharBin(int index) {
		char[] binChars = { '0', '1' };
		return binChars[index];
	}

	// ת����16���ƣ��õ�����λ4
	public static String toHex(int num) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 8; i++) {
			int temp = num & 15;// ����&������1111��2 =15
			num = num >>> 4;
			sb.append(getCharHex(temp));

		}
		// ����ת֮��Ľ������
		return new String(sb.reverse());
	}

	// ת����2���ƣ��õ�Ҳ����λ1
	private static String toBin(int num) {
		StringBuffer sb = new StringBuffer();
		while (num != 0) {
			int index = num & 1;// ����&������1��2 =1
			sb.append(getCharBin(index));
			num = num >>> 1;
		}
		return new String(sb.reverse());
	}

	// ת����8���ƣ�����λ3��
	private static String toOct(int num) {
		StringBuffer sb = new StringBuffer();
		while (num != 0) {
			int index = num & 7;// ����&������111��2 =7
			sb.append(index);
			num = num >>> 3;
		}
		return new String(sb.reverse());
	}

}
