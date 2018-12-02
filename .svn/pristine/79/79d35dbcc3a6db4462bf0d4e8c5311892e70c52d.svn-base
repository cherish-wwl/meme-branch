package com.meme.core.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;

import com.meme.core.spring.SpringContextManager;

public class JdbcHelper {

	public static DefaultSqlSessionFactory getSessionFactory(){
		return (DefaultSqlSessionFactory) SpringContextManager.getBean("sqlSessionFactory");
	}
	
	/**
	 * 获取表字段结构信息
	 * @param tableName
	 * @return
	 */
	public static List<FieldStruct> getMetaData(String tableName){
		Connection connection=null;
		SqlSession sqlSession=null;
		Map<String,FieldStruct> map=new LinkedHashMap<String, FieldStruct>();
		try{
			sqlSession=getSessionFactory().openSession();
			connection= sqlSession.getConfiguration().getEnvironment().getDataSource().getConnection();
			
			PreparedStatement pst = null;  
			pst = connection.prepareStatement("select * from "+tableName+" limit 0,1");  
			ResultSetMetaData rsd = pst.executeQuery().getMetaData();  
			for(int i = 0; i < rsd.getColumnCount(); i++) {
				FieldStruct field=new FieldStruct();
				field.setColumnClassName(rsd.getColumnClassName(i+1));
				field.setColumnLength(rsd.getColumnDisplaySize(i+1));
				field.setColumnName(rsd.getColumnName(i+1));
				field.setColumnTypeName(rsd.getColumnTypeName(i+1));
				field.setPrecision(rsd.getPrecision(i+1));
				field.setScale(rsd.getScale(i+1));
				map.put(field.getColumnName(), field);
				
//				System.out.print("java类型："+rsd.getColumnClassName(i + 1));  
//				System.out.print("  数据库类型:"+rsd.getColumnTypeName(i + 1));  
//				System.out.print("  字段名称:"+rsd.getColumnName(i + 1));  
//				System.out.print("  字段长度:"+rsd.getColumnDisplaySize(i + 1));
//				System.out.print("  Precision:"+rsd.getPrecision(i + 1));
//				System.out.print("  Scale:"+rsd.getScale(i + 1));
//				System.out.print("  CatalogName:"+rsd.getCatalogName(i+1));
//				System.out.print("  ColumnType:"+rsd.getColumnType(i+1));
//				System.out.print("  ColumnTypeName:"+rsd.getColumnTypeName(i+1));
//				System.out.print("  SchemaName:"+rsd.getSchemaName(i+1));
//				System.out.print("  ColumnLabel:"+rsd.getColumnLabel(i+1));
//				System.out.println();  
			}  
		    
			DatabaseMetaData dbMetaData = connection.getMetaData();
			ResultSet rs= dbMetaData.getColumns(null, null, tableName, "%");
			
			while(rs.next()){
				String COLUMN_NAME=rs.getString("COLUMN_NAME");
				int NULLABLE = rs.getInt("NULLABLE");
				map.get(COLUMN_NAME).setNullable(NULLABLE);
				ResultSet primaryKeyResultSet=dbMetaData.getPrimaryKeys(null, null, tableName);
				while(primaryKeyResultSet.next()){
					String key=primaryKeyResultSet.getString("COLUMN_NAME");
					if(key.equalsIgnoreCase(COLUMN_NAME)) {
						map.get(COLUMN_NAME).setPrimaryKey(true);
						break;
					}
				}
				
//				java.sql.Types t=null;
//				String COLUMN_SIZE=rs.getString("COLUMN_SIZE");
//				String DATA_TYPE=rs.getString("DATA_TYPE");//字段数据类型(对应java.sql.Types中的常量)
//				String TYPE_NAME = rs.getString("TYPE_NAME");
//				String REMARKS=rs.getString("REMARKS");
//				String DECIMAL_DIGITS=rs.getString("DECIMAL_DIGITS");
//				System.out.println(
//				"COLUMN_NAME="+COLUMN_NAME
//				+",COLUMN_SIZE="+COLUMN_SIZE
//				//+",DATA_TYPE="+DATA_TYPE
//				+",TYPE_NAME="+TYPE_NAME
//				+",NULLABLE="+NULLABLE
//				//+",REMARKS="+REMARKS
//				+",DECIMAL_DIGITS="+DECIMAL_DIGITS);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(null!=connection&&!connection.isClosed()) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(null!=sqlSession) sqlSession.close();
		}
		FieldStruct[] array=new FieldStruct[map.size()];
		return Arrays.asList(map.values().toArray(array));
	}
}
