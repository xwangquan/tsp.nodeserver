package cn.richinfo.tsp.nodeserver.persistence.bean;

/**
 * 
 * @ClassName: DriveCustomBean
 * @Description: TODO()
 * @author 王全
 * @date 2015-11-6 下午5:56:19
 */
public class DriveCustomBean {
	public String dirving_event_type;
	public String dirving_event_data;
	public float acceleration = 0f;
	// 急减速事件 BYTE[2] 单位：0.1m/s2
	public float deceleration =0f;;	
	public String create_time;
	public int id;
	public int nodeid;
	public String report_time;
	public String trip_id;
	public int uin;
	public String vtu_id;

	public String getDirving_event_type() {
		return dirving_event_type;
	}

	public void setDirving_event_type(String dirving_event_type) {
		this.dirving_event_type = dirving_event_type;
	}

	public String getDirving_event_data() {
		return dirving_event_data;
	}

	public void setDirving_event_data(String dirving_event_data) {
		this.dirving_event_data = dirving_event_data;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public float getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(float acceleration) {
		this.acceleration = acceleration;
	}

	public float getDeceleration() {
		return deceleration;
	}

	public void setDeceleration(float deceleration) {
		this.deceleration = deceleration;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNodeid() {
		return nodeid;
	}

	public void setNodeid(int nodeid) {
		this.nodeid = nodeid;
	}

	public String getReport_time() {
		return report_time;
	}

	public void setReport_time(String report_time) {
		this.report_time = report_time;
	}

	public String getTrip_id() {
		return trip_id;
	}

	public void setTrip_id(String trip_id) {
		this.trip_id = trip_id;
	}

	public int getUin() {
		return uin;
	}

	public void setUin(int uin) {
		this.uin = uin;
	}

	public String getVtu_id() {
		return vtu_id;
	}

	public void setVtu_id(String vtu_id) {
		this.vtu_id = vtu_id;
	}

}
