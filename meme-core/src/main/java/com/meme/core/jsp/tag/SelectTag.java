package com.meme.core.jsp.tag;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.alibaba.fastjson.JSONObject;
import com.meme.core.cache.DictCache;
import com.meme.core.pojo.DictItemView;
import com.meme.core.util.StringUtil;

/**
 * 自定义表单标签，单选按钮，多选按钮，下拉菜单
 * @author hzl
 *
 */
public class SelectTag extends SimpleTagSupport{

	private String id;
	/**name不设置时取id的值*/
	private String name;
	/**type值有三种：radio,checkbox,select*/
	private String type;
	/** class属性**/
	private String cssClass;
	/** style属性**/
	private String cssStyle;
	/** 选中值，checkbox时可为多个值，可使用List或者基本数据类型**/
	private Object selected;
	/** 字典组编码 **/
	private String dictGroupCode;
	/** 点击事件**/
	private String onclick;
	/** 改变事件**/
	private String onchange;
	/**事件堆栈，点击和改变事件在此设置失效，严格要求使用json格式字符串,{'onclick':'','onblur':''}**/
	private String events;
	/** 是否禁用**/
	private boolean disabled=false;
	/** 是否显示默认下拉项，默认显示第一项为“请选择”，defaultOption有值时显示其值**/
	private boolean isShowDefault=false;
	/**默认下拉项，有值时无需设置isShowDefault为true都显示**/
	private String defaultOption;
	private String attributes;
	
