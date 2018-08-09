package com.tipray.core.exception;

/**
 * 权限异常抛出
 * @author chends
 *
 */
public class PermissionException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public PermissionException() {
		super();
	}
	
	public PermissionException(String message) {
        super(message);
    }
	
	public PermissionException(Throwable cause) {
        super(cause);
    }
	
	public PermissionException(String message, Throwable cause) {
        super(message, cause);
    }
}
