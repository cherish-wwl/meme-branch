package com.meme.im.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.meme.core.base.BaseController;
import com.meme.core.cache.DictCache;
import com.meme.core.easyui.Pagination;
import com.meme.core.http.ResultMessage;
import com.meme.core.log.LogType;
import com.meme.core.log.annotation.SysLog;
import com.meme.im.form.ImForm;
import com.meme.im.service.ImMessageService;

@Controller
@RequestMapping("/im/message/")
public class ImMessageController extends BaseController {

	@Resource
	private ImMessageService imMessageService;

	@RequestMapping("index")
	public ModelAndView index() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("/im/message/list", model);
	}

	@RequestMapping("list")
	@ResponseBody
	public Pagination list(HttpServletRequest request, ImForm form) {
		List<Map<String, Object>> list = this.imMessageService.selectByPaginationView(form);
		int count = this.imMessageService.countView(form);
		if (null != list && list.size() > 0) {
			for(int i=0;i<list.size();i++){
				Object state=list.get(i).get("state");
				Object msgtype=list.get(i).get("msgtype");
				Object send_mtype=list.get(i).get("send_mtype");
				Object accept_mtype=list.get(i).get("accept_mtype");
				if(null!=state) list.get(i).put("state", DictCache.getDictItem("MESSAGE_STATE", state.toString()).getDictitemname());
				if(null!=msgtype) list.get(i).put("msgtype", DictCache.getDictItem("MESSAGE_TYPE", msgtype.toString()).getDictitemname());
				if(null!=send_mtype) list.get(i).put("send_mtype", DictCache.getDictItem("MEMBER_ACCOUNT_TYPE", send_mtype.toString()).getDictitemname());
				if(null!=accept_mtype) list.get(i).put("accept_mtype", DictCache.getDictItem("MEMBER_ACCOUNT_TYPE", accept_mtype.toString()).getDictitemname());
			}
		}
		Pagination pagination = new Pagination();
		pagination.setRows(list);
		pagination.setTotal(count);
		return pagination;
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	@SysLog(event = "删除聊天记录",type=LogType.DELETE)
	@Transactional
	public ResultMessage delete(@RequestParam("ids[]") List<Object> ids){
        return this.imMessageService.delete(ids);
	}
}
