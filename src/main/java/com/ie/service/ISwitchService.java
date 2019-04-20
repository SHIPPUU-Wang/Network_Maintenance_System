package com.ie.service;

import com.ie.pojo.Host;

/**
 * 交换机配置 业务层接口
 */
public interface ISwitchService {

	/**
	 * 接口设置 access模式
	 */
	boolean access(Host host, String inte, Integer vlan, Integer maximum);

	/**
	 * 接口设置 trunk模式
	 */
	boolean trunk(Host host, String inte, String vlan, Integer nativeVlan);

	/**
	 * 创建Vlan
	 */
	boolean createVlan(Host host, Integer vlan, String vlanName);

	/**
	 * 对Vlan配置IP地址
	 */
	boolean vlanIP(Host host, Integer vlan, String ipAddress, String subnetMask);

	/**
	 * 修改优先级
	 */
	boolean modifyPriority(Host host, Integer vlan, Integer priority);

	/**
	 * 设定主根/副根
	 */
	boolean root(Host host, Integer vlan1, Integer vlan2);

	/**
	 * 设置快速端口
	 */
	boolean portfast(Host host, String inte);

	/**
	 * STP 其他设置
	 */
	boolean other(Host host, String command);
}
