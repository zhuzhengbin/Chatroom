package com.scu.login;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * �������ˣ�����������Կͻ��˵���Ϣ�����м򵥴����жϵ�½���˺������Ƿ���ȷ���������ͻ���
 * @author zhuzhengbin
 *
 */
public class Server {
	public static void main(String[] args) throws Exception {
		ServerSocket server = new ServerSocket(9999);
		boolean isRunning = true;
		while(isRunning) {
			Socket client = server.accept();
			// �½�һ���̣߳����Դ�����������ϵĽ����뷢��
			new Thread(new Channel(client)).start();
		}
	}
	
	// ��������
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
		
		
		private String receive() {	// ��TCP�����н�����Ϣ
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
