package com.meme.core.mybatis.provider;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meme.core.mybatis.Criterion;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.mybatis.EntityColumn;
import com.meme.core.util.StringUtil;

/**
 * 通用provider，生成mapper中带provider注解方法的sql语句
 * @author hzl
 *
 */
public class CommonProvider extends BaseProvider {

	private static Logger LOG=LoggerFactory.getLogger(CommonProvider.class);
	
	/**
	 * 根据实体查询
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String selectByEntity(final Map<String,Object> params) throws Exception{
		SQL sql= new SQL() {{
            Object entity = getEntity(params);
            Class<?> entityClass = getEntityClass(params);
            SELECT(MapperHelper.getTableColumnsString(entityClass,null));
            FROM(MapperHelper.getTableName(entityClass));
            if (entity != null) {
                final MetaObject metaObject = SystemMetaObject.forObject(entity);
                List<EntityColumn> cols=MapperHelper.getTableColumnsWithBlobs(entityClass);
                for (EntityColumn column : cols) {
                    Object value = null;
                    if(column.getProperty()!=null) {
                    		value = metaObject.getValue(column.getProperty());
                    }
                    if (value == null || value.toString().equals("")) {
                        continue;
                    } else if (column.getJavaType().equals(String.class)||column.getJavaType().isAssignableFrom(Date.class)) {
                        if (!StringUtil.isEmpty(value.toString())) {
                            WHERE(column.getColumn() + "='"+value+"'");
                        }
                    } else {
                        WHERE(column.getColumn() + "="+value);
                    }
                }
            }
        }};
        return sql.toString();
	}
	
	/**
	 * 查询所有数据
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String selectAll(final Map<String,Object> params) throws Exception{
		SQL sql= new SQL() {{
            Class<?> entityClass = getEntityClass(params);
            SELECT(MapperHelper.getTableColumnsString(entityClass,null));
            FROM(MapperHelper.getTableName(entityClass));
        }};
        return sql.toString();
	}
	
	/**
	 * 批量插入
	 * @param params
	 * @return
	 * @throws Exception
	 */
//	@SelectKeyInject
	public String batchInsert(final Map<String,Object> params){
		//为避免报Parameter 'id' not found异常，此处添加空值的id参数，也可在基类BaseMapper添加@SelectKey注解避免报此异常
//		params.put("id", null);
		
		Class<?> entityClass = getEntityClass(params);
		Map<String,EntityColumn> cols= MapperHelper.getTableColumnsMap(entityClass);
        List<?> list=getListInParams(params);
    	StringBuffer sql=new StringBuffer();
    	LinkedHashMap<String,String> columns=new LinkedHashMap<String,String>();
    	List<List<Object>> values=new ArrayList<List<Object>>();
    	
        if(null!=list&&list.size()>0){
        	for(Object obj:list){
        		List<Object> valList=new ArrayList<Object>();
        		for(Map.Entry<String, EntityColumn> entry:cols.entrySet()){
        			MetaObject metaObject = SystemMetaObject.forObject(obj);
        			Object value = metaObject.getValue(entry.getValue().getProperty());
        			if(entry.getValue().getIsAutoIncrement()&&null==value) continue;

    				Class<?> javaType=entry.getValue().getJavaType();
        			if(null==value){
        				//表字段定义默认值，批量插入时没有默认值，自动取表定义默认值
        				if(null!=entry.getValue().getDefaultValue()){
        					columns.put(entry.getKey(), entry.getKey());
        					if(entry.getValue().getDefaultValue().toString().equalsIgnoreCase("CURRENT_TIMESTAMP")){
        						//时间类型取当前时间时不加引号
        						valList.add(entry.getValue().getDefaultValue());
        					}else if (javaType.equals(String.class)||javaType.isAssignableFrom(Date.class)) {
        						valList.add("'"+entry.getValue().getDefaultValue()+"'");
        					}else{
        						valList.add(entry.getValue().getDefaultValue());
        					}
        				}
        			}else{
        				columns.put(entry.getKey(), entry.getKey());
        				if (javaType.equals(String.class)||javaType.isAssignableFrom(Date.class)) {
        					valList.add("'"+String.valueOf(value)+"'");
        				}else{
        					valList.add(value);
        				}
        			}
        		}
        		if(valList.size()>0) values.add(valList);
        	}
        	sql.append("INSERT INTO ");
        	sql.append(MapperHelper.getTableName(entityClass));
        	sql.append("(");
        	int i=0;
        	for(Map.Entry<String, String> entry:columns.entrySet()){
        		if(i==0) sql.append(entry.getValue());
        		else sql.append(",").append(entry.getValue());
        		i++;
        	}
        	sql.append(")");
        	sql.append(" VALUES ");
        	for(List<Object> objList:values){
        		sql.append("(");
            	StringBuffer sb=new StringBuffer();
            	i=0;
        		for(Object obj:objList){
        			if(i==0) sb.append(obj);
        			else sb.append(",").append(obj);
        			i++;
        		}
        		sql.append(sb.toString());
        		sql.append("),");
        	}
        }
        LOG.info(sql.toString());
        return sql.substring(0,sql.length()-1);
	}
	
