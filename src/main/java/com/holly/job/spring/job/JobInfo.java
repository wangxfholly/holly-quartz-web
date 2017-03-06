package com.holly.job.spring.job;

import java.io.Serializable;

import com.holly.job.spring.utils.DateTimeUtils;


/** 
* @ClassName: JobInfo 
* @Description: 作业明细实体类
* @author holly.wang wangxfholly@126.com
* @date 2017年2月27日 下午9:49:28 
*  
*/
public class JobInfo implements Serializable{

    // 调度器名称
    private String schedName;
    // 触发器名称
    private String triggerName;
    // 触发器分组
    private String triggerGroup;
    // 作业名称
    private String jobName;
    // 作业分组
    private String jobGroup;
    // 触发器功能描述
    private String description;
    // 下次执行时间
    private String nextFireTime;
    // 上次执行时间
    private String prevFireTime;
    // 优先级
    private String priopity;
    // 触发器状态
    private String triggerState;
    // 触发器类型
    private String triggerType;
    // 开始时间
    private String startTime;
    // 结束时间
    private String endTime;
    // 时间规则描述
    private String cronExpression;
    // 作业的类名称
    private String jobClassName;
    
    public String getSchedName() {
		return schedName;
	}

	public void setSchedName(String schedName) {
		this.schedName = schedName;
	}

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getTriggerGroup() {
		return triggerGroup;
	}

	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNextFireTime() {
		return nextFireTime;
	}

	public void setNextFireTime(String nextFireTime) {
		this.nextFireTime = DateTimeUtils.fomat(nextFireTime, "yyyy-MM-dd HH:mm:ss SSS");
	}

	public String getPrevFireTime() {
		return prevFireTime;
	}

	public void setPrevFireTime(String prevFireTime) {
		this.prevFireTime = DateTimeUtils.fomat(prevFireTime, "yyyy-MM-dd HH:mm:ss SSS");;
	}

	public String getTriggerState() {
		return triggerState;
	}

	public void setTriggerState(String triggerState) {
		if (TriggerState.STATE_WAITING.equals(triggerState)) {
		    this.triggerState = TriggerState.STATE_WAITING.getDes();
		}
		if (TriggerState.STATE_ACQUIRED.equals(triggerState)) {
		    this.triggerState = TriggerState.STATE_ACQUIRED.getDes();
		}
		if (TriggerState.STATE_COMPLETE.equals(triggerState)) {
		    this.triggerState = TriggerState.STATE_COMPLETE.getDes();
		}

		if (TriggerState.STATE_BLOCKED.equals(triggerState)) {
		    this.triggerState = TriggerState.STATE_BLOCKED.getDes();
		}
		if (TriggerState.STATE_PAUSED.equals(triggerState)) {
		    this.triggerState = TriggerState.STATE_PAUSED.getDes();
		}
	}

	public String getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getJobClassName() {
		return jobClassName;
	}

	public void setJobClassName(String jobClassName) {
		this.jobClassName = jobClassName;
	}

	public String getPriopity() {
		return priopity;
	}

	public void setPriopity(String priopity) {
		this.priopity = priopity;
	}
	
}
