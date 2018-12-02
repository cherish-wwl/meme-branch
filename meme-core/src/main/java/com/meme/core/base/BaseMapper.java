package com.meme.core.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.meme.core.mybatis.Criterion;
import com.meme.core.mybatis.provider.CommonProvider;
import com.meme.core.spring.db.SqlMapper;

/**
 * Mapper类接口，定义常用的数据库操作
 * @author hzl
 *
 */
public interface BaseMapper extends SqlMapper{

	/**
	 * 根据主键删除记录
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(Object id);

	/**
	 * 插入记录
	 * @param record
	 * @return
	 */
    <T> int insert(T record);

	/**
	 * 插入记录，字段为空时此字段不插入数据
	 * @param record
	 * @return
	 */
    <T> int insertSelective(T record);

	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
    <T> T selectByPrimaryKey(Object id);

	/**
	 * 更新记录，字段为空时此字段不做更新
	 * @param record
	 * @return
	 */
    <T> int updateByPrimaryKeySelective(T record);

	/**
	 * 更新记录
	 * @param record
	 * @return
	 */
    <T> int updateByPrimaryKey(T record);

	/**
	 * 查询所有记录
	 * @return
	 */
    @SelectProvider(type=CommonProvider.class,method="selectAll")
    <T> List<Map<String, Object>> selectAll(@Param("entityClass")Class<T> record);
    

    @SelectProvider(type=CommonProvider.class,method="selectByEntity")
    <T> List<Map<String, Object>> selectByEntity(@Param("record")T record);

	/**
	 * 查询分页
	 * @param pagination
	 * @return
	 */
    @SelectProvider(type=CommonProvider.class,method="selectByPagination")
    <T> List<Map<String, Object>> selectByPagination(@Param("entityClass")Class<T> record,@Param("list")List<Criterion> list);

	/**
	 * 分页统计总记录数
	 * @param pagination
	 * @return
	 */
    @SelectProvider(type=CommonProvider.class,method="count")
    <T> int count(@Param("entityClass")Class<T> record,@Param("list")List<Criterion> list);
    
    /**
     * 主键自增表的批量插入记录，避免自增主键报Parameter 'id' not found异常，加入@SelectKey注解，也可在基类CommonProvider相应方法中添加空值的id参数
     * @param record
     * @return
     */
    @InsertProvider(type=CommonProvider.class,method="batchInsert")
    @SelectKey(statement="SELECT DISTINCT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=int.class)
    <T> int batchInsert(@Param("list")List<T> record,@Param("entityClass")Class<T> entityClass);

	/**
	 * 批量根据主键删除记录
	 * @param record
	 * @return
	 */
    @DeleteProvider(type=CommonProvider.class,method="batchDelete")
    <T> int batchDelete(@Param("list")List<Object> record,@Param("entityClass")Class<T> entityClass);

	/**
	 * 批量根据主键更新记录
	 * @param record
	 * @return
	 */
    @UpdateProvider(type=CommonProvider.class,method="batchUpdate")
    <T> int batchUpdate(@Param("list")List<T> record,@Param("entityClass")Class<T> entityClass);
    
    /**
     * 根据criterion查询
     * @param list
     * @param entityClass
     * @return
     */
    @SelectProvider(type=CommonProvider.class,method="selectByCriterions")
    <T> List<Map<String, Object>> selectByCriterions(@Param("list")List<Criterion> list,@Param("entityClass")Class<T> entityClass);
    
    /**
     * 根据criterion删除
     * @param list
     * @param entityClass
     * @return
     */
    @DeleteProvider(type=CommonProvider.class,method="deleteByCriterions")
    <T> int deleteByCriterions(@Param("list")List<Criterion> list,@Param("entityClass")Class<T> entityClass);
    
    /**
     * 根据criterion查询规则更新特定值
     * @param params
     * @param list
     * @param entityClass
     * @return
     */
    @UpdateProvider(type=CommonProvider.class,method="updateByParameterMap")
    <T> int updateByParameterMap(@Param("params")Map<String,Object> params,@Param("list")List<Criterion> list,@Param("entityClass")Class<T> entityClass);
}
