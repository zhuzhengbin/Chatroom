package com.scu.login;

import java.net.Socket;

/**
 * 
 * Ŀ�꣺
 * 1��ʵ��˽��
 * @author zhuzhengbin
 *
 */
public class Client {
	public static void main(String[] args) throws Exception{
		System.out.println("---------------------------------�ͻ���---------------------------------");
		Socket client = new Socket("localhost",9999);
		new Thread(new Send(client)).start();
		new Thread(new Receive(client)).start();
//		client.close();
	}
}
