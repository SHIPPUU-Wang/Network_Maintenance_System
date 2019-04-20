package com.ie.Util;

import org.springframework.stereotype.Component;

import java.io.PrintStream;

/**
 * 设备基本操作 工具类
 */
@Component("basicOperationUtil")
public class BasicOperationUtil {

	/**
	 * sleep 50 ms
	 */
	private void sleep() {
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送多个回车
	 *
	 * @param printStream 输出流
	 */
	public void sendEnter(PrintStream printStream) {
		for (int i = 0; i < 5; i++) {
			// 写命令
			printStream.println("\r");
			// 将命令发送到telnet Server
			printStream.flush();
			sleep();

			printStream.println("");
			printStream.flush();

			sleep();
		}
	}

	/**
	 * 发送 enable，进入特权模式
	 *
	 * @param printStream 输出流
	 */
	public void sendEnable(PrintStream printStream) {
		printStream.println("enable");
		printStream.flush();
		printStream.println("\r");
		printStream.flush();

		sleep();
		System.out.println("command: enable");
	}

	/**
	 * 发送 configure terminal，进入全局配置模式
	 *
	 * @param printStream 输出流
	 */
	public void sendConfigure_terminal(PrintStream printStream) {
		printStream.println("configure terminal");
		printStream.flush();
		printStream.println("\r");
		printStream.flush();

		sleep();
		System.out.println("command: configure terminal");
	}

	/**
	 * 发送 hostname ，修改设备名
	 *
	 * @param printStream 输出流
	 * @param deviceName  设备名
	 */
	public void sendHostname(PrintStream printStream, String deviceName) {
		printStream.println("hostname " + deviceName);
		printStream.flush();
		printStream.println("\r");
		printStream.flush();

		sleep();
		System.out.println("command: hostname " + deviceName);
	}

	/**
	 * 发送 hostname Router，还原路由器设备名
	 *
	 * @param printStream 输出流
	 */
	public void sendHostnameRouter(PrintStream printStream) {
		printStream.println("hostname Router");
		printStream.flush();
		printStream.println("\r");
		printStream.flush();

		sleep();
		System.out.println("command: hostname Router");
	}

	/**
	 * 发送 hostname Switch，还原交换机设备名
	 *
	 * @param printStream 输出流
	 */
	public void sendHostnameSwitch(PrintStream printStream) {
		printStream.println("hostname Switch");
		printStream.flush();
		printStream.println("\r");
		printStream.flush();

		sleep();
	}

	/**
	 * 发送 interface ，进入接口配置模式
	 *
	 * @param printStream   输出流
	 * @param interfaceName 接口名
	 */
	public void sendInterface(PrintStream printStream, String interfaceName) {
		printStream.println("interface " + interfaceName);
		printStream.flush();
		printStream.println("\r");
		printStream.flush();

		sleep();

		System.out.println("command: interface " + interfaceName);
	}

	/**
	 * 发送 end，回到特权模式
	 *
	 * @param printStream 输出流
	 */
	public void sendEnd(PrintStream printStream) {
		printStream.println("end");
		printStream.flush();
		printStream.println("\r");
		printStream.flush();

		sleep();

		System.out.println("command: end");
	}

	/**
	 * 发送 exit，回到上一层模式
	 *
	 * @param printStream 输出流
	 */
	public void sendExit(PrintStream printStream) {
		printStream.println("exit");
		printStream.flush();
		printStream.println("\r");
		printStream.flush();

		sleep();

		System.out.println("command: exit");
	}

	/**
	 * 发送 show ip route，查看路由表
	 *
	 * @param printStream 输出流
	 */
	public void send_show_ip_route(PrintStream printStream) {
		printStream.println("show ip route \b  ");
		printStream.println("  \b  ");
		printStream.println("    ");
		printStream.flush();
		printStream.println("  \b  ");
		printStream.println(" ");
		printStream.flush();
		printStream.println("\r");
		printStream.flush();

		sleep();

		System.out.println("command: show ip route");
	}

	/**
	 * 发送 show ip interface brief，查看接口信息
	 *
	 * @param printStream 输出流
	 */
	public void send_show_ip_interface_brief(PrintStream printStream) {
		printStream.println("show ip interface brief");
		printStream.flush();
		printStream.println("\r");
		printStream.flush();

		sleep();
		System.out.println("command: show ip interface brief");
	}

	/**
	 * 发送 show ipv6 interface brief，查看接口信息（IPv6）
	 *
	 * @param printStream 输出流
	 */
	public void send_show_ipv6_interface_brief(PrintStream printStream) {
		printStream.println("show ipv6 interface brief");
		printStream.flush();
		printStream.println("\r");
		printStream.flush();

		sleep();

		System.out.println("command: show ipv6 interface brief");
	}

	/**
	 * 发送 ping
	 *
	 * @param printStream 输出流
	 * @param ipAddress   IP地址
	 */
	public void sendPing(PrintStream printStream, String ipAddress) {
		printStream.println("ping " + ipAddress);
		printStream.flush();
		printStream.println("\r");
		printStream.flush();

		sleep();
	}

	/**
	 * 发送 no ip domain-lookup，关闭自动域名解析
	 *
	 * @param printStream
	 */
	public void send_no_ip_domain__lookup(PrintStream printStream) {
		printStream.println("no ip domain-lookup");
		printStream.flush();
		printStream.println("\r");
		printStream.flush();

		sleep();

		System.out.println("command: no ip domain-lookup");
	}

}


