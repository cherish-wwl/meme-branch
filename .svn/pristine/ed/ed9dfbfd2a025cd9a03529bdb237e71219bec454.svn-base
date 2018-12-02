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
package com.meme.im.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.meme.core.base.AbstractService;
import com.meme.core.form.Form;
import com.meme.core.mybatis.MapperHelper;
import com.meme.im.dao.MemeFreightTemplateMapper;
import com.meme.im.pojo.MemeFreightTemplate;
import com.meme.im.service.MemeFreightTemplateService;

@Service("MemeFreightTemplateService")
public class MemeFreightTemplateServiceImpl extends AbstractService implements MemeFreightTemplateService {
	
	@Resource private MemeFreightTemplateMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(MemeFreightTemplate record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(MemeFreightTemplate record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public MemeFreightTemplate selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(MemeFreightTemplate record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(MemeFreightTemplate record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemeFreightTemplate> selectAll(){
		return (List<MemeFreightTemplate>) MapperHelper.toBeanList(this.mapper.selectAll(MemeFreightTemplate.class), MemeFreightTemplate.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MemeFreightTemplate> selectByPagination(Form form) {
		this.buildSearchCriterion(MemeFreightTemplate.class, form);
		this.buildOrderByCriterion(MemeFreightTemplate.class, form);
		this.buildLimitCriterion(MemeFreightTemplate.class, form);
		return (List<MemeFreightTemplate>) MapperHelper.toBeanList(this.mapper.selectByPagination(MemeFreightTemplate.class,this.getList()), MemeFreightTemplate.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(MemeFreightTemplate.class, form);
		return this.mapper.count(MemeFreightTemplate.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemeFreightTemplate> selectByEntity(MemeFreightTemplate record) {
		return (List<MemeFreightTemplate>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), MemeFreightTemplate.class);
	}

	@Override
	public int batchInsert(List<MemeFreightTemplate> record) {
		return this.mapper.batchInsert(record,MemeFreightTemplate.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,MemeFreightTemplate.class);
	}

	@Override
	public int batchUpdate(List<MemeFreightTemplate> record) {
		return this.mapper.batchUpdate(record,MemeFreightTemplate.class);
	}

}
