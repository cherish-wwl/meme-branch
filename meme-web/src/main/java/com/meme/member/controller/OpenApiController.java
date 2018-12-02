package com.meme.member.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.meme.core.base.BaseController;
import com.meme.core.cache.DictCache;
import com.meme.core.easyui.Pagination;
import com.meme.core.form.Form;
import com.meme.core.http.ResponseMessage;
import com.meme.core.http.ResultMessage;
import com.meme.core.pojo.DictItem;
import com.meme.core.pojo.DictItemView;
import com.meme.core.service.DictItemService;
import com.meme.core.util.StringUtil;
import com.meme.im.entity.MemeColumnSectionTreeGrid;
import com.meme.im.entity.MemeColumnTreeGrid;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.Member;
import com.meme.im.pojo.MemberAlbum;
import com.meme.im.pojo.MemberAlbumItem;
import com.meme.im.pojo.MemberBgTem;
import com.meme.im.pojo.MemberBillboard;
import com.meme.im.pojo.MemberBillboardItem;
import com.meme.im.pojo.MemberHobby;
import com.meme.im.pojo.MemberHobbyType;
import com.meme.im.pojo.MemberSign;
import com.meme.im.pojo.MemeCategory;
import com.meme.im.pojo.MemeColumn;
import com.meme.im.pojo.MemeFreightTemplate;
import com.meme.im.pojo.MemeOrder;
import com.meme.im.pojo.TvStation;
import com.meme.im.pojo.view.MemeColumnSectionView;
import com.meme.im.redis.WebsocketSessionService;
import com.meme.im.service.MemberAlbumItemService;
import com.meme.im.service.MemberAlbumService;
import com.meme.im.service.MemberBgTemService;
import com.meme.im.service.MemberBillboardItemService;
import com.meme.im.service.MemberBillboardService;
import com.meme.im.service.MemberHobbyService;
import com.meme.im.service.MemberHobbyTypeService;
import com.meme.im.service.MemberService;
import com.meme.im.service.MemberSignService;
import com.meme.im.service.MemeCategoryService;
import com.meme.im.service.MemeColumnSectionService;
import com.meme.im.service.MemeColumnService;
import com.meme.im.service.MemeContentService;
import com.meme.im.service.MemeContentVoteService;
import com.meme.im.service.MemeFreightTemplateService;
import com.meme.im.service.MemeOrderService;
import com.meme.im.service.OpenApiService;
import com.meme.im.service.TvProgramService;
import com.meme.im.service.TvStationService;
import com.meme.im.util.AccountUtil;

/**
 * ����api�ӿ�
 * @author hzl
 *
 */
@Controller
@RequestMapping("/member/open/api/")
public class OpenApiController extends BaseController{

	@Resource private WebsocketSessionService websocketSessionService;
	@Resource private MemberBillboardService memberBillboardService;
	@Resource private MemberBillboardItemService memberBillboardItemService;
	@Resource private MemberSignService memberSignService;
	@Resource private MemberAlbumService memberAlbumService;
	@Resource private MemberAlbumItemService memberAlbumItemService;
	@Resource private MemberHobbyService memberHobbyService;
	@Resource private MemberBgTemService memberBgTemService;
	@Resource private MemberService memberService;
	@Resource private DictItemService dictItemService;
	@Resource private MemberHobbyTypeService memberHobbyTypeService;
	@Resource private OpenApiService openApiService;
	@Resource private MemeContentService memeContentService;
	@Resource private MemeColumnSectionService memeColumnSectionService;
	@Resource private MemeColumnService memeColumnService;
	@Resource private MemeCategoryService memeCategoryService;
	@Resource private TvProgramService tvProgramService;
	@Resource private TvStationService tvStationService;
	@Resource private MemeContentVoteService memeContentVoteService;
	@Resource private MemeOrderService memeOrderService;
	@Resource private MemeFreightTemplateService memeFreightTemplateService;
	
	private static final Logger logger = Logger.getLogger("=====����api�ӿ�=====");

	/**
	 * ��ȡ��ǰ��¼��Ա
	 * @param request
	 * @return
	 */
	@RequestMapping("getCurrentMember")
	@ResponseBody
	public JSONObject getCurrentMember(HttpServletRequest request,HttpServletResponse response,String token,String memberid,@RequestHeader(value="Origin") String Origin){
		JSONObject obj=new JSONObject();
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		Object tmpid = null;
		if(!StringUtil.isEmpty(memberid)){
			tmpid=memberid;
		}else{
			tmpid = AccountUtil.getMemberid(token);
		}
		logger.debug("========"+tmpid);
		Member member=null;
		MemberSign sign=null;
		List<MemberAlbumItem> albumitems=null;
		if(null!=tmpid){
			member=this.memberService.selectByPrimaryKey(Long.valueOf(tmpid.toString()));
			if(null!=member){
				member.setAccount(null);
				member.setMpassword(null);
				member.setSalt(null);
			}

			Form form=new Form();
			form.setPage(1);
			form.setPagesize(1);
			form.setSort("signtime");
			form.setOrder(Form.DESC);
			form.setPrimarykey(Long.valueOf(tmpid.toString()));
			form.setState("0");
			form.init();
			List<MemberSign> signs=this.memberSignService.selectByPagination(form);
			if(null!=signs&&signs.size()>0){
				sign=signs.get(0);
			}
			albumitems=this.memberAlbumItemService.selectTopFiveAlbumItems(tmpid);
		}
		String domain=member.getDomain();
		if(!StringUtil.isEmpty(domain)){
			if(domain.startsWith("http://")) member.setDomain(domain.replaceAll("http://", ""));
		}
		obj.put("member", member);
		obj.put("sign", sign);
		obj.put("albumitems", albumitems);
		return obj;
	}
	
