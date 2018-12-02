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
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meme.core.base.AbstractService;
import com.meme.core.form.Form;
import com.meme.core.mybatis.MapperHelper;
import com.meme.im.dao.MemeWbOrderSettlementMapper;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.MemeWbOrderSettlement;
import com.meme.im.service.MemeWbOrderSettlementService;

@Service("MemeWbOrderSettlementService")
public class MemeWbOrderSettlementServiceImpl extends AbstractService implements MemeWbOrderSettlementService {

	@Autowired
	private MemeWbOrderSettlementMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		// TODO Auto-generated method stub
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(MemeWbOrderSettlement record) {
		// TODO Auto-generated method stub
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(MemeWbOrderSettlement record) {
		// TODO Auto-generated method stub
		return this.mapper.insertSelective(record);
	}

	@Override
	public MemeWbOrderSettlement selectByPrimaryKey(Object id) {
		// TODO Auto-generated method stub
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(MemeWbOrderSettlement record) {
		// TODO Auto-generated method stub
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(MemeWbOrderSettlement record) {
		// TODO Auto-generated method stub
		return this.mapper.updateByPrimaryKey(record);
	}

	@Override
	public List<MemeWbOrderSettlement> selectAll() {
		// TODO Auto-generated method stub
		return (List<MemeWbOrderSettlement>) MapperHelper.toBeanList(this.mapper.selectAll(MemeWbOrderSettlement.class), MemeWbOrderSettlement.class);
	}

	@Override
	public List<MemeWbOrderSettlement> selectByPagination(Form form) {
		// TODO Auto-generated method stub
		this.buildSearchCriterion(MemeWbOrderSettlement.class, form);
		this.buildOrderByCriterion(MemeWbOrderSettlement.class, form);
		this.buildLimitCriterion(MemeWbOrderSettlement.class, form);
		return (List<MemeWbOrderSettlement>) MapperHelper.toBeanList(this.mapper.selectByPagination(MemeWbOrderSettlement.class,this.getList()), MemeWbOrderSettlement.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(MemeWbOrderSettlement.class, form);
		return this.mapper.count(MemeWbOrderSettlement.class,this.getList());
	}

	@Override
	public List<MemeWbOrderSettlement> selectByEntity(MemeWbOrderSettlement record) {
		// TODO Auto-generated method stub
		return (List<MemeWbOrderSettlement>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), MemeWbOrderSettlement.class);
	}

	@Override
	public int batchInsert(List<MemeWbOrderSettlement> record) {
		// TODO Auto-generated method stub
		return this.mapper.batchInsert(record, MemeWbOrderSettlement.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		// TODO Auto-generated method stub
		return this.mapper.batchDelete(record, MemeWbOrderSettlement.class);
	}

	@Override
	public int batchUpdate(List<MemeWbOrderSettlement> record) {
		return this.mapper.batchUpdate(record,MemeWbOrderSettlement.class);
	}

	@Override
	public List<Map<String, Object>> selectByPaginationView(ImForm form) {
		return this.mapper.selectByPaginationView(form);
	}

	@Override
	public int countView(ImForm form) {
		return this.mapper.countView(form);
	}

	/* (non-Javadoc)
	 * @see com.meme.im.service.MemeWbOrderSettlementService#doWbOrderQuery(java.lang.Long, java.lang.Integer)
	 */
	@Override
	public List<Map<String,Object>> doWbOrderQuery(Long memberid, Integer state) {
		// TODO Auto-generated method stub
		return this.mapper.doWbOrderQuery(memberid, state);
	}

}
