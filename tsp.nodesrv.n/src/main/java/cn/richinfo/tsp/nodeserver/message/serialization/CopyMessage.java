package cn.richinfo.tsp.nodeserver.message.serialization;

import java.util.ArrayList;

/**
 * @ClassName: CopyMessage
 * @Description: TODO()
 * @author 王全
 * @date 2014-11-3 下午12:54:24
 */
public class CopyMessage extends NameServerSerializeMessage {

	private static final long serialVersionUID = 1L;
	String str = new String(
			"  刘邦死后，冒顿单于于万里之外，微信摇一摇，发微信给吕后说：“孤偾之君，生于沮泽之中，长于平野牛马之域"
					+ "，数至边境，愿游中国。陛下独立，孤偾独居。两主不乐，无以自虞，愿以所有，易其所无。”意思就是：“我本孤独无依之君王，"
					+ "出生于沮泽之中，生长于原野广阔牛马成群之域，数次到达中国边境，希望到中国一游。陛下现在也是孤独一人，寂寞独居，身为一国之主，"
					+ "我们两人都享不到天伦之乐，也没有什么可以自娱自乐，一个鳏夫，一个寡居，不如就嫁给我，还可以相互补偿");
	ArrayList<String> list = new ArrayList<String>();

	public CopyMessage() {
		for (int i = 0; i < 10000; i++) {
			list.add(str);
		}
	}
}
