/**   
* @Title: JobClassNotFoudException.java 
* @Package com.holly.job.exception 
* @Description: TODO
* @author holly.wang wangxfholly@126.com   
* @date 2017年3月2日 上午11:25:36 
* @version V1.0   
*/
package com.holly.job.exception;

/** 
 * @ClassName: JobClassNotFoudException 
 * @Description: 创建Job的时候  jobDetail通过反射实例化class的时候报ClassNotFoundException
 * @author holly.wang wangxfholly@126.com
 * @date 2017年3月2日 上午11:25:36 
 *  
 */
public class JobClassNotFoundException extends  ClassNotFoundException{

	/** 
	* @Fields serialVersionUID : generate
	*/ 
	private static final long serialVersionUID = 2803903234649961936L;
	private int code;
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

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 
     * Constructors.
     * 
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    public JobClassNotFoundException() {
        super();
    }

    public JobClassNotFoundException(String msg) {
        super(msg);
    }

    public JobClassNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }


    
    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 
     * Interface.
     * 
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    /**
     * <p>
     * Return the exception that is the underlying cause of this exception.
     * </p>
     * 
     * <p>
     * This may be used to find more detail about the cause of the error.
     * </p>
     * 
     * @return the underlying exception, or <code>null</code> if there is not
     *         one.
     */
    public Throwable getUnderlyingException() {
        return super.getCause();
    }

    @Override
    public String toString() {
        Throwable cause = getUnderlyingException(); 
        if (cause == null || cause == this) {
            return super.toString();
        } else {
            return super.toString() + " [See nested exception: " + cause + "]";
        }
    }
	
}
