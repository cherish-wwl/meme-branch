package com.meme.im.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.meme.core.base.BaseController;
import com.meme.core.easyui.Pagination;
import com.meme.core.http.ResultMessage;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.MemeWbOrderSettlement;
import com.meme.im.service.MemeWbOrderService;
import com.meme.im.service.MemeWbOrderSettlementService;

@Controller
@RequestMapping("/im/wbordersettlement/")
public class MemeWbOrderSettlementController extends BaseController {
	
	@Resource private MemeWbOrderSettlementService memeWbOrderSettlementService;

	@RequestMapping("index")
	public ModelAndView index(){
		return new ModelAndView("/im/wbordersettlement/list");
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Pagination list(ImForm form){
		List<Map<String,Object>> list=this.memeWbOrderSettlementService.selectByPaginationView(form);
		int count=this.memeWbOrderSettlementService.countView(form);
		Pagination pagination=new Pagination(list,count);
		return pagination;
	}
	
	@RequestMapping("wbOrderQuery")
	public ModelAndView wbOrderQuery(Long memberid,Integer state){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("memberid", memberid);
		model.put("state", state);
		return new ModelAndView("/im/wbordersettlement/wbOrder"+state,model);
	}
	
	@RequestMapping("doWbOrderQuery")
	@ResponseBody
	public List<Map<String,Object>> doWbOrderQuery(Long memberid,Integer state){
		return memeWbOrderSettlementService.doWbOrderQuery(memberid,state);
	}
	
	@RequestMapping("updateState")
	@ResponseBody
	public ResultMessage updateState(String id,Integer state){
		MemeWbOrderSettlement memeWbOrderSettlement = new MemeWbOrderSettlement();
		memeWbOrderSettlement.setId(id);
		memeWbOrderSettlement.setSettlement_state(state);
		memeWbOrderSettlement.setSettlement_time(new Date());
		this.memeWbOrderSettlementService.updateByPrimaryKeySelective(memeWbOrderSettlement);
		return ResultMessage.defaultSuccessMessage();
	}
}