package com.meme.core.spring.listener;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.meme.core.cache.DictCache;
import com.meme.core.cache.ParamsCache;
import com.meme.core.pojo.DictItemView;
import com.meme.core.pojo.Params;
import com.meme.core.service.DictItemService;
import com.meme.core.service.ParamsService;

/**
 * 初始化缓存监听器，spring完成初始化后加载数据字典进内存
 * @author hzl
 *
 */
@Component
public class InitCacheListener implements InitializingBean, ServletContextAware {

	@SuppressWarnings("unused")
	private ServletContext servletContext;
	@Resource
	private DictItemService dictItemService;
	@Resource
	private ParamsService paramsService;
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext=servletContext;
	}

	/**
	 * 初始化内存操作
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		List<DictItemView> list=this.dictItemService.selectAllDict(null);
		List<Params> params=this.paramsService.selectAllParams(null);
//		DictCache.initCaches(list);
//		ParamsCache.initCaches(params);
		DictCache.getDictInitializer().initCaches(list, null);
		ParamsCache.getParamsInitializer().initCaches(params, null);
	}
}
