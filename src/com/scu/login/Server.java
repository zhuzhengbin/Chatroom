package com.scu.login;

import java.net.ServerSocket;

/**
 * 服务器端：负责接收来自客户端的消息，进行简单处理（判断登陆的账号密码是否正确）后反馈给客户端
 * @author zhuzhengbin
 *
 */
public class Server {
	public static void main(String[] args) throws Exception {
		ServerSocket server = new ServerSocket(9999);
	}
}
