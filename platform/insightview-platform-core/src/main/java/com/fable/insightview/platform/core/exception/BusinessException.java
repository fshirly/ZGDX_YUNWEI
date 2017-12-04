package com.fable.insightview.platform.core.exception;

/**
 * 业务异常, 比如 无效的用户, 权限不允许等
 * @author luzl
 */

public class BusinessException extends BaseException {

	private static final long serialVersionUID = 1L;

	/**
	 * 默认构造函数
	 */
	public BusinessException() {
	}

	/**
	 * @param code 错误编码
	 * @param message 错误消息
	 * @param cause 错误原因
	 */
	public BusinessException(int code, String message, Throwable cause) {
		super(code, message, cause);
	}

	/**
	 * @param code 错误编码
	 * @param message 错误消息
	 */
	public BusinessException(int code, String message) {
		super(code, message);
	}

	/**
	 * @param code 错误编码
	 * @param cause 错误消息
	 */
	public BusinessException(int code, Throwable cause) {
		super(code, cause);
	}

	/**
	 * @param message 错误消息
	 * @param cause 错误原因
	 */
	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message 错误消息
	 */
	public BusinessException(String message) {
		super(message);
	}

	/**
	 * @param cause 错误原因
	 */
	public BusinessException(Throwable cause) {
		super(cause);
	}
}
