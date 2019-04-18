package com.scu.login;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务器端：负责接收来自客户端的消息，进行简单处理（判断登陆的账号密码是否正确）后反馈给客户端
 * @author zhuzhengbin
 *
 */
public class Server {
	public static void main(String[] args) throws Exception {
		ServerSocket server = new ServerSocket(9999);
		boolean isRunning = true;
		while(isRunning) {
			Socket client = server.accept();
			// 新建一个线程，用以处理这个连接上的接收与发送
			new Thread(new Channel(client)).start();
		}
	}
	
	// 接收连接
	static class Channel implements Runnable{
		private Socket client;
		private DataInputStream dis;
		private DataOutputStream dos;
//		private boolean isRunning;
		
		Channel(){
		}
		public Channel(Socket client) {
			this.client = client;
//			this.isRunning = true;
			try {
				dis = new DataInputStream(client.getInputStream());
				dos = new DataOutputStream(client.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		private String receive() {	// 从TCP连接中接收消息
			String msg = "";
			try {
				msg = dis.readUTF();
				return msg;
			} catch (IOException e) {
				e.printStackTrace();
				return msg;
			}
		}
		
		private void send(String msg) {
			try {
				dos.writeUTF(msg);
				dos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		@Override
		public void run() {
//			while(isRunning) {
				String msg = receive();
				send(msg);
//			}
		}
		
	}
}
