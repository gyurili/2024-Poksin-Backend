package com.viewmore.poksin.response;

import lombok.Data;

@Data
public class ResponseDTO<T> {
    private Integer status;
    private String code;
    private String message;
    private T data;

}