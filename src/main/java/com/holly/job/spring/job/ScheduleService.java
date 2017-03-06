package com.holly.job.spring.job;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.quartz.Calendar;
import org.quartz.InterruptableJob;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.ListenerManager;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.UnableToInterruptJobException;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.holly.job.exception.JobClassNotFoundException;
import com.holly.job.exception.TriggerCronParseParseException;

/**
 * @ClassName: ScheduleService
 * @Description: 调度器，用于作业的添加、删除、启动
 * @author holly.wang wangxfholly@126.com
 * @date 2017年2月27日 下午9:55:14
 * 
 */
@Service
public class ScheduleService {

	@Autowired
	private Scheduler scheduler;// 作业调度器

	@Autowired
	private QuartzDao quartzDao;

	/** 
	* @Title: addJob 
	* @Description: TODO
	* @param: @param jobName
	* @param: @param group
	* @param: @param jobClassName
	* @param: @param description
	* @param: @return
	* @param: @throws Exception
	* @return: boolean
	* @throws 
	*/
	public boolean addJob(String jobName, String group,String jobClassName, String description) throws Exception {
		try {
			JobDetail jobDetail = getJobDetail(jobName, group, jobClassName, description);
			scheduler.addJob(jobDetail, false);
		} catch (ClassNotFoundException e) {
			throw new JobClassNotFoundException();
		} catch (SchedulerException e) {
			throw e;
		}
		return true;
	}
	
	/**
	 * @throws SchedulerException 
	 * @throws TriggerCronParseParseException  
	* @Title: addJobTrigger 
	* @Description: TODO
	* @param: 
	* @return: void
	* @throws 
	*/
	public boolean addOrUpdateJobTrigger(String triggerName, String group,String cronExpression,String jobName,String jobGroup) throws TriggerCronParseParseException, SchedulerException {
		try {
			Trigger trigger = getJobTrigger(triggerName,group,cronExpression,jobName,jobGroup);
			scheduler.addTrigger(trigger, true);
		} catch (ParseException e) {
			throw new TriggerCronParseParseException(e.getMessage(), e.getErrorOffset());
		} catch (SchedulerException e) {
			throw e;
		}
		return true;
		
	}
	
	 /**
     * Return a list of <code>JobExecutionContext</code> objects that
     * represent all currently executing Jobs in this Scheduler instance.
     * 
     * <p>
     * This method is not cluster aware.  That is, it will only return Jobs
     * currently executing in this Scheduler instance, not across the entire
     * cluster.
     * </p>
     * 
     * <p>
     * Note that the list returned is an 'instantaneous' snap-shot, and that as
     * soon as it's returned, the true list of executing jobs may be different.
     * Also please read the doc associated with <code>JobExecutionContext</code>-
     * especially if you're using RMI.
     * </p>
     * 
     * @see JobExecutionContext
     */
	
	public List<JobExecutionContext>  getCurrentlyExecutingJobs() throws SchedulerException {
		try {
			return scheduler.getCurrentlyExecutingJobs();
		} catch (SchedulerException e) {
			throw e;
		}
	}

    /** 
    * @Title: getListenerManager 
    * @Description: Get a reference to the scheduler's <code>ListenerManager</code>, through which listeners may be registered.
    * @param: @return
    * @param: @throws SchedulerException
    * @return: ListenerManager
    * @throws 
    */
    public ListenerManager getListenerManager() throws SchedulerException {
        return scheduler.getListenerManager();
    }
	
	
	
	/**
	 * @Title: removeTrigdger
	 * @Description: 删除job
	 * @param triggerName
	 * @param groupa
	 * @throws Exception
	 * @return boolean
	 */
	public boolean removeTriggerAndJob(String triggerName, String group)
			throws Exception {

		TriggerKey triggerKey = geTriggerKey(triggerName, group);
		try {
			scheduler.pauseTrigger(triggerKey);// 停止触发器
			return scheduler.unscheduleJob(triggerKey);// 移除触发器
		} catch (SchedulerException e) {
			throw new Exception("删除作业时发生异常", e);
		}
	}

	/**
	 * @Title: resumeTrigger
	 * @Description: 重启作业
	 * @param: triggerName
	 * @param: group
	 * @throws Exception
	 * @return: void
	 */
	public void resumeTrigger(String triggerName, String group)
			throws Exception {
		TriggerKey triggerKey = geTriggerKey(triggerName, group);
		try {
			scheduler.resumeTrigger(triggerKey);
		} catch (SchedulerException e) {
			throw new Exception("重启作业时发生异常", e);
		}
	}

	/**
	 * @Title: pauseTrigger
	 * @Description: 暂停作业
	 * @param: triggerName
	 * @param: group
	 * @throws Exception
	 * @return: void
	 */
	public void pauseTrigger(String triggerName, String group) throws Exception {
		TriggerKey triggerKey = geTriggerKey(triggerName, group);
		try {
			scheduler.pauseTrigger(triggerKey);
		} catch (SchedulerException e) {
			throw new Exception("暂停作业是发生异常", e);
		}
	}

	/**
	 * @Title: scheduleJob
	 * @Description: 新增作业
	 * @param jobName
	 *            作业名称
	 * @param group
	 *            作业和触发器所在分组
	 * @param job_class_name
	 *            作业的类名称，必须是全限定类名 如：com.acca.xxx.service（不能是接口）
	 * @param triggerName
	 *            触发器名称
	 * @param cronExpression
	 *            时间规则
	 * @param description
	 *            描述
	 * @return: void
	 * @throws ParseException
	 * @throws ClassNotFoundException
	 * @throws SchedulerException
	 */

	public void scheduleJob(String jobName, String group, String jobClassName,
			String triggerName, String cronExpression, String description)
			throws ParseException, ClassNotFoundException, SchedulerException {
		try {
			JobDetail jobDetail = getJobDetail(jobName, group, jobClassName,
					description);
			Trigger trigger = getTrigger(triggerName, group, cronExpression,
					description);
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (ClassNotFoundException e) {
			throw e;
		} catch (ParseException e) {
			throw e;
		} catch (SchedulerException e) {
			throw e;
		}
	}
	
	
	 /** 
	* @Title: scheduleJobs 
	* @Description: Schedule all of the given jobs with the related set of triggers.
	* @param: @param triggersAndJobs
	* @param: @param replace
	* @param: @throws SchedulerException
	* @return: void
	* @throws 
	*/
	public void scheduleJobs(Map<JobDetail, Set<? extends Trigger>> triggersAndJobs, boolean replace) throws SchedulerException{
		 scheduler.scheduleJobs(triggersAndJobs, replace);
	 }
	
	
	
	/** 
	* @Title: deleteJob 
	* @Description: Delete the identified <code>Job</code>s from the Scheduler - and any
     * associated <code>Trigger</code>s.
	* @param: @param jobKey
	* @param: @return
	* @param: @throws SchedulerException
	* @return: boolean
	* @throws 
	*/
	public boolean deleteJob(JobKey jobKey) throws SchedulerException {
		return scheduler.deleteJob(jobKey);
	}

	
	 /** 
	* @Title: pauseTrigger 
	* @Description: Pause the <code>{@link Trigger}</code> with the given key.
	* @param: @param triggerKey
	* @param: @throws SchedulerException
	* @return: void
	* @throws 
	*/
	public void pauseTrigger(TriggerKey triggerKey)
		        throws SchedulerException {
		scheduler.pauseTrigger(triggerKey);
	}
	
	
	/** 
	* @Title: resumeTrigger 
	* @Description:  Resume (un-pause) the <code>{@link Trigger}</code> with the given
     * key.
	* @param: @param triggerKey
	* @param: @throws SchedulerException
	* @return: void
	* @throws 
	*/
	public void resumeTrigger(TriggerKey triggerKey)
		        throws SchedulerException {
		scheduler.resumeTrigger(triggerKey);
	 }
	 
	 
	/**
	 * @Title: rescheduleJob
	 * @Description: 更新触发器的时间规则
	 * @param triggerName
	 *            触发器名称
	 * @param group
	 *            触发器分组
	 * @param cronExpression
	 *            时间规则
	 * @throws ParseException
	 *             解析异常
	 * @throws SchedulerException
	 *             调度器异常
	 * @return: void
	 */
	public void rescheduleJob(String triggerName, String group,
			String cronExpression) throws ParseException, SchedulerException {
		TriggerKey triggerKey = geTriggerKey(triggerName, group);
		try {
			Trigger newTrigger = getTrigger(triggerName, group, cronExpression);
			scheduler.rescheduleJob(triggerKey, newTrigger);
		} catch (ParseException e) {
			throw e;
		} catch (SchedulerException e) {
			throw e;
		}

	}

	/**
	 * @Title: triggerJob
	 * @Description: 立即执行作业，不考虑时间规则
	 * @param jobName
	 *            作业名称
	 * @param group
	 *            作业分组
	 * @throws SchedulerException
	 *             调度器异常
	 * @return: void
	 */
	public void triggerJob(String jobName, String group)
			throws SchedulerException {
		JobKey jobKey = new JobKey(jobName, group);
		try {
			scheduler.triggerJob(jobKey);
		} catch (SchedulerException e) {
			throw new SchedulerException("立即执行作业时发生异常", e);
		}
	}

	/**
	 * @Title: queryInfo
	 * @Description: 查询作业信息
	 * @param params
	 * @return: List<JobInfo>
	 */
	public List<JobInfo> queryInfo(Map<String, String> params) {
		return quartzDao.getList(params);

	}

	/**
	 * 
	 * 
	 * @Title: getTrigger
	 * @Description: 获取出发器
	 * @param triggerName
	 *            触发器名称
	 * @param group
	 *            触发器分组
	 * @param cronExpression
	 *            触发器时间规则
	 * @throws ParseException
	 *             解析异常
	 * @return: Trigger
	 */
	@SuppressWarnings("deprecation")
	private Trigger getTrigger(String triggerName, String group,
			String cronExpression) throws ParseException {

		CronTriggerImpl triggerImpl = new CronTriggerImpl(triggerName, group);
		try {
			triggerImpl.setCronExpression(cronExpression);
		} catch (ParseException e) {
			throw e;
		}
		return triggerImpl;
	}
	private Trigger getJobTrigger(String triggerName, String group,
			String cronExpression,String jobName,String jobGroup) throws ParseException {
		
		CronTriggerImpl triggerImpl = new CronTriggerImpl(triggerName, group);
		try {
			triggerImpl.setCronExpression(cronExpression);
			triggerImpl.setJobGroup(jobGroup);
			triggerImpl.setJobName(jobName);
			triggerImpl.setNextFireTime(new Date());
		} catch (ParseException e) {
			throw e;
		}
		return triggerImpl;
	}
	 /** 
	* @Title: getTriggersOfJob 
	* @Description: Get all <code>{@link Trigger}</code> s that are associated with the
     * identified <code>{@link org.quartz.JobDetail}</code>.
     * 
	* @param: @param jobKey
	* @param: @return
	* @param: @throws SchedulerException
	* @return: List<? extends Trigger>
	* @throws 
	*/
	public List<? extends Trigger> getTriggersOfJob(JobKey jobKey)
		        throws SchedulerException {
		 return scheduler.getTriggersOfJob(jobKey);
	 }
	
	
	/** 
	* @Title: getJobDetail 
	* @Description: Get the <code>{@link JobDetail}</code> for the <code>Job</code>
     * instance with the given key.
	* @param: @param jobKey
	* @param: @return
	* @param: @throws SchedulerException
	* @return: JobDetail
	* @throws 
	*/
	public JobDetail getJobDetail(JobKey jobKey)
	        throws SchedulerException {
		return scheduler.getJobDetail(jobKey);
	}

    /** 
    * @Title: getTrigger 
    * @Description: Get the <code>{@link Trigger}</code> instance with the given key.
    * @param: @param triggerKey
    * @param: @return
    * @param: @throws SchedulerException
    * @return: Trigger
    * @throws 
    */
    public Trigger getTrigger(TriggerKey triggerKey)
        throws SchedulerException {
    	return scheduler.getTrigger(triggerKey);
    }
	

	/**
	 * @Title: geTriggerKey
	 * @Description: 获取触发器的key
	 * @param triggerName
	 * @param group
	 * @return: TriggerKey
	 */
	private TriggerKey geTriggerKey(String triggerName, String group) {
		TriggerKey triggerKey = new TriggerKey(triggerName, group);
		return triggerKey;
	}

	/**
	 * @Title: getJobDetail
	 * @Description: 创建Jobdetail类SSS
	 * @param jobName
	 *            作业名称
	 * @param group
	 *            作业所在组
	 * @param jobClassName
	 *            作业的权限定类名
	 * @param description
	 * @throws ClassNotFoundException
	 * @return: JobDetail
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	private JobDetail getJobDetail(String jobName, String group,
			String jobClassName, String description)
			throws ClassNotFoundException {
		try {
			JobDetailImpl detail = new JobDetailImpl(jobName, group,
					(Class<? extends Job>) Class.forName(jobClassName));
			detail.setDescription(description);
			detail.setDurability(true);
			return detail;
		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundException(jobClassName + "不存在或者书写不正确", e);
		}
	}

	/**
	 * @Title: getTrigger
	 * @Description: 创建触发器
	 * @param triggerName
	 *            触发器名称
	 * @param group
	 *            触发器所在组
	 * @param cronExpression
	 *            触发器的时间规则
	 * @throws ParseException
	 *             解析时间规则异常
	 * @return: Trigger
	 */
	@SuppressWarnings("deprecation")
	private Trigger getTrigger(String triggerName, String group,
			String cronExpression, String description) throws ParseException {
		CronTriggerImpl triggerImpl = new CronTriggerImpl(triggerName, group);
		triggerImpl.setDescription(description);
		try {
			triggerImpl.setCronExpression(cronExpression);
		} catch (ParseException e) {
			throw e;
		}
		return triggerImpl;
	}

    /** 
    * @Title: addCalendar 
    * @Description: TODO
    * @param: @param calName
    * @param: @param calendar
    * @param: @param replace
    * @param: @param updateTriggers
    * @param: @throws SchedulerException
    * @return: void
    * @throws 
    */
    public void addCalendar(String calName, Calendar calendar, boolean replace, boolean updateTriggers)
            throws SchedulerException {
    	scheduler.addCalendar(calName, calendar, replace, updateTriggers);
    }

     
    /** 
    * @Title: deleteCalendar 
    * @Description: Delete the identified <code>Calendar</code> from the Scheduler.
    * @param: @param calName
    * @param: @return
    * @param: @throws SchedulerException
    * @return: boolean
    * @throws 
    */
    public boolean deleteCalendar(String calName) throws SchedulerException {
    	return scheduler.deleteCalendar(calName);
    }

    /**
     * Get the <code>{@link Calendar}</code> instance with the given name.
     */
    public Calendar getCalendar(String calName) throws SchedulerException {
    	return scheduler.getCalendar(calName);
    }

        
    /** 
    * @Title: getCalendarNames 
    * @Description:  Get the names of all registered <code>{@link Calendar}s</code>.
    * @param: @return
    * @param: @throws SchedulerException
    * @return: List<String>
    * @throws 
    */
    public List<String> getCalendarNames() throws SchedulerException {
    	return scheduler.getCalendarNames();
    }

    /**
     * Request the interruption, within this Scheduler instance, of all 
     * currently executing instances of the identified <code>Job</code>, which 
     * must be an implementor of the <code>InterruptableJob</code> interface.
     * 
     * <p>
     * If more than one instance of the identified job is currently executing,
     * the <code>InterruptableJob#interrupt()</code> method will be called on
     * each instance.  However, there is a limitation that in the case that  
     * <code>interrupt()</code> on one instances throws an exception, all 
     * remaining  instances (that have not yet been interrupted) will not have 
     * their <code>interrupt()</code> method called.
     * </p>
     * 
     * <p>
     * This method is not cluster aware.  That is, it will only interrupt 
     * instances of the identified InterruptableJob currently executing in this 
     * Scheduler instance, not across the entire cluster.
     * </p>
     * 
     * @return true if at least one instance of the identified job was found
     * and interrupted.
     * @throws UnableToInterruptJobException if the job does not implement
     * <code>InterruptableJob</code>, or there is an exception while 
     * interrupting the job.
     * @see InterruptableJob#interrupt()
     * @see #getCurrentlyExecutingJobs()
     * @see #interrupt(String)
     */
    public boolean interrupt(JobKey jobKey) throws UnableToInterruptJobException  {
    	return scheduler.interrupt(jobKey);
    }
        
    /**
     * Request the interruption, within this Scheduler instance, of the 
     * identified executing <code>Job</code> instance, which 
     * must be an implementor of the <code>InterruptableJob</code> interface.
     * 
     * <p>
     * This method is not cluster aware.  That is, it will only interrupt 
     * instances of the identified InterruptableJob currently executing in this 
     * Scheduler instance, not across the entire cluster.
     * </p>
     * 
     * @param fireInstanceId the unique identifier of the job instance to
     * be interrupted (see {@link JobExecutionContext#getFireInstanceId()}
     * @return true if the identified job instance was found and interrupted.
     * @throws UnableToInterruptJobException if the job does not implement
     * <code>InterruptableJob</code>, or there is an exception while 
     * interrupting the job.
     * @see InterruptableJob#interrupt()
     * @see #getCurrentlyExecutingJobs()
     * @see JobExecutionContext#getFireInstanceId()
     * @see #interrupt(JobKey)
     */
     public boolean interrupt(String fireInstanceId) throws UnableToInterruptJobException {
    	 return scheduler.interrupt(fireInstanceId);
     }

	/**
	 * @throws SchedulerException  
	* @Title: halt 
	* @Description: TODO
	* @param: 
	* @return: void
	* @throws 
	*/
	public void shutdown(boolean waitForJobsToComplete) throws SchedulerException {
		scheduler.shutdown(waitForJobsToComplete);
		
	}
	

}
