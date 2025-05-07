package com.example.common;

/**
 * @Auther: youMeng
 * @Date: 2025/3/6 - 03 - 06 - 18:06
 * @Description: com.example.common
 * @version: 1.0
 */

// 统一返回类
public class Result {
    private String code; // 状态码
    private Object data; // 返回数据
    private String msg; // 错误信息或者成功信息

    public static Result success() {
        Result result = new Result();
        result.setCode("200");
        result.setMsg("请求成功");
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.setCode("200");
        result.setData(data);
        result.setMsg("请求成功");
        return result;
    }

    public static Result error(String msg) {
        Result result = new Result();
        result.setCode("500");
        result.setMsg(msg);
        return result;
    }

    public static Result error(String code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
