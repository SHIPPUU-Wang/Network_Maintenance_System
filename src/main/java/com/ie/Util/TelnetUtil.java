package com.ie.Util;

import org.apache.commons.net.telnet.TelnetClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * Telnet 工具类
 */
@Component("telnetUtil")
public class TelnetUtil {

	/**
	 * 建立连接
	 *
	 * @param ipAddress 主机IP地址
	 * @param port      端口号
	 * @return Telnet连接对象
	 */
	public TelnetClient telnetConnect(String ipAddress, int port) {

		//指明Telnet终端类型，否则会返回来的数据中文会乱码
		TelnetClient telnetClient = new TelnetClient("ANSI");

		//socket延迟时间：5000ms
		telnetClient.setDefaultTimeout(50);

		// 建立一个连接
		try {
			telnetClient.connect(ipAddress, port);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return telnetClient;
	}

	/**
	 * 建立输出流（发送命令）
	 *
	 * @param telnetClient telnet 连接
	 * @return 输出流
	 */
	public PrintStream getPrintStream(TelnetClient telnetClient) {
		// 写命令的流
		return new PrintStream(telnetClient.getOutputStream());
	}

	/**
	 * 建立输入流（接收数据）
	 *
	 * @param telnetClient telnet 连接
	 * @return 输入流
	 */
	public InputStream getInputStream(TelnetClient telnetClient) {
		return telnetClient.getInputStream();
	}

	/**
	 * 发送命令
	 *
	 * @param printStream 输出流
	 * @param command     命令
	 */
	public void sendCommand(PrintStream printStream, String command) {

		// 写命令
		printStream.println(command);
		// 将命令发送到telnet Server
		printStream.flush();
		printStream.println("\r");
		printStream.flush();

		// sleep 50 ms
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 读取Server返回的数据
	 *
	 * @param inputStream 输入流
	 * @param str         结束标识
	 * @return StringBuffer
	 */
	public StringBuffer getData(InputStream inputStream, String str) {

		StringBuffer strbuffer = new StringBuffer(20000);

		byte[] b = new byte[1024];
		int size;
		try {
			//读取Server返回来的数据，直到读到结束标识
			while (true) {
				size = inputStream.read(b);
				if (-1 != size) {
					strbuffer.append(new String(b, 0, size));
					if (strbuffer.toString().trim().endsWith(str)) {
//						System.out.println("读取到Router>");
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return strbuffer;
	}

	/**
	 * 关闭 Telnet连接
	 *
	 * @param telnetClient Telnet连接对象
	 */
	public void disTelnetConnect(TelnetClient telnetClient) {
		try {
			telnetClient.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭 Telnet连接
	 *
	 * @param telnetClient Telnet连接对象
	 * @param inputStream  输入流
	 */
	public void disTelnetConnect(TelnetClient telnetClient, InputStream inputStream) {

		try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// printStream.close();

		try {
			telnetClient.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
