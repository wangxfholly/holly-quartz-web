/**   
* @Title: TriggerCronParseParseException.java 
* @Package com.holly.job.exception 
* @Description: TODO
* @author holly.wang wangxfholly@126.com   
* @date 2017年3月2日 下午2:52:24 
* @version V1.0   
*/
package com.holly.job.exception;

import java.text.ParseException;

/** 
 * @ClassName: TriggerCronParseParseException 
 * @Description: TODO
 * @author holly.wang wangxfholly@126.com
 * @date 2017年3月2日 下午2:52:24 
 *  
 */
public class TriggerCronParseParseException extends ParseException{

	/** 
	* @Fields serialVersionUID : generate 
	*/ 
	private static final long serialVersionUID = 4049754332400522897L;
	public TriggerCronParseParseException(String s, int errorOffset) {
		super(s, errorOffset);
	}
}
