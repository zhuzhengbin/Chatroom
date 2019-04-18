package com.scu.login;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 封装程序从服务器接收反馈信息，然后进行客户端部分的处理（打印显示等）
 * @author zhuzhengbin
 *
 */
public class Receive implements Runnable{
	private Socket client;	// 一条客户端与服务器的TCP连接
	private DataInputStream dis;
	
	public Receive() {
	}
	
	public Receive(Socket client) {
		this.client = client;
		try {
			dis = new DataInputStream(client.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			String msg = dis.readUTF();
			System.out.println(msg);
//			close(client);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void close(Closeable... targets) {
		for(Closeable target:targets) {
			if(target != null) {
				try {
					target.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
}
