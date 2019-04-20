package com.ie.controller;

import com.ie.dao.HostMapper;
import com.ie.pojo.Host;
import com.ie.pojo.User;
import com.ie.service.impl.RouterProtocolServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 路由选择协议 控制类
 */
@RestController
@CrossOrigin
@RequestMapping("/routerProtocol")
public class RouterProtocolController {

	@Resource(name = "hostDao")
	private HostMapper hostDao;

	@Resource(name = "routerProtocolService")
	private RouterProtocolServiceImpl routerProtocolService;

	/**
	 * 配置rip协议
	 *
	 * @param session          session
	 * @param ripVersion       rip版本
	 * @param ipAddress        IP地址
	 * @param autoSummary      自动汇总判断
	 * @param passiveInterface 被动端口
	 * @return Map <String, Object>
	 */
	@RequestMapping("/rip")
	public Map<String, Object> rip(HttpSession session, int ripVersion, String ipAddress, boolean autoSummary, String passiveInterface) {
		Map<String, Object> map = new HashMap<>();
		int i = checkLoginAndHostInformation(session);

		if (i == 0) {
			User user = (User) session.getAttribute("user");
			Host host = hostDao.findHost(user.getUserId());

			boolean flag = routerProtocolService.rip(host, ripVersion, ipAddress, autoSummary, passiveInterface);

			map.put("state", flag);
		} else {
			map.put("state", false);
		}

		map.put("errNum", i);
		return map;
	}

	/**
	 * 配置eigrp协议
	 *
	 * @param session          session
	 * @param AS               AS号
	 * @param ipAddress        IP地址
	 * @param autoSummary      自动汇总判断
	 * @param passiveInterface 被动端口
	 * @return Map <String, Object>
	 */
	@RequestMapping("/eigrp")
	public Map<String, Object> eigrp(HttpSession session, Integer AS, String ipAddress, boolean autoSummary, String passiveInterface) {
		Map<String, Object> map = new HashMap<>();
		int i = checkLoginAndHostInformation(session);

		if (i == 0) {
			User user = (User) session.getAttribute("user");
			Host host = hostDao.findHost(user.getUserId());

			boolean flag = routerProtocolService.eigrp(host, AS, ipAddress, autoSummary, passiveInterface);

			map.put("state", flag);
		} else {
			map.put("state", false);
		}

		map.put("errNum", i);
		return map;
	}

	/**
	 * 配置ospf协议
	 *
	 * @param session   session
	 * @param process   进程ID
	 * @param routerID  routerID
	 * @param ipAddress IP地址
	 * @param wildcard  通配符（反掩码）
	 * @param area      区域号
	 * @return Map <String, Object>
	 */
	@RequestMapping("/ospf")
	public Map<String, Object> ospf(HttpSession session, Integer process, String routerID, String ipAddress, String wildcard, Integer area) {
		Map<String, Object> map = new HashMap<>();
		int i = checkLoginAndHostInformation(session);

		if (i == 0) {
			User user = (User) session.getAttribute("user");
			Host host = hostDao.findHost(user.getUserId());

			boolean flag = routerProtocolService.ospf(host, process, routerID, ipAddress, wildcard, area);

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
				if (host.getIdentifier() != 1)
					i = 3;
			}
		}
		return i;
	}

}
