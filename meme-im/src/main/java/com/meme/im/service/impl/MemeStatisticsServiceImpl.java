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

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.meme.im.dao.MemeStatisticsMapper;
import com.meme.im.form.ImForm;
import com.meme.im.service.MemeStatisticsService;

@Service("MemeStatisticsService")
public class MemeStatisticsServiceImpl implements MemeStatisticsService {
	
	@Resource private MemeStatisticsMapper memeStatisticsMapper;

	@Override
	public List<Map<String, Object>> selectByPaginationView1(ImForm form) {
		// TODO Auto-generated method stub
		return memeStatisticsMapper.selectByPaginationView1(form);
	}

	@Override
	public int countView1(ImForm form) {
		// TODO Auto-generated method stub
		return memeStatisticsMapper.countView1(form);
	}

	@Override
	public List<Map<String, Object>> selectByPaginationView2(ImForm form) {
		// TODO Auto-generated method stub
		return memeStatisticsMapper.selectByPaginationView2(form);
	}

	@Override
	public int countView2(ImForm form) {
		// TODO Auto-generated method stub
		return memeStatisticsMapper.countView2(form);
	}

}
