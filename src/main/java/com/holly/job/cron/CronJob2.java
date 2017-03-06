/**   
* @Title: CronJob2.java 
* @Package com.holly.job.cron 
* @Description: TODO
* @author holly.wang wangxfholly@126.com   
* @date 2017年3月2日 上午10:35:24 
* @version V1.0   
*/
package com.holly.job.cron;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @ClassName: CronJob2 
 * @Description: TODO
 * @author holly.wang wangxfholly@126.com
 * @date 2017年3月2日 上午10:35:24 
 *  
 */
public class CronJob2 implements Job{

	private static final Logger log = LoggerFactory.getLogger(CronJob2.class);
	/* (非 Javadoc) 
	* <p>Title: execute</p> 
	* <p>Description: </p> 
	* @param context
	* @throws JobExecutionException 
	* @see org.quartz.Job#execute(org.quartz.JobExecutionContext) 
	*/
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		log.info("CronJob2 execute");
	}

}
