package com.meme.im.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.meme.core.base.BaseMapper;
import com.meme.im.form.MovieForm;
import com.meme.im.pojo.MemeMovie;
import com.meme.im.pojo.MemeMovieUpdate;

public interface MemeMovieMapper extends BaseMapper{

	/**
	 * 根据id查询movie
	 * @return
	 */
	MemeMovie selectById(String movieId);
	
	/**
	 * 根据电影类型查询movie，可根据录入时间（最新）、点赞数（最热）排序
	 * @param movieCategory
	 * @param orderBy
	 * @return
	 */
	List<MemeMovie> selectAllMovies(@Param("movieCategory")String movieCategory,@Param("orderBy")Integer orderBy);
	
	/**
	 * 分页查询电影和会员名
	 * @param form
	 * @return
	 */
	List<Map<String,Object>> selectAllMoviesAndMembers(MovieForm form);
	/**
	 * 统计分页查询消息总数
	 * @param form
	 * @return
	 */
	int totalNum(MovieForm form);
	
	/**
	 * 电影更新可修改部分的数据
	 * @param update
	 * @return
	 */
	int update(MemeMovieUpdate update);
	
	/**
	 * 根据id删除电影
	 * @param ids
	 * @return
	 */
	int deleteMovie(@Param("ids")List<Object> ids);

	
}