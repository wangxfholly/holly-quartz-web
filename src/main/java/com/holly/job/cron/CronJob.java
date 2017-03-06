package com.holly.job.cron;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
* @ClassName: CronJob1 
* @Description: TODO
* @author holly.wang wangxfholly@126.com
* @date 2017年2月27日 下午8:56:09 
*  
*/
public class CronJob implements Job {
	private static final Logger log = LoggerFactory.getLogger(CronJob.class);
    public void execute(JobExecutionContext context) throws JobExecutionException {
    	log.info("CronJob1 execute");
    }

}
