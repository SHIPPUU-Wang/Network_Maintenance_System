package com.ie.service;

import com.ie.pojo.Host;

/**
 * 其他配置 业务层接口
 */
public interface IOtherService {

	/**
	 * 普通ACL 配置
	 */
	boolean acl1(Host host, Integer aclNum, String controlStatement, String ipAddress, String wildcard, String inte, String direction);

	/**
	 * 扩展ACL 配置
	 */
	boolean acl2(Host host, Integer aclNum, String controlStatement, String protocol, String sourceIp, String destinationIp, String service, String inte, String direction);

	/**
	 * 静态NAT配置
	 */
	boolean staticNAT(Host host, String source, String localIpAddress, String globalIpAddress);

	/**
	 * 动态NAT配置
	 */
	boolean dynamicNAT(Host host, String startIpAddress, String endIpAddress, String subnetMask, String source, Integer aclNum);

	/**
	 * DHCP服务配置
	 */
	boolean dhcp(Host host, String dhcpName, String ipAddress, String subnetMask, String gateway, String dns);

	/**
	 * DHCP服务 不分配地址设置
	 */
	boolean excludedIP(Host host, String startIpAddress, String endIpAddress);

	/**
	 * DHCP 客户端接口 配置
	 */
	boolean clientInterface(Host host, String inte);

}