	/**
	 * ���ݶ���������ȡ��ԱID
	 * @param request
	 * @return
	 */
	@RequestMapping("getMemberIdByPathname")
	@ResponseBody
	public String getMemberIdByPathname(HttpServletRequest request,HttpServletResponse response,String pathname,@RequestHeader(value="Origin") String Origin){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		return memberService.getMemberIdByPathname(pathname).toString();
	}
	
	/**
	 * ��ȡ��Ա��Ϣ
	 * @param request
	 * @return
	 */
	@RequestMapping("getMember")
	@ResponseBody
	public Member getMember(HttpServletRequest request,HttpServletResponse response,String memberid,@RequestHeader(value="Origin") String Origin){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		if(StringUtil.isEmpty(memberid)) return null;
		Member member=this.memberService.selectByPrimaryKey(Long.valueOf(memberid));
		member.setAccount(null);
		member.setMpassword(null);
		member.setSalt(null);
		return member;
	}
	
	/**
	 * ���ݻ�Աid���߻�Ա�˺Ż�ȡ��Ա��Ϣ
	 * @param request
	 * @return
	 */
	@RequestMapping("getMemberInfo")
	@ResponseBody
	public Member getMemberInfo(HttpServletRequest request,HttpServletResponse response,String memberid,String account,@RequestHeader(value="Origin") String Origin){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		if(StringUtil.isEmpty(memberid) && StringUtil.isEmpty(account)) return null;
		Member member = null;
		if(!StringUtil.isEmpty(memberid)) {
			member = this.memberService.selectByPrimaryKey(Long.valueOf(memberid));
		}else if(!StringUtil.isEmpty(account)) {
			Member member2 = new Member();
			member2.setAccount(account);
			List<Member> selectByEntity = this.memberService.selectByEntity(member2);
			member = selectByEntity.get(0);
		}
		return member;
	}
	
	/**
	 * ��ȡ�������ߺ������ݽӿڣ��������ͷ���,��Ա����������
	 * @param request
	 * @param response
	 * @param page ��ǰҳ��
	 * @param pageSize Ĭ�ϵ�ҳ15����¼
	 * @param state state=1��ѯ��������ע���Ա��Ϊnull��������ֵʱ��ѯ�������߻�Ա
	 * @return
	 */
	@RequestMapping("getOnlineMembers")
	@ResponseBody
	public List<Member> getOnlineMembers(HttpServletRequest request,HttpServletResponse response,Integer page,Integer pageSize,Integer state,@RequestHeader(value="Origin") String Origin){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		List<Member> members=new ArrayList<Member>();
		if(state==null) state=0;
		List<String> list=this.websocketSessionService.getPagination(page, pageSize,state);
		if(null!=list&&list.size()>0){
			for(String m:list){
				Member mem=JSONObject.parseObject(m, Member.class);
				members.add(mem);
			}
			return members;
		}
		return members;
	}
	
	/**
	 * ��ȡ���»�Ա����
	 * @param request
	 * @param response
	 * @param memberid ��Աid
	 * @return
	 */
	@RequestMapping("getNewMemberBillBoard")
	@ResponseBody
	public List<MemberBillboard> getNewMemberBillBoard(HttpServletRequest request,HttpServletResponse response,String token,String memberid,@RequestHeader(value="Origin") String Origin){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		List<MemberBillboard> list=new ArrayList<MemberBillboard>();
		Object tmpid = null;
		if(!StringUtil.isEmpty(memberid)){
			tmpid=memberid;
		}else{
			tmpid = AccountUtil.getMemberid(token);
		}
		if(tmpid==null) return null;
		Form form=new Form();
		form.setPage(1);
		form.setPagesize(1);
		form.setSort("addtime");
		form.setOrder(Form.DESC);
		form.setPrimarykey(Long.valueOf(tmpid.toString()));
		form.init();
		list=this.memberBillboardService.selectByPagination(form);
		return list;
	}
	
