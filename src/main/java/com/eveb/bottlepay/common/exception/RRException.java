package com.eveb.bottlepay.common.exception;

/**
 * 自定义异常
 *
 */
public class RRException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
    private String msg;
    private int code = 500;
    private int unAuth=401;
    
    public RRException(String msg) {
		super(msg);
		this.msg = msg;
	}
    public int getUnAuth() {
		return unAuth;
	}
	public void setUnAuth(int unAuth) {
		this.unAuth = unAuth;
	}
	public RRException(String msg,int unAuth,int code) {
    	super(msg);
    	this.msg=msg;
    	this.unAuth=unAuth;
    }
	
	public RRException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}
	
	public RRException(String msg, int code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}
	
	public RRException(String msg, int code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	
}
