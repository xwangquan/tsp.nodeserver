package cn.richinfo.tsp.nodeserver.persistence.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.richinfo.tsp.nodeserver.persistence.dao.UserVtuDao;
import cn.richinfo.tsp.nodeserver.persistence.service.UserVtuService;

@Service("userVtuService")
public class UserVtuServiceImpl implements UserVtuService {

	@Autowired
	private UserVtuDao userVtuDao;

	@Override
	public String getKey(String value) {
		return userVtuDao.getKey(value);
	}

}