	/**
	 * ɾ������ӿ�
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteMemberBillBoard")
	@ResponseBody
	public ResultMessage deleteMemberBillBoard(HttpServletRequest request,HttpServletResponse response,String id,@RequestHeader(value="Origin") String Origin){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		if(StringUtil.isEmpty(id)) ResultMessage.failed("�ṩ��Ҫɾ���Ĺ�����");
		Object memberid=AccountUtil.getMemberid(request);
		if(null==memberid) return ResultMessage.failed("��Ա�˺�δ��¼");
		MemberBillboard billboard= this.memberBillboardService.selectByPrimaryKey(id);
		if(null==billboard) return ResultMessage.failed("�����Ų���ȷ");
		if(!billboard.getMemberid().toString().equals(memberid.toString())) return ResultMessage.failed("�޷�ɾ���ǵ�ǰ��¼��Ա����");
		int result=this.memberBillboardService.deleteByPrimaryKey(id);
		if(result>0) return ResultMessage.defaultSuccessMessage();
		return ResultMessage.defaultFaileMessage();
	}
	
	/**
	 * ��ҳ��ȡǩ��
	 * @param request
	 * @param response
	 * @param memberid ��Աid
	 * @param page ��ǰҳ��
	 * @param pagesize ÿҳ��ʾ����
	 * @return
	 */
	@RequestMapping("getMemberSign")
	@ResponseBody
	public Object getMemberSign(HttpServletRequest request,HttpServletResponse response,String token,String memberid,String type,Integer page,Integer pagesize,@RequestHeader(value="Origin") String Origin){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		List<MemberSign> list=new ArrayList<MemberSign>();
		Object tmpid = null;
		if(!StringUtil.isEmpty(memberid)){
			tmpid=memberid;
		}else{
			tmpid = AccountUtil.getMemberid(token);
		}
		if(null==page) page=1;
		if(null==pagesize) pagesize=1;
		
		Form form=new Form();
		form.setPage(page);
		form.setPagesize(pagesize);
		form.setSort("signtime");
		form.setOrder(Form.DESC);
		form.setState(type);
		form.setPrimarykey(Long.valueOf(tmpid.toString()));
		form.init();
		list=this.memberSignService.selectByPagination(form);
		int total=this.memberSignService.count(form);
		Pagination pagination=new Pagination();
		pagination.setRows(list);
		pagination.setTotal(total);
		return pagination;
	}
	
	/**
	 * ɾ����Աǩ��������
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteMemberSign")
	@ResponseBody
	public ResultMessage deleteMemberSign(HttpServletRequest request,HttpServletResponse response,String id,@RequestHeader(value="Origin") String Origin){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		if(StringUtil.isEmpty(id)) ResultMessage.failed("�ṩ��Ҫɾ����ǩ�������±��");
		Object memberid=AccountUtil.getMemberid(request);
		if(null==memberid) return ResultMessage.failed("��Ա�˺�δ��¼");
		MemberSign object= this.memberSignService.selectByPrimaryKey(id);
		if(null==object) return ResultMessage.failed("ǩ�������±�Ų���ȷ");
		if(!object.getMemberid().toString().equals(memberid.toString())) return ResultMessage.failed("�޷�ɾ���ǵ�ǰ��¼��Աǩ��������");
		int result=this.memberSignService.deleteByPrimaryKey(id);
		if(result>0) return ResultMessage.defaultSuccessMessage();
		return ResultMessage.defaultFaileMessage();
	}
	
	/**
	 * ������ݽṹ
	 * @author hzl
	 *
	 */
	class AlbumList extends MemberAlbum{
		List<MemberAlbumItem> list=new ArrayList<MemberAlbumItem>();

		public List<MemberAlbumItem> getList() {
			return list;
		}

		public void setList(List<MemberAlbumItem> list) {
			this.list = list;
		}
	}
	
	/**
	 * ��Ա����ҳ���ݽӿ�
	 * @param request
	 * @param response
	 * @param memberid
	 * @param page
	 * @param pagesize
	 * @return
	 */
	@RequestMapping("getMemberAlbum")
	@ResponseBody
	public Object getMemberAlbum(HttpServletRequest request,HttpServletResponse response,String token,String memberid,Integer page,Integer pagesize,@RequestHeader(value="Origin") String Origin){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		Object tmpid = null;
		if(!StringUtil.isEmpty(memberid)){
			tmpid=memberid;
		}else{
			tmpid = AccountUtil.getMemberid(token);
		}
		
		if(null==page) page=1;
		if(null==pagesize) pagesize=15;
		Form form=new Form();
		form.setPage(page);
		form.setPagesize(pagesize);
		form.setSort("addtime");
		form.setOrder(Form.DESC);
		form.setPrimarykey(Long.valueOf(tmpid.toString()));
		form.init();
		List<MemberAlbum> albums=this.memberAlbumService.selectByPagination(form);
		int total=this.memberAlbumService.count(form);
		Map<String,AlbumList> map=new LinkedHashMap<String, AlbumList>();
		if(null!=albums&&albums.size()>0){
			List<Object> albumids=new ArrayList<Object>();
			for(MemberAlbum a:albums){
				albumids.add(a.getAlbumid());
				AlbumList li=new AlbumList();
				li.setAddtime(a.getAddtime());
				li.setAlbumid(a.getAlbumid());
				li.setContent(a.getContent());
				li.setMemberid(a.getMemberid());
				map.put(a.getAlbumid(), li);
			}
			List<MemberAlbumItem> items=this.memberAlbumItemService.selectByAlbumids(albumids);
			if(null!=items&&items.size()>0){
				for(MemberAlbumItem it:items){
					map.get(it.getAlbumid()).getList().add(it);
				}
			}
		}
		
		List<AlbumList> list=new ArrayList<AlbumList>();
		if(map.size()>0){
			for(Map.Entry<String,AlbumList> entry:map.entrySet()){
				list.add(entry.getValue());
			}
		}
		Pagination pagination=new Pagination();
		pagination.setRows(list);
		pagination.setTotal(total);
		return pagination;
	}
	
