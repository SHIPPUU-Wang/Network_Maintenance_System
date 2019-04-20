package com.ie.service.impl;

import com.ie.Util.BasicOperationUtil;
import com.ie.Util.TelnetUtil;
import com.ie.pojo.Host;
import com.ie.service.IOtherService;
import org.apache.commons.net.telnet.TelnetClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.PrintStream;

/**
 * 其他配置 业务层实现类
 */
@Service("otherService")
public class OtherServiceImpl implements IOtherService {

	@Resource(name = "telnetUtil")
	private TelnetUtil telnetUtil;

	@Resource(name = "basicOperationUtil")
	private BasicOperationUtil basicOperationUtil;

	/**
	 * 普通ACL 配置
	 *
	 * @param aclNum           ACL编号
	 * @param controlStatement 控制语句
	 * @param ipAddress        IP地址
	 * @param wildcard         通配符（反掩码）
	 * @param inte             控制接口
	 * @param direction        控制接口流量方向
	 * @return boolean
	 */
	@Override
	public boolean acl1(Host host, Integer aclNum, String controlStatement, String ipAddress, String wildcard, String inte, String direction) {
		TelnetClient telnetClient = telnetUtil.telnetConnect(host.getHostAddress(), host.getPort());
		PrintStream printStream = telnetUtil.getPrintStream(telnetClient);

		// 必要操作
		necessaryOperationRouter(printStream);

		// 建立普通ACL
		telnetUtil.sendCommand(printStream, "access-list " + aclNum + " " + controlStatement + " " + ipAddress + " " + wildcard);

		telnetUtil.sendCommand(printStream, "interface " + inte);

		// 控制接口流量方向
		telnetUtil.sendCommand(printStream, "ip access-group " + aclNum + " " + direction);

		// 回到特权模式
		basicOperationUtil.sendEnd(printStream);

		// 关闭 Telnet连接
		telnetUtil.disTelnetConnect(telnetClient);

		return true;
	}

	/**
	 * 扩展ACL 配置
	 *
	 * @param aclNum           ACL编号
	 * @param controlStatement 控制语句
	 * @param protocol         指定协议
	 * @param sourceIp         源 IP
	 * @param destinationIp    目的 IP
	 * @param service          服务 / 端口号
	 * @param inte             控制接口
	 * @param direction        控制接口流量方向
	 * @return boolean
	 */
	@Override
	public boolean acl2(Host host, Integer aclNum, String controlStatement, String protocol, String sourceIp, String destinationIp, String service, String inte, String direction) {
		TelnetClient telnetClient = telnetUtil.telnetConnect(host.getHostAddress(), host.getPort());
		PrintStream printStream = telnetUtil.getPrintStream(telnetClient);

		// 必要操作
		necessaryOperationRouter(printStream);

		String command = "access-list " + aclNum + " " + controlStatement + " " + protocol + " ";
		// host 12.12.12.0 host 23.23.23.0 eq 80";

		// 判断 sourceIp
		if ("any".equals(sourceIp))
			command = command + sourceIp + " ";
		else
			command = command + "host " + sourceIp + " ";

		// 判断 destinationIp
		if ("any".equals(destinationIp))
			command = command + destinationIp + " ";
		else
			command = command + "host " + destinationIp + " ";

		command = command + "eq " + service;

		// 建立扩展ACL
		telnetUtil.sendCommand(printStream, command);

		telnetUtil.sendCommand(printStream, "interface " + inte);

		telnetUtil.sendCommand(printStream, "ip access-group " + aclNum + " " + direction);

		// 回到特权模式
		basicOperationUtil.sendEnd(printStream);

		// 关闭 Telnet连接
		telnetUtil.disTelnetConnect(telnetClient);

		return true;
	}

	/**
	 * 静态NAT配置
	 *
	 * @param source          指定转换源
	 * @param localIpAddress  本地IP地址
	 * @param globalIpAddress 全局IP地址（转换后IP地址）
	 * @return boolean
	 */
	@Override
	public boolean staticNAT(Host host, String source, String localIpAddress, String globalIpAddress) {
		TelnetClient telnetClient = telnetUtil.telnetConnect(host.getHostAddress(), host.getPort());
		PrintStream printStream = telnetUtil.getPrintStream(telnetClient);

		// 必要操作
		necessaryOperationRouter(printStream);

		// 配置静态NAT
		telnetUtil.sendCommand(printStream, "ip nat " + source + " source static " + localIpAddress + " " + globalIpAddress);

		// 回到特权模式
		basicOperationUtil.sendEnd(printStream);

		// 关闭 Telnet连接
		telnetUtil.disTelnetConnect(telnetClient);

		return true;
	}

