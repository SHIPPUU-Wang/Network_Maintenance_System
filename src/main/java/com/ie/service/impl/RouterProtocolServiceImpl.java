package com.ie.service.impl;

import com.ie.Util.BasicOperationUtil;
import com.ie.Util.TelnetUtil;
import com.ie.pojo.Host;
import com.ie.service.IRouterProtocolService;
import org.apache.commons.net.telnet.TelnetClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.PrintStream;

/**
 * 路由选择协议 业务层实现类
 */
@Service("routerProtocolService")
public class RouterProtocolServiceImpl implements IRouterProtocolService {

	@Resource(name = "telnetUtil")
	private TelnetUtil telnetUtil;

	@Resource(name = "basicOperationUtil")
	private BasicOperationUtil basicOperationUtil;

	/**
	 * 配置rip协议
	 *
	 * @param host             主机设备信息
	 * @param ripVersion       rip版本
	 * @param ipAddress        IP地址
	 * @param autoSummary      自动汇总判断
	 * @param passiveInterface 被动端口
	 * @return boolean
	 */
	@Override
	public boolean rip(Host host, int ripVersion, String ipAddress, boolean autoSummary, String passiveInterface) {

		TelnetClient telnetClient = telnetUtil.telnetConnect(host.getHostAddress(), host.getPort());
		PrintStream printStream = telnetUtil.getPrintStream(telnetClient);

		// 必要操作
		necessaryOperationRouter(printStream);

		// 进入rip配置模式
		telnetUtil.sendCommand(printStream, "router rip");
		// 设置rip版本
		telnetUtil.sendCommand(printStream, "version " + ripVersion);

		// 判断自动汇总
		if (autoSummary)
			// 关闭自动汇总
			telnetUtil.sendCommand(printStream, "no auto-summary");
		else
			// 开启自动汇总
			telnetUtil.sendCommand(printStream, "auto-summary");

		// 判断ipAddress
		if (ipAddress != null && !ipAddress.trim().isEmpty())
			telnetUtil.sendCommand(printStream, "network " + ipAddress);

		// 判断被动接口
		if (passiveInterface != null && !passiveInterface.trim().isEmpty())
			telnetUtil.sendCommand(printStream, "passive-interface " + passiveInterface);

		// 回到特权模式
		basicOperationUtil.sendEnd(printStream);

		// 关闭 Telnet连接
		telnetUtil.disTelnetConnect(telnetClient);

		return true;
	}

	/**
	 * 配置eigrp协议
	 *
	 * @param host             主机设备信息
	 * @param AS               AS号
	 * @param ipAddress        IP地址
	 * @param autoSummary      自动汇总判断
	 * @param passiveInterface 被动端口
	 * @return boolean
	 */
	@Override
	public boolean eigrp(Host host, Integer AS, String ipAddress, boolean autoSummary, String passiveInterface) {

		TelnetClient telnetClient = telnetUtil.telnetConnect(host.getHostAddress(), host.getPort());
		PrintStream printStream = telnetUtil.getPrintStream(telnetClient);

		// 必要操作
		necessaryOperationRouter(printStream);

		// 进入eigrp配置模式
		telnetUtil.sendCommand(printStream, "router eigrp " + AS);

		// 判断自动汇总
		if (autoSummary)
			// 关闭自动汇总
			telnetUtil.sendCommand(printStream, "no auto-summary");
		else
			// 开启自动汇总
			telnetUtil.sendCommand(printStream, "auto-summary");

		// 判断ipAddress
		if (ipAddress != null && !ipAddress.trim().isEmpty())
			telnetUtil.sendCommand(printStream, "network " + ipAddress);

		// 判断被动接口
		if (passiveInterface != null && !passiveInterface.trim().isEmpty())
			telnetUtil.sendCommand(printStream, "passive-interface " + passiveInterface);

		// 回到特权模式
		basicOperationUtil.sendEnd(printStream);

		// 关闭 Telnet连接
		telnetUtil.disTelnetConnect(telnetClient);

		return true;
	}

	/**
	 * 配置ospf协议
	 *
	 * @param host      主机设备信息
	 * @param process   进程ID
	 * @param routerID  routerID
	 * @param ipAddress IP地址
	 * @param wildcard  通配符（反掩码）
	 * @param area      区域号
	 * @return boolean
	 */
	@Override
	public boolean ospf(Host host, Integer process, String routerID, String ipAddress, String wildcard, Integer area) {

		TelnetClient telnetClient = telnetUtil.telnetConnect(host.getHostAddress(), host.getPort());
		PrintStream printStream = telnetUtil.getPrintStream(telnetClient);

		// 必要操作
		necessaryOperationRouter(printStream);

		// 进入ospf配置模式
		telnetUtil.sendCommand(printStream, "router ospf " + process);

		// 设置routerID
		if (routerID != null && !routerID.trim().isEmpty())
			telnetUtil.sendCommand(printStream, "router-id " + routerID);

		// 判断ipAddress
		if (ipAddress != null && !ipAddress.trim().isEmpty())
			telnetUtil.sendCommand(printStream, "network " + ipAddress + " " + wildcard + " area " + area);

		// 回到特权模式
		basicOperationUtil.sendEnd(printStream);

		// 关闭 Telnet连接
		telnetUtil.disTelnetConnect(telnetClient);

		return true;
	}

	/**
	 * Router 必要操作
	 *
	 * @param printStream 输出流
	 */
	private void necessaryOperationRouter(PrintStream printStream) {
//		basicOperationUtil.sendEnter(printStream);
//		basicOperationUtil.sendExit(printStream);
		basicOperationUtil.sendEnter(printStream);
		basicOperationUtil.sendEnable(printStream);
		basicOperationUtil.sendConfigure_terminal(printStream);
		basicOperationUtil.sendHostnameRouter(printStream);
		basicOperationUtil.send_no_ip_domain__lookup(printStream);
	}

}
