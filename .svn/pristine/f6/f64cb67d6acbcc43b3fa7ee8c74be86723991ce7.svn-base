package com.meme.core.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.meme.core.util.PropertiesUtil;

/**
 * JDBC数据库连接工厂类，单例线程安全
 * @author hzl
 *
 */
public class ConnectionFactory {

	private static ConnectionFactory instance = null;
	private static String url=null;
	private static String user=null;
	private static String password=null;
	private static String driver="com.mysql.jdbc.Driver";
	
	static{
		url=PropertiesUtil.getProperty("url","druid.properties").toString();
		user=PropertiesUtil.getProperty("user","druid.properties").toString();
		password=PropertiesUtil.getProperty("password","druid.properties").toString();
	}
	
	private ConnectionFactory() {
	}
	
	private static synchronized void init() {
		if (instance == null) {
			instance=new ConnectionFactory();
		}
	}
	
	public static ConnectionFactory getInstance() {
		if (instance == null) {
			init();
		}
		return instance;
	}
	
	public Connection getConnection(){
		initDriver();
		Connection connection=null;
		try {
			connection=DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	private void initDriver(){
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
