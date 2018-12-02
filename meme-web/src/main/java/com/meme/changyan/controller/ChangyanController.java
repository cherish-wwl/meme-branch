package com.meme.changyan.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONArray;
import com.meme.changyan.pojo.User;
import com.meme.changyan.pojo.UserInfo;
import com.meme.core.base.BaseController;
import com.meme.core.util.JWTUtil;
import com.meme.core.util.MD5Util;
import com.meme.im.pojo.Member;
import com.meme.im.service.MemberService;
import com.meme.im.util.AccountUtil;
import com.meme.im.util.CookieUtil;
import com.meme.im.util.IMConstants;

@Controller
@RequestMapping("/member/changyan/")
public class ChangyanController extends BaseController{

    @Resource private MemberService memberService;

    //�ýӿ�ֻ�е������ѵ�¼��getUserInfo����δ��¼ʱ���Żᱻ����������¼������վ
    @RequestMapping("login")
    public void loginByCy(HttpServletRequest request,HttpServletResponse response,
                          @RequestParam(value = "callback") String callback,
                          @RequestParam(value = "user_id") String user_id,
                          @RequestParam(value = "nickname") String nickname,
                          @RequestParam(value = "sign") String sign,
                          @RequestParam(value = "img_url") String img_url,
                          @RequestParam(value = "profile_url") String profile_url) {
        //�Լ���վ�ĵ�¼�߼�����¼��¼��Ϣ��cookie
        //token��cookie
        Map<String, Object> info = new HashMap<String, Object>();
        info.put(IMConstants.COOKIE_MEMBER_KEY, user_id);
        String token=JWTUtil.buildToken(info);
        info.clear();
        info.put(IMConstants.COOKIE_TOKEN_KEY, token);
        CookieUtil.setCookies(request, response, info, null);
        try {
            response.getWriter().write(callback + "({\"user_id\":"+user_id+",reload_page:0})");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("logout")
    public void loginBySite(@RequestParam(value = "callback") String callback,HttpServletResponse resp) {
        //����Լ���վcookies��Ϣ,ͬʱǰ��logout.js��������������cookie
        try {
            resp.getWriter().write(callback + "({\"code\":\"1\",reload_page:0, js-src:logout.js})");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //�ýӿ���ҳ��ÿһ�μ���ʱ���ᱻ���ã������ж��û����Լ���վ�Ƿ��¼
    @RequestMapping("getUserInfo")
    public void getUserInfo(@RequestParam(value = "callback") String callback, HttpServletRequest res, HttpServletResponse resp) {
        UserInfo userinfo=new UserInfo();
        Object memberid=AccountUtil.getMemberid(res);
        if(null==memberid){
            userinfo.setIs_login(0);//�û�δ��¼
        }else{
            userinfo.setIs_login(1);//�û��ѵ�¼
            User user = new User();
            Member member=this.memberService.selectByPrimaryKey(Long.valueOf(memberid.toString()));
            if(null!=member){
                user.setUser_id(member.getMemberid());
                user.setNickname(member.getNickname());
                user.setImg_url(member.getAvatar());
                user.setProfile_url(member.getDomain());
                user.setSign(MD5Util.generatePassword(member.getMemberid().toString()));
                userinfo.setUser(user);
            }else{
                userinfo.setIs_login(0);
            }
        }
        resp.setContentType("application/x-javascript;charset=utf-8");
        try {
            resp.getWriter().write(callback+"("+JSONArray.toJSONString(userinfo)+")");//ƴ�ӳ�jsonp��ʽ
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}