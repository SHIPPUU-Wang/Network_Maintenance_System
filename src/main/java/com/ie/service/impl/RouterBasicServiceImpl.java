package com.ie.service.impl;

import com.ie.Util.BasicOperationUtil;
import com.ie.Util.TelnetUtil;
import com.ie.pojo.Host;
import com.ie.service.IRouterBasicService;
import org.apache.commons.net.telnet.TelnetClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * 路由器基本配置 业务层实现类
 */
@Service("routerBasicService")
public class RouterBasicServiceImpl implements IRouterBasicService {

	@Resource(name = "telnetUtil")
	private TelnetUtil telnetUtil;

	@Resource(name = "basicOperationUtil")
	private BasicOperationUtil basicOperationUtil;

	/**
	 * 接口配置
	 *
	 * @param inte
	 * @param ipAddress
	 * @param subnetMask
	 * @param openInterface
	 * @param ipVersion
	 * @param host          host信息
	 * @return boolean值
	 */
	@Override
	public boolean interfaceIP(String inte, String ipAddress, String subnetMask, Boolean openInterface, int ipVersion, Host host) {

		TelnetClient telnetClient = telnetUtil.telnetConnect(host.getHostAddress(), host.getPort());
		PrintStream printStream = telnetUtil.getPrintStream(telnetClient);

		// 必要操作
		necessaryOperationRouter(printStream);

		// 开启 IPv6 单播路由功能
		if (ipVersion == 6)
			telnetUtil.sendCommand(printStream, "ipv6 unicast-routing");

		// 进入接口配置模式
		basicOperationUtil.sendInterface(printStream, inte);

		if (ipAddress != null && !(ipAddress.trim().isEmpty())) {

			String command = "";
			if (ipVersion == 4) {
				command = "ip address " + ipAddress + " " + subnetMask;
				System.out.println("command: " + command);
			} else if (ipVersion == 6) {
				command = "ipv6 address " + ipAddress + "/" + subnetMask;
			}

			telnetUtil.sendCommand(printStream, command);

		}

		// 打开接口
		if (openInterface) {
			telnetUtil.sendCommand(printStream, "no shutdown");
			System.out.println("command: no shutdown");
		} else
			telnetUtil.sendCommand(printStream, "shutdown");

		// 回到特权模式
		basicOperationUtil.sendEnd(printStream);

		// 关闭 Telnet连接
		telnetUtil.disTelnetConnect(telnetClient);

		return true;
	}

	/**
	 * 查看路由表
	 *
	 * @param host
	 * @return 路由表数据
	 */
	@Override
	public String showRoutingTable(Host host) {

		String findStr = "";

		TelnetClient telnetClient = telnetUtil.telnetConnect(host.getHostAddress(), host.getPort());
		PrintStream printStream = telnetUtil.getPrintStream(telnetClient);
		InputStream inputStream = telnetUtil.getInputStream(telnetClient);

		// 必要操作
		necessaryOperationRouter(printStream);
		// 回到特权模式
		basicOperationUtil.sendEnd(printStream);
		// 查看路由表
		basicOperationUtil.send_show_ip_route(printStream);
		// 获取初始数据
		StringBuffer stringBuffer = telnetUtil.getData(inputStream, "Router#");

		// 截取数据
		findStr = dataInterception(stringBuffer, "Router#show ip route");

		// 关闭 Telnet连接
		telnetUtil.disTelnetConnect(telnetClient, inputStream);

		return findStr;
	}

	/**
	 * 查看接口信息
	 *
	 * @param host      主机信息
	 * @param ipVersion IP版本
	 * @return 接口信息
	 */
	@Override
	public String showInterfaceInformation(Host host, int ipVersion) {

		String findStr = "";

		TelnetClient telnetClient = telnetUtil.telnetConnect(host.getHostAddress(), host.getPort());
		PrintStream printStream = telnetUtil.getPrintStream(telnetClient);
		InputStream inputStream = telnetUtil.getInputStream(telnetClient);

		// 必要操作
		necessaryOperationRouter(printStream);
		// 回到特权模式
		basicOperationUtil.sendEnd(printStream);

		if (ipVersion == 4) {
			// 查看接口信息
			basicOperationUtil.send_show_ip_interface_brief(printStream);
			// 获取初始数据
			StringBuffer stringBuffer = telnetUtil.getData(inputStream, "Router#");
			// 截取数据
			findStr = dataInterception(stringBuffer, "Router#show ip interface brief");
		} else if (ipVersion == 6) {
			// 查看接口信息
			basicOperationUtil.send_show_ipv6_interface_brief(printStream);
			// 获取初始数据
			StringBuffer stringBuffer = telnetUtil.getData(inputStream, "Router#");
			// 截取数据
			findStr = dataInterception(stringBuffer, "Router#show ipv6 interface brief");
		}

		// 关闭 Telnet连接
		telnetUtil.disTelnetConnect(telnetClient, inputStream);

		return findStr;
	}

