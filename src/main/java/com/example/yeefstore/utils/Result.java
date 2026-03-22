package com.example.yeefstore.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private int code;
    private String message;
    private Object data;
    public Result(int code,String message){
        this.code = code;
        this.message = message;
    }
}
