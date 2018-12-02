package com.meme.core.excel.handler;

import com.meme.core.util.DateUtil;


/**
 * GMT时间转换处理类
 * @author hzl
 *
 */
public class GMTDateHandler implements IExcelHandler {

	public GMTDateHandler() {
	}

	public String execute(String value) {
		return DateUtil.transferGMT(value, null);
	}

}
