package com.ie.controller;

import com.ie.dao.HostMapper;
import com.ie.pojo.Host;
import com.ie.pojo.User;
import com.ie.service.impl.RouterBasicServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 路由器基本配置 控制类
 */
@RestController
@CrossOrigin
@RequestMapping("/routerBasic")
public class RouterBasicController {

	@Resource(name = "hostDao")
	private HostMapper hostDao;

	@Resource(name = "routerBasicService")
	private RouterBasicServiceImpl routerBasicService;

	/**
	 * 接口配置 IPv4
	 *
	 * @param inte          接口名称
	 * @param ipAddress     IPv4 地址
	 * @param subnetMask    子网掩码
	 * @param openInterface 是否打开接口，Boolean值
	 * @param ipVersion     IP 版本：v4、v6
	 * @return Map <String,Object> ipVersion
	 */
	@RequestMapping("/interface")
	public Map<String, Object> interfaceIP(HttpSession session, String inte, String ipAddress, String subnetMask, Boolean openInterface, int ipVersion) {
		Map<String, Object> map = new HashMap<>();
		map.put("errNum", 0);

		boolean b = checkInterfaceInformation(inte, ipAddress, subnetMask);
		if (b) {
			int i = checkLoginAndHostInformation(session);
			if (i == 0) {

				User user = (User) session.getAttribute("user");
				Host host = hostDao.findHost(user.getUserId());
				boolean flag = routerBasicService.interfaceIP(inte, ipAddress, subnetMask, openInterface, ipVersion, host);

				map.put("state", flag);
				return map;
			} else {
				map.put("state", false);
				map.put("errNum", i);
				return map;
			}

		} else {
			map.put("state", false);
			return map;
		}

	}

	/**
	 * 查看路由表
	 *
	 * @return Map <String,Object>
	 */
	@RequestMapping("/showRoutingTable")
	public Map<String, Object> showRoutingTable(HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		map.put("errNum", 0);

		int i = checkLoginAndHostInformation(session);
		if (i == 0) {

			User user = (User) session.getAttribute("user");
			Host host = hostDao.findHost(user.getUserId());

			String routingTable = routerBasicService.showRoutingTable(host);

			while (true) {
				if (routingTable == null)
					routingTable = routerBasicService.showRoutingTable(host);
				else break;
			}

			if (routingTable == null) {
				map.put("state", false);
			} else {
				map.put("state", true);
				map.put("routingTable", routingTable);
			}

		} else {
			map.put("state", false);
			map.put("errNum", i);
		}

		return map;

	}

	/**
	 * 查看接口信息
	 *
	 * @param ipVersion IP版本
	 * @return Map <String,Object>
	 */
	@RequestMapping("/showInterfaceInformation")
	public Map<String, Object> showInterfaceInformation(HttpSession session, int ipVersion) {
		Map<String, Object> map = new HashMap<>();
		map.put("errNum", 0);

		int i = checkLoginAndHostInformation(session);
		if (i == 0) {

			User user = (User) session.getAttribute("user");
			Host host = hostDao.findHost(user.getUserId());

			String interfaceInformation = routerBasicService.showInterfaceInformation(host, ipVersion);

			while (true) {
				if (interfaceInformation == null)
					interfaceInformation = routerBasicService.showInterfaceInformation(host, ipVersion);
				else break;
			}

			if (interfaceInformation == null) {
				map.put("state", false);
			} else {
				map.put("state", true);
				map.put("interfaceInformation", interfaceInformation);
			}

		} else {
			map.put("state", false);
			map.put("errNum", i);

		}
		return map;

	}

	/**
	 * 配置静态路由
	 *
	 * @param session            session
	 * @param destinationNetwork 目的网络
	 * @param subnetMask         子网掩码
	 * @param nextHopAddress     下一跳地址
	 * @param outputInterface    输出接口
	 * @param AD                 管理距离
	 * @return Map <String, Object>
	 */
	@RequestMapping("/staticRouter")
	public Map<String, Object> staticRouter(HttpSession session, String destinationNetwork, String subnetMask, String nextHopAddress, String outputInterface, Integer AD) {
		Map<String, Object> map = new HashMap<>();
		int i = checkLoginAndHostInformation(session);

		if (i == 0) {
			User user = (User) session.getAttribute("user");
			Host host = hostDao.findHost(user.getUserId());

			boolean flag = routerBasicService.staticRouter(host, destinationNetwork, subnetMask, nextHopAddress, outputInterface, AD);

			map.put("state", flag);
		} else {
			map.put("state", false);
		}

		map.put("errNum", i);
		return map;
	}

	/**
	 * 配置静态默认路由
	 *
	 * @param nextHopAddress  下一跳地址
	 * @param outputInterface 输出接口
	 * @return Map <String, Object>
	 */
	@RequestMapping("/defaultRoute")
	public Map<String, Object> defaultRoute(HttpSession session, String nextHopAddress, String outputInterface) {
		Map<String, Object> map = new HashMap<>();
		int i = checkLoginAndHostInformation(session);

		if (i == 0) {
			User user = (User) session.getAttribute("user");
			Host host = hostDao.findHost(user.getUserId());

			boolean flag = routerBasicService.defaultRoute(host, nextHopAddress, outputInterface);

			map.put("state", flag);
		} else {
			map.put("state", false);
		}

		map.put("errNum", i);
		return map;
	}

	/**
	 * ping 操作
	 *
	 * @param ipAddress IP 地址
	 * @param ipVersion IP 版本
	 * @return Map <String, Object>
	 */
	@RequestMapping("/ping")
	public Map<String, Object> ping(HttpSession session, String ipAddress, int ipVersion) {
		Map<String, Object> map = new HashMap<>();
		int i = checkLoginAndHostInformation(session);

		if (i == 0) {
			User user = (User) session.getAttribute("user");
			Host host = hostDao.findHost(user.getUserId());

			String pingData = routerBasicService.ping(host, ipAddress, ipVersion);

			while (true) {
				if (pingData == null) {

					// sleep 10秒
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					pingData = routerBasicService.ping(host, ipAddress, ipVersion);
				} else break;
			}

			if (pingData == null)
				map.put("state", false);
			else {
				map.put("state", true);
				map.put("ping", pingData);
			}

		} else {
			map.put("state", false);
		}

		map.put("errNum", i);
		return map;
	}

	/**
	 * 检查接口信息
	 *
	 * @param inte
	 * @param ipAddress
	 * @param subnetMask
	 * @return
	 */
	private boolean checkInterfaceInformation(String inte, String ipAddress, String subnetMask) {
		if (inte == null || inte.trim().isEmpty()) {
			return false;
		} else if (ipAddress != null && !(ipAddress.trim().isEmpty())) {
			if (subnetMask == null || subnetMask.trim().isEmpty())
				return false;
		}
		return true;
	}

	/**
	 * 检查用户登录和Host信息
	 *
	 * @param session
	 * @return 0：正常，1：登录超时，2：host为空，3：当前连接设备不是路由器
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
				if (host.getIdentifier() != 1)
					i = 3;
			}
		}
		return i;
	}

}
