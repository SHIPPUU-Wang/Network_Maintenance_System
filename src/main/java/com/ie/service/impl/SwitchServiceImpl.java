package com.ie.service.impl;

import com.ie.Util.BasicOperationUtil;
import com.ie.Util.TelnetUtil;
import com.ie.pojo.Host;
import com.ie.service.ISwitchService;
import org.apache.commons.net.telnet.TelnetClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.PrintStream;

/**
 * 交换机配置 业务层实现类
 */
@Service("switchService")
public class SwitchServiceImpl implements ISwitchService {

	@Resource(name = "telnetUtil")
	private TelnetUtil telnetUtil;

	@Resource(name = "basicOperationUtil")
	private BasicOperationUtil basicOperationUtil;

	/**
	 * 接口设置 access模式
	 *
	 * @param inte    接口
	 * @param vlan    Vlan划分
	 * @param maximum 接口最大接入数
	 * @return boolean
	 */
	@Override
	public boolean access(Host host, String inte, Integer vlan, Integer maximum) {
		TelnetClient telnetClient = telnetUtil.telnetConnect(host.getHostAddress(), host.getPort());
		PrintStream printStream = telnetUtil.getPrintStream(telnetClient);

		// 必要操作
		necessaryOperationSwitch(printStream);

		// 进入接口配置模式
		basicOperationUtil.sendInterface(printStream, inte);

		// 接口设置 access模式
		telnetUtil.sendCommand(printStream, "switchport mode access");

		// Vlan划分
		if (vlan != null)
			telnetUtil.sendCommand(printStream, "switchport access vlan " + vlan);

		// 接口最大接入数
		if (maximum != null) {
			telnetUtil.sendCommand(printStream, "switchport port-security");
			telnetUtil.sendCommand(printStream, "switchport port-security maximum " + maximum);
		}

		// 回到特权模式
		basicOperationUtil.sendEnd(printStream);

		// 关闭 Telnet连接
		telnetUtil.disTelnetConnect(telnetClient);

		return true;
	}

	/**
	 * 接口设置 trunk模式
	 *
	 * @param inte       接口
	 * @param vlan       允许通过的Vlan
	 * @param nativeVlan 本帧Vlan
	 * @return boolean
	 */
	@Override
	public boolean trunk(Host host, String inte, String vlan, Integer nativeVlan) {
		TelnetClient telnetClient = telnetUtil.telnetConnect(host.getHostAddress(), host.getPort());
		PrintStream printStream = telnetUtil.getPrintStream(telnetClient);

		// 必要操作
		necessaryOperationSwitch(printStream);

		// 进入接口配置模式
		basicOperationUtil.sendInterface(printStream, inte);

		// 接口设置 trunk模式
		telnetUtil.sendCommand(printStream, "switchport mode trunk");

		if (vlan != null && !vlan.trim().isEmpty())
			// 允许通过的Vlan
			telnetUtil.sendCommand(printStream, "switchport trunk allowed vlan " + vlan);

		if (nativeVlan != null)
			// 本帧Vlan
			telnetUtil.sendCommand(printStream, "switchport trunk native vlan " + nativeVlan);

		// 回到特权模式
		basicOperationUtil.sendEnd(printStream);

		// 关闭 Telnet连接
		telnetUtil.disTelnetConnect(telnetClient);

		return true;
	}

	/**
	 * 创建 Vlan
	 *
	 * @param vlan     Vlan编号
	 * @param vlanName Vlan名称
	 * @return boolean
	 */
	@Override
	public boolean createVlan(Host host, Integer vlan, String vlanName) {
		TelnetClient telnetClient = telnetUtil.telnetConnect(host.getHostAddress(), host.getPort());
		PrintStream printStream = telnetUtil.getPrintStream(telnetClient);

		// 必要操作
		necessaryOperationSwitch(printStream);

		// 创建 Vlan
		telnetUtil.sendCommand(printStream, "vlan " + vlan);

		if (vlanName != null && !vlanName.trim().isEmpty())
			telnetUtil.sendCommand(printStream, "name " + vlanName);

		// 回到特权模式
		basicOperationUtil.sendEnd(printStream);

		// 关闭 Telnet连接
		telnetUtil.disTelnetConnect(telnetClient);

		return true;
	}

