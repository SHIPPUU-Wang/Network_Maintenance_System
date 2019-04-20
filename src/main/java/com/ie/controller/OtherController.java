package com.ie.controller;

import com.ie.dao.HostMapper;
import com.ie.pojo.Host;
import com.ie.pojo.User;
import com.ie.service.impl.OtherServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 其他配置 控制类
 */
@RestController
@CrossOrigin
@RequestMapping("/other")
public class OtherController {

	@Resource(name = "hostDao")
	private HostMapper hostDao;

	@Resource(name = "otherService")
	private OtherServiceImpl otherService;

	/**
	 * 普通ACL 配置
	 *
	 * @param session          session
	 * @param aclNum           ACL编号
	 * @param controlStatement 控制语句
	 * @param ipAddress        IP地址
	 * @param wildcard         通配符（反掩码）
	 * @param inte             控制接口
	 * @param direction        控制接口流量方向
	 * @return Map <String, Object>
	 */
	@RequestMapping("/acl1")
	public Map<String, Object> acl1(HttpSession session, Integer aclNum, String controlStatement, String ipAddress, String wildcard, String inte, String direction) {
		Map<String, Object> map = new HashMap<>();
		int i = checkLoginAndHostInformation(session);

		if (i == 0) {
			User user = (User) session.getAttribute("user");
			Host host = hostDao.findHost(user.getUserId());

			boolean flag = otherService.acl1(host, aclNum, controlStatement, ipAddress, wildcard, inte, direction);

			map.put("state", flag);
		} else {
			map.put("state", false);
		}

		map.put("errNum", i);
		return map;
	}

	/**
	 * 扩展ACL 配置
	 *
	 * @param session          session
	 * @param aclNum           ACL编号
	 * @param controlStatement 控制语句
	 * @param protocol         指定协议
	 * @param sourceIp         源 IP
	 * @param destinationIp    目的 IP
	 * @param service          服务 / 端口号
	 * @param inte             控制接口
	 * @param direction        控制接口流量方向
	 * @return Map <String, Object>
	 */
	@RequestMapping("/acl2")
	public Map<String, Object> acl2(HttpSession session, Integer aclNum, String controlStatement, String protocol, String sourceIp, String destinationIp, String service, String inte, String direction) {
		Map<String, Object> map = new HashMap<>();
		int i = checkLoginAndHostInformation(session);

		if (i == 0) {
			User user = (User) session.getAttribute("user");
			Host host = hostDao.findHost(user.getUserId());

			boolean flag = otherService.acl2(host, aclNum, controlStatement, protocol, sourceIp, destinationIp, service, inte, direction);

			map.put("state", flag);
		} else {
			map.put("state", false);
		}

		map.put("errNum", i);
		return map;
	}

	/**
	 * 静态NAT配置
	 *
	 * @param session         session
	 * @param source          指定转换源
	 * @param localIpAddress  本地IP地址
	 * @param globalIpAddress 全局IP地址（转换后IP地址）
	 * @return Map <String, Object>
	 */
	@RequestMapping("/staticNAT")
	public Map<String, Object> staticNAT(HttpSession session, String source, String localIpAddress, String globalIpAddress) {
		Map<String, Object> map = new HashMap<>();
		int i = checkLoginAndHostInformation(session);

		if (i == 0) {
			User user = (User) session.getAttribute("user");
			Host host = hostDao.findHost(user.getUserId());

			boolean flag = otherService.staticNAT(host, source, localIpAddress, globalIpAddress);

			map.put("state", flag);
		} else {
			map.put("state", false);
		}

		map.put("errNum", i);
		return map;
	}

	/**
	 * 动态NAT配置
	 *
	 * @param session        session
	 * @param startIpAddress 地址池起始IP
	 * @param endIpAddress   地址池结束IP
	 * @param subnetMask     子网掩码
	 * @param source         指定转换源
	 * @param aclNum         ACL编号
	 * @return Map <String, Object>
	 */
	@RequestMapping("/dynamicNAT")
	public Map<String, Object> dynamicNAT(HttpSession session, String startIpAddress, String endIpAddress, String subnetMask, String source, Integer aclNum) {
		Map<String, Object> map = new HashMap<>();
		int i = checkLoginAndHostInformation(session);

		if (i == 0) {
			User user = (User) session.getAttribute("user");
			Host host = hostDao.findHost(user.getUserId());

			boolean flag = otherService.dynamicNAT(host, startIpAddress, endIpAddress, subnetMask, source, aclNum);

			map.put("state", flag);
		} else {
			map.put("state", false);
		}

		map.put("errNum", i);
		return map;
	}

	/**
	 * DHCP服务配置
	 *
	 * @param session    session
	 * @param dhcpName   DHCP地址池名称
	 * @param ipAddress  DHCP分配网段
	 * @param subnetMask 子网掩码
	 * @param gateway    默认网关
	 * @param dns        DNS服务器
	 * @return Map <String, Object>
	 */
	@RequestMapping("/dhcp")
	public Map<String, Object> dhcp(HttpSession session, String dhcpName, String ipAddress, String subnetMask, String gateway, String dns) {
		Map<String, Object> map = new HashMap<>();
		int i = checkLoginAndHostInformation(session);

		if (i == 0) {
			User user = (User) session.getAttribute("user");
			Host host = hostDao.findHost(user.getUserId());

			boolean flag = otherService.dhcp(host, dhcpName, ipAddress, subnetMask, gateway, dns);

			map.put("state", flag);
		} else {
			map.put("state", false);
		}

		map.put("errNum", i);
		return map;
	}

	/**
	 * DHCP服务 不分配地址设置
	 *
	 * @param session        session
	 * @param startIpAddress 起始 IP
	 * @param endIpAddress   结束 IP
	 * @return Map <String, Object>
	 */
	@RequestMapping("/excludedIP")
	public Map<String, Object> excludedIP(HttpSession session, String startIpAddress, String endIpAddress) {
		Map<String, Object> map = new HashMap<>();
		int i = checkLoginAndHostInformation(session);

		if (i == 0) {
			User user = (User) session.getAttribute("user");
			Host host = hostDao.findHost(user.getUserId());

			boolean flag = otherService.excludedIP(host, startIpAddress, endIpAddress);

			map.put("state", flag);
		} else {
			map.put("state", false);
		}

		map.put("errNum", i);
		return map;
	}

	/**
	 * DHCP 客户端接口 配置
	 *
	 * @param session session
	 * @param inte    客户端接口
	 * @return Map <String, Object>
	 */
	@RequestMapping("/clientInterface")
	public Map<String, Object> clientInterface(HttpSession session, String inte) {
		Map<String, Object> map = new HashMap<>();
		int i = checkLoginAndHostInformation(session);

		if (i == 0) {
			User user = (User) session.getAttribute("user");
			Host host = hostDao.findHost(user.getUserId());

			boolean flag = otherService.clientInterface(host, inte);

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
				// 判断当前连接设备是否是 路由器
				if (host.getIdentifier() != 1)
					i = 3;
			}
		}
		return i;
	}

}
