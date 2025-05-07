package com.example.exception;

/**
 * @Auther: youMeng
 * @Date: 2025/3/6 - 03 - 06 - 21:45
 * @Description: com.example.exception
 * @version: 1.0
 * 自定义异常
 * 运行时异常
 */
public class CustomerException extends RuntimeException{
    private String code;
    private String msg;

    public CustomerException() {
    }

    public CustomerException(String msg) {
        this.code = "500";
        this.msg = msg;
    }

    public CustomerException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
