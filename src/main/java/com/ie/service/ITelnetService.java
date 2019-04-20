package com.ie.service;

import com.ie.pojo.Host;

/**
 * Telnet 业务层接口
 */
public interface ITelnetService {

	/**
	 * 连接设备
	 *
	 * @param host 主机连接信息
	 * @return
	 */
	void telnetEquipment(Host host, Integer userId);

	/**
	 * 获取host信息
	 *
	 * @return
	 */
	Host getHost(Integer userId);

	/**
	 * 删除host信息
	 *
	 * @param userId
	 */
	void deleteHost(Integer userId);

}
