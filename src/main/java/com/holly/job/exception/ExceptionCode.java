/**   
* @Title: EXCEPTION.java 
* @Package com.holly.job.exception 
* @Description: TODO
* @author holly.wang wangxfholly@126.com   
* @date 2017年3月2日 上午11:41:44 
* @version V1.0   
*/
package com.holly.job.exception;

/** 
 * @ClassName: EXCEPTION 
 * @Description: TODO
 * @author holly.wang wangxfholly@126.com
 * @date 2017年3月2日 上午11:41:44 
 *  
 */
public enum ExceptionCode {

	DURIBLE(1001,"Jobs added with no trigger must be durable");
	private int code;
	private String des;
	
	/** 
	 * @return code 
	 */
	public int getCode() {
		return code;
	}

	/** 
	 * @param code 要设置的 code 
	 */
	public void setCode(int code) {
		this.code = code;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	ExceptionCode(int code,String des) {
		this.code = code;
		this.des = des;
	}
}
