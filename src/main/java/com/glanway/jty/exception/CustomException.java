package com.glanway.jty.exception;

/**
 *  自定义异常
 *  @author  tianxuan
 *  @Time     2016/4/8
 */
public  class CustomException extends RuntimeException {

    private int code;

    private String description;

    public CustomException(){
        super();
    }
    public CustomException(Throwable throwable){
        super(throwable);
    }

    public CustomException(String description){
        super(description);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
