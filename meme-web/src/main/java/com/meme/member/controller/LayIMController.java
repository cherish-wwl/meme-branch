package com.meme.member.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meme.core.base.BaseController;
import com.meme.core.cache.DictCache;
import com.meme.core.cache.ParamsCache;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.util.JWTUtil;
import com.meme.core.util.StringUtil;
import com.meme.im.entity.FriendJson;
import com.meme.im.entity.Mine;
import com.meme.im.entity.UploadResult;
import com.meme.im.entity.enums.OnlineType;
import com.meme.im.pojo.ImGroup;
import com.meme.im.pojo.Member;
import com.meme.im.service.ImFriendApplyService;
import com.meme.im.service.ImFriendService;
import com.meme.im.service.ImGroupService;
import com.meme.im.service.ImMessageService;
import com.meme.im.service.LayIMService;
import com.meme.im.service.MemberService;
import com.meme.im.util.AccountUtil;
import com.meme.im.util.IMConstants;
import com.meme.im.websocket.ServerPoint;
import com.meme.im.websocket.WebSocketServer;
import com.meme.qiniu.pojo.QiniuDir;
import com.meme.qiniu.service.QiniuDirService;
import com.meme.qiniu.util.QiniuAPI;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.UploadManager;

@Controller
@RequestMapping("/member/im/")
public class LayIMController extends BaseController{

    @Resource private QiniuDirService qiniuDirService;
    @Resource private MemberService memberService;
    @Resource private ImFriendApplyService imFriendApplyService;
    @Resource private ImFriendService imFriendService;
    @Resource private LayIMService layIMService;
    @Resource private ImMessageService imMessageService;
    @Resource private ImGroupService imGroupService;

