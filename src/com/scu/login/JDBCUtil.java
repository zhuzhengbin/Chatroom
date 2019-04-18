package com.scu.login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * ʹ�����ݿ�Ĺ�����
 * @author zhuzhengbin
 *
 */
public class JDBCUtil {
	private static Properties pro = null;
	
	static {
		pro = new Properties();	// ��̬���ؿ�ֻ��ʹ�þ�̬����
		try {
			pro.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// �������������������ݿ�����
	public static Connection getMysqlConn() {
		
		try {
			Class.forName(pro.getProperty("mysqlDriver"));
			return DriverManager.getConnection(pro.getProperty("mysqlURL"),pro.getProperty("mysqlUser"),pro.getProperty("mysqlPwd"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// �ر����ӣ��ͷ���Դ
	public static void close() {
		
	}
}
