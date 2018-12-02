package com.meme.im.redis;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.meme.core.util.StringUtil;
import com.meme.im.redis.util.RedisUtil;

/**
 * 生成临时访客，缓存ip-访客键值对
 * @author hzl
 *
 */
@Service("IpTmpMemberService")
public class IpTmpMemberService {
	@Resource private RedisUtil redisUtil;
	public static final String NAMESPACE_IP_MEMBER="ip_member:";
	
	/**
	 * 添加ip-memberid记录
	 * @param ip
	 * @param memberid
	 * @return
	 */
	public boolean add(String ip,String memberid){
		try{
			boolean result=redisUtil.cacheHash(NAMESPACE_IP_MEMBER+ip, memberid, memberid);
			return result;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 查询某ip记录是否存在该memberid
	 * @param ip
	 * @param memberid
	 * @return
	 */
	public boolean containKey(String ip, String memberid){
		try {
			String value=this.redisUtil.getHashValue(NAMESPACE_IP_MEMBER+ip, memberid);
			if(StringUtil.isAllNotEmpty(value)) return true;
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
