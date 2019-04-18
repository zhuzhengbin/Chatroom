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

/**
 * 服务器端：负责接收来自客户端的消息，进行简单处理（判断登陆的账号密码是否正确）后反馈给客户端
 * @author zhuzhengbin
 *
 */
public class Server {
	public static void main(String[] args) throws Exception {
		System.out.println("---------------------------------服务器---------------------------------");
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
		
		public boolean isLegal(String msg) {
			String[] info = msg.split("&");
			String uname = info[0];
			String upwd = info[1];
			// 从数据库中查询uname为uname的记录，比较upwd是否相等
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
			while(isRunning) {
				String msg = receive();	// 消息的格式是uname&upwd
//				String[] info = msg.split("&");
//				String uname = info[0];
//				String upwd = info[1];
				boolean flag = isLegal(msg);
				if(flag) {
					send("欢迎您！");
				}else {
					send("对不起，您的账号或密码不正确！");
				}
				
			}
		}
		
	}
}