	/**
	 * 配置静态路由
	 *
	 * @param host               主机设备信息
	 * @param destinationNetwork 目的网络
	 * @param subnetMask         子网掩码
	 * @param nextHopAddress     下一跳地址
	 * @param outputInterface    输出接口
	 * @param AD                 管理距离
	 * @return boolean
	 */
	@Override
	public boolean staticRouter(Host host, String destinationNetwork, String subnetMask, String nextHopAddress, String outputInterface, Integer AD) {
		TelnetClient telnetClient = telnetUtil.telnetConnect(host.getHostAddress(), host.getPort());
		PrintStream printStream = telnetUtil.getPrintStream(telnetClient);

		// 必要操作
		necessaryOperationRouter(printStream);

		String command = "ip route " + destinationNetwork + " " + subnetMask + " ";

		// 判断 下一跳地址、输出接口
		if ((nextHopAddress == null || nextHopAddress.trim().isEmpty()) && outputInterface != null && !outputInterface.trim().isEmpty())
			command = command + outputInterface;
		else if ((outputInterface == null || outputInterface.trim().isEmpty()) && nextHopAddress != null && !nextHopAddress.trim().isEmpty())
			command = command + nextHopAddress;
		else if (!(nextHopAddress == null || outputInterface == null || nextHopAddress.trim().isEmpty() || outputInterface.trim().isEmpty()))
			command = command + outputInterface + " " + nextHopAddress;
		else return false;

		// 判断管理距离AD
		if (AD != null && AD > 0)
			command = command + " " + AD;

		// 发送关键命令
		telnetUtil.sendCommand(printStream, command);

		// 回到特权模式
		basicOperationUtil.sendEnd(printStream);

		// 关闭 Telnet连接
		telnetUtil.disTelnetConnect(telnetClient);

		return true;
	}

	/**
	 * 配置静态默认路由
	 *
	 * @param host            主机设备信息
	 * @param nextHopAddress  下一跳地址
	 * @param outputInterface 输出接口
	 * @return boolean
	 */
	@Override
	public boolean defaultRoute(Host host, String nextHopAddress, String outputInterface) {
		TelnetClient telnetClient = telnetUtil.telnetConnect(host.getHostAddress(), host.getPort());
		PrintStream printStream = telnetUtil.getPrintStream(telnetClient);

		// 必要操作
		necessaryOperationRouter(printStream);

		String command = "ip route 0.0.0.0 0.0.0.0 ";

		// 判断 下一跳地址、输出接口
		if ((nextHopAddress == null || nextHopAddress.trim().isEmpty()) && outputInterface != null && !outputInterface.trim().isEmpty())
			command = command + outputInterface;
		else if ((outputInterface == null || outputInterface.trim().isEmpty()) && nextHopAddress != null && !nextHopAddress.trim().isEmpty())
			command = command + nextHopAddress;
		else if (!(nextHopAddress == null || outputInterface == null || nextHopAddress.trim().isEmpty() || outputInterface.trim().isEmpty()))
			command = command + outputInterface + " " + nextHopAddress;
		else return false;

		// 发送关键命令
		telnetUtil.sendCommand(printStream, command);

		// 回到特权模式
		basicOperationUtil.sendEnd(printStream);

		// 关闭 Telnet连接
		telnetUtil.disTelnetConnect(telnetClient);

		return true;
	}

	/**
	 * ping 操作
	 *
	 * @param host      主机设备信息
	 * @param ipAddress IP 地址
	 * @param ipVersion IP 版本
	 * @return ping操作结果数据
	 */
	@Override
	public String ping(Host host, String ipAddress, int ipVersion) {
		TelnetClient telnetClient = telnetUtil.telnetConnect(host.getHostAddress(), host.getPort());
		PrintStream printStream = telnetUtil.getPrintStream(telnetClient);
		InputStream inputStream = telnetUtil.getInputStream(telnetClient);

		// 必要操作
		necessaryOperationRouter(printStream);
		// 回到特权模式
		basicOperationUtil.sendEnd(printStream);

		if (ipAddress == null || ipAddress.trim().isEmpty())
			return null;

		// 发送 ping
		basicOperationUtil.sendPing(printStream, ipAddress);

		String findStr = "";

		// 获取初始数据
		StringBuffer stringBuffer = telnetUtil.getData(inputStream, "Router#");
		// 截取数据
		findStr = dataInterception(stringBuffer, "Router#ping " + ipAddress);

		System.out.println(findStr);

		// 关闭 Telnet连接
		telnetUtil.disTelnetConnect(telnetClient, inputStream);

		return findStr;
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

	/**
	 * 对初始数据进行截取
	 *
	 * @param stringBuffer 原始数据
	 * @param flag         截取标志
	 * @return 截取结果
	 */
	private String dataInterception(StringBuffer stringBuffer, String flag) {

		StringBuilder findStringBuffer = new StringBuilder();

		if (stringBuffer == null) {
			findStringBuffer = null;
		} else {

			String[] strbrffers = stringBuffer.toString().split("\n");

			int t = 0;
			for (int i = strbrffers.length - 1; i >= 0; i--) {
				//
				if (flag.equals(strbrffers[i].trim())) {
					t = i;
				}
			}

			if (t != 0) {
				for (int j = t + 1; j <= strbrffers.length; j++) {
					if ("Router#".equals(strbrffers[j].trim())) {
						break;
					}
//					System.out.println(strbrffers[j]);
					findStringBuffer.append(strbrffers[j]).append('\n');
				}

			} else {
				System.out.println("未截取到数据！");
				findStringBuffer = null;
			}
		}

		if (findStringBuffer == null)
			return null;
		else
			return findStringBuffer.toString();
	}

}
