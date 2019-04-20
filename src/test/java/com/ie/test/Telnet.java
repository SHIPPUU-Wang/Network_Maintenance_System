package com.ie.test;

import org.apache.commons.net.telnet.TelnetClient;

import java.io.*;
import java.util.Scanner;

public class Telnet {

	TelnetClient telnetClient;
	InputStream inputStream;
	PrintStream printStream;

	public void con() {

		//指明Telnet终端类型，否则会返回来的数据中文会乱码
		telnetClient = new TelnetClient("vt200");

		//socket延迟时间：5000ms
		telnetClient.setDefaultTimeout(5000);
		try {
			telnetClient.connect("127.0.0.1", 2103);  //建立一个连接
		} catch (IOException e) {
			e.printStackTrace();
		}

		printStream = new PrintStream(telnetClient.getOutputStream());  //写命令的流
	}

	public void sendCommand() {
		/*printStream.println("\r"); //写命令
		printStream.flush(); //将命令发送到telnet Server
		printStream.println("\r"); //写命令
		printStream.flush(); //将命令发送到telnet Server
		printStream.println(" "); //写命令
		printStream.flush(); //将命令发送到telnet Server
		printStream.println(" "); //写命令
		printStream.flush(); //将命令发送到telnet Server
		printStream.println(" "); //写命令
		printStream.flush(); //将命令发送到telnet Server
		printStream.println(" "); //写命令
		printStream.flush(); //将命令发送到telnet Server*/
		printStream.println("en"); //写命令
		printStream.flush(); //将命令发送到telnet Server*/
		printStream.println(""); //写命令
		printStream.flush(); //将命令发送到telnet Server*/
		printStream.println("show ip interface brief"); //写命令
		printStream.println(" "); //写命令
		printStream.flush(); //将命令发送到telnet Server*/
		printStream.println(" \b"); //写命令
		printStream.flush(); //将命令发送到telnet Server*/

//		printStream.println("   "); //写命令
//		printStream.println("\r"); //写命令
//		printStream.println(" "); //写命令
//		printStream.println(" "); //写命令
//		printStream.println(" "); //写命令
//		printStream.flush(); //将命令发送到telnet Server
		System.out.println("命令已发送！");

		inputStream = telnetClient.getInputStream();
		Scanner input = new Scanner(inputStream);
		 /*while (true) {

				boolean flag = false;
				String str = "Codes: C - connected, S - static, R - RIP, M - mobile, B - BGP";
				if(str.equals(input.next().trim())){
					System.out.println(str);
					while (true){
						String s1 = "Router>";
						if (s1.equals(input.next().trim())){
							flag = true;
							break;
						}else {

							System.out.println(input.next());
						}
					}

				}
				if (flag){
					break;
				}
//				System.out.println(input.next());
			}*/

//		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		StringBuffer strbuffer = new StringBuffer(20000);
		String line;
		try {
			String str = "Router#show ip route";

			/*while (true) {
				strbuffer.append(in.readLine());
				if(strbuffer.toString().trim().endsWith("Router>")){
					break;
				}

			}*/

			/**********************************************************************************/
			byte[] b = new byte[1024];
			int size;
			while (true) {     //读取Server返回来的数据，直到读到登陆标识，这个时候认为可以输入用户名
				size = inputStream.read(b);

				if (-1 != size) {
					strbuffer.append(new String(b, 0, size));
					if (strbuffer.toString().trim().endsWith("R1#")) {
						System.out.println("R1#");
						break;
					} else if (strbuffer.toString().trim().endsWith("Router#")) {
						System.out.println("读取到Router#");
						break;
					}
				}
			}
//			System.out.println(strbuffer);
			/**********************************************************************************/

			/*while (true) {     //读取Server返回来的数据，直到读到登陆标识，这个时候认为可以输入用户名

				strbuffer.append(input.nextLine());
				if (strbuffer.toString().trim().endsWith("R1#")) {
					System.out.println("读取到Router>");
					break;
				}



			}*/

		} catch (IOException e) {
			e.printStackTrace();
		}






//		System.out.println("====="+strbuffer.toString());
		String[] strbrffers = strbuffer.toString().split("\n");
		/*int p = 1;
		for (String s : strbrffers) {
			System.out.println(p + ": " + s);
			p++;
		}*/
		int t = 0;
		for (int i = strbrffers.length-1; i >= 0; i--) {
			if("Router#show ip interface brief".equals(strbrffers[i].trim())){
				t = i;
			}
//			System.out.println(i+": "+strbrffers[i]);
		}

		System.out.println("t:"+t);


//		System.out.println(strbrffers.length);


		if (t != 0){
			System.out.println("*****************************");
			for(int j=t+1;j<=strbrffers.length;j++){
				if ("Router#".equals(strbrffers[j].trim())){
					break;
				}
				System.out.println(strbrffers[j]);
			}
			System.out.println("*****************************");

		}else {
			System.out.println("未获取到路由表！");
		}




//		int i = 1;
//		while (i<=100) {
//			System.out.println(input.nextLine());
//			i++;
//		}

	}

	public void discon() {

//		try {
//			inputStream.close();
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		printStream.close();

		try {
			telnetClient.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		Telnet t = new Telnet();
		t.con();
		t.sendCommand();
//		System.out.println(exeCmd());
		t.discon();

//			Scanner input = new Scanner(telnetClient.getInputStream());
//
//			System.out.println("1: " + input.next());

			/*byte[] b = new byte[1024];
			int size;
			StringBuffer sBuffer = new StringBuffer(300);
			while (true) {     //读取Server返回来的数据，直到读到登陆标识，这个时候认为可以输入用户名
				size = inputStream.read(b);

				if (-1 != size) {
					sBuffer.append(new String(b, 0, size));
//					System.out.println(sBuffer.toString());
					if (sBuffer.toString().trim().endsWith("Router>")) {
						System.out.println("读取到Router>");
						break;
					}else if(sBuffer.toString().trim().endsWith("Router#")) {
						System.out.println("读取到Router>");
						break;
					}
				}
			}*/

//			telnetClient.getInputStream();
//			System.out.println(sBuffer.toString());

			/*while (true) {
				boolean flag = false;
				String str = "Codes: C - connected, S - static, R - RIP, M - mobile, B - BGP";
				if(str.equals(input.next().trim())){
					System.out.println(str);
					while (true){
						String s1 = "Router>";
						if (s1.equals(input.next().trim())){
							flag = true;
							break;
						}else {

							System.out.println(input.next());
						}
					}

				}
				if (flag){
					break;
				}
//				System.out.println(input.next());
			}*/

//			StringBuffer sBuffer = new StringBuffer();
////			char ch = (char)inputStream.read();
//////			size = inputStream.read(b);
//////			sBuffer.append(new String(b, 0, size));
////			sBuffer.append(ch);

//			Scanner input = new Scanner(telnetClient.getInputStream());
		int i = 1;
//			while (i <= 100){
//				System.out.println(input.nextLine());
//				i++;
//			}

//			pStream.close();
//			inputStream.close();
//			telnetClient.disconnect();

//			if (null != pStream) {
//				pStream.close();
//			}
//			telnetClient.disconnect();
	}

	public static String exeCmd() {

		String result = null;

		try {

			String[] cmd = new String[]{"sh", "ip", "route"};

			Process ps = Runtime.getRuntime().exec(cmd);

			BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));

			StringBuffer sb = new StringBuffer();

			String line;

			while ((line = br.readLine()) != null) {

				//执行结果加上回车

				sb.append(line).append("\n");

			}

			result = sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}

}