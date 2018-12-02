/********************************************************
 ***                     _ooOoo_                       ***
 ***                    o8888888o                      ***
 ***                    88" . "88                      ***
 ***                    (| -_- |)                      ***
 ***                    O\  =  /O                      ***
 ***                 ____/`---'\____                   ***
 ***               .'  \\|     |//  `.                 ***
 ***              /  \\|||  :  |||//  \                ***
 ***             /  _||||| -:- |||||-  \               ***
 ***             |   | \\\  -  /// |   |               ***
 ***             | \_|  ''\---/''  |   |               ***
 ***             \  .-\__  `-`  ___/-. /               ***
 ***           ___`. .'  /--.--\  `. . __              ***
 ***        ."" '<  `.___\_<|>_/___.'  >'"".           ***
 ***       | | :  `- \`.;`\ _ /`;.`/ - ` : | |         ***
 ***       \  \ `-.   \_ __\ /__ _/   .-` /  /         ***
 ***  ======`-.____`-.___\_____/___.-`____.-'======    ***
 ***                     `=---='                       ***
 ***   .............................................   ***
 ***         佛祖镇楼                  BUG辟易         ***
 ***   佛曰:                                           ***
 ***           写字楼里写字间，写字间里程序员；        ***
 ***           程序人员写程序，又拿程序换酒钱。        ***
 ***           酒醒只在网上坐，酒醉还来网下眠；        ***
 ***           酒醉酒醒日复日，网上网下年复年。        ***
 ***           但愿老死电脑间，不愿鞠躬老板前；        ***
 ***           奔驰宝马贵者趣，公交自行程序员。        ***
 ***           别人笑我忒疯癫，我笑自己命太贱；        ***
 ***           不见满街漂亮妹，哪个归得程序员？        ***
 *********************************************************
 ***               江城子 . 程序员之歌                 ***
 ***           十年生死两茫茫，写程序，到天亮。        ***
 ***               千行代码，Bug何处藏。               ***
 ***           纵使上线又怎样，朝令改，夕断肠。        ***
 ***           领导每天新想法，天天改，日日忙。        ***
 ***               相顾无言，惟有泪千行。              ***
 ***           每晚灯火阑珊处，夜难寐，加班狂。        ***
 *********************************************************
 ***      .--,       .--,                              ***
 ***      ( (  \.---./  ) )                            ***
 ***       '.__/o   o\__.'                             ***
 ***          {=  ^  =}              高山仰止,         ***
 ***           >  -  <               景行行止.         ***
 ***          /       \              虽不能至,         ***
 ***         //       \\             心向往之。        ***
 ***        //|   .   |\\                              ***
 ***        "'\       /'"_.-~^`'-.                     ***
 ***           \  _  /--'         `                    ***
 ***         ___)( )(___                               ***
 ***        (((__) (__)))                              ***
 ********************************************************/
package com.meme.im.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.meme.core.base.BaseController;
import com.meme.core.cache.DictCache;
import com.meme.core.easyui.Pagination;
import com.meme.core.pojo.DictItemView;
import com.meme.im.form.ImForm;
import com.meme.im.service.MemeStatisticsService;

@Controller
@RequestMapping("/im/statistics")
public class MemeStatisticsController extends BaseController {
	
	@Resource private MemeStatisticsService memeStatisticsService;
	
	@RequestMapping("index1")
	public ModelAndView index1(){
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("/im/statistics/list1",model);
	}
	
	@RequestMapping("index2")
	public ModelAndView index2(){
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("/im/statistics/list2",model);
	}
	
	@RequestMapping("list1")
	@ResponseBody
	public Pagination list1(HttpServletRequest request,ImForm form) throws IllegalAccessException, InvocationTargetException{
		List<Map<String, Object>> list = this.memeStatisticsService.selectByPaginationView1(form);
		for(Map<String,Object> map:list) {
			if(map.get("part") != null) {
				DictItemView dictItem = DictCache.getDictItem("MEME_PART",map.get("part").toString());
				map.put("partText", dictItem.getDictitemname());
				if(map.get("type") != null) {
					map.put("typeText", DictCache.getDictItem(dictItem.getRemark(),map.get("type").toString()).getDictitemname());
				}
			}
		}
		int count=this.memeStatisticsService.countView1(form);
		Pagination pagination=new Pagination();
		pagination.setRows(list);
		pagination.setTotal(count);
		return pagination;
	}
	
	@RequestMapping("list2")
	@ResponseBody
	public Pagination list2(HttpServletRequest request,ImForm form) throws IllegalAccessException, InvocationTargetException{
		List<Map<String, Object>> list = this.memeStatisticsService.selectByPaginationView2(form);
		for(Map<String,Object> map:list) {
			if(map.get("mtype") != null) {
				map.put("mtypeText", DictCache.getDictItem("MEMBER_ACCOUNT_TYPE",map.get("mtype").toString()).getDictitemname());
			}
			if(map.get("part") != null) {
				DictItemView dictItem = DictCache.getDictItem("MEME_PART",map.get("part").toString());
				map.put("partText", dictItem.getDictitemname());
				if(map.get("type") != null) {
					map.put("typeText", DictCache.getDictItem(dictItem.getRemark(),map.get("type").toString()).getDictitemname());
				}
			}
		}
		int count=this.memeStatisticsService.countView2(form);
		Pagination pagination=new Pagination();
		pagination.setRows(list);
		pagination.setTotal(count);
		return pagination;
	}
}
