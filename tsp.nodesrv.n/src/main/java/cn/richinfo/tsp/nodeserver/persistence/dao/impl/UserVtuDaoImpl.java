package cn.richinfo.tsp.nodeserver.persistence.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.richinfo.tsp.nodeserver.persistence.dao.UserVtuDao;
import cn.richinfo.tsp.nodeserver.persistence.service.UserVtuService;

/** 
 * @ClassName: UserVtuDaoImpl 
 * @Description: TODO() 
 * @author  ÍõÈ«  
 * @date 2015-11-26 ÏÂÎç7:44:25  
 */
@Service("userVtuDao")
public class UserVtuDaoImpl implements UserVtuDao {

	@Autowired
	UserVtuService  userVtuService;
	/* (non-Javadoc)
	 * @see cn.richinfo.tsp.nodeserver.persistence.dao.UserVtuDao#getKey(java.lang.String)
	 */
	@Override
	public String getKey(String value) {
		return userVtuService.getKey(value);
	}

}
