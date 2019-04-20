package com.ie.service;

import com.ie.pojo.Host;

/**
 * 路由选择协议 业务层接口
 */
public interface IRouterProtocolService {

	/**
	 * 配置rip协议
	 */
	boolean rip(Host host, int ripVersion, String ipAddress, boolean autoSummary, String passiveInterface);

	/**
	 * 配置eigrp协议
	 */
	boolean eigrp(Host host, Integer AS, String ipAddress, boolean autoSummary, String passiveInterface);

	/**
	 * 配置ospf协议
	 */
	boolean ospf(Host host, Integer process, String routerID, String ipAddress, String wildcard, Integer area);

}
