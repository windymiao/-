package com.example.university.common;


import lombok.Data;

// 封装给前端的数据
@Data
public class Result {

    private Integer code;  // 编码:200/400
    private String message;  // 成功/失败
    private Long total;     // 总记录数
    private Object data;    // 返回的数据

    // 失败时，返回类型
    public static Result fail(){
        return result(400,"失败",0,null);
    }
    // 成功时，没有返回值
    public static Result success(){
        return result(200,"成功",0,null);
    }
    // 成功时，返回值为一条数据
    public static Result success(Object data){
        return result(200,"成功",0,data);
    }
    // 成功时，返回值为多条数据
    public static Result success(long total,Object data){
        return result(200,"成功",total,data);
    }

    private static Result result(int code, String message,long total,Object data){
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        result.setTotal(total);
        result.setData(data);
        return result;
    }

}