	/**
	 * 动态NAT配置
	 *
	 * @param startIpAddress 地址池起始IP
	 * @param endIpAddress   地址池结束IP
	 * @param subnetMask     子网掩码
	 * @param source         指定转换源
	 * @param aclNum         ACL编号
	 * @return boolean
	 */
	@Override
	public boolean dynamicNAT(Host host, String startIpAddress, String endIpAddress, String subnetMask, String source, Integer aclNum) {
		TelnetClient telnetClient = telnetUtil.telnetConnect(host.getHostAddress(), host.getPort());
		PrintStream printStream = telnetUtil.getPrintStream(telnetClient);

		// 必要操作
		necessaryOperationRouter(printStream);

		// 配置 NAT地址池
		telnetUtil.sendCommand(printStream, "ip nat pool todd " + startIpAddress + " " + endIpAddress + " netmask " + subnetMask);

		// ACL匹配地址 转换为 NAT地址池中的IP地址
		telnetUtil.sendCommand(printStream, "ip nat " + source + " source list " + aclNum + " pool todd");

		// 回到特权模式
		basicOperationUtil.sendEnd(printStream);

		// 关闭 Telnet连接
		telnetUtil.disTelnetConnect(telnetClient);

		return true;
	}

	/**
	 * DHCP 服务配置
	 *
	 * @param dhcpName   DHCP地址池名称
	 * @param ipAddress  DHCP分配网段
	 * @param subnetMask 子网掩码
	 * @param gateway    默认网关
	 * @param dns        DNS服务器
	 * @return boolean
	 */
	@Override
	public boolean dhcp(Host host, String dhcpName, String ipAddress, String subnetMask, String gateway, String dns) {
		TelnetClient telnetClient = telnetUtil.telnetConnect(host.getHostAddress(), host.getPort());
		PrintStream printStream = telnetUtil.getPrintStream(telnetClient);

		// 必要操作
		necessaryOperationRouter(printStream);

		// 开启DHCP服务
		telnetUtil.sendCommand(printStream, "service dhcp");

		// 关闭DHCP日志记录
		telnetUtil.sendCommand(printStream, "no ip dhcp conflict logging");

		// 配置DHCP地址池名称
		telnetUtil.sendCommand(printStream, "ip dhcp pool " + dhcpName);

		// 分配DHCP网段
		telnetUtil.sendCommand(printStream, "network " + ipAddress + " " + subnetMask);
		// 配置 默认网关
		telnetUtil.sendCommand(printStream, "default-router " + gateway);
		// 配置 DNS服务器
		telnetUtil.sendCommand(printStream, "dns-server " + dns);

		// 回到特权模式
		basicOperationUtil.sendEnd(printStream);

		// 关闭 Telnet连接
		telnetUtil.disTelnetConnect(telnetClient);

		return true;
	}

	/**
	 * DHCP服务 不分配地址设置
	 *
	 * @param startIpAddress 起始 IP
	 * @param endIpAddress   结束 IP
	 * @return boolean
	 */
	@Override
	public boolean excludedIP(Host host, String startIpAddress, String endIpAddress) {
		TelnetClient telnetClient = telnetUtil.telnetConnect(host.getHostAddress(), host.getPort());
		PrintStream printStream = telnetUtil.getPrintStream(telnetClient);

		// 必要操作
		necessaryOperationRouter(printStream);

		String command = "ip dhcp excluded-address " + startIpAddress + " ";

		// 判断endIpAddress是否为空
		if (endIpAddress != null && !endIpAddress.trim().isEmpty())
			command = command + endIpAddress;

		// DHCP服务 不分配地址设置
		telnetUtil.sendCommand(printStream, command);

		// 回到特权模式
		basicOperationUtil.sendEnd(printStream);

		// 关闭 Telnet连接
		telnetUtil.disTelnetConnect(telnetClient);

		return true;
	}

	/**
	 * DHCP 客户端接口 配置
	 *
	 * @param inte 客户端接口
	 * @return boolean
	 */
	@Override
	public boolean clientInterface(Host host, String inte) {
		TelnetClient telnetClient = telnetUtil.telnetConnect(host.getHostAddress(), host.getPort());
		PrintStream printStream = telnetUtil.getPrintStream(telnetClient);

		// 必要操作
		necessaryOperationRouter(printStream);

		telnetUtil.sendCommand(printStream, "interface " + inte);

		// 将该接口设置从DHCP服务获取IP地址
		telnetUtil.sendCommand(printStream, "ip address dhcp");

		telnetUtil.sendCommand(printStream, "no shutdown");

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
		// basicOperationUtil.sendEnter(printStream);
		// basicOperationUtil.sendExit(printStream);
		basicOperationUtil.sendEnter(printStream);
		basicOperationUtil.sendEnable(printStream);
		basicOperationUtil.sendConfigure_terminal(printStream);
		basicOperationUtil.sendHostnameRouter(printStream);
		basicOperationUtil.send_no_ip_domain__lookup(printStream);
	}
}
