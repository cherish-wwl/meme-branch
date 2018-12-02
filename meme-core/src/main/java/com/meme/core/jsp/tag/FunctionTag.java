package com.meme.core.jsp.tag;

import com.meme.core.cache.DictCache;
import com.meme.core.pojo.DictItemView;
import com.meme.core.util.DateUtil;
import com.meme.core.util.HtmlUtil;

/**
 * 自定义EL表达式函数库
 * @author hzl
 *
 */
public class FunctionTag {

	/**
	 * 查找字典项
	 * @param dictgroupCode
	 * @param dictitemCode
	 * @return
	 */
	public static DictItemView reverseDictItem(String dictgroupCode,String dictitemCode){
		DictItemView item=DictCache.getDictItem(dictgroupCode, dictitemCode);
		if(null==item) return null;
		else return item;
	}
	
	/**
	 * 毫秒转中式日期表达方式
	 * @param milSec
	 * @return
	 */
	public static String millisec2CNDate(Long milSec){
		return DateUtil.millisec2CNDate(milSec);
	}
	
	/**
	 * 转换html特殊字符
	 * @param str
	 * @return
	 */
	public static String parseSpecialChars(String str){
		return HtmlUtil.htmlspecialchars(str);
	}
}
