package cn.richinfo.tsp.nodeserver.client;

/**
 * User session status
 * 
 * @ClassName: SessionStatusItem
 * @Description: TODO()
 * @author ��ȫ
 * @date 2014-11-19 ����11:08:35
 */
public enum SessionStatusItem {

	MESSAGEQUEE(1, "MESSAGEQUEE"), PROCESSOR(2, "PROCESSOR"), CALLBACK(3,
			"CALLBACK");

	private String name;
	private int id;

	SessionStatusItem(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
