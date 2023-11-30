package com.hit.community.dto;

import com.hit.community.entity.Member;
import lombok.Getter;
import lombok.Setter;

public record MemberResponse(
        String name,
        String email,
        String profile
) {

    public static MemberResponse of(
            String name,
            String email,
            String profile
    ){
        return new MemberResponse(name, email, profile);
    }

    public static MemberResponse fromMemberResponse(Member member){
        return MemberResponse.of(
                member.getName(),
                member.getEmail(),
                member.getProfile()
        );
    }

}
