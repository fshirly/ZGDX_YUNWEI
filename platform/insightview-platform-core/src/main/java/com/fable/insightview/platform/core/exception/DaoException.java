package com.fable.insightview.platform.core.exception;

/**
 * 数据库异常
 * @author luzl
 */

public class DaoException extends SystemException {

	private static final long serialVersionUID = 1L;

	/**
	 * 默认构造函数
	 */
	public DaoException() {
	}

	/**
	 * @param code 错误编码
	 * @param message 错误消息
	 * @param cause 错误原因
	 */
	public DaoException(int code, String message, Throwable cause) {
		super(code, message, cause);
	}

	/**
	 * @param code 错误编码
	 * @param message 错误消息
	 */
	public DaoException(int code, String message) {
		super(code, message);
	}

	/**
	 * @param code 错误编码
	 * @param cause 错误消息
	 */
	public DaoException(int code, Throwable cause) {
		super(code, cause);
	}

	/**
	 * @param message 错误消息
	 * @param cause 错误原因
	 */
	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message 错误消息
	 */
	public DaoException(String message) {
		super(message);
	}

	/**
	 * @param cause 错误原因
	 */
	public DaoException(Throwable cause) {
		super(cause);
	}
}
