package com.meme.im.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.meme.core.http.ResultMessage;

public interface OpenApiService {

	ResultMessage deleteMemberAlbum(HttpServletRequest request,HttpServletResponse response,String id);
	
	ResultMessage contentVote(HttpServletRequest request,HttpServletResponse response,Long id,Integer state);
}
