package com.ie.service;

import com.ie.pojo.Host;

/**
 * 路由器基本配置 业务层接口
 */
public interface IRouterBasicService {

	/**
	 * 接口配置
	 */
	boolean interfaceIP(String inte, String ipAddress, String subnetMask, Boolean openInterface, int ipVersion, Host host);

	/**
	 * 查看路由表
	 */
	String showRoutingTable(Host host);

	/**
	 * 查看接口信息
	 */
	String showInterfaceInformation(Host host, int ipVersion);

	/**
	 * 配置静态路由
	 */
	boolean staticRouter(Host host, String destinationNetwork, String subnetMask, String nextHopAddress, String outputInterface, Integer AD);

	/**
	 * 配置静态默认路由
	 */
	boolean defaultRoute(Host host, String nextHopAddress, String outputInterface);

	/**
	 * ping 操作
	 */
	String ping(Host host, String ipAddress, int ipVersion);

}
