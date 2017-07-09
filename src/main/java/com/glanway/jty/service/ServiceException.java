package com.glanway.jty.service;

/**
 * @author vacoor
 */
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = -5540213871579036701L;

	public ServiceException() {
    }

    public ServiceException(String s) {
        super(s);
    }

    public ServiceException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ServiceException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public String getLocalizedMessage() {
        return super.getMessage();
        // return SpringUtils.getMessage(getMessage());
    }
}
