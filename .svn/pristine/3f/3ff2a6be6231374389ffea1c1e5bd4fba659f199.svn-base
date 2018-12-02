package com.meme.im.service.impl;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meme.core.http.ResultMessage;
import com.meme.core.util.AddressUtil;
import com.meme.core.util.StringUtil;
import com.meme.im.pojo.MemberAlbum;
import com.meme.im.pojo.MemeContent;
import com.meme.im.pojo.MemeContentVote;
import com.meme.im.service.MemberAlbumItemService;
import com.meme.im.service.MemberAlbumService;
import com.meme.im.service.MemeContentService;
import com.meme.im.service.MemeContentVoteService;
import com.meme.im.service.OpenApiService;
import com.meme.im.util.AccountUtil;

@Service("OpenApiService")
@Transactional
public class OpenApiServiceImpl implements OpenApiService {

	@Resource private MemberAlbumService memberAlbumService;
	@Resource private MemberAlbumItemService memberAlbumItemService;
	@Resource private MemeContentService memeContentService;
	@Resource private MemeContentVoteService memeContentVoteService;
	
	@Override
	public ResultMessage deleteMemberAlbum(HttpServletRequest request, HttpServletResponse response, String id) {
		if(StringUtil.isEmpty(id)) ResultMessage.failed("提供需要删除的相册编号");
		Object memberid=AccountUtil.getMemberid(request);
		if(null==memberid) return ResultMessage.failed("会员账号未登录");
		MemberAlbum object= this.memberAlbumService.selectByPrimaryKey(id);
		if(null==object) return ResultMessage.failed("相册编号不正确");
		if(!object.getMemberid().toString().equals(memberid.toString())) return ResultMessage.failed("无法删除非当前登录会员相册");
		this.memberAlbumService.deleteByPrimaryKey(id);
		this.memberAlbumItemService.deleteByAlbum(id);
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage contentVote(HttpServletRequest request, HttpServletResponse response, Long id, Integer state) {
		if(id==null || id==0l) ResultMessage.failed("请提供内容编号");
		Object memberid=AccountUtil.getMemberid(request);
		if(null==memberid) return ResultMessage.failed("会员账号未登录");
		MemeContent object= this.memeContentService.selectByPrimaryKey(id);
		if(null==object) return ResultMessage.failed("内容不存在");
		String ip=AddressUtil.getRealIPAddress(request);
		boolean isvoted=this.memeContentVoteService.checkVoted(id, Long.valueOf(memberid.toString()), ip);
		if(isvoted) return ResultMessage.failed("今天已点赞，请勿重复点赞");
		
		MemeContent content=new MemeContent();
		content.setContentid(id);
		if(state==null) state=1;
		if(state==0){
			content.setDownvote(object.getDownvote()+1);
		}else{
			content.setUpvote(object.getUpvote()+1);
		}
		this.memeContentService.updateByPrimaryKeySelective(content);
		MemeContentVote vote=new MemeContentVote();
		vote.setContentid(id);
		vote.setIp(ip);
		vote.setMemberid(Long.valueOf(memberid.toString()));
		vote.setState(state);
		vote.setVotetime(new Date());
		this.memeContentVoteService.insertSelective(vote);
		return ResultMessage.defaultSuccessMessage();
	}

}
