package com.holly.job.spring.client;

import java.text.ParseException;
import java.util.List;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.holly.job.spring.controller.JobController;
import com.holly.job.spring.job.ScheduleService;


public class Test {

    public static void main(String[] args) throws Exception {

	// 获取schedulefactory
//	SchedulerFactory sf=new StdSchedulerFactory("C:/Users/holly.wang/git/holly-quartz/src/main/resources.dev/quartz.properties");
//	
//	Scheduler sch=sf.getScheduler();
//	SchedulerService schedulerService = new SchedulerService();
//	schedulerService.setScheduler(sch);
//	schedulerService.start();
//	System.out.println(schedulerService.checkExists(new JobKey("AirProductImport")));
	
	ApplicationContext context=new ClassPathXmlApplicationContext("spring/app-root.xml");
	
	ScheduleService scheduleService=(ScheduleService) context.getBean("scheduleService");
	
	/*JobController jobAction=(JobController) context.getBean("jobController");
	
	jobAction.queryAll();*/
	
	scheduleService.scheduleJob("cronJob2", "TEST2", "com.holly.job.cron.CronJob", "cronTrigger2", "0/5 * * * * ? ","测试2");
//	
	scheduleService.scheduleJob("cronJob", "TEST", "com.holly.job.cron.CronJob", "cronTrigger", "0/1 * * * * ? ","测试1");
//	
//	scheduleService.removeTrigdger("cronTrigger", "TEST");
	
//	scheduleService.pauseTrigger("cronTrigger", "TEST");
//	scheduleService.pauseTrigger("cronTrigger2", "TEST2");
//	scheduleService.pauseTrigger("AirProductImport-t", "DATA-IMPORT");
	
//	scheduleService.rescheduleJob("cronTrigger", "TEST", "0/15 * * * * ?");
	
	//立即执行作业
//	scheduleService.triggerJob("cronJob", "TEST");
	
//	QuartzDao quartzDao=(QuartzDao) context.getBean("quartzDao");
//	
//	List<JobDetail> details=quartzDao.getList(null);
//	
//	for(JobDetail detail:details){
//	    
//	    System.out.println(detail.getTrigger_name());
//	}

    }

}
