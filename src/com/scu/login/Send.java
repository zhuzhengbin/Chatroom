package com.scu.login;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
	private BufferedReader console;
	private DataOutputStream dos;
	private boolean isRunning;


	public Send() {	// 空构造器
	}

	public Send(Socket client) {
		this.client = client;
		this.isRunning = true;
		try {
			console = new BufferedReader(new InputStreamReader(System.in));
			dos = new DataOutputStream(client.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		while(isRunning) {
			try {
				System.out.println("请输入账号:");
				String id = console.readLine();
				System.out.println("请输入密码:");
				String pwd = console.readLine();
				String msg = id+"&"+pwd;
				dos.writeUTF(msg);
				dos.flush();
				//			close(client);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
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
