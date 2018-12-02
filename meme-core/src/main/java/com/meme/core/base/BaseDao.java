package com.meme.core.base;

import java.util.List;

/**
 * 基类DAO接口
 * @author hzl
 *
 * @param <T> 实体类
 * @param <K> 分页类
 */
@Deprecated
public interface BaseDao<T,K> {
	
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
	 */
    List<T> selectAll();

	/**
	 * 查询分页
	 * @param pagination
	 * @return
	 */
    List<T> selectByPagination(K pagination);

	/**
	 * 分页统计总记录数
	 * @param pagination
	 * @return
	 */
    int count(K pagination);

	/**
	 * 根据实体类查找记录
	 * @param record
	 * @return
	 */
    List<T> selectByEntity(T record);
    
    /**
     * 批量插入记录
     * @param record
     * @return
     */
    int batchInsert(List<T> record);

	/**
	 * 批量删除记录
	 * @param record
	 * @return
	 */
    int batchDelete(List<Object> record);

	/**
	 * 批量更新记录
	 * @param record
	 * @return
	 */
    int batchUpdate(List<T> record);
}