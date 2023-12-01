package com.hit.community.dto.jwt;


import com.hit.community.entity.Role;

public record SecurityUserDto(
        String email,
        String name,
        String profile,
        Role role
) {

    public static SecurityUserDto of(
            String email,
            String name,
            String profile,
            Role role
    ){
        return new SecurityUserDto(email, name, profile, role);
    }
}
