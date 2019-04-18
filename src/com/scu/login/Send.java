package com.scu.login;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 封装，从客户端读取消息，然后将消息发送到服务器
 * 1、使用多线程，便于多个客户端同时与服务器连接并发送消息
 * @author zhuzhengbin
 *
 */
public class Send implements Runnable {
	// 关联的TCP连接
	private Socket client = null;
	private BufferedOutputStream bos = null;
	
	public Send() {
	}
	
	public Send(Socket client) {
		this.client = client;
		try {
			bos = new BufferedOutputStream(this.client.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void run() {
		
	}
	
	
	
}
