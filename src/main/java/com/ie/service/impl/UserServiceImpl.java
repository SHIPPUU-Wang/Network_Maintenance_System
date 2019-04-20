package com.ie.service.impl;

import com.ie.dao.UserMapper;
import com.ie.pojo.User;
import com.ie.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * User 业务层实现类
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

	@Resource(name = "userDao")
	private UserMapper userDao;

	/**
	 * 通过userName，password查找用户
	 *
	 * @param userName 用户名
	 * @param password 密码
	 * @return User 用户实体类
	 */
	@Override
	public User findUserByUserNameAndPassword(String userName, String password) {

		if (userName == null || password == null || userName.trim().isEmpty() || password.trim().isEmpty()) {
			return null;
		}

		List<User> users = userDao.findUserByUserName(userName);

		User user = null;

		for (User u : users) {
			if (password.equals(u.getPassword())) {
				user = u;
				break;
			}
		}
		return user;
	}

	/**
	 * 添加用户
	 *
	 * @param userName
	 * @param password
	 * @return boolean值
	 */
	@Override
	public boolean addUser(String userName, String password) {
		if (userName == null || password == null || userName.trim().isEmpty() || password.trim().isEmpty()) {
			return false;
		} else {
			userDao.addUser(userName, password);
			return true;
		}
	}
}
