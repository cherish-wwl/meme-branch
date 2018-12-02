package com.meme.im.redis;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.meme.core.redis.RedisBaseDao;
import com.meme.core.util.StringUtil;
import com.meme.im.pojo.Member;
import com.meme.im.redis.util.RedisUtil;

@Service("WebsocketSessionService")
public class WebsocketSessionService extends RedisBaseDao<String, Member> implements IWebsocketSessionService{
	
	private static final Logger logger = Logger.getLogger(WebsocketSessionService.class);
	@Resource private RedisUtil redisUtil;
	public static final String NAMESPACE_ONLINE_MEMBER="online_member";
	
	@Override
	public boolean add(final Member member) {
		try{
			String key=member.getMemberid().toString();
			String value=JSONObject.toJSONString(member);
			boolean result=redisUtil.cacheHash(NAMESPACE_ONLINE_MEMBER+":all", key, value);
			if(member.getState()==1) {
				redisUtil.cacheHash(NAMESPACE_ONLINE_MEMBER+":registered", key, value);
			}
			return result;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void delete(String key) {
		this.redisUtil.removeHash(NAMESPACE_ONLINE_MEMBER+":all", key);
		this.redisUtil.removeHash(NAMESPACE_ONLINE_MEMBER+":registered", key);
	}

	@Override
	public Member getMember(final String key) {
		try {
			String value=this.redisUtil.getHashValue(NAMESPACE_ONLINE_MEMBER+":all", key);
			if(StringUtil.isAllNotEmpty(value)) return null;
			return JSONObject.parseObject(value, Member.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<String> getPagination(Integer page,Integer pageSize,Integer state) {
		if(null==page||page==0) page=1;
		if(null==pageSize||pageSize==0) pageSize=15;
		Long total=this.redisUtil.getHashTotal((state==1)?NAMESPACE_ONLINE_MEMBER+":registered":NAMESPACE_ONLINE_MEMBER+":all");
		if(null==total||total==0L) return null;
		long totalPage = (total - 1l) / Long.valueOf(pageSize.toString())+1;
		Long start=0l;
		if(totalPage<Long.valueOf(page.toString())){
			start=Long.valueOf(((totalPage-1)*pageSize));
		}else{
			start=Long.valueOf(((page-1)*pageSize));
		}
		List<String> list=this.redisUtil.getHashList((state==1)?NAMESPACE_ONLINE_MEMBER+":registered":NAMESPACE_ONLINE_MEMBER+":all", start, start+Long.valueOf(pageSize.toString()));
		return list;
	}

	@Override
	public Long getTotal(Integer state) {
		return this.redisUtil.getHashTotal((state==1)?NAMESPACE_ONLINE_MEMBER+":registered":NAMESPACE_ONLINE_MEMBER+":all");
	}
}