	/**
	 * ɾ����Ա���
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteMemberAlbum")
	@ResponseBody
	public ResultMessage deleteMemberAlbum(HttpServletRequest request,HttpServletResponse response,String id,@RequestHeader(value="Origin") String Origin){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		return this.openApiService.deleteMemberAlbum(request, response, id);
	}
	
	/**
	 * ɾ����Ա��Ƭ
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteMemberAlbumItem")
	@ResponseBody
	public ResultMessage deleteMemberAlbumItem(HttpServletRequest request,HttpServletResponse response,String id,@RequestHeader(value="Origin") String Origin){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		if(StringUtil.isEmpty(id)) ResultMessage.failed("�ṩ��Ҫɾ������Ƭ���");
		Object memberid=AccountUtil.getMemberid(request);
		if(null==memberid) return ResultMessage.failed("��Ա�˺�δ��¼");
		MemberAlbumItem object= this.memberAlbumItemService.selectByPrimaryKey(id);
		if(null==object) return ResultMessage.failed("��Ƭ��Ų���ȷ");
		
		MemberAlbum ma=this.memberAlbumService.selectByPrimaryKey(object.getAlbumid());
		if(null==ma) return ResultMessage.failed("��᲻����");
		
		if(!ma.getMemberid().toString().equals(memberid.toString())) return ResultMessage.failed("�޷�ɾ���ǵ�ǰ��¼��Ա��Ƭ");
		int result=this.memberAlbumItemService.deleteByPrimaryKey(id);
		if(result>0) return ResultMessage.defaultSuccessMessage();
		return ResultMessage.defaultFaileMessage();
	}
	
	/**
	 * ��Ƶ���ݽṹ
	 * @author hzl
	 *
	 */
	class BillboardList extends MemberBillboard{
		List<MemberBillboardItem> list=new ArrayList<MemberBillboardItem>();
		
		public List<MemberBillboardItem> getList() {
			return list;
		}
		
		public void setList(List<MemberBillboardItem> list) {
			this.list = list;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(Arrays.toString("213131<|><|>".split("<\\|>")));
		System.out.println("213131||".split("\\|").length);
	}
	/**
	 * ��Ա��Ƶ��ҳ���ݽӿ�
	 * @param request
	 * @param response
	 * @param memberid
	 * @param page
	 * @param pagesize
	 * @return
	 */
	@RequestMapping("getMemberBillboard")
	@ResponseBody
	public Object getMemberBillboard(HttpServletRequest request,HttpServletResponse response,String token,String memberid,Integer page,Integer pagesize,@RequestHeader(value="Origin") String Origin){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		Object tmpid = null;
		if(!StringUtil.isEmpty(memberid)){
			tmpid=memberid;
		}else{
			tmpid = AccountUtil.getMemberid(token);
		}
		
		if(null==page) page=1;
		if(null==pagesize) pagesize=15;
		Form form=new Form();
		form.setPage(page);
		form.setPagesize(pagesize);
		form.setSort("addtime");
		form.setOrder(Form.DESC);
		form.setPrimarykey(Long.valueOf(tmpid.toString()));
		form.init();
		List<MemberBillboard> billboards = this.memberBillboardService.selectByPagination(form);
		int total=this.memberBillboardService.count(form);
		Map<String,BillboardList> map=new LinkedHashMap<String, BillboardList>();
		if(null!=billboards&&billboards.size()>0){
			List<Object> billboardids=new ArrayList<Object>();
			for(MemberBillboard b:billboards){
				billboardids.add(b.getId());
				BillboardList li=new BillboardList();
				li.setAddtime(b.getAddtime());
				li.setId(b.getId());
				li.setContent(b.getContent());
				li.setMemberid(b.getMemberid());
				map.put(b.getId(), li);
			}
			List<MemberBillboardItem> items=this.memberBillboardItemService.selectByBillboardids(billboardids);
			if(null!=items&&items.size()>0){
				for(MemberBillboardItem it:items){
					map.get(it.getBillboardid()).getList().add(it);
				}
			}
		}
		
		List<BillboardList> list=new ArrayList<BillboardList>();
		if(map.size()>0){
			for(Map.Entry<String,BillboardList> entry:map.entrySet()){
				list.add(entry.getValue());
			}
		}
		Pagination pagination=new Pagination();
		pagination.setRows(list);
		pagination.setTotal(total);
		return pagination;
	}
	
	
	/**
	 * ɾ����Ա��Ƶ
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteMemberBillboardItem")
	@ResponseBody
	public ResultMessage deleteMemberBillboardItem(HttpServletRequest request,HttpServletResponse response,String id,@RequestHeader(value="Origin") String Origin){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		if(StringUtil.isEmpty(id)) ResultMessage.failed("�ṩ��Ҫɾ������Ƶ���");
		Object memberid=AccountUtil.getMemberid(request);
		if(null==memberid) return ResultMessage.failed("��Ա�˺�δ��¼");
		MemberBillboardItem object= this.memberBillboardItemService.selectByPrimaryKey(id);
		if(null==object) return ResultMessage.failed("��Ƶ��Ų���ȷ");
		
		MemberBillboard mb=this.memberBillboardService.selectByPrimaryKey(object.getBillboardid());
		if(null==mb) return ResultMessage.failed("��Ƶ���治����");
		
		if(!mb.getMemberid().toString().equals(memberid.toString())) return ResultMessage.failed("�޷�ɾ���ǵ�ǰ��¼��Ա��Ƭ");
		int result=this.memberBillboardItemService.deleteByPrimaryKey(id);
		if(result>0) return ResultMessage.defaultSuccessMessage();
		return ResultMessage.defaultFaileMessage();
	}
	
	/**
	 * ��ȡ��Ա��Ȥ�������ݽӿ�
	 * @param request
	 * @param response
	 * @param type
	 * @param memberid
	 * @param page
	 * @param pagesize
	 * @return
	 */
	@RequestMapping("getMemberHobby")
	@ResponseBody
	public Object getMemberHobby(HttpServletRequest request,HttpServletResponse response,String type,String token,String memberid,Integer page,Integer pagesize,@RequestHeader(value="Origin") String Origin){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		List<MemberHobby> list=new ArrayList<MemberHobby>();
		Object tmpid = null;
		if(!StringUtil.isEmpty(memberid)){
			tmpid=memberid;
		}else{
			tmpid = AccountUtil.getMemberid(token);
		}
		if(null==page) page=1;
		if(null==pagesize) pagesize=1;
		
		Form form=new Form();
		form.setPage(page);
		form.setPagesize(pagesize);
		form.setSort("addtime");
		form.setOrder(Form.DESC);
		form.setPrimarykey(Long.valueOf(tmpid.toString()));
		form.setState(type);
		form.init();
		list=this.memberHobbyService.selectByPagination(form);
		int total=this.memberHobbyService.count(form);
		Pagination pagination=new Pagination();
		pagination.setRows(list);
		pagination.setTotal(total);
		return pagination;
	}
	
