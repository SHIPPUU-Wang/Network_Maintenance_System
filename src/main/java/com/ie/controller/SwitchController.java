package com.ie.controller;

import com.ie.dao.HostMapper;
import com.ie.pojo.Host;
import com.ie.pojo.User;
import com.ie.service.impl.SwitchServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 交换机配置 控制类
 */
@RestController
@CrossOrigin
@RequestMapping("/switch")
public class SwitchController {

	@Resource(name = "hostDao")
	private HostMapper hostDao;

	@Resource(name = "switchService")
	private SwitchServiceImpl switchService;

	/**
	 * 接口设置 access模式
	 *
	 * @param session session
	 * @param inte    接口
	 * @param vlan    Vlan划分
	 * @param maximum 接口最大接入数
	 * @return Map <String, Object>
	 */
	@RequestMapping("/access")
	public Map<String, Object> access(HttpSession session, String inte, Integer vlan, Integer maximum) {
		Map<String, Object> map = new HashMap<>();
		int i = checkLoginAndHostInformation(session);

		if (i == 0) {
			User user = (User) session.getAttribute("user");
			Host host = hostDao.findHost(user.getUserId());

			boolean flag = switchService.access(host, inte, vlan, maximum);

			map.put("state", flag);
		} else {
			map.put("state", false);
		}

		map.put("errNum", i);
		return map;
	}

	/**
	 * 接口设置 trunk模式
	 *
	 * @param session    session
	 * @param inte       接口
	 * @param vlan       允许通过的Vlan
	 * @param nativeVlan 本帧Vlan
	 * @return Map <String, Object>
	 */
	@RequestMapping("/trunk")
	public Map<String, Object> trunk(HttpSession session, String inte, String vlan, Integer nativeVlan) {
		Map<String, Object> map = new HashMap<>();
		int i = checkLoginAndHostInformation(session);

		if (i == 0) {
			User user = (User) session.getAttribute("user");
			Host host = hostDao.findHost(user.getUserId());

			boolean flag = switchService.trunk(host, inte, vlan, nativeVlan);

			map.put("state", flag);
		} else {
			map.put("state", false);
		}

		map.put("errNum", i);
		return map;
	}

	/**
	 * 创建Vlan
	 *
	 * @param session  session
	 * @param vlan     Vlan编号
	 * @param vlanName Vlan名称
	 * @return Map <String, Object>
	 */
	@RequestMapping("/createVlan")
	public Map<String, Object> createVlan(HttpSession session, Integer vlan, String vlanName) {
		Map<String, Object> map = new HashMap<>();
		int i = checkLoginAndHostInformation(session);

		if (i == 0) {
			User user = (User) session.getAttribute("user");
			Host host = hostDao.findHost(user.getUserId());

			boolean flag = switchService.createVlan(host, vlan, vlanName);

			map.put("state", flag);
		} else {
			map.put("state", false);
		}

		map.put("errNum", i);
		return map;
	}

	/**
	 * 对Vlan配置IP地址
	 *
	 * @param session    session
	 * @param vlan       Vlan编号
	 * @param ipAddress  IP地址
	 * @param subnetMask 子网掩码
	 * @return Map <String, Object>
	 */
	@RequestMapping("/vlanIP")
	public Map<String, Object> vlanIP(HttpSession session, Integer vlan, String ipAddress, String subnetMask) {
		Map<String, Object> map = new HashMap<>();
		int i = checkLoginAndHostInformation(session);

		if (i == 0) {
			User user = (User) session.getAttribute("user");
			Host host = hostDao.findHost(user.getUserId());

			boolean flag = switchService.vlanIP(host, vlan, ipAddress, subnetMask);

			map.put("state", flag);
		} else {
			map.put("state", false);
		}

		map.put("errNum", i);
		return map;
	}

	/**
	 * 修改优先级
	 *
	 * @param session  session
	 * @param vlan     Vlan编号
	 * @param priority 优先级
	 * @return Map <String, Object>
	 */
	@RequestMapping("/modifyPriority")
	public Map<String, Object> modifyPriority(HttpSession session, Integer vlan, Integer priority) {
		Map<String, Object> map = new HashMap<>();
		int i = checkLoginAndHostInformation(session);

		if (i == 0) {
			User user = (User) session.getAttribute("user");
			Host host = hostDao.findHost(user.getUserId());

			boolean flag = switchService.modifyPriority(host, vlan, priority);

			map.put("state", flag);
		} else {
			map.put("state", false);
		}

		map.put("errNum", i);
		return map;
	}

	/**
	 * 设定主根/副根
	 *
	 * @param session session
	 * @param vlan1   主根 Vlan编号
	 * @param vlan2   副根 Vlan编号
	 * @return Map <String, Object>
	 */
	@RequestMapping("/root")
	public Map<String, Object> root(HttpSession session, Integer vlan1, Integer vlan2) {
		Map<String, Object> map = new HashMap<>();
		int i = checkLoginAndHostInformation(session);

		if (i == 0) {
			User user = (User) session.getAttribute("user");
			Host host = hostDao.findHost(user.getUserId());

			boolean flag = switchService.root(host, vlan1, vlan2);

			map.put("state", flag);
		} else {
			map.put("state", false);
		}

		map.put("errNum", i);
		return map;
	}

	/**
	 * 设置快速端口
	 *
	 * @param session session
	 * @param inte    选择接口
	 * @return Map <String, Object>
	 */
	@RequestMapping("/portfast")
	public Map<String, Object> portfast(HttpSession session, String inte) {
		Map<String, Object> map = new HashMap<>();
		int i = checkLoginAndHostInformation(session);

		if (i == 0) {
			User user = (User) session.getAttribute("user");
			Host host = hostDao.findHost(user.getUserId());

			boolean flag = switchService.portfast(host, inte);

			map.put("state", flag);
		} else {
			map.put("state", false);
		}

		map.put("errNum", i);
		return map;
	}

	/**
	 * STP 其他设置
	 *
	 * @param session session
	 * @param command 配置命令
	 * @return Map <String, Object>
	 */
	@RequestMapping("/other")
	public Map<String, Object> other(HttpSession session, String command) {
		Map<String, Object> map = new HashMap<>();
		int i = checkLoginAndHostInformation(session);

		if (i == 0) {
			User user = (User) session.getAttribute("user");
			Host host = hostDao.findHost(user.getUserId());

			boolean flag = switchService.other(host, command);

			map.put("state", flag);
		} else {
			map.put("state", false);
		}

		map.put("errNum", i);
		return map;
	}

	/**
	 * 检查用户登录和Host信息
	 *
	 * @param session session
	 * @return 0：正常，1：登录超时，2：host为空，3：当前连接设备不是交换机
	 */
	private int checkLoginAndHostInformation(HttpSession session) {
		int i = 0;
		User u = (User) session.getAttribute("user");
		if (u == null) {
			i = 1;
		} else {
			Host host = hostDao.findHost(u.getUserId());
			if (host == null)
				i = 2;
			else {
				// 判断当前连接设备是否是 交换机
				if (host.getIdentifier() != 2)
					i = 3;
			}
		}
		return i;
	}

}
