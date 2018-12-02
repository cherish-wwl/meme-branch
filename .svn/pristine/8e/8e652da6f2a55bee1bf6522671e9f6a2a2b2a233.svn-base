package com.meme.core.mybatis;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;

import com.meme.core.mybatis.annotation.Table;
import com.meme.core.spring.SpringContextManager;

/**
 * Mapper工具类
 * @author hzl
 *
 */
public class MapperHelper {
	
	public static DefaultSqlSessionFactory getSessionFactory(){
		return (DefaultSqlSessionFactory) SpringContextManager.getBean("sqlSessionFactory");
	}
	
	/**
	 * 获取表字段元数据，是否自增、是否NULL值、默认值
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public static Map<String,EntityColumn> getMetaData(String tableName){
		Map<String,EntityColumn> map=new HashMap<String, EntityColumn>();
		Connection connection=null;
		SqlSession sqlSession=null;
		try{
			sqlSession=getSessionFactory().openSession();
			connection= sqlSession.getConfiguration().getEnvironment().getDataSource().getConnection();
			
			PreparedStatement pst = null;  
			pst = connection.prepareStatement("select * from "+tableName+" limit 0,1");  
			ResultSetMetaData rsd = pst.executeQuery().getMetaData();  
			for(int i = 0; i < rsd.getColumnCount(); i++) {
				EntityColumn field=new EntityColumn();
				field.setColumn(rsd.getColumnName(i+1).toLowerCase());
				field.setColumnLength(rsd.getColumnDisplaySize(i+1));
				field.setPrecision(rsd.getPrecision(i+1));
				field.setScale(rsd.getScale(i+1));
				map.put(field.getColumn(), field);
			}
			
			DatabaseMetaData dbMetaData = connection.getMetaData();
			ResultSet rs= dbMetaData.getColumns(null, null, tableName, "%");
			while(rs.next()){
				String columnName=rs.getString("COLUMN_NAME").toLowerCase();
				String isAutoIncrement = rs.getString("IS_AUTOINCREMENT");
				Object defaultValue = rs.getObject("COLUMN_DEF");
				int nullable = rs.getInt("NULLABLE");
				if(isAutoIncrement.equalsIgnoreCase("YES")) map.get(columnName).setIsAutoIncrement(true);
				else if(isAutoIncrement.equalsIgnoreCase("NO")) map.get(columnName).setIsAutoIncrement(false);
				if(nullable==0) map.get(columnName).setNullable(false);
				else map.get(columnName).setNullable(true);
				if(isAutoIncrement.equalsIgnoreCase("NO")) map.get(columnName).setDefaultValue(defaultValue);
//				map.put(map.get(columnName).getColumn(), map.get(columnName));
				
				ResultSet primaryKeyResultSet=dbMetaData.getPrimaryKeys(null, null, tableName);
				while(primaryKeyResultSet.next()){
					String key=primaryKeyResultSet.getString("COLUMN_NAME");
					if(key.equalsIgnoreCase(columnName)) {
						map.get(columnName).setIsPrimaryKey(true);
						break;
					}
				}
				
//				System.out.println("column="+columnName+",IS_AUTOINCREMENT="+isAutoIncrement+",NULLABLE="+nullable+",COLUMN_DEF="+defaultValue);
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
		return map;
	}
	
	/**
	 * 获取实体类对应的表名
	 * @param entityClass
	 * @return
	 * @throws Exception
	 */
	public static String getTableName(Class<?> entityClass){
		try {
			if (entityClass.isAnnotationPresent(Table.class)) {
	            Table table = entityClass.getAnnotation(Table.class);
	            if (!table.name().equals("")) {
	                return table.name();
	            }
	        }else throw new Exception("无法获取实体类表名注解，请检查是否添加了实体类的表名注解");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取实体类对应的表名的主键
	 * @param entityClass
	 * @return
	 * @throws Exception
	 */
	public static String getTableId(Class<?> entityClass){
		try {
			if (entityClass.isAnnotationPresent(Table.class)) {
	            Table table = entityClass.getAnnotation(Table.class);
	            if (!table.id().equals("")) {
	                return table.id().toLowerCase();
	            }
	        }else throw new Exception("无法获取实体类表名注解，请检查是否添加了实体类的表名注解");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取xml文件定义的resultmap节点数组
	 * @param entityClass
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<ResultMap> getResultMap(Class<?> entityClass){
		List<ResultMap> list=new ArrayList<ResultMap>();
		try{
			Collection<ResultMap> col=getSessionFactory().getConfiguration().getResultMaps();
			if(null==col||col.size()==0) throw new Exception("无法解析mapper xml中的resultmap节点，请确认已生成xml文件并且定义类的result map节点");
			Iterator it=col.iterator();
			while(it.hasNext()){
				Object obj=it.next();
				if(obj instanceof ResultMap){
					ResultMap map=(ResultMap) obj;
					if(map.getType().getName().equals(entityClass.getName())){
						list.add(map);
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 获取表字段名逗号分隔的字符串
	 * @param entityClass
	 * @param containKey 是否包含主键
	 * @return
	 * @throws Exception
	 */
	public static String getTableColumnsString(Class<?> entityClass,Boolean containKey) throws Exception {
		List<EntityColumn> list=getTableColumnsWithBlobs(entityClass);
		StringBuffer cols=new StringBuffer();
		if(null!=list&&list.size()>0){
			for(int i=0;i<list.size();i++){
				if(null!=containKey&&!containKey&&list.get(i).getColumn().equals(getTableId(entityClass))) continue;
				cols.append(list.get(i).getColumn()).append(",");
			}
		}else throw new Exception("请检查mapper xml文件是否已配置resultmap节点");
		return cols.substring(0,cols.length()-1);
    }
	
	public static List<EntityColumn> getTableColumns(Class<?> entityClass) {
		return null;
	}
	
	/**
	 * 获取表字段和实体类属性映射关系，对应mybatis-generator工具生成的ResultMapWithBLOBs
	 * @param entityClass
	 * @return
	 * @throws Exception 
	 * @throws SQLException 
	 */
	public static List<EntityColumn> getTableColumnsWithBlobs(Class<?> entityClass) {
		List<ResultMap> list=getResultMap(entityClass);
//		Map<String,ResultMapping> kv=new HashMap<String,ResultMapping>();
		
		Map<String,EntityColumn> map=getMetaData(getTableName(entityClass));
		
		if(null!=list&&list.size()>0){
			for(int i=0;i<list.size();i++){
				ResultMap resultMap=list.get(i);
				List<ResultMapping> resultMappings=resultMap.getResultMappings();
				if(null!=resultMappings&&resultMappings.size()>0){
					for(int j=0;j<resultMappings.size();j++){
						//kv.put(resultMappings.get(j).getColumn().toLowerCase(), resultMappings.get(j));
						
						map.get(resultMappings.get(j).getColumn().toLowerCase()).setProperty(resultMappings.get(j).getProperty());
						map.get(resultMappings.get(j).getColumn().toLowerCase()).setJavaType(resultMappings.get(j).getJavaType());
						map.get(resultMappings.get(j).getColumn().toLowerCase()).setJdbcType(resultMappings.get(j).getJdbcType());
						
					}
				}
			}
		}
//		List<EntityColumn> columns=new ArrayList<EntityColumn>();
//		if(kv.size()>0){
//			for(Map.Entry<String, ResultMapping> entry:kv.entrySet()){
//				EntityColumn c=new EntityColumn();
//				c.setColumn(entry.getKey().toLowerCase());
//				c.setProperty(entry.getValue().getProperty());
//				c.setJavaType(entry.getValue().getJavaType());
//				c.setJdbcType(entry.getValue().getJdbcType());
//				c.setIsAutoIncrement(map.get(entry.getKey()).getIsAutoIncrement());
//				c.setNullable(map.get(entry.getKey()).getNullable());
//				c.setDefaultValue(map.get(entry.getKey()).getDefaultValue());
//				columns.add(c);
//			}
//		}
		EntityColumn[] columns=new EntityColumn[map.size()];
        return Arrays.asList(map.values().toArray(columns));
    }
	
	/**
	 * key-column,value-EntityColumn
	 * @param entityClass
	 * @return
	 * @throws Exception 
	 * @throws SQLException 
	 */
	public static Map<String,EntityColumn> getTableColumnsMap(Class<?> entityClass){
		List<EntityColumn> list=getTableColumnsWithBlobs(entityClass);
		Map<String,EntityColumn> kv=new LinkedHashMap<String,EntityColumn>();
		if(null!=list&&list.size()>0){
			for(EntityColumn rm:list){
				kv.put(rm.getColumn().toLowerCase(), rm);
			}
		}
		return kv;
	}
	
	/**
	 * map转bean类
	 * @param map
	 * @param beanClass
	 * @return
	 */
	public static Object map2Bean(Map<String, Object> map, Class<?> beanClass) {
        try {
            if (map == null) {
                return null;
            }
            List<EntityColumn> list=getTableColumnsWithBlobs(beanClass);
            Map<String,Object> kv=new HashMap<String, Object>();
            if(null!=list&&list.size()>0){
            	for(int i=0;i<list.size();i++){
            		if(list.get(i).getProperty() != null) {
            			kv.put(list.get(i).getProperty(), map.get(list.get(i).getColumn()));
            		}
            	}
            }
            Object bean = beanClass.newInstance();
            BeanUtils.copyProperties(bean, kv);
            return bean;
        } catch (InstantiationException e) {
            throw new RuntimeException(beanClass.getCanonicalName() + "类没有默认空的构造方法!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	/**
	 * map数组转bean数组
	 * @param mapList
	 * @param beanClass
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toBeanList(List mapList, Class<?> beanClass) {
        if (mapList == null || mapList.size() == 0) {
            return null;
        }
        List list = new ArrayList<Object>(mapList.size());
        for (Object map : mapList) {
            list.add(map2Bean((Map) map, beanClass));
        }
        return list;
    }
	
}
