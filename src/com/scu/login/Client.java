package com.scu.login;

import java.net.Socket;

/**
 * 
 * 目标：
 * 1、实现私聊
 * @author zhuzhengbin
 *
 */
public class Client {
	public static void main(String[] args) throws Exception{
		System.out.println("---------------------------------客户端---------------------------------");
		Socket client = new Socket("localhost",9999);
		new Thread(new Send(client)).start();
		new Thread(new Receive(client)).start();
//		client.close();
	}
}