    /**
     * �ϴ�ͼƬ
     * @param request
     * @return
     */
    @RequestMapping("uploadImage")
    @ResponseBody
    public String uploadImage(HttpServletRequest request,HttpServletResponse response,@RequestHeader(value="Origin") String Origin) throws Exception{
    		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
        String bucket=null;
        bucket=DictCache.getDictItemList("QINIU_STORAGE_NAME").elements().nextElement().getDictitemcode();
        if(StringUtil.isEmpty(bucket)) bucket="Ĭ�Ͽռ�";
        String prefix=ParamsCache.get("IM_CHAT_FILE_DIR")==null?"":ParamsCache.get("IM_CHAT_FILE_DIR").getValue();
        if(!StringUtil.isEmpty(prefix)){
            QiniuDir record=new QiniuDir();
            record.setBucket(bucket);
            record.setFulldir(prefix.endsWith("/")?prefix:(prefix+"/"));
            List<QiniuDir> list=this.qiniuDirService.selectByEntity(record);
            //��ţĿ¼���޴�Ŀ¼�����Ŀ¼���������Ŀ¼�ṹ
            if(null==list||list.size()==0){
                this.qiniuDirService.prefix2Dir(bucket, prefix);
            }
        }

        UploadManager uploadManager = QiniuAPI.getUploadManager(Zone.autoZone());
        String upToken = QiniuAPI.getUploadToken(bucket,null);
        ResultMessage result=QiniuAPI.getBucketDomains(bucket);
        String msg=result.getMessage();
        if(result.getState().equals("0")) 
        return "<script type='text/javascript'>window.location.href='"+Origin+"/uploadhelper?callback="+URLEncoder.encode(JSON.toJSONString(UploadResult.failed(-1,"��ȡ��ţ�ռ�����ʧ�ܣ�"+msg)), "UTF-8")+"';</script>";
        String[] domains=(String[]) result.getData();
        String domain=null;
        if(null!=domains&&domains.length>0) domain=domains[0];

        JSONObject data=null;

        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
        if(multipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
            Iterator<String> it=multiRequest.getFileNames();
            if(it.hasNext()){
                MultipartFile file=multiRequest.getFile(it.next());
                String name=file.getOriginalFilename();
                String suffix=name.substring(name.lastIndexOf(".")+1,name.length());

                try {
//			    	String hash=Etag.data(file.getBytes());
                    String hash=UUID.randomUUID().toString().replaceAll("-", "");
                    //Ĭ�ϲ�ָ��key������£����ļ����ݵ�hashֵ��Ϊ�ļ���
                    String key = null;

                    if(StringUtil.isEmpty(prefix)) key=hash+"."+suffix;
                    else key=(prefix.endsWith("/")?prefix:(prefix+"/"))+hash+"."+suffix;
                    uploadManager.put(file.getBytes(), key, upToken);

                    data=new JSONObject();
                    data.put("src", "http://"+domain+"/"+key);
                } catch (QiniuException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "<script type='text/javascript'>window.location.href='"+Origin+"/uploadhelper?callback="+URLEncoder.encode(JSON.toJSONString(UploadResult.success(data)), "UTF-8")+"';</script>";
    }

    /**
     * �ϴ��ļ�
     * @param request
     * @return
     */
    @RequestMapping("uploadFile")
    @ResponseBody
    public String uploadFile(HttpServletRequest request,HttpServletResponse response,@RequestHeader(value="Origin") String Origin) throws Exception{
    	response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
        String bucket=null;
        bucket=DictCache.getDictItemList("QINIU_STORAGE_NAME").elements().nextElement().getDictitemcode();
        if(StringUtil.isEmpty(bucket)) bucket="Ĭ�Ͽռ�";
        String prefix=ParamsCache.get("IM_CHAT_FILE_DIR")==null?"":ParamsCache.get("IM_CHAT_FILE_DIR").getValue();
        if(!StringUtil.isEmpty(prefix)){
            QiniuDir record=new QiniuDir();
            record.setBucket(bucket);
            record.setFulldir(prefix.endsWith("/")?prefix:(prefix+"/"));
            List<QiniuDir> list=this.qiniuDirService.selectByEntity(record);
            //��ţĿ¼���޴�Ŀ¼�����Ŀ¼���������Ŀ¼�ṹ
            if(null==list||list.size()==0){
                this.qiniuDirService.prefix2Dir(bucket, prefix);
            }
        }

        UploadManager uploadManager = QiniuAPI.getUploadManager(Zone.autoZone());
        String upToken = QiniuAPI.getUploadToken(bucket,null);
        ResultMessage result=QiniuAPI.getBucketDomains(bucket);
        String msg=result.getMessage();
        if(result.getState().equals("0")) 
            return "<script type='text/javascript'>window.location.href='"+Origin+"/uploadhelper?callback="+URLEncoder.encode(JSON.toJSONString(UploadResult.failed(-1,"��ȡ��ţ�ռ�����ʧ�ܣ�"+msg)), "UTF-8")+"';</script>";
        String[] domains=(String[]) result.getData();
        String domain=null;
        if(null!=domains&&domains.length>0) domain=domains[0];

        JSONObject data=null;

        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
        if(multipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
            Iterator<String> it=multiRequest.getFileNames();
            if(it.hasNext()){
                MultipartFile file=multiRequest.getFile(it.next());
                String name=file.getOriginalFilename();
                String suffix=name.substring(name.lastIndexOf(".")+1,name.length());

                try {
//			    	String hash=Etag.data(file.getBytes());
                    String hash=UUID.randomUUID().toString().replaceAll("-", "");
                    //Ĭ�ϲ�ָ��key������£����ļ����ݵ�hashֵ��Ϊ�ļ���
                    String key = null;

                    if(StringUtil.isEmpty(prefix)) key=hash+"."+suffix;
                    else key=(prefix.endsWith("/")?prefix:(prefix+"/"))+hash+"."+suffix;
                    uploadManager.put(file.getBytes(), key, upToken);

                    data=new JSONObject();
                    data.put("src", "http://"+domain+"/"+key);
                } catch (QiniuException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "<script type='text/javascript'>window.location.href='"+Origin+"/uploadhelper?callback="+URLEncoder.encode(JSON.toJSONString(UploadResult.success(data)), "UTF-8")+"';</script>";

    }

    /**
     * layim init��ʼ�����ݽӿ�
     * @param request
     * @return
     */
    @RequestMapping("init")
    @ResponseBody
    public ResultMessage init(HttpServletRequest request,HttpServletResponse response,@RequestHeader(value="Origin") String Origin){
    	response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
        JSONObject initJson=new JSONObject();
        Mine mine=this.layIMService.getMine(request);
        if(null==mine) return ResultMessage.failed("��ʼ������ʧ��");
        List<FriendJson> friend=this.layIMService.getFriends(request);

        //��ӿͷ����鵽�����б�
        List<Map<String,Object>> services=this.memberService.getAllServiceMembers();
        if(null!=services&&services.size()>0){
            LinkedHashMap<Long, FriendJson> list=new LinkedHashMap<Long, FriendJson>();
            for(Map<String,Object> map:services){
            		ServerPoint serverPoint = WebSocketServer.sessions.get(map.get("id").toString());
				if(serverPoint != null && serverPoint.getSession().isOpen()) {
					map.put("status", OnlineType.online.getType());
				}else {
					map.put("status", OnlineType.offline.getType());
				}
                //����¼�˺�Ϊ�ͷ�ʱ���Լ������
                if(mine.getMemberid().toString().equals(map.get("memberid").toString())) continue;
                Long groupid=Long.valueOf(map.get("groupid").toString());
                if(list.containsKey(groupid)){
                    list.get(groupid).getList().add(map);
                }else{
                    FriendJson friendJson=new FriendJson();
                    friendJson.setGroupname(map.get("groupname").toString());
                    friendJson.setId(groupid);
                    friendJson.getList().add(map);
                    list.put(groupid, friendJson);
                }
            }
            if(list.size()>0) {
                Collection<FriendJson> jsons=list.values();
                Iterator<FriendJson> it=jsons.iterator();
                while(it.hasNext()){
                    FriendJson next=it.next();
                    friend.add(next);
                }
            }
        }
        for(FriendJson json:friend) {
        		json.getList().sort(new Comparator<Object>() {
				@Override
				public int compare(Object o1, Object o2) {
					Map<String,Object> map1 = (Map<String,Object>)o1;
					Map<String,Object> map2 = (Map<String,Object>)o2;
					String s1 = (String)map1.get("status");
					String s2 = (String)map2.get("status");
					if(s1.compareTo(s2) >0) {
						return -1;
					}else if(s1.compareTo(s2) <0) {
						return 1;
					}else {
						long l1 = ((Timestamp)map1.get("lastlogin")).getTime();
						long l2 = ((Timestamp)map2.get("lastlogin")).getTime();
						if(l1 < l2) {
							return 1;
						}else if(l1 > l2){
							return -1;
						}else {
							return 0;
						}
					}
				}
			});
        }
        List<ImGroup> group = this.imGroupService.selectGroups((Long)AccountUtil.getMemberid(request));
        initJson.put("mine", mine);
        initJson.put("friend", friend);
        initJson.put("group", group);

        Member from=null;
        String friendid=request.getParameter("friendid");
        if(!StringUtil.isEmpty(friendid)){
            from=this.memberService.selectByPrimaryKey(Long.valueOf(friendid));
        }
        initJson.put("from",from);
        return ResultMessage.success("�ɹ���ʼ������",initJson);
    }

    /**
     * ��ǰ�˺Ÿ�����Ϣ�ӿ�
     * @param request
     * @return
     */
    @RequestMapping("getMine")
    @ResponseBody
    public Mine getMine(HttpServletRequest request,HttpServletResponse response,@RequestHeader(value="Origin") String Origin){
    	response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
        return this.layIMService.getMine(request);
    }

    /**
     * layim �����б����ݽӿ�
     * @param request
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping("getFriends")
    @ResponseBody
    public Collection<FriendJson> getFriends(HttpServletRequest request) {
        return this.layIMService.getFriends(request);
    }

    /**
     * layim Ⱥ���ݽӿ�
     * @param request
     * @return
     */
    @RequestMapping("getGroups")
    @ResponseBody
    public Object getGroups(HttpServletRequest request){
        return null;
    }

    /**
     * ��֤token
     * @return
     */
    @RequestMapping("validate")
    @ResponseBody
    public ResultMessage validate(HttpServletRequest request,HttpServletResponse response,@RequestHeader(value="Origin") String Origin,String token){
    	response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
        if(StringUtil.isEmpty(token)) {
            return ResultMessage.defaultFaileMessage();
        }
        Map<String,Object> claims=JWTUtil.verifyToken(token);
        if(null==claims) {
            return ResultMessage.defaultFaileMessage();
        }
        Object memberid=claims.get(IMConstants.COOKIE_MEMBER_KEY);
        if(null==memberid) {
            return ResultMessage.defaultFaileMessage();
        }
        Member member=this.memberService.selectByPrimaryKey(memberid);
        if(null==member){
            return ResultMessage.defaultFaileMessage();
        }
        return ResultMessage.defaultSuccessMessage();
    }

    /**
     * ��ҳ��ѯ��ǰ��¼��Ա��İ�������ݽӿ�
     * @param request
     * @param form
     * @return
     */
    @RequestMapping("getStrangers")
    @ResponseBody
    public Object getStrangers(HttpServletRequest request,Form form){
        return this.layIMService.getStrangers(request, form);
    }
    
    /**
     * ��ҳ��ѯ��ǰ���е�Ⱥ��
     * @param request
     * @param form
     * @return
     */
    @RequestMapping("getGrouplist")
    @ResponseBody
    public Object getGrouplist(HttpServletRequest request,Form form){
    	return this.layIMService.getGrouplist(request, form);
    }
    
    /**
     * ��ѯȺ���Ա
     * @param request
     * @param form
     * @return
     */
    @RequestMapping("getGroupMembers")
    @ResponseBody
    public Object getGroupMembers(HttpServletRequest request,Long groupid){
    	return this.imGroupService.getGroupMembers(groupid);
    }

    /**
     * �����Ϊ���ѽӿ�
     * @param request
     * @param
     * @return
     */
    @RequestMapping("doApplyFriend")
    @ResponseBody
    public ResultMessage doApplyFriend(HttpServletRequest request,String acceptid,String applymsg,HttpServletResponse response,@RequestHeader(value="Origin") String Origin){
    	response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
        return this.layIMService.doApplyFriend(request, acceptid, applymsg);
    }
    
    /**
     * �����Ⱥ�ӿ�
     * @param request
     * @param
     * @return
     */
    @RequestMapping("doApplyGroup")
    @ResponseBody
    public ResultMessage doApplyGroup(HttpServletRequest request,String groupid,String applymsg,HttpServletResponse response,@RequestHeader(value="Origin") String Origin){
    	response.setHeader("Access-Control-Allow-Origin", Origin);
    	response.setHeader("Access-Control-Allow-Credentials", "true");
    	return this.layIMService.doApplyGroup(request, groupid, applymsg);
    }

    /**
     * ͬ���ܾ���Ӻ�������
     * @param applyid
     * @param isaccept
     * @return
     */
    @RequestMapping("acceptFriend")
    @ResponseBody
    public ResultMessage acceptFriend(Long applyid,Boolean isaccept,HttpServletResponse response,@RequestHeader(value="Origin") String Origin){
    	response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
        return this.imFriendApplyService.accept(applyid, isaccept);
    }
    
    /**
     * ͬ���ܾ���Ӻ�������
     * @param applyid
     * @param isaccept
     * @return
     */
    @RequestMapping("acceptGroup")
    @ResponseBody
    public ResultMessage acceptGroup(Long applyid,Boolean isaccept,HttpServletResponse response,@RequestHeader(value="Origin") String Origin){
    	response.setHeader("Access-Control-Allow-Origin", Origin);
    	response.setHeader("Access-Control-Allow-Credentials", "true");
    	return this.imFriendApplyService.acceptGroup(applyid, isaccept);
    }

    /**
     * ��ҳ��ѯ��������֪ͨ�ӿ�
     * @param request
     * @return
     */
    @RequestMapping("getApplyFriends")
    @ResponseBody
    public Object getApplyFriends(HttpServletRequest request,Form form,HttpServletResponse response,@RequestHeader(value="Origin") String Origin){
    	response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
        return this.layIMService.getApplyFriends(request, form);
    }
    
    /**
     * ��ҳ��ѯ��������֪ͨ�ӿ�
     * @param request
     * @return
     */
    @RequestMapping("getApplyGroups")
    @ResponseBody
    public Object getApplyGroups(HttpServletRequest request,Form form,HttpServletResponse response,@RequestHeader(value="Origin") String Origin){
    	response.setHeader("Access-Control-Allow-Origin", Origin);
    	response.setHeader("Access-Control-Allow-Credentials", "true");
    	return this.layIMService.getApplyGroups(request, form);
    }

    /**
     * ɾ�����ѽӿ�
     * @return
     */
    @RequestMapping("delFriend")
    @ResponseBody
    public ResultMessage delFriend(HttpServletRequest request,String friendid,HttpServletResponse response,@RequestHeader(value="Origin") String Origin){
    	response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
        return this.layIMService.delFriend(request, friendid);
    }
    
    /**
     * ��ȡ�ͺ��ѵ������¼
     * @return
     */
    @RequestMapping("getFriendMessage")
    @ResponseBody
    public List<Map<String,Object>> getFriendMessage(HttpServletRequest request,String friendid,String type,Integer page,HttpServletResponse response,@RequestHeader(value="Origin") String Origin){
	    	response.setHeader("Access-Control-Allow-Origin", Origin);
	    	response.setHeader("Access-Control-Allow-Credentials", "true");
	    	Object mineid = AccountUtil.getMemberid(request);
	    	return this.imMessageService.getFriendMessage(friendid, mineid,page,type);
    }
    
    /**
     * ����Ⱥ��ӿ�
     * @return
     */
    @RequestMapping("createGroup")
    @ResponseBody
    public ResultMessage createGroup(HttpServletRequest request,HttpServletResponse response,ImGroup imGroup,@RequestHeader(value="Origin") String Origin){
    	response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
        return this.imGroupService.add(request, imGroup);
    }
    
}