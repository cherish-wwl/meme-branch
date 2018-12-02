package com.meme.im.redis.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil {
	
    private Logger logger = Logger.getLogger(this.getClass());
    @Resource private RedisTemplate<String, String> stringRedisTemplate;
    
    /**
     * 缓存键值对
     * @param redisKey
     * @param hashKey
     * @param hashValue
     * @param time 超时时间，单位：秒
     * @return
     */
    public boolean cacheHash(String redisKey,String hashKey,String hashValue,long time){
    	try{
    		HashOperations<String, String,String> hashOps=this.stringRedisTemplate.opsForHash();
    		hashOps.put(redisKey, hashKey, hashValue);
    		if (time > 0) stringRedisTemplate.expire(redisKey, time, TimeUnit.SECONDS);
    		return true;
    	}catch (Exception e) {
			logger.error("缓存[" + hashKey + "]失败, value[" + hashValue + "]", e);
		}
    	return false;
    }
    
    /**
     * 默认缓存不超时
     * @param redisKey
     * @param hashKey
     * @param hashValue
     * @return
     */
    public boolean cacheHash(String redisKey,String hashKey,String hashValue){
    	return this.cacheHash(redisKey, hashKey, hashValue, -1);
    }
    
    /**
     * 分页获取键值对缓存数据
     * @param redisKey
     * @param start
     * @param end
     * @return
     */
    public List<String> getHashList(String redisKey, long start, long end){
    	try{
    		HashOperations<String, String,String> hashOps=this.stringRedisTemplate.opsForHash();
    		List<String> list=new ArrayList<String>();
    		Set<String> keys=hashOps.keys(redisKey);
    		if(null!=keys&&keys.size()>0){
    			Iterator<String> it=keys.iterator();
    			int i=0;
    			while(it.hasNext()){
    				String key=it.next();
    				if(i>=start){
    					if(i<end){
            				String value=hashOps.get(redisKey, key);
            				list.add(value);
    					}else break;
    				}
    				i++;
    			}
    		}
    		return list;
    	}catch (Exception e) {
    		logger.error("获取hash值list缓存失败key[" + redisKey + ", error[" + e + "]");
		}
    	return null;
    }
    
    /**
     * 获取键值对缓存总数
     * @param redisKey
     * @return
     */
    public long getHashTotal(String redisKey){
    	try{
    		HashOperations<String, String,String> hashOps=this.stringRedisTemplate.opsForHash();
    		return hashOps.keys(redisKey).size();
    	}catch (Exception e) {
    		logger.error("获取hash总数失败[" + redisKey + ", error[" + e + "]");
		}
    	return 0l;
    }
    
    /**
     * 移除hash缓存项
     * @param redisKey
     * @param hashKey
     * @return
     */
    public boolean removeHash(String redisKey,String hashKey){
    	try{
    		HashOperations<String, String,String> hashOps=this.stringRedisTemplate.opsForHash();
    		hashOps.delete(redisKey, hashKey);
    		return true;
    	}catch (Exception e) {
    		logger.error("删除hash失败[" + redisKey + ",hashKey="+hashKey+", error[" + e + "]");
		}
    	return false;
    }
    
    /**
     * 获取hash缓存值
     * @param redisKey
     * @param hashKey
     * @return
     */
    public String getHashValue(String redisKey,String hashKey){
    	try{
    		HashOperations<String, String,String> hashOps=this.stringRedisTemplate.opsForHash();
    		return hashOps.get(redisKey, hashKey);
    	}catch (Exception e) {
    		logger.error("获取hash缓存值失败[" + redisKey + ",hashKey="+hashKey+", error[" + e + "]");
		}
    	return null;
    }
}