	/**
	 * 根据主键批量删除
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String batchDelete(final Map<String,Object> params) throws Exception{
		SQL sql= new SQL() {{
            Class<?> entityClass = getEntityClass(params);
            DELETE_FROM(MapperHelper.getTableName(entityClass));
            List<?> list=getListInParams(params);
            Map<String,EntityColumn> map=MapperHelper.getTableColumnsMap(entityClass);
            String key=MapperHelper.getTableId(entityClass);
            Class<?> javaType=map.get(key.toLowerCase()).getJavaType();
            StringBuffer ids=new StringBuffer();
            for(Object obj:list){
            	if (javaType.equals(String.class)||javaType.isAssignableFrom(Date.class)) ids.append("'"+obj+"',");
            	else ids.append(obj+",");
            }
            WHERE(MapperHelper.getTableId(entityClass)+" in("+ids.substring(0,ids.length()-1)+")");
        }};
        return sql.toString();
	}
	
	/**
	 * 根据主键批量更新
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String batchUpdate(final Map<String,Object> params) throws Exception{
		Class<?> entityClass = getEntityClass(params);
		Map<String,EntityColumn> cols= MapperHelper.getTableColumnsMap(entityClass);
        List<?> list=getListInParams(params);
        String tableName=MapperHelper.getTableName(entityClass);
        String id=MapperHelper.getTableId(entityClass);
    	StringBuffer sql=new StringBuffer();
    	sql.append(" UPDATE ").append(tableName).append(" SET ");
    	Map<String,Object> ids=new HashMap<String,Object>();
    	for(Map.Entry<String, EntityColumn> entry:cols.entrySet()){
    		sql.append(entry.getValue().getColumn()).append("=");
    		sql.append(" CASE ").append(id);
    		Class<?> javaType=entry.getValue().getJavaType();
        	for(Object obj:list){
        		MetaObject metaObject = SystemMetaObject.forObject(obj);
    			Object value = metaObject.getValue(entry.getValue().getProperty());
    			Object idValue=metaObject.getValue(id);
    			
    			if (cols.get(id).getJavaType().equals(String.class)||cols.get(id).getJavaType().isAssignableFrom(Date.class))
    				sql.append(" WHEN '").append(idValue.toString()).append("' ");
    			else sql.append(" WHEN ").append(idValue).append(" ");
    			if(javaType.equals(String.class)||javaType.isAssignableFrom(Date.class)){
    				if(null!=value) {
    					sql.append(" THEN '").append(value.toString()).append("' ");
    				}else {
    					if(!entry.getValue().getNullable()&&entry.getValue().getDefaultValue()!=null){
    						sql.append(" THEN '"+entry.getValue().getDefaultValue()+"'");
    					}else sql.append(" THEN NULL ");
    				}
    			}else {
    				if(null!=value) {
    					sql.append(" THEN ").append(value).append(" ");
    				}else{
    					if(!entry.getValue().getNullable()&&entry.getValue().getDefaultValue()!=null){
        					sql.append(" THEN "+entry.getValue().getDefaultValue());
        				}
        				else sql.append(" THEN NULL ");
    				}
    			}
    			
    			if(cols.get(id).getJavaType().equals(String.class)||cols.get(id).getJavaType().isAssignableFrom(Date.class))
    				ids.put(String.valueOf(idValue),"'"+idValue.toString()+"'");
    			else ids.put(String.valueOf(idValue),idValue);
        	}
        	sql.append(" END,");
    	}
    	int i=0;
    	String s=sql.substring(0, sql.length()-1);
    	sql=new StringBuffer();
    	sql.append(s);
    	sql.append(" WHERE ").append(id).append(" IN (");
    	for(Map.Entry<String, Object> entry:ids.entrySet()){
    		if(i==0) sql.append(entry.getValue());
    		else sql.append(","+entry.getValue());
    		i++;
    	}
    	sql.append(")");
    	
    	return sql.toString();
	}
	
	/**
	 * 查询分页
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String selectByPagination(final Map<String,Object> params) throws Exception{
		Class<?> entityClass = getEntityClass(params);
		StringBuffer sql=new StringBuffer();
		Object obj=params.get("list");
		sql.append(" SELECT ");
		sql.append(MapperHelper.getTableColumnsString(entityClass,null));
		sql.append(" FROM ");
		sql.append(MapperHelper.getTableName(entityClass));
		sql.append(" WHERE 1=1 ");
		if(null!=obj){
			List<Criterion> list=(List<Criterion>) obj;
			if(list.size()>0){
				for(Criterion c:list){
					if(!StringUtil.isEmpty(c.toString())) sql.append(c.toString());
				}
			}
		}

//		Object formObj=params.get("form");
//		Class<?> formClass=formObj.getClass();
//		String sort=null;
//		String order=null;
//		Integer offset=BaseForm.DEFAULT_OFFSET;
//		Integer limit=BaseForm.DEFAULT_LIMIT;
//		for(;formClass!=Object.class;formClass=formClass.getSuperclass()){
//			Field[] fields= formClass.getDeclaredFields();
//			for(int i=0;i<fields.length;i++){
//				fields[i].setAccessible(true);
//				if(fields[i].getName().equals("sort")){
//					sort=fields[i].get(formObj)==null?null:fields[i].get(formObj).toString();
//				}
//				if(fields[i].getName().equals("order")){
//					order=fields[i].get(formObj)==null?null:fields[i].get(formObj).toString();
//				}
//				if(fields[i].getName().equals("offset")){
//					offset=fields[i].get(formObj)==null?BaseForm.DEFAULT_OFFSET:Integer.valueOf(fields[i].get(formObj).toString());
//				}
//				if(fields[i].getName().equals("limit")){
//					limit=fields[i].get(formObj)==null?BaseForm.DEFAULT_LIMIT:Integer.valueOf(fields[i].get(formObj).toString());
//				}
//			}
//		}
//		if(!StringUtil.isEmpty(sort)){
//			sql.append(" ORDER BY ").append(sort).append(" ");
//			if(!StringUtil.isEmpty(order)) sql.append(" ").append(order).append(" ");
//		}
//		sql.append(" LIMIT ").append(offset).append(",").append(limit);
		return sql.toString();
	}
	
	/**
	 * 分页查询统计数量
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String count(final Map<String,Object> params) throws Exception{
		Class<?> entityClass = getEntityClass(params);
		StringBuffer sql=new StringBuffer();
		Object obj=params.get("list");
		sql.append(" SELECT count(*) ");
		sql.append(" FROM ");
		sql.append(MapperHelper.getTableName(entityClass));
		sql.append(" WHERE 1=1 ");
		if(null!=obj){
			List<Criterion> list=(List<Criterion>) obj;
			if(list.size()>0){
				for(Criterion c:list){
					if(!StringUtil.isEmpty(c.toString())) sql.append(c.toString());
				}
			}
		}
		return sql.toString();
	}
	
	/**
     * 根据criterion查询
     * @param list
     * @param entityClass
     * @return
	 * @throws Exception 
     */
    @SuppressWarnings("unchecked")
	public String selectByCriterions(final Map<String,Object> params) throws Exception{
    	Class<?> entityClass = getEntityClass(params);
		StringBuffer sql=new StringBuffer();
		Object obj=params.get("list");
		sql.append(" SELECT ");
		sql.append(MapperHelper.getTableColumnsString(entityClass,null));
		sql.append(" FROM ");
		sql.append(MapperHelper.getTableName(entityClass));
		sql.append(" WHERE 1=1 ");
		if(null!=obj){
			List<Criterion> list=(List<Criterion>) obj;
			if(list.size()>0){
				for(Criterion c:list){
					if(!StringUtil.isEmpty(c.toString())) sql.append(c.toString());
				}
			}
		}
		return sql.toString();
    }
    