	@SuppressWarnings("unchecked")
	@Override
	public void doTag(){
		StringBuffer tag=new StringBuffer();
		ConcurrentHashMap<String,DictItemView> map=DictCache.getDictItemList(dictGroupCode);
//		if(null==map||map.size()==0){
//			try {
//				throw new Exception("缓存数据异常，请重新加载缓存！");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		JSONObject eventObj=null;
		JSONObject attrObj=null;
		if(!StringUtil.isEmpty(this.getEvents())){
			try {
				eventObj=JSONObject.parseObject(this.getEvents());
			} catch (Exception e) {
				try {
					throw new Exception("事件堆栈字符串格式不正确，请按照json格式填写！");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		if(!StringUtil.isEmpty(this.getAttributes())){
			try {
				attrObj=JSONObject.parseObject(this.getAttributes());
			} catch (Exception e) {
				try {
					throw new Exception("附加属性字符串格式不正确，请按照json格式填写！");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		if(this.getType().toLowerCase().equals("radio")||this.getType().toLowerCase().equals("checkbox")){
			if(map!=null&&map.size()>0){
				for(Map.Entry<String, DictItemView> entry:map.entrySet()){
					tag.append("<input type='").append(this.getType()).append("'");
					if(this.isDisabled()){
						tag.append(" disabled='disabled'");
					}
					tag.append(" style='").append(this.getCssStyle()).append("'");
					tag.append(" class='").append(this.getCssClass()).append("'");
					tag.append(" id='").append(this.getId()).append("'");
					tag.append(" name='").append(this.getName()).append("'");
					tag.append(" onclick='").append(this.getOnclick()).append("'");
					tag.append(" onchange='").append(this.getOnchange()).append("'");
					//添加事件组
					if(null!=eventObj&&eventObj.size()>0){
						Iterator<String> keys=eventObj.keySet().iterator();
						while(keys.hasNext()){
							String key=keys.next();
							String value=eventObj.getString(key);
							//在事件堆栈属性设置onclick和onchange事件失效
							if(key.toLowerCase().equals("onclick")||key.toLowerCase().equals("onchange")) continue;
							
							if(!StringUtil.isEmpty(value)) tag.append(" "+key+"='").append(value).append("'");
						}
						
					}
					if(null!=attrObj&&attrObj.size()>0){
						Iterator<String> keys=attrObj.keySet().iterator();
						while(keys.hasNext()){
							String key=keys.next();
							String value=attrObj.getString(key);
							
							if(!StringUtil.isEmpty(value)) tag.append(" "+key+"='").append(value).append("'");
						}
					}
					if(null!=this.getSelected()){
						if(this.getSelected() instanceof List){
							List<Object> values=(List<Object>)this.getSelected();
							if(values.size()>0){
								for(Object value:values){
									if(String.valueOf(entry.getValue().getDictitemcode()).equals(String.valueOf(value))){
										tag.append(" checked ");
										break;
									}
								}
							}
						}else{
							if(String.valueOf(entry.getValue().getDictitemcode()).equals(String.valueOf(this.getSelected()))){
								tag.append(" checked ");
							}
						}
					}
					tag.append(" value='").append(StringUtil.isEmpty(entry.getValue().getDictitemcode())?"":entry.getValue().getDictitemcode()).append("'");
					tag.append("/>");
					tag.append(entry.getValue().getDictitemname());
				}
			}
		}else if(this.getType().toLowerCase().equals("select")){
			tag.append("<select");
			if(this.isDisabled()){
				tag.append(" disabled='disabled'");
			}
			tag.append(" style='").append(this.getCssStyle()).append("'");
			tag.append(" class='").append(this.getCssClass()).append("'");
			tag.append(" id='").append(this.getId()).append("'");
			tag.append(" name='").append(this.getName()).append("'");
			tag.append(" onclick='").append(this.getOnclick()).append("'");
			tag.append(" onchange='").append(this.getOnchange()).append("'");
			//添加事件组
			if(null!=eventObj&&eventObj.size()>0){
				Iterator<String> keys=eventObj.keySet().iterator();
				while(keys.hasNext()){
					String key=keys.next();
					String value=eventObj.getString(key);
					
					//在事件堆栈属性设置onclick和onchange事件失效
					if(key.toLowerCase().equals("onclick")||key.toLowerCase().equals("onchange")) continue;
					
					if(!StringUtil.isEmpty(value)) tag.append(" "+key+"='").append(value).append("'");
				}
			}
			if(null!=attrObj&&attrObj.size()>0){
				Iterator<String> keys=attrObj.keySet().iterator();
				while(keys.hasNext()){
					String key=keys.next();
					String value=attrObj.getString(key);
					
					if(!StringUtil.isEmpty(value)) tag.append(" "+key+"='").append(value).append("'");
				}
			}
			tag.append(">");
			if(this.isShowDefault()){
				if(StringUtil.isAllEmpty(this.getDefaultOption())) {
					tag.append("<option value=''>请选择</option>");
				}else{
					tag.append("<option value=''>"+this.getDefaultOption()+"</option>");
				}
			}else{
				if(StringUtil.isAllNotEmpty(this.getDefaultOption())){
					tag.append("<option value=''>"+this.getDefaultOption()+"</option>");
				}
			}
			if(map!=null&&map.size()>0){
				for(Map.Entry<String, DictItemView> entry:map.entrySet()){
					tag.append("<option value='").append(StringUtil.isEmpty(entry.getValue().getDictitemcode())?"":entry.getValue().getDictitemcode()).append("'");
					if(null!=this.getSelected()){
						if(this.getSelected() instanceof List){
							List<Object> values=(List<Object>)this.getSelected();
							if(values.size()>0){
								for(Object value:values){
									if(String.valueOf(entry.getValue().getDictitemcode()).equals(String.valueOf(value))){
										tag.append(" selected='true' ");
										break;
									}
								}
							}
						}else{
							if(String.valueOf(entry.getValue().getDictitemcode()).equals(String.valueOf(this.getSelected()))){
								tag.append(" selected='true' ");
							}
						}
					}
					tag.append(">");
					tag.append(entry.getValue().getDictitemname());
					tag.append("</option>");
				}
			}
			tag.append("</select>");
		}
		try {
			this.getJspContext().getOut().write(tag.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return StringUtil.isEmpty(this.name)?this.id:this.name;
	}
	public void setName(String name) {
		this.name=StringUtil.isEmpty(name)?this.id:name;
	}
	public String getCssClass() {
		return StringUtil.isEmpty(this.cssClass)?"":this.cssClass;
	}
	public void setCssClass(String cssClass) {
		this.cssClass = StringUtil.isEmpty(cssClass)?"":cssClass;
	}
	public String getCssStyle() {
		return StringUtil.isEmpty(this.cssStyle)?"":this.cssStyle;
	}
	public void setCssStyle(String cssStyle) {
		this.cssStyle = StringUtil.isEmpty(cssStyle)?"":cssStyle;
	}
	public Object getSelected() {
		return selected;
	}
	public void setSelected(Object selected) {
		this.selected = selected;
	}
	public String getDictGroupCode() {
		return dictGroupCode;
	}
	public void setDictGroupCode(String dictGroupCode) {
		this.dictGroupCode = dictGroupCode;
	}
	public String getOnclick() {
		return StringUtil.isEmpty(this.onclick)?"":this.onclick;
	}
	public void setOnclick(String onclick) {
		this.onclick = StringUtil.isEmpty(onclick)?"":onclick;
	}
	public String getOnchange() {
		return StringUtil.isEmpty(this.onchange)?"":this.onchange;
	}
	public void setOnchange(String onchange) {
		this.onchange = StringUtil.isEmpty(onchange)?"":onchange;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isShowDefault() {
		return isShowDefault;
	}

	public void setShowDefault(boolean isShowDefault) {
		this.isShowDefault = isShowDefault;
	}

	public String getEvents() {
		return events;
	}

	public void setEvents(String events) {
		this.events = events;
	}

	public String getDefaultOption() {
		return defaultOption;
	}

	public void setDefaultOption(String defaultOption) {
		this.defaultOption = defaultOption;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
}
