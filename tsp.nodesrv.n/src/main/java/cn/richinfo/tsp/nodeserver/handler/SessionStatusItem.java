package cn.richinfo.tsp.nodeserver.handler;

/**
 * server session status
 * 
 * @ClassName: SessionStatusItem
 * @Description: TODO()
 * @author ÍõÈ«
 * @date 2014-11-20 ÏÂÎç1:59:42
 */
public enum SessionStatusItem {

	MESSAGEQUEE(1, "MESSAGEQUEE");

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
