package com.hit.community.dto;

import lombok.Getter;


public record MemberRequest(
        String name,
        String email,
        String profile,
        String studentId,
        String nickName,
        String gender,
        String major

) {

    public static MemberRequest of(
            String name,
            String email,
            String profile,
            String studentId,
            String nickName,
            String gender,
            String major
    ){
        return new MemberRequest(name, email, profile, studentId, nickName, gender, major);
    }


}