	/**
	 * ��ȡ��Ա�����б�
	 * @param request
	 * @param response
	 * @param token
	 * @param page
	 * @param pagesize
	 * @return
	 */
	@RequestMapping("getMemberOrder")
	@ResponseBody
	public Object getMemberOrder(HttpServletRequest request,HttpServletResponse response,String token,Integer page,Integer pagesize,@RequestHeader(value="Origin") String Origin){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		Object memberid=AccountUtil.getMemberid(request);
		if(null==memberid) return ResultMessage.failed("��Ա�˺�δ��¼");
		
		if(null==page) page=1;
		if(null==pagesize) pagesize=15;
		
		ImForm form=new ImForm();
		form.setMemberid(Long.valueOf(memberid.toString()));
		form.setPage(page);
		form.setPagesize(pagesize);
		form.init();
		List<MemeOrder> list=new ArrayList<MemeOrder>();
		list=this.memeOrderService.selectMemberOrders(form);
		int total=this.memeOrderService.countMemberOrders(form);
		Pagination pagination=new Pagination();
		pagination.setRows(list);
		pagination.setTotal(total);
		return pagination;
	}
	
	/**
	 * ɾ����Ա��Ȥ����
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteMemberHobby")
	@ResponseBody
	public ResultMessage deleteMemberHobby(HttpServletRequest request,HttpServletResponse response,String id,@RequestHeader(value="Origin") String Origin){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		if(StringUtil.isEmpty(id)) ResultMessage.failed("�ṩ��Ҫɾ������Ȥ���ñ��");
		Object memberid=AccountUtil.getMemberid(request);
		if(null==memberid) return ResultMessage.failed("��Ա�˺�δ��¼");
		MemberHobby object= this.memberHobbyService.selectByPrimaryKey(id);
		if(null==object) return ResultMessage.failed("��Ȥ���ñ�Ų���ȷ");
		if(!object.getMemberid().toString().equals(memberid.toString())) return ResultMessage.failed("�޷�ɾ���ǵ�ǰ��¼��Ա��Ȥ����");
		int result=this.memberHobbyService.deleteByPrimaryKey(id);
		if(result>0) return ResultMessage.defaultSuccessMessage();
		return ResultMessage.defaultFaileMessage();
	}
	
	@RequestMapping("test")
	public ModelAndView test(String id){
		MemberHobby obj=this.memberHobbyService.selectByPrimaryKey(id);
		Map<String,Object> model=new HashMap<String, Object>();
		String content=new String(obj.getContent());
		model.put("content", content);
		return new ModelAndView("/member/center/test",model);
	}
	
	/**
	 * ��ȡģ������
	 * @return
	 */
	@RequestMapping("getBgTemplates")
	@ResponseBody
	public List<MemberBgTem> getBgTemplates(){
		return this.memberBgTemService.getBgTemplates();
	}
	
	/**
	 * layer �ؼ���������
	 * @param request
	 * @return
	 */
	@RequestMapping("getLayerPhotos")
	@ResponseBody
	public Object getLayerPhotos(HttpServletRequest request,HttpServletResponse response,String token,String memberid,@RequestHeader(value="Origin") String Origin){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		Object tmpid = null;
		if(!StringUtil.isEmpty(memberid)){
			tmpid=memberid;
		}else{
			tmpid = AccountUtil.getMemberid(token);
		}
		List<DictItem> list=this.dictItemService.selectByDictgroupcode("ARTICLE_TYPE");
		MemberHobbyType record=new MemberHobbyType();
		record.setMemberid(Long.valueOf(tmpid.toString()));
		List<MemberHobbyType> types=this.memberHobbyTypeService.selectByEntity(record);
		if(null!=types&&types.size()>0){
			for(MemberHobbyType t:types){
				if(null!=list&&list.size()>0){
					for(int i=0;i<list.size();i++){
						if(t.getType().toString().equals(list.get(i).getDictitemcode())){
							if(!StringUtil.isEmpty(t.getAlias())) list.get(i).setDictitemname(t.getAlias());
						}
					}
				}
			}
		}
		return list;
	}
	
//	/**
//	 * ɾ����Ƭ�ӿ�
//	 * @param request
//	 * @param response
//	 * @param itemid
//	 * @return
//	 */
//	@RequestMapping("delAlbumItem")
//	@ResponseBody
//	public ResultMessage delAlbumItem(HttpServletRequest request,HttpServletResponse response,String itemid){
//		response.setHeader("Access-Control-Allow-Origin", Origin);
//		response.setHeader("Access-Control-Allow-Credentials", "true");
//		String token=null;
//		token=AccountUtil.getToken(request);
//		Object tmpid = null;
//		if(StringUtil.isEmpty(token)) return new ResultMessage("-1","���¼�ٲ���");
//		tmpid = AccountUtil.getMemberid(token);
//		if(tmpid==null) return new ResultMessage("-1","���¼�ٲ���");
//		Member member=this.memberService.selectByPrimaryKey(Long.valueOf(tmpid.toString()));
//		if(member==null) return new ResultMessage("-1","���¼�ٲ���");
//		MemberAlbumItem item=this.memberAlbumItemService.selectByPrimaryKey(itemid);
//		if(item==null) return ResultMessage.failed("��Ƭ������");
//		MemberAlbum album=this.memberAlbumService.selectByPrimaryKey(item.getAlbumid());
//		if(album==null) return ResultMessage.failed("��᲻����");
//		if(album.getMemberid().longValue()!=member.getMemberid().longValue()) return ResultMessage.failed("�Ǳ�����Ƭ��ֹɾ��");
//		
//		int result=this.memberAlbumItemService.deleteByPrimaryKey(itemid);
//		if(result>0) return ResultMessage.success("�ɹ�ɾ����Ƭ");
//		return ResultMessage.failed("ɾ����Ƭʧ��");
//	}
	
