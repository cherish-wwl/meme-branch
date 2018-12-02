package com.meme.system.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.meme.core.base.BaseController;
import com.meme.core.base.IController;
import com.meme.core.cache.DictCache;
import com.meme.core.easyui.Pagination;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.log.LogType;
import com.meme.core.log.annotation.SysLog;
import com.meme.core.mybatis.EntityColumn;
import com.meme.core.pojo.DictItemView;
import com.meme.core.pojo.Params;
import com.meme.core.pojo.ParamsView;
import com.meme.core.service.ParamsService;
import com.meme.core.util.StringUtil;
import com.meme.core.validator.Handler;
import com.meme.core.validator.ValidateBuilder;
import com.meme.core.validator.entity.ColumnRule;

@Controller("SystemParamsController")
@RequestMapping("/system/params/")
public class ParamsController extends BaseController implements IController{

	@Resource private ParamsService paramsService;

	/**
	 * 参数表验证
	 */
	@RequestMapping(value="loadValidationRules")
	@ResponseBody
	@Override
	public String loadValidationRules() {
		ValidateBuilder.clear();
		
		ColumnRule rule=new ColumnRule();
		rule.setColumn("prec");
		Map<String,String> ruleMap=new LinkedHashMap<String, String>();
		ruleMap.put("positive", "true");
		rule.setRules(ruleMap);
		ValidateBuilder.addRules(rule);
		
		rule=new ColumnRule();
		rule.setColumn("scale");
		ruleMap=new LinkedHashMap<String, String>();
		ruleMap.put("positive", "true");
		rule.setRules(ruleMap);
		ValidateBuilder.addRules(rule);
		
		String js=ValidateBuilder.createJsValidateRules(Params.class);
		return js;
	}
	
	/**
	 * 变量值验证
	 * @param type
	 * @return
	 */
	@RequestMapping(value="loadColumnRules")
	@ResponseBody
	public String loadColumnRules(String type){
		ValidateBuilder.clear();
		Collection<DictItemView> items=DictCache.getDictItemList("DICTGROUP_TYPE").values();
		if(type==null){
			if(items.size()>0){
				type=items.iterator().next().getDictitemcode();
			}
		}
		List<EntityColumn> columns=new ArrayList<EntityColumn>();
		List<Params> list=this.paramsService.selectAllParams(type);
		if(null!=list&&list.size()>0){
			for(Params p:list){
				Class<?> clazz=Handler.parseType(p.getVartype());
				//解析java类型为空则不验证此变量
				if(null==clazz) continue;
				EntityColumn co=new EntityColumn();
				co.setColumn(p.getName());
				co.setDefaultValue(p.getDefval());
				co.setJavaType(clazz);
				if(p.getNullable()==0) co.setNullable(true);
				else co.setNullable(false);
				co.setPrecision(p.getPrec());
				co.setScale(p.getScale());
				
				columns.add(co);
				
				//添加附加验证规则
				if(!StringUtil.isEmpty(p.getValidate())){
					ColumnRule rule=new ColumnRule();
					rule.setColumn(p.getName());
					
					Map<String,String> ruleMap=new LinkedHashMap<String, String>();
					JSONObject validate=JSONObject.parseObject(p.getValidate());
					Set<String> keys=validate.keySet();
					Iterator<String> it=keys.iterator();
					while(it.hasNext()){
						String key=it.next();
						ruleMap.put(key, validate.get(key).toString());
					}
					rule.setRules(ruleMap);
					ValidateBuilder.addRules(rule);
				}
			}
		}
		String js=ValidateBuilder.createJsValidateRules(columns);
		return js;
	}
	
	@RequestMapping("index")
	public ModelAndView index(String type){
		Collection<DictItemView> items=DictCache.getDictItemList("DICTGROUP_TYPE").values();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("items", items);
		if(type==null){
			if(items.size()>0){
				type=items.iterator().next().getDictitemcode();
			}
		}
		List<Params> list=this.paramsService.selectAllParams(type);
		model.put("list", list);
		model.put("type", type);
		return new ModelAndView("/system/params/index",model);
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Pagination list(HttpServletRequest request,@ModelAttribute("searchForm") Form form) throws Exception{
		int count=this.paramsService.count(form);
		List<Params> list=this.paramsService.selectByPagination(form);
		List<ParamsView> objs=new ArrayList<ParamsView>();
		if(null!=list&&list.size()>0) {
			for(Params obj:list){
				ParamsView v=JSONObject.parseObject(JSONObject.toJSONString(obj), ParamsView.class);
				if(StringUtil.isAllNotEmpty(obj.getType())) v.setTypeString(DictCache.getDictItem("DICTGROUP_TYPE", v.getType()).getDictitemname());
				objs.add(v);
			}
		}
		
		Pagination pagination=new Pagination();
		pagination.setRows(objs);
		pagination.setTotal(count);
		return pagination;
	}
	
	@RequestMapping("addView")
	public ModelAndView addView(){
		Map<String, Object> model = new HashMap<String, Object>();
		Collection<DictItemView> items=DictCache.getDictItemList("DICTGROUP_TYPE").values();
		model.put("items", items);
		return new ModelAndView("/system/params/add",model);
	}
	
	@RequestMapping("editView")
	public ModelAndView editView(Long id) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Params obj=null;
		if(id!=null&&id!=0L){
			obj=this.paramsService.selectByPrimaryKey(id);
		}
		resultMap.put("object", obj);
		return new ModelAndView("/system/params/edit",resultMap);
	}
	
	/**
	 * 增加
	 * @param request
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("add")
	@ResponseBody
	@SysLog(event = "新增配置参数",type=LogType.ADD)
	public ResultMessage add(HttpServletRequest request,Params params){
		return this.paramsService.add(request, params);
	}
	
	/**
	 * 修改
	 * @param request
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@ResponseBody
	@SysLog(event = "修改配置参数",type=LogType.UPDATE)
	public ResultMessage edit(HttpServletRequest request,Params params) throws Exception{
		return this.paramsService.edit(request, params);
	}
	
	@RequestMapping("batchupdate")
	@ResponseBody
	@SysLog(event = "批量修改配置参数",type=LogType.UPDATE)
	public ResultMessage batchupdate(HttpServletRequest request){
		return this.paramsService.batchupdate(request);
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	@SysLog(event = "删除配置参数",type=LogType.DELETE)
	public ResultMessage delete(@RequestParam("ids[]") List<Object> ids){
        return this.paramsService.delete(ids);
	}
	
	@RequestMapping("clearRules")
	@ResponseBody
	@SysLog(event = "清空验证规则",type=LogType.DELETE)
	public ResultMessage clearRules(){
        return this.paramsService.clearRules();
	}
}
