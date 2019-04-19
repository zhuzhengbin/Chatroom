package com.scu.login;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * ��������
 * Ŀ�꣺
 * ���û���������Ϣת���������û�
 * @author zhuzhengbin
 *
 */
public class Server {
	private static List<Channel> connList = new ArrayList<>();
	public static void main(String[] args) throws Exception {
		System.out.println("---------------------------------������---------------------------------");
		ServerSocket server = new ServerSocket(9999);
		boolean isRunning = true;
		while(isRunning) {
			Socket client = server.accept();
			// ����һ���û�����
			Channel channel = new Channel(client);
			// �����ӱ��浽���ӳ�
			connList.add(channel);
			// Ϊ�����Ӵ����߳�
			new Thread(channel).start();
		}
	}

	// ��������
	static class Channel implements Runnable{
		private Socket client;
		private DataInputStream dis;
		private DataOutputStream dos;
		private boolean isRunning;

		Channel(){
		}
		public Channel(Socket client) {
			this.client = client;
			this.isRunning = true;
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

		private void sendToOthers(String msg) {	// ��һ���ͻ�����������Ϣת�������ӵ��������������û�
			// �����������������е������û�������Ϣ
			for(Channel ch:connList) {
				ch.send(msg);
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

		public boolean isLegal(String msg) {	// �жϵ�½�û��Ƿ�Ϸ�
			String[] info = msg.split("&");
			String uname = info[0];
			String upwd = info[1];
			// �����ݿ��в�ѯunameΪuname�ļ�¼���Ƚ�upwd�Ƿ����
			Connection conn = JDBCUtil.getMysqlConn();
			String sql = "select upwd from userinfo where uname=?";
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1,uname);
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					if(rs.getString("upwd").equals(upwd)) {
						return true;
					}else {
						return false;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			return false;
		}


		@Override
		public void run() {
			// ��һ�ν�����Ϣ�������ж��û��ĺϷ���
			String msg = receive();	// ��Ϣ�ĸ�ʽ��uname&upwd
			boolean flag = isLegal(msg);
			if(flag) {
				send("��ӭ����");
			}else {
				send("�Բ��������˺Ż����벻��ȷ��");
			}
			while(isRunning) {	// ѭ���������Կͻ��˵���Ϣ��������ת��
				msg = receive();	// ��ȡ���Կͻ��˵���Ϣ
//				send(msg);  // ���ͻ�ԭ�ͻ���
				sendToOthers(msg); // �������ͻ��˷�����Ϣ
			}
		}

	}
}
