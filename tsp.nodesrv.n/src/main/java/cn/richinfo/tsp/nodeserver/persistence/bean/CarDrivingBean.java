package cn.richinfo.tsp.nodeserver.persistence.bean;

/**
 * @ClassName: CarDrivingBean
 * @Description: TODO()
 * @author ÍõÈ«
 * @date 2014-11-21 ÏÂÎç4:51:34
 */
public class CarDrivingBean {
	private int uin;
	private String trip_id;
	private String car_driving_info;
	private String vtu_id;
	private int nodeid;

	public int getUin() {
		return uin;
	}

	public void setUin(int uin) {
		this.uin = uin;
	}

	public String getTrip_id() {
		return trip_id;
	}

	public void setTrip_id(String trip_id) {
		this.trip_id = trip_id;
	}

	public String getCar_driving_info() {
		return car_driving_info;
	}

	public void setCar_driving_info(String car_driving_info) {
		this.car_driving_info = car_driving_info;
	}

	public String getVtu_id() {
		return vtu_id;
	}

	public void setVtu_id(String vtu_id) {
		this.vtu_id = vtu_id;
	}

	public int getNodeid() {
		return nodeid;
	}

	public void setNodeid(int nodeid) {
		this.nodeid = nodeid;
	}

}
