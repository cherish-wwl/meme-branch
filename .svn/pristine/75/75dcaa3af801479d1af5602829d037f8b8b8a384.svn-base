package com.meme.core.util;

/**
 * ID生成器
 * @author hzl
 *
 */
public class IDGenerator {

	public static final long workerid = Long.valueOf(PropertiesUtil.getProperty("workerid")).longValue();
	public static final long datacenterid = Long.valueOf(PropertiesUtil.getProperty("datacenterid")).longValue();
	public static final IdWorker idWorker = new IdWorker(workerid, datacenterid);

	/**
	 * 生成数据表ID
	 * @return
	 */
	public synchronized static long generateId() {
		return idWorker.nextId();
	}
}
