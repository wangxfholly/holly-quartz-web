/**   
 * @Title: ResResult.java 
 * @Package com.holly.job.entity 
 * @Description: 实体类package
 * @author holly.wang wangxfholly@126.com   
 * @date 2017年3月2日 上午10:39:47 
 * @version V1.0   
 */
package com.holly.job.entity;

import java.io.Serializable;

/**
 * @ClassName: ResResult
 * @Description: 返回的json数据包装
 * @author holly.wang wangxfholly@126.com
 * @date 2017年3月2日 上午10:39:47
 * 
 */
public class ResResult implements Serializable {

	/**
	 * @Fields serialVersionUID : generate
	 */
	private static final long serialVersionUID = -8058242499648911281L;
	private int code;
	private String msg;
	private Object data;

	public ResResult() {
		this.code = 0;
		this.msg = "";
	}

	public ResResult(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	/**
	 * @return code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code
	 *            要设置的 code
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            要设置的 msg
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data
	 *            要设置的 data
	 */
	public void setData(Object data) {
		this.data = data;
	}

}
