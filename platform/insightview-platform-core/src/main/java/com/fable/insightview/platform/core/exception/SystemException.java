package com.fable.insightview.platform.core.exception;

/**
 * 系统异常, 比如数据库/网络/文件IO等系统的异常.
 * @author Guojf
 */

public class SystemException extends BaseException {

	private static final long serialVersionUID = 1L;

	/**
	 * 默认构造函数
	 */
	public SystemException() {
	}

	/**
	 * @param code 错误编码
	 * @param message 错误消息
	 * @param cause 错误原因
	 */
	public SystemException(int code, String message, Throwable cause) {
		super(code, message, cause);
	}

	/**
	 * @param code 错误编码
	 * @param message 错误消息
	 */
	public SystemException(int code, String message) {
		super(code, message);
	}

	/**
	 * @param code 错误编码
	 * @param cause 错误消息
	 */
	public SystemException(int code, Throwable cause) {
		super(code, cause);
	}

	/**
	 * @param message 错误消息
	 * @param cause 错误原因
	 */
	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message 错误消息
	 */
	public SystemException(String message) {
		super(message);
	}

	/**
	 * @param cause 错误原因
	 */
	public SystemException(Throwable cause) {
		super(cause);
	}
}
