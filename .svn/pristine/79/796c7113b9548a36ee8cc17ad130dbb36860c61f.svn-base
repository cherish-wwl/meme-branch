package com.meme.core.mybatis.provider;

import java.util.List;
import java.util.Map;

/**
 * provider基类
 * @author hzl
 *
 */
public abstract class BaseProvider {

	/**
	 * 获取provider注解的mapper类方法的实体或者主键参数
	 * @param params
	 * @return
	 */
	protected Object getEntity(Map<String, Object> params) {
        Object result;
        if (params.containsKey("record")) {
            result = params.get("record");
        } else if (params.containsKey("key")) {
            result = params.get("key");
        } else {
            throw new RuntimeException("当前方法没有实体或主键参数");
        }
        if (result == null) {
            throw new NullPointerException("实体或者主键参数不能为空");
        }
        return result;
    }
	
	/**
	 * 获取provider注解的mapper类方法的实体参数
	 * @param params
	 * @return
	 */
	protected Class<?> getEntityClass(Map<String, Object> params) {
        Class<?> entityClass = null;
        if (params.containsKey("record")) {
            entityClass = getEntity(params).getClass();
        } else if (params.containsKey("entityClass")) {
            entityClass = (Class<?>) params.get("entityClass");
        }
        if (entityClass == null) {
            throw new RuntimeException("无法获取实体类型!");
        }
        return entityClass;
    }
	
	protected Object getParam(Map<String, Object> params,String key){
		if (params.containsKey(key)){
			return params.get(key);
		}
		return null;
	}
	
	/**
	 * 返回参数名为list的数组
	 * @param params
	 * @return
	 */
	protected List<?> getListInParams(Map<String, Object> params){
		if(params.containsKey("list")) return (List<?>) params.get("list");
		return null;
	}
}
