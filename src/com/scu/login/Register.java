package com.scu.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 用户注册
 * @author zhuzhengbin
 *
 */
public class Register {
	public static void main(String[] args) {
		System.out.println("---------------------------------------注册界面---------------------------------------");
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			try {
				System.out.println("请输入账号：");
				String uname = console.readLine();
				System.out.println("请输入密码：");
				String upwd = console.readLine();
				System.out.println("请输入生日（格式如\"1998-12-12\"：");
				String prBirthday = console.readLine();	// 未处理的日期
				Date birthday = str2Date(prBirthday);	// 转化为日期格式
				System.out.println("请输入手机号：");
				String telphone = console.readLine();
				new User(uname,upwd,birthday,telphone);	// 创建对象的同时，写入数据库
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private static Date str2Date(String prBirthday) {	// 输入字符串，转化为日期格式
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return new Date(format.parse(prBirthday).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		
	}
}

class User{
	private String uname;
	private Date birthday;
	private String upwd;
	private String telphone;

	User(String uname,String upwd,Date birthday,String telphone){
		this.uname = uname;
		this.upwd = upwd;
		this.birthday = birthday;
		this.telphone = telphone;
		store(); // 将信息保存到数据库
	}

	private void store() {
		Connection conn = JDBCUtil.getMysqlConn();
		String sql = "insert into userinfo (uname,upwd,birthday,telphone) values (?,?,?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, uname);
			ps.setString(2, upwd);
			ps.setDate(3, birthday);
			ps.setString(4, telphone);
			ps.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("提交失败");
		}

	}
}










