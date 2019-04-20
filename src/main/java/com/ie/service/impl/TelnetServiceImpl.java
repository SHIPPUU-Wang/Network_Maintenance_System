package com.ie.service.impl;

import com.ie.dao.HostMapper;
import com.ie.pojo.Host;
import com.ie.service.ITelnetService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Telnet 业务层实现类
 */
@Service("telnetService")
public class TelnetServiceImpl implements ITelnetService {

	@Resource(name = "hostDao")
	private HostMapper hostDao;

	/**
	 * 连接设备
	 *
	 * @param host 主机连接信息
	 * @return
	 */
	@Override
	public void telnetEquipment(Host host, Integer userId) {
		// 清空host表
		hostDao.deleteHost(userId);
		// 将当前登录用户 userId设为 hostId
		host.setHostId(userId);
		hostDao.addHost(host);
	}

	/**
	 * 获取host信息
	 *
	 * @return
	 */
	@Override
	public Host getHost(Integer userId) {
		return hostDao.findHost(userId);
	}

	/**
	 * 删除host信息
	 */
	@Override
	public void deleteHost(Integer userId) {
		hostDao.deleteHost(userId);
	}
}
