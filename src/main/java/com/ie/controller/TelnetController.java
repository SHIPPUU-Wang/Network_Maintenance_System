package com.ie.controller;

import com.ie.pojo.Host;
import com.ie.pojo.User;
import com.ie.service.impl.TelnetServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Telnet 控制类
 */
@RestController
@CrossOrigin
@RequestMapping("/telnet")
public class TelnetController {

	@Resource(name = "telnetService")
	private TelnetServiceImpl telnetService;

	/**
	 * 连接设备
	 *
	 * @param host 主机信息实体类
	 * @return Map <String , Object>
	 */
	@RequestMapping("/telnetEquipment")
	public Map<String, Object> telnetEquipment(Host host, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		map.put("errNum", 0);

		if (host.getHostAddress() == null || host.getHostAddress().trim().isEmpty() || host.getPort() == null || host.getIdentifier() == null) {
			map.put("state", false);
		} else {
			User u = (User) session.getAttribute("user");
			if (u == null) {
				map.put("state", false);
				map.put("errNum", 1);
			} else {
				telnetService.telnetEquipment(host, u.getUserId());
				map.put("state", true);
			}
		}
		return map;
	}

	/**
	 * 获取连接主机信息
	 *
	 * @return Map <String , Object>
	 */
	@RequestMapping("/getHost")
	public Map<String, Object> getHost(HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		map.put("errNum", 0);

		User u = (User) session.getAttribute("user");
		if (u == null) {
			map.put("state", false);
			map.put("errNum", 1);
		}else {
			Host host = telnetService.getHost(u.getUserId());

			if (host == null) {
				map.put("state", false);
			} else {
				map.put("state", true);
				map.put("host", host);
			}
		}
		return map;
	}
}
