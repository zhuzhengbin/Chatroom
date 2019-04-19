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
 * 服务器端
 * 目标：
 * 将用户发来的消息转发给其它用户
 * @author zhuzhengbin
 *
 */
public class Server {
	private static List<Channel> connList = new ArrayList<>();
	public static void main(String[] args) throws Exception {
		System.out.println("---------------------------------服务器---------------------------------");
		ServerSocket server = new ServerSocket(9999);
		boolean isRunning = true;
		while(isRunning) {
			Socket client = server.accept();
			// 创建一个用户连接
			Channel channel = new Channel(client);
			// 将连接保存到连接池
			connList.add(channel);
			// 为新连接创建线程
			new Thread(channel).start();
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

		private void sendToOthers(String msg) {	// 把一个客户发过来的消息转发给连接到服务器的其它用户
			// 遍历容器，向容器中的所有用户发送消息
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

		public boolean isLegal(String msg) {	// 判断登陆用户是否合法
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
			// 第一次接收消息，用于判断用户的合法性
			String msg = receive();	// 消息的格式是uname&upwd
			boolean flag = isLegal(msg);
			if(flag) {
				send("欢迎您！");
			}else {
				send("对不起，您的账号或密码不正确！");
			}
			while(isRunning) {	// 循环接收来自客户端的消息，并将其转发
				msg = receive();	// 读取来自客户端的消息
//				send(msg);  // 发送回原客户端
				sendToOthers(msg); // 向其它客户端发送消息
			}
		}

	}
}
