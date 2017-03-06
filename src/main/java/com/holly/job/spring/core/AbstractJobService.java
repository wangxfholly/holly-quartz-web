package com.holly.job.spring.core;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @ClassName: AbstractJobService
 * @Description: 抽象出job的实现类
 * @author holly.wang wangxfholly@126.com
 * @date 2017年2月27日 下午9:21:05
 * 
 */
public abstract class AbstractJobService implements Job {

	public final void execute(JobExecutionContext context)
			throws JobExecutionException {
		beforeExecute(context);
		doExecute();
		afterExecute(context);
	}

	/**
	 * @Title: beforeExecute
	 * @Description: 执行前的操作
	 * @param context
	 *            设定文件
	 * @return void 返回类型
	 * @throws
	 */
	protected void beforeExecute(JobExecutionContext context) {

	}

	/**
	 * @Title: afterExecute
	 * @Description: 执行后的操作
	 * @param context
	 *            设定文件
	 * @return void 返回类型
	 * @throws
	 */
	protected void afterExecute(JobExecutionContext context) {

	}

	/**
	 * @Title: doExecute
	 * @Description: 具体执行逻辑
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	protected abstract void doExecute();

}