    /**
     * 根据criterion删除
     * @param list
     * @param entityClass
     * @return
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
	public String deleteByCriterions(final Map<String,Object> params) throws Exception{
    	Class<?> entityClass = getEntityClass(params);
		StringBuffer sql=new StringBuffer();
		Object obj=params.get("list");
		sql.append(" DELETE FROM ");
		sql.append(MapperHelper.getTableName(entityClass));
		sql.append(" WHERE 1=1 ");
		if(null!=obj){
			List<Criterion> list=(List<Criterion>) obj;
			if(list.size()>0){
				for(Criterion c:list){
					if(!StringUtil.isEmpty(c.toString())) sql.append(c.toString());
				}
			}
		}
		return sql.toString();
    }
    
    @SuppressWarnings("unchecked")
	public String updateByParameterMap(final Map<String,Object> params) throws Exception{
    	Class<?> entityClass = getEntityClass(params);
    	Map<String,Object> map=(Map<String,Object>)this.getParam(params, "params");
    	Map<String,EntityColumn> cols= MapperHelper.getTableColumnsMap(entityClass);
    	StringBuffer sql=new StringBuffer();
    	Object obj=params.get("list");
    	if(null!=map&&map.size()>0&&null!=obj){
    		List<Criterion> list=(List<Criterion>) obj;
			if(list.size()>0){
				String tableName=MapperHelper.getTableName(entityClass);
	    		sql.append(" UPDATE ").append(tableName).append(" SET ");
	    		int i=0;
	    		for(Map.Entry<String, Object> entry:map.entrySet()){
	    			EntityColumn column=cols.get(entry.getKey());
	    			if(i==0) {
	    				sql.append(entry.getKey()).append("=");
	    				if(column.getJavaType().equals(String.class)||column.getJavaType().isAssignableFrom(Date.class)){
	    					sql.append("'").append(entry.getValue()).append("'");
	    				}else{
	    					sql.append(entry.getValue());
	    				}
	    			}else{
	    				sql.append(",");
	    				sql.append(entry.getKey()).append("=");
	    				if(column.getJavaType().equals(String.class)||column.getJavaType().isAssignableFrom(Date.class)){
	    					sql.append("'").append(entry.getValue()).append("'");
	    				}else{
	    					sql.append(entry.getValue());
	    				}
	    			}
	    			i++;
	    		}
	    		sql.append(" WHERE 1=1 ");
	    		for(Criterion c:list){
					if(!StringUtil.isEmpty(c.toString())) sql.append(c.toString());
				}
			}
    	}
    	
    	return sql.toString();
    }
}
