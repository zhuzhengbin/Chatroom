package com.scu.login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 使用数据库的工具类
 * @author zhuzhengbin
 *
 */
public class JDBCUtil {
	private static Properties pro = null;
	
	static {
		pro = new Properties();	// 静态加载块只能使用静态变量
		try {
			pro.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 加载类驱动并创建数据库连接
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
	
	// 关闭连接，释放资源
	public static void close() {
		
	}
}
