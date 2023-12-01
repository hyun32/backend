package com.hit.community.dto.jwt;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record StatusResponseDto(
        Integer status,
        Object data
) {


    public static StatusResponseDto of(Integer status, Object data){
        return new StatusResponseDto(status, data);
    }

    public static StatusResponseDto addStatus(Integer status){
        return StatusResponseDto.of(status, null);
    }

    public StatusResponseDto success(){
        return addStatus(200);
    }
    public static StatusResponseDto success(Object data){
        return StatusResponseDto.of(200, data);
    }
}
