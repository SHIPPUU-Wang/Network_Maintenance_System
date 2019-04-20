package com.ie.controller;

import com.ie.pojo.User;
import com.ie.service.impl.TelnetServiceImpl;
import com.ie.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * User 控制器
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Resource(name = "userService")
	private UserServiceImpl userService;

	@Resource(name = "telnetService")
	private TelnetServiceImpl telnetService;

	/**
	 * 用户登录
	 *
	 * @param userName
	 * @param password
	 * @param session
	 * @return
	 */
	@RequestMapping("/login")
	public Map<String, Object> login(String userName, String password, HttpSession session) {
		Map<String, Object> map = new HashMap<>();

		User user = userService.findUserByUserNameAndPassword(userName, password);

		if (user == null) {
			map.put("state", false);
		} else {
			session.setAttribute("user", user);
			map.put("state", true);
			// 清空host表
			telnetService.deleteHost(user.getUserId());
		}

		return map;
	}

	/**
	 * 用户注册
	 *
	 * @param userName
	 * @param password
	 * @return
	 */
	@RequestMapping("/register")
	public Map<String, Object> register(String userName, String password) {
		Map<String, Object> map = new HashMap<>();

		boolean b = userService.addUser(userName, password);
		if (b)
			map.put("state", true);
		else
			map.put("state", false);

		return map;
	}

	/**
	 * 登录锁定
	 *
	 * @param session
	 * @param password
	 * @return
	 */
	@RequestMapping("/loginLockout")
	public Map<String, Object> loginLockout(HttpSession session, String password) {
		Map<String, Object> map = new HashMap<>();

		User u = (User) session.getAttribute("user");
		if (u == null) {
			map.put("state", false);
			map.put("num", 0);
		} else {
			User user = userService.findUserByUserNameAndPassword(u.getUserName(), password);

			if (user == null) {
				map.put("state", false);
				map.put("num", 1);
			} else
				map.put("state", true);
		}

		return map;
	}

	/**
	 * 获取当前登录用户的用户信息
	 *
	 * @param u 从Session中获取key为"user"的User
	 * @return
	 */
	@RequestMapping("/getUserInformation")
//	public Map<String, Object> getUserInformation(@SessionAttribute("user") User u) {
	public Map<String, Object> getUserInformation(HttpSession session) {
		Map<String, Object> map = new HashMap<>();

		 User u = (User) session.getAttribute("user");

		if (u == null) {
			map.put("state", false);
		} else {
			map.put("state", true);
			map.put("userId", u.getUserId());
			map.put("userName", u.getUserName());
		}

		return map;
	}

	/**
	 * 退出登录
	 *
	 * @param session
	 */
	@RequestMapping("/logout")
	public void logout(HttpSession session) {

		User u = (User) session.getAttribute("user");
		if (u != null) {
			// 清空host表
			telnetService.deleteHost(u.getUserId());
		}

		// 清除session中的所有信息
		session.invalidate();

	}

}
