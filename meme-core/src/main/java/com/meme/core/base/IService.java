package com.meme.core.base;

import java.util.List;

/**
 * Service接口，定义常用数据库操作
 * @author hzl
 *
 * @param <T> 实体类
 * @param <K> 表单类
 */
public interface IService<T,K> {

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
    int insert(T record);

	/**
	 * 插入记录，字段为空时此字段不插入数据
	 * @param record
	 * @return
	 */
    int insertSelective(T record);

	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
    T selectByPrimaryKey(Object id);

	/**
	 * 更新记录，字段为空时此字段不做更新
	 * @param record
	 * @return
	 */
    int updateByPrimaryKeySelective(T record);

	/**
	 * 更新记录
	 * @param record
	 * @return
	 */
    int updateByPrimaryKey(T record);

	/**
	 * 查询所有记录
	 * @return
	 * @throws Exception 
	 */
    List<T> selectAll();

	/**
	 * 查询分页
	 * @param form
	 * @return
	 */
    List<T> selectByPagination(K form);

	/**
	 * 分页统计总记录数
	 * @param form
	 * @return
	 */
    int count(K form);

	/**
	 * 根据实体类查找记录
	 * @param record
	 * @return
	 */
    List<T> selectByEntity(T record);
    
    /**
     * 主键自增表的批量插入记录
     * @param record
     * @return
     */
    int batchInsert(List<T> record);

	/**
	 * 根据ID数组批量删除记录
	 * @param record
	 * @return
	 */
    int batchDelete(List<Object> record);

	/**
	 * 根据ID批量更新记录
	 * @param record
	 * @return
	 */
    int batchUpdate(List<T> record);
}
