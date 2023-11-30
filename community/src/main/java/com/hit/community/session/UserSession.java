package com.hit.community.session;

import com.hit.community.entity.Member;
import com.hit.community.entity.Role;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@RequiredArgsConstructor
public class UserSession implements Serializable {
    private String name;
    private String email;
    private String picture;
    private Role role;

    public UserSession(Member member) {

        this.name = member.getName();
        this.email = member.getEmail();
        this.picture = member.getProfile();
        this.role = member.getRole();

    }
}