	@RequestMapping("getMemberHobbyGroup")
	@ResponseBody
	public Object getMemberHobbyGroup(HttpServletRequest request,HttpServletResponse response,String token,String memberid,@RequestHeader(value="Origin") String Origin){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		Object tmpid = null;
		if(!StringUtil.isEmpty(memberid)){
			tmpid=memberid;
		}else{
			tmpid = AccountUtil.getMemberid(token);
		}
		List<DictItem> list=this.dictItemService.selectByDictgroupcode("ARTICLE_TYPE");
		MemberHobbyType record=new MemberHobbyType();
		record.setMemberid(Long.valueOf(tmpid.toString()));
		List<MemberHobbyType> types=this.memberHobbyTypeService.selectByEntity(record);
		if(null!=types&&types.size()>0){
			for(MemberHobbyType t:types){
				if(null!=list&&list.size()>0){
					for(int i=0;i<list.size();i++){
						if(t.getType().toString().equals(list.get(i).getDictitemcode())){
							if(!StringUtil.isEmpty(t.getAlias())) list.get(i).setDictitemname(t.getAlias());
						}
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * ��ȡ��Ŀ����
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping("getMemeColumn")
	@ResponseBody
	public ResultMessage getMemeColumn(HttpServletRequest request,HttpServletResponse response,@RequestHeader(value="Origin") String Origin) throws IllegalAccessException, InvocationTargetException{
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		List<MemeColumnTreeGrid> nodes=new ArrayList<MemeColumnTreeGrid>();
		List<MemeColumn> list=this.memeColumnService.selectAllColumns();
		if(null!=list&&list.size()>0){
			List<MemeColumnTreeGrid> tmp=this.getColumnTreeNodes(list);
			if(null!=tmp&&tmp.size()>0) nodes.addAll(tmp);
		}
		return ResultMessage.success(ResponseMessage.SUCCESS,nodes);
	}
	public List<MemeColumnTreeGrid> getColumnTreeNodes(List<MemeColumn> list) throws IllegalAccessException, InvocationTargetException{
		List<MemeColumnTreeGrid> nodes=new ArrayList<MemeColumnTreeGrid>();
		if(null!=list&&list.size()>0){
			for(MemeColumn obj:list){
				if(obj.getPid().longValue()==0l){
					MemeColumnTreeGrid node=new MemeColumnTreeGrid();
					BeanUtils.copyProperties(node, obj);
					DictItemView state=DictCache.getDictItem("SF", node.getState().toString());
					if(null!=state){
						node.setStateText(state.getDictitemname());
					}
					if(StringUtil.isAllNotEmpty(node.getTag())){
						DictItemView tag=DictCache.getDictItem("TAG_LIST", node.getTag());
						if(null!=tag) node.setTagText(tag.getDictitemname());
					}
					nodes.add(node);
				}else{
					recursionColumnTreeNode(nodes, obj);
				}
			}
		}
		return nodes;
	}
	public static List<MemeColumnTreeGrid> recursionColumnTreeNode(List<MemeColumnTreeGrid> list,MemeColumn obj) throws IllegalAccessException, InvocationTargetException{
		for(int i=0;i<list.size();i++){
			MemeColumnTreeGrid node=list.get(i);
			if(obj.getPid().longValue()==node.getId().longValue()){
				MemeColumnTreeGrid tn=new MemeColumnTreeGrid();
				BeanUtils.copyProperties(tn, obj);
				
				DictItemView state=DictCache.getDictItem("SF", tn.getState().toString());
				if(null!=state){
					tn.setStateText(state.getDictitemname());
				}
				if(StringUtil.isAllNotEmpty(tn.getTag())){
					DictItemView tag=DictCache.getDictItem("TAG_LIST", tn.getTag());
					if(null!=tag) tn.setTagText(tag.getDictitemname());
				}
				
				boolean flag=false;
				if(null!=node.getChildren()&&node.getChildren().size()>0){
					for(MemeColumnTreeGrid t:node.getChildren()){
						if(t.getId().toString().equals(obj.getId().toString())) {flag=true;break;}
					}
				}
				if(!flag){
					list.get(i).getChildren().add(tn);
				}
				break;
			}else{
				recursionColumnTreeNode(list.get(i).getChildren(), obj);
			}
		}
		return list;
	}
	
	/**
	 * ��ȡ��Ŀ������
	 * @param request
	 * @param response
	 * @param columnid
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping("getMemeColumnSection")
	@ResponseBody
	public ResultMessage getMemeColumnSection(HttpServletRequest request,HttpServletResponse response,Long columnid,String columncode) throws IllegalAccessException, InvocationTargetException{
		if((null==columnid || columnid==0l) && StringUtil.isEmpty(columncode)) return ResultMessage.failed("������Ŀ��Ż��߱�����߱���һ��");
		MemeColumn obj=null;
		if(null!=columnid&&columnid!=0l){
			obj=this.memeColumnService.selectByPrimaryKey(columnid);
		}else{
			List<MemeColumn> list=this.memeColumnService.selectByCode(columncode);
			if(null!=list&&list.size()>1) return ResultMessage.failed("������Ŀ��������ظ�������ϵ����Ա����");
			obj=list.get(0);
		}
		if(null == obj) return ResultMessage.failed("�޷����Ҵ���Ŀ�ķֿ�����");
		
		List<MemeColumnSectionTreeGrid> nodes=new ArrayList<MemeColumnSectionTreeGrid>();
		List<MemeColumnSectionView> list=this.memeColumnSectionService.selectColumnSections(obj.getId());
		if(null!=list&&list.size()>0){
			List<MemeColumnSectionTreeGrid> tmp=this.getColumnSectionTreeNodes(list);
			if(null!=tmp&&tmp.size()>0) nodes.addAll(tmp);
		}
		return ResultMessage.success(ResponseMessage.SUCCESS,nodes);
	}
	public List<MemeColumnSectionTreeGrid> getColumnSectionTreeNodes(List<MemeColumnSectionView> list) throws IllegalAccessException, InvocationTargetException{
		List<MemeColumnSectionTreeGrid> nodes=new ArrayList<MemeColumnSectionTreeGrid>();
		if(null!=list&&list.size()>0){
			for(MemeColumnSectionView obj:list){
				if(obj.getPid().longValue()==0l){
					MemeColumnSectionTreeGrid node=new MemeColumnSectionTreeGrid();
					BeanUtils.copyProperties(node, obj);
					DictItemView ispagination=DictCache.getDictItem("SF", node.getIspagination().toString());
					if(null!=ispagination){
						node.setIspaginationText(ispagination.getDictitemname());
					}
					if(StringUtil.isAllNotEmpty(node.getTag())){
						DictItemView tag=DictCache.getDictItem("TAG_LIST", node.getTag());
						if(null!=tag) node.setTagText(tag.getDictitemname());
					}
					nodes.add(node);
				}else{
					recursionColumnSectionTreeNode(nodes, obj);
				}
			}
		}
		return nodes;
	}
	public static List<MemeColumnSectionTreeGrid> recursionColumnSectionTreeNode(List<MemeColumnSectionTreeGrid> list,MemeColumnSectionView obj) throws IllegalAccessException, InvocationTargetException{
		for(int i=0;i<list.size();i++){
			MemeColumnSectionTreeGrid node=list.get(i);
			if(obj.getPid().longValue()==node.getId().longValue()){
				MemeColumnSectionTreeGrid tn=new MemeColumnSectionTreeGrid();
				BeanUtils.copyProperties(tn, obj);
				
				DictItemView ispagination=DictCache.getDictItem("SF", tn.getIspagination().toString());
				if(null!=ispagination){
					tn.setIspaginationText(ispagination.getDictitemname());
				}
				if(StringUtil.isAllNotEmpty(tn.getTag())){
					DictItemView tag=DictCache.getDictItem("TAG_LIST", tn.getTag());
					if(null!=tag) tn.setTagText(tag.getDictitemname());
				}
				
				boolean flag=false;
				if(null!=node.getChildren()&&node.getChildren().size()>0){
					for(MemeColumnSectionTreeGrid t:node.getChildren()){
						if(t.getId().toString().equals(obj.getId().toString())) {flag=true;break;}
					}
				}
				if(!flag){
					list.get(i).getChildren().add(tn);
				}
				break;
			}else{
				recursionColumnSectionTreeNode(list.get(i).getChildren(), obj);
			}
		}
		return list;
	}
	
	/**
	 * ��ȡ��Ŀ��������
	 * @param request
	 * @param response
	 * @param columnid
	 * @param columncode
	 * @param pid
	 * @return
	 */
	@RequestMapping("getMemeCategory")
	@ResponseBody
	public ResultMessage getMemeCategory(HttpServletRequest request,HttpServletResponse response,Long columnid,String columncode,Long pid){
		if((null==columnid || columnid==0l) && StringUtil.isEmpty(columncode)) return ResultMessage.failed("������Ŀ��Ż��߱�����߱���һ��");
		MemeColumn obj=null;
		if(null!=columnid&&columnid!=0l){
			obj=this.memeColumnService.selectByPrimaryKey(columnid);
		}else{
			List<MemeColumn> list=this.memeColumnService.selectByCode(columncode);
			if(null!=list&&list.size()>1) return ResultMessage.failed("������Ŀ��������ظ�������ϵ����Ա����");
			obj=list.get(0);
		}
		if(null == obj) return ResultMessage.failed("�޷����Ҵ���Ŀ�ķ�������");
		if(pid==null) pid=0l;
		
		List<MemeCategory> list=this.memeCategoryService.selectSubCats(obj.getId(), pid);
		return ResultMessage.success(ResponseMessage.SUCCESS,list);
	}
	
	/**
	 * ��ȡ������������
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping("getMemeContent")
	@ResponseBody
	public ResultMessage getMemeContent(HttpServletRequest request,HttpServletResponse response,ImForm form,@RequestHeader(value="Origin") String Origin){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		
		if(null==form.getPage()) form.setPage(1);
		if(null==form.getPagesize()) form.setPagesize(9);
		
		if((null==form.getColumnid() || form.getColumnid()==0l) && StringUtil.isEmpty(form.getColumncode())) return ResultMessage.failed("������Ŀ��Ż��߱�����߱���һ��");
		if((null==form.getCatid()|| form.getCatid()==0l) && StringUtil.isEmpty(form.getSectioncode())) return ResultMessage.failed("������Ŀ�����Ż�����Ŀ�������߱���һ��");
		
		MemeColumn col=null;
		MemeCategory cat=null;
		if(null!=form.getColumnid()&&form.getColumnid()!=0l){
			col=this.memeColumnService.selectByPrimaryKey(form.getColumnid());
		}else if(!StringUtil.isEmpty(form.getColumncode())){
			List<MemeColumn> list=this.memeColumnService.selectByCode(form.getColumncode());
			if(null!=list&&list.size()>1) return ResultMessage.failed("������Ŀ��������ظ�������ϵ����Ա����");
			if(list.size()==1) col=list.get(0);
		}
		if(null != col){
			form.setColumnid(col.getId());
		}
		
		if(null != form.getCatid() && form.getCatid() != 0l){
			cat=this.memeCategoryService.selectByPrimaryKey(form.getCatid());
		}
//		if(null == cat) return ResultMessage.failed("������Ŀ���಻����");
		if(null != cat) form.setCatid(cat.getId());
		
		form.setSort("con.addtime,con.sortno");
		form.setOrder(Form.DESC);
		form.init();
		List<Map<String, Object>> list=this.memeContentService.selectContentPagination(form);
		Integer count=this.memeContentService.countContentPagination(form);
		if(null ==count) count=0;
		
		Pagination pagination=new Pagination(list,count);
		return ResultMessage.success(ResponseMessage.SUCCESS,pagination);
	}
	
	/**
	 * ���Ͳ���
	 * @param request
	 * @param response
	 * @param id
	 * @param state
	 * @return
	 */
	@RequestMapping("contentVote")
	@ResponseBody
	public ResultMessage contentVote(HttpServletRequest request,HttpServletResponse response,Long id,Integer state,@RequestHeader(value="Origin") String Origin){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		return this.openApiService.contentVote(request, response, id, state);
	}
	
	class TvStationEntity{
		private DictItem tvtype;
		private List<TvStation> list=new ArrayList<TvStation>();
		public DictItem getTvtype() {
			return tvtype;
		}
		public void setTvtype(DictItem tvtype) {
			this.tvtype = tvtype;
		}
		public List<TvStation> getList() {
			return list;
		}
		public void setList(List<TvStation> list) {
			this.list = list;
		}
	}
	
	@RequestMapping("getTvStation")
	@ResponseBody
	public ResultMessage getTvStation(HttpServletRequest request,HttpServletResponse response,@RequestHeader(value="Origin") String Origin){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		List<DictItem> list=this.dictItemService.selectByDictgroupcode("TV_TYPE");
		if(null==list||list.size()==0) return ResultMessage.failed("Ƶ�������쳣");
		Map<String, TvStationEntity> map=new HashMap<String, TvStationEntity>();
		for(DictItem item:list){
			TvStationEntity entity=new TvStationEntity();
			entity.setTvtype(item);
			map.put(item.getDictitemcode(), entity);
		}
		List<TvStation> stations=this.tvStationService.selectAll();
		if(null==stations || stations.size() == 0) return ResultMessage.failed("��Ƶ������");
		for(TvStation station:stations){
			map.get(station.getTvtype().toString()).getList().add(station);
		}
		return ResultMessage.success("", map.values());
	}
	
	@RequestMapping("getTvProgram")
	@ResponseBody
	public ResultMessage getTvProgram(HttpServletRequest request,HttpServletResponse response,String type,@RequestHeader(value="Origin") String Origin){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		return this.tvProgramService.getTvProgram(request, response, type);
	}
	
	@RequestMapping("getFreightTemplate")
	@ResponseBody
	public ResultMessage getFreightTemplate(HttpServletRequest request,HttpServletResponse response,Integer type,String district,@RequestHeader(value="Origin") String Origin){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		MemeFreightTemplate memeFreightTemplate = new MemeFreightTemplate();
		memeFreightTemplate.setType(type);
		memeFreightTemplate.setDistrict(district);
		List<MemeFreightTemplate> list = memeFreightTemplateService.selectByEntity(memeFreightTemplate);
		if(list != null && list.size() > 0) {
			return ResultMessage.success("�����ɹ�", list.get(0));
		}else {
			return ResultMessage.failed("û�в鵽�˷�ģ������");
		}
		
	}
}
