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
 * �û�ע��
 * @author zhuzhengbin
 *
 */
public class Register {
	public static void main(String[] args) {
		System.out.println("---------------------------------------ע�����---------------------------------------");
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			try {
				System.out.println("�������˺ţ�");
				String uname = console.readLine();
				System.out.println("���������룺");
				String upwd = console.readLine();
				System.out.println("���������գ���ʽ��\"1998-12-12\"��");
				String prBirthday = console.readLine();	// δ���������
				Date birthday = str2Date(prBirthday);	// ת��Ϊ���ڸ�ʽ
				System.out.println("�������ֻ��ţ�");
				String telphone = console.readLine();
				new User(uname,upwd,birthday,telphone);	// ���������ͬʱ��д�����ݿ�
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private static Date str2Date(String prBirthday) {	// �����ַ�����ת��Ϊ���ڸ�ʽ
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
		store(); // ����Ϣ���浽���ݿ�
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
			System.out.println("�ύʧ��");
		}

	}
}










