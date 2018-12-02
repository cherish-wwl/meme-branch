package com.meme.core.redis;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * redis dao基类
 * 
 * @author hzl
 * 
 * @param <K>
 * @param <V>
 */
public abstract class RedisBaseDao<K extends Serializable, V extends Serializable> {

	@Resource
	private RedisTemplate<K, V> redisTemplate;

	public RedisTemplate<K, V> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	protected RedisSerializer<String> getRedisSerializer() {
		return redisTemplate.getStringSerializer();
	}
}