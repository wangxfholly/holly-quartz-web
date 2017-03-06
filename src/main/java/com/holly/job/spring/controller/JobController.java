package com.holly.job.spring.controller;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.quartz.SchedulerException;
import org.quartz.impl.calendar.AnnualCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.holly.job.entity.ResResult;
import com.holly.job.spring.job.JobInfo;
import com.holly.job.spring.job.ScheduleService;

/**
 * @ClassName: JobController
 * @Description: TODO
 * @author holly.wang wangxfholly@126.com
 * @date 2017年2月27日 下午9:06:48
 * 
 */
@Controller
@RequestMapping("/job")
public class JobController {

	private static final Logger log = LoggerFactory
			.getLogger(JobController.class);
	@Autowired
	private ScheduleService scheduleService;
	@RequestMapping("/addJob")
	private @ResponseBody ResResult addJob(@RequestBody JobInfo jobInfo)
			throws Exception {

		ResResult result = new ResResult();
		scheduleService.addJob(jobInfo.getJobName(), jobInfo.getJobGroup(),
				jobInfo.getJobClassName(), jobInfo.getDescription());
		return result;
	}
	@RequestMapping("/addCalendar")
	private @ResponseBody ResResult addCalendar(@RequestBody JobInfo jobInfo)
			throws Exception {
		
		ResResult result = new ResResult();
		AnnualCalendar holidays = new AnnualCalendar();  
        //五一劳动节  
        Calendar laborDay = new GregorianCalendar(2017, 03, 07);  
        holidays.setDayExcluded(laborDay, true);//排除的日期，如果设置为false则为包含  
        //万圣节（31号）  
        Calendar halloween = new GregorianCalendar(2017, 03, 06);  
        holidays.setDayExcluded(halloween, true);//排除该日期  
        scheduleService.addCalendar("cronCalendar2", holidays, true, true);
		return result;
	}
	@RequestMapping("/addOrUpdateJobTrigger")
	private @ResponseBody ResResult addOrUpdateJobTrigger(@RequestBody JobInfo jobInfo)
			throws Exception {
		
		ResResult result = new ResResult();
		scheduleService.addOrUpdateJobTrigger(jobInfo.getTriggerName(),jobInfo.getTriggerGroup(),jobInfo.getCronExpression(),jobInfo.getJobName(),jobInfo.getJobGroup());
		return result;
	}
	
	
	/** 
	* @Title: halt 
	* @Description: 停止quartz schedule  所有任务都不会运行
	* @param: @return
	* @param: @throws Exception
	* @return: ResResult
	* @throws 
	*/
	@RequestMapping("/shutdown")
	private @ResponseBody ResResult halt()
			throws Exception {
		
		ResResult result = new ResResult();
		scheduleService.shutdown(true);
		return result;
	}

	/**
	 * @Title: queryAll
	 * @Description: 所有job列表
	 * @param: @return
	 * @return: Object
	 * @throws
	 */
	@RequestMapping("/queryAll")
	public @ResponseBody Object queryAll() {
		List<JobInfo> list = scheduleService.queryInfo(null);
		return list;
	}

	/**
	 * @Title: pause
	 * @Description: 暂停作业
	 * @param @param triggerName
	 * @param @param triggerGroup
	 * @param @return 设定文件
	 * @return Object 返回类型
	 * @throws
	 */
	@RequestMapping("/pause")
	public @ResponseBody Object pause(@RequestBody JobInfo jobInfo) {
		try {
			scheduleService.pauseTrigger(jobInfo.getTriggerName(),
					jobInfo.getTriggerGroup());
		} catch (Exception e) {
		}
		return "Success";

	}

	/**
	 * @Title: removeJob
	 * @Description: 删除作业
	 * @param: @param jobInfo
	 * @param: @return
	 * @return: Object
	 * @throws
	 */
	@RequestMapping("/remove")
	public @ResponseBody Object removeJob(@RequestBody JobInfo jobInfo) {
		try {
			scheduleService.removeTriggerAndJob(jobInfo.getTriggerName(),
					jobInfo.getTriggerGroup());
		} catch (Exception e) {
		}
		return "Success";

	}

	/**
	 * @Title: rescheduleJob
	 * @Description: 更新触发器的时间规则
	 * @param: @param jobInfo
	 * @param: @return
	 * @return: Object
	 * @throws
	 */
	@RequestMapping("/rescheduleJob")
	public @ResponseBody Object rescheduleJob(@RequestBody JobInfo jobInfo) {
		try {
			scheduleService.rescheduleJob(jobInfo.getTriggerName(),
					jobInfo.getTriggerGroup(), jobInfo.getCronExpression());
		} catch (Exception e) {
		}
		return "Success";

	}

	/**
	 * @Title: restart
	 * @Description: 重启作业
	 * @param @param triggerName
	 * @param @param triggerGroup
	 * @param @return 设定文件
	 * @return Object 返回类型
	 * @throws
	 */
	@RequestMapping("/restart")
	public @ResponseBody Object restart(@RequestBody JobInfo jobInfo) {
		try {
			scheduleService.resumeTrigger(jobInfo.getTriggerName(),
					jobInfo.getTriggerGroup());
		} catch (Exception e) {
			log.error("restart error", e);
		}
		return "success";
	}

	/**
	 * @Title: executeNow
	 * @Description: 立即执行
	 * @param @param jobName
	 * @param @param jobGroup
	 * @param @return 设定文件
	 * @return Object 返回类型
	 * @throws
	 */
	@RequestMapping("/executeNow")
	public @ResponseBody Object executeNow(@RequestBody JobInfo jobInfo) {
		try {
			scheduleService.triggerJob(jobInfo.getJobName(),
					jobInfo.getJobGroup());
		} catch (SchedulerException e) {
			log.error("executeNow error", e);
		}
		return "";
	}
}
