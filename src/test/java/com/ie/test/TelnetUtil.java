package com.ie.test;

import org.apache.commons.net.telnet.TelnetClient;

import java.io.InputStream;
import java.io.PrintStream;

public class TelnetUtil {

	// telnet有VT100 VT52 VT220 VTNT ANSI等协议。
	private TelnetClient telnet = new TelnetClient("vt200");

	private InputStream in;
	private PrintStream out;

//	private static final String DEFAULT_AIX_PROMPT = "C:\\Users\\Administrator>";
	private static final String DEFAULT_AIX_PROMPT = "Router>";
	// telnet 端口
	private String port;
	// 用户名
	private String user;
	// 密码
	private String password;
	// IP 地址
	private String ip;
	// 缺省端口
	private static final int DEFAULT_TELNET_PORT = 23;

	public TelnetUtil(String ip, String user, String password) {
		this.ip = ip;
		this.port = String.valueOf(TelnetUtil.DEFAULT_TELNET_PORT);
		this.user = user;
		this.password = password;
	}

	public TelnetUtil(String ip, String port) {
		this.ip = ip;
		this.port = port;

	}

	public TelnetUtil(String ip, String port, String user, String password) {
		this.ip = ip;
		this.port = port;
		this.user = user;
		this.password = password;
	}

	/**
	 * @return boolean 连接成功返回true，否则返回false
	 */
	private boolean connect() {
		boolean isConnect = true;
		try {
			telnet.connect(ip, Integer.parseInt(port));
			in = telnet.getInputStream();
			out = new PrintStream(telnet.getOutputStream());


			/** Log the user on* */
			/*readUntil("login: ");
			write(user);
			readUntil("password: ");
			write(password);*/
			/** Advance to a prompt */
//			readUntil(DEFAULT_AIX_PROMPT);


		} catch (Exception e) {
			isConnect = false;
			e.printStackTrace();
			return isConnect;
		}
		return isConnect;
	}

	public void su(String user, String password) {
		try {
			write("su" + " - " + user);
			readUntil("Password:");
			write(password);
			readUntil(DEFAULT_AIX_PROMPT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String readUntil(String pattern) {
		try {
			char lastChar = pattern.charAt(pattern.length() - 1);
			StringBuffer sb = new StringBuffer();
			char ch = (char) in.read();
			while (true) {
				// System.out.print(ch);// ---需要注释掉
				sb.append(ch);
				if (ch == lastChar) {
					if (sb.toString().trim().endsWith(pattern)) {
						// 处理编码，界面显示乱码问题
//						byte[] temp = sb.toString().getBytes("iso8859-1");
//						return new String(temp, "GBK");
						return sb.toString();
					}
				}
				ch = (char) in.read();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void write(String value) {
		try {
			System.out.println("******************* value:"+value);
			out.println(value);

			out.flush();
			// System.out.println(value);// ---需要注释掉
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String sendCommand(String command) {
		try {
			write(command);
			return readUntil("Router>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void disconnect() {
		try {
			telnet.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getIPConfig() {
		this.connect();
		String result = this.sendCommand("ipconfig");
		this.disconnect();
		// 去除命令提示符
		return result.substring(0, result.indexOf(DEFAULT_AIX_PROMPT));
	}

	private String getDir() {
		this.connect();
		String result = this.sendCommand("dir");
		this.disconnect();
		// 去除命令提示符
		return result.substring(0, result.indexOf(DEFAULT_AIX_PROMPT));
	}

	private String getDir1() {
		boolean cc = this.connect();
		if(cc){
			System.out.println("连接成功");
		}else {
			System.out.println("连接失败！");
		}

		String result = this.sendCommand("sh ip route\r");
		this.disconnect();
		// 去除命令提示符
		//return result.substring(0, result.indexOf(DEFAULT_AIX_PROMPT));
		return result;
	}

	public static void main(String[] args) {
		TelnetUtil telnet = new TelnetUtil("127.0.0.1","2501");
		System.out.println(telnet.getDir1());
//		System.out.println(telnet.getIPConfig());

//		String str = null;
//		if (str == null){
//			System.out.println("可以");
//		}else {
//			System.out.println("不可以");
//		}
	}

}
