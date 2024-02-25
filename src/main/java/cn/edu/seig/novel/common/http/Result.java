package cn.edu.seig.novel.common.http;

import lombok.Data;

@Data
public class Result {

    private int code;
    private String message;
    private Object data;

    public Result() {
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result success() {
        return new Result(200, "success");
    }

    public static Result success(Object data) {
        return new Result(200, data);
    }

    public static Result success(String message, Object data) {
        return new Result(200, message, data);
    }

    public static Result fail() {
        return new Result(500, "系统错误");
    }

    public static Result fail(String message) {
        return new Result(500, message);
    }

    public static Result fail(int code, String message) {
        return new Result(code, message);
    }
}
