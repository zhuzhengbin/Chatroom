package com.scu.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
				new User(uname,upwd);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

class User{
	private String uname;
	private String upwd;

	User(String uname,String upwd){
		this.uname = uname;
		this.upwd = upwd;
		store(); // ����Ϣ���浽���ݿ�
	}

	private void store() {
		Connection conn = JDBCUtil.getMysqlConn();
		String sql = "insert into userinfo (uname,upwd) values (?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, uname);
			ps.setString(2, upwd);
			ps.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("�ύʧ��");
		}

	}
}