	/**
	 * 对Vlan配置IP地址
	 *
	 * @param vlan       Vlan编号
	 * @param ipAddress  IP地址
	 * @param subnetMask 子网掩码
	 * @return boolean
	 */
	@Override
	public boolean vlanIP(Host host, Integer vlan, String ipAddress, String subnetMask) {
		TelnetClient telnetClient = telnetUtil.telnetConnect(host.getHostAddress(), host.getPort());
		PrintStream printStream = telnetUtil.getPrintStream(telnetClient);

		// 必要操作
		necessaryOperationSwitch(printStream);

		// 进入 vlan虚拟接口配置模式
		telnetUtil.sendCommand(printStream, "interface vlan " + vlan);

		telnetUtil.sendCommand(printStream, "ip address " + ipAddress + " " + subnetMask);

		telnetUtil.sendCommand(printStream, "no shutdown");

		// 回到特权模式
		basicOperationUtil.sendEnd(printStream);

		// 关闭 Telnet连接
		telnetUtil.disTelnetConnect(telnetClient);

		return true;
	}

	/**
	 * 修改优先级
	 *
	 * @param vlan     Vlan编号
	 * @param priority 优先级
	 * @return boolean
	 */
	@Override
	public boolean modifyPriority(Host host, Integer vlan, Integer priority) {
		TelnetClient telnetClient = telnetUtil.telnetConnect(host.getHostAddress(), host.getPort());
		PrintStream printStream = telnetUtil.getPrintStream(telnetClient);

		// 必要操作
		necessaryOperationSwitch(printStream);

		// 修改优先级
		telnetUtil.sendCommand(printStream, "spanning-tree vlan " + vlan + " priority " + priority);

		// 回到特权模式
		basicOperationUtil.sendEnd(printStream);

		// 关闭 Telnet连接
		telnetUtil.disTelnetConnect(telnetClient);

		return true;
	}

	/**
	 * 设定主根/副根
	 *
	 * @param vlan1 主根 Vlan编号
	 * @param vlan2 副根 Vlan编号
	 * @return boolean
	 */
	@Override
	public boolean root(Host host, Integer vlan1, Integer vlan2) {
		TelnetClient telnetClient = telnetUtil.telnetConnect(host.getHostAddress(), host.getPort());
		PrintStream printStream = telnetUtil.getPrintStream(telnetClient);

		// 必要操作
		necessaryOperationSwitch(printStream);

		// 设定主根
		telnetUtil.sendCommand(printStream, "spanning-tree vlan " + vlan1 + " root primary");

		// 设定副根
		telnetUtil.sendCommand(printStream, "spanning-tree vlan " + vlan2 + " root secondary");

		// 回到特权模式
		basicOperationUtil.sendEnd(printStream);

		// 关闭 Telnet连接
		telnetUtil.disTelnetConnect(telnetClient);

		return true;
	}

	/**
	 * 设置快速端口
	 *
	 * @param inte 选择接口
	 * @return boolean
	 */
	@Override
	public boolean portfast(Host host, String inte) {
		TelnetClient telnetClient = telnetUtil.telnetConnect(host.getHostAddress(), host.getPort());
		PrintStream printStream = telnetUtil.getPrintStream(telnetClient);

		// 必要操作
		necessaryOperationSwitch(printStream);

		// 进入接口配置模式
		basicOperationUtil.sendInterface(printStream, inte);

		// 设置快速端口
		telnetUtil.sendCommand(printStream, "spanning-tree portfast");

		// 回到特权模式
		basicOperationUtil.sendEnd(printStream);

		// 关闭 Telnet连接
		telnetUtil.disTelnetConnect(telnetClient);

		return true;
	}

	/**
	 * STP 其他设置
	 *
	 * @param command 配置命令
	 * @return boolean
	 */
	@Override
	public boolean other(Host host, String command) {
		TelnetClient telnetClient = telnetUtil.telnetConnect(host.getHostAddress(), host.getPort());
		PrintStream printStream = telnetUtil.getPrintStream(telnetClient);

		// 必要操作
		necessaryOperationSwitch(printStream);

		// STP 其他设置
		telnetUtil.sendCommand(printStream, "spanning-tree " + command);

		// 回到特权模式
		basicOperationUtil.sendEnd(printStream);

		// 关闭 Telnet连接
		telnetUtil.disTelnetConnect(telnetClient);

		return true;
	}

	/**
	 * Switch 必要操作
	 *
	 * @param printStream 输出流
	 */
	private void necessaryOperationSwitch(PrintStream printStream) {
		// basicOperationUtil.sendEnter(printStream);
		// basicOperationUtil.sendExit(printStream);
		basicOperationUtil.sendEnter(printStream);
		basicOperationUtil.sendEnable(printStream);
		basicOperationUtil.sendConfigure_terminal(printStream);
		basicOperationUtil.sendHostnameSwitch(printStream);
		basicOperationUtil.send_no_ip_domain__lookup(printStream);
	}
}
