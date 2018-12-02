package com.meme.core.validator;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.meme.core.mybatis.EntityColumn;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.pojo.ValidateRules;
import com.meme.core.service.ValidateRulesService;
import com.meme.core.util.IDGenerator;
import com.meme.core.util.StringUtil;
import com.meme.core.validator.entity.ColumnRule;

@Component
public class ValidateBuilder {

	static Map<String, ColumnRule> additionalRules = new LinkedHashMap<String, ColumnRule>();
	@Resource private ValidateRulesService validateRulesService;
	private static ValidateBuilder builder;
	
	@PostConstruct
	public void init(){
		builder=this;
		builder.validateRulesService=this.validateRulesService;
	}
	
	public static String columnRules(List<EntityColumn> list){
		StringBuffer tableRules=new StringBuffer();
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				ColumnRule rule = Handler.handleRequest(list.get(i));
				
				// 当前字段有附加验证规则时添加附加验证规则
				if (additionalRules.size() > 0) {
					ColumnRule additionalRule = additionalRules.get(rule.getColumn());
					if (null != additionalRule) {
						if (null != additionalRule.getRules()&& additionalRule.getRules().size() > 0) {
							rule.getRules().putAll(additionalRule.getRules());
						}
						// 从附加验证规则容器中移除添加到已有字段的验证规则，避免后续重复添加
						additionalRules.remove(rule.getColumn());
					}
				}

				if (i == 0)
					tableRules.append(rule.toString());
				else
					tableRules.append(",").append(rule.toString());
			}
			// 附加字段验证规则添加
			if (null != additionalRules && additionalRules.size() > 0) {
				for (Map.Entry<String, ColumnRule> entry : additionalRules.entrySet()) {
					if(!StringUtil.isEmpty(tableRules.toString())) tableRules.append(",");
					tableRules.append(entry.getValue().toString());
				}
			}
		}
		return tableRules.toString();
	}
	
	public static String createJsValidateRules(List<EntityColumn> list){
		StringBuffer js = new StringBuffer();
		js.append("var rulesSettings={");
		js.append(columnRules(list));
		js.append("}");
		return js.toString();
	}

	/**
	 * 生成jquery validate插件表单验证规则
	 * 
	 * @param list
	 * @return
	 */
	public static String createJsValidateRules(Class<?> clazz) {
		StringBuffer js = new StringBuffer();
		js.append("var rulesSettings={");

		String tableName=MapperHelper.getTableName(clazz);
		ValidateRules validateRules=builder.validateRulesService.selectLastRulesByTableName(tableName);
		StringBuffer tableRules=new StringBuffer();
		if(null!=validateRules){
			js.append(validateRules.getRules());
		}else{
			List<EntityColumn> list = MapperHelper.getTableColumnsWithBlobs(clazz);
			String str=columnRules(list);
			tableRules.append(str);
			
			ValidateRules vr=new ValidateRules();
			long id=IDGenerator.generateId();
			vr.setId(id);
			vr.setTablename(tableName);
			vr.setRules(str);
			vr.setAddtime(new Date());
			builder.validateRulesService.insertSelective(vr);
		}
		js.append(tableRules.toString());
		js.append("}");
		return js.toString();
	}

	/**
	 * 清除附加验证规则
	 */
	public static void clear() {
		additionalRules.clear();
	}

	/**
	 * 添加附加验证规则
	 * 
	 * @param rule
	 */
	public static void addRules(ColumnRule rule) {
		additionalRules.put(rule.getColumn(), rule);
	}

	public static Map<String, ColumnRule> getAdditionalRules() {
		return additionalRules;
	}
}
