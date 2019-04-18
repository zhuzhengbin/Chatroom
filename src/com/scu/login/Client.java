package com.scu.login;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 项目简介：
 * 使用数据库、多线程、socket相关知识实现用户的远程登陆，可以在服务端判断登陆是否成功
 * 
 * 目标：
 * 1、实现从客户端输入账号密码，在服务器端判断是否正确并返回结果
 * @author zhuzhengbin
 *
 */
public class Client {
	public static void main(String[] args) throws Exception{
		Socket client = new Socket("localhost",9999);
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("请输入账号");
		String id = console.readLine();
		System.out.println("请输入密码");
		String pwd = console.readLine();
		String msg = id+"&"+pwd;
		System.out.println(msg);
		
		
		client.close();
	}
}
