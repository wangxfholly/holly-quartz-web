package com.holly.job.spring.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @ClassName: QuartzDao
 * @Description: Quartz定时作业数据操作类
 * @author holly.wang wangxfholly@126.com
 * @date 2017年2月27日 下午9:50:26
 * 
 */
@Component
public class QuartzDao {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public static final String QRTZ_SQL = "select tr.*,cr.CRON_EXPRESSION,jo.JOB_CLASS_NAME from QRTZ_TRIGGERS tr,QRTZ_CRON_TRIGGERS cr,QRTZ_JOB_DETAILS jo  "
			+ " where cr.SCHED_NAME=tr.SCHED_NAME and cr.TRIGGER_NAME=tr.TRIGGER_NAME and cr.TRIGGER_GROUP=tr.TRIGGER_GROUP  "
			+ " and jo.SCHED_NAME=tr.SCHED_NAME and jo.JOB_NAME=tr.JOB_NAME and jo.JOB_GROUP=tr.JOB_GROUP";

	/**
	 * @Title: getList
	 * @Description: 根据条件查询Quartz作业的信息
	 * @param @param params
	 * @param @return 设定文件
	 * @return List<JobInfo> 返回类型
	 * @throws
	 */
	public List<JobInfo> getList(Map<String, String> params) {

		List<Map<String, Object>> resultsList = jdbcTemplate
				.queryForList(QRTZ_SQL);

		if (resultsList == null || resultsList.size() == 0) {

			return null;
		}

		List<JobInfo> details = new ArrayList<JobInfo>(resultsList.size());

		for (Map<String, Object> map : resultsList) {
			details.add(createJobDetail(map));
		}

		return details;

	}

	/**
	 * 创建作业明细信息
	 * 
	 * @Title: createJobDetail
	 * @Description: TODO
	 * @param map
	 * @return
	 * @return: JobDetail
	 */
	private JobInfo createJobDetail(Map<String, Object> map) {
		JobInfo detail = new JobInfo();
		detail.setSchedName(map.get("sched_name") + "");
		detail.setTriggerName(map.get("trigger_name") + "");
		detail.setTriggerGroup(map.get("trigger_group") + "");
		detail.setDescription(map.get("desccription") + "");
		detail.setJobName(map.get("job_name") + "");
		detail.setJobGroup(map.get("job_group") + "");
		detail.setNextFireTime(map.get("next_fire_time") + "");
		detail.setPrevFireTime(map.get("prev_fire_time") + "");
		detail.setCronExpression(map.get("cron_expression") + "");
		detail.setPriopity(map.get("priopity") + "");
		detail.setEndTime(map.get("end_time") + "");
		detail.setStartTime(map.get("start_time") + "");
		detail.setJobClassName(map.get("job_class_name") + "");
		detail.setTriggerState(map.get("trigger_state") + "");
		detail.setTriggerType(map.get("trigger_type") + "");
		return detail;
	}
}
