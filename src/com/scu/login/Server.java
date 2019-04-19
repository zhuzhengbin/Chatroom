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
		private String name;	// 连接者的名字

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
			// 对输入信息进行判断，看是否为私聊(私聊消息的格式为@xxx:msg)
			if(msg.startsWith("@")) {	// 如果是私聊，则只向目标客户端发送消息
				int index = msg.indexOf(":");	// 找到“:”的索引位置
				String uname = msg.substring(1,index);	// 获取@对象的名称
				// 遍历容器，查找目标对象
				for(Channel ch:connList) { 
					if(uname.equals(ch.getName())) {
						ch.send(this.name+"悄悄对您说："+msg);	// 向该目标发送消息并退出循环
						break;
					}
					
				}
			}else {
				for(Channel ch:connList) { // 如果不是私聊，则遍历容器，向容器中的所有非己方用户发送消息
					if(ch == this) {	// 如果是自己，就跳过，进入下一次循环
						continue;
					}
					// 如果不是自己，就向其发送消息
					ch.send(this.name+"对大家说："+msg);
				}
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
						this.name = uname;
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
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		

	}
}
