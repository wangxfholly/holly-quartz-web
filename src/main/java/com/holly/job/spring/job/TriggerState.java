package com.holly.job.spring.job;

/**
 * 触发器状态
 * @ClassName: TriggerState 
 * @Description: TODO
 * @author: zhouhua
 * @date: 2016年3月15日 下午5:06:18
 */
public enum TriggerState {

	STATE_WAITING("WAITING","等待中状态"),STATE_ACQUIRED("ACQUIRED","已获取状态"),
	STATE_EXECUTING("EXECUTING","执行中状态"),STATE_COMPLETE("COMPLETE","执行完成状态"),
	STATE_BLOCKED("BLOCKED","阻塞状态"),STATE_ERROR("ERROR","出错了"),
	STATE_PAUSED("PAUSED","暂停状态");
	private String code;
	private String des;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	TriggerState(String code,String des) {
		this.code = code;
		this.des = des;
	}
}
