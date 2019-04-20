package com.ie.service;

import com.ie.pojo.User;

/**
 * User 业务层
 */
public interface IUserService {

	User findUserByUserNameAndPassword(String userName, String password);

	boolean addUser(String userName, String password);
}
