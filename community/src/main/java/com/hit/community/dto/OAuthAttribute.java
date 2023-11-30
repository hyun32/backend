package com.hit.community.dto;


import com.hit.community.entity.Member;
import com.hit.community.entity.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class OAuthAttribute {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private Object nameAttributeValue;
    private String name;
    private String email;
    private String profile;
    private String provider;
    private Role role;

    @Builder
    public OAuthAttribute(Map<String, Object> attributes, String nameAttributeKey, Object nameAttributeValue,
                          String name, String email, String profile,Role role, String provider) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.nameAttributeValue = nameAttributeValue;
        this.name = name;
        this.email = email;
        this.profile = profile;
        this.role = role;
        this.provider = provider;
    }

    public static OAuthAttribute of(
            String provider,
            String nameAttributeKey,
            Map<String, Object> attributes
    ){
        
        switch (provider){
            case "google":
                return ofGoogle(provider, nameAttributeKey, attributes);
            case "naver":
                return ofNaver(provider, attributes);
            default: throw new RuntimeException();
        }

    }

    private static OAuthAttribute ofGoogle(
            String provider,
            String userNameAttributeName,
            Map<String, Object> attributes
    ){

        return OAuthAttribute.builder()
                .attributes(attributes)
                .nameAttributeKey("sub")
                .nameAttributeValue(attributes.get("sub"))
                .name((String)attributes.get("name"))
                .email((String)attributes.get("email"))
                .profile((String)attributes.get("picture"))
                .role(Role.USER)
                .provider(provider)
                .build();
    }



    private static OAuthAttribute ofNaver(
            String provider,
            Map<String, Object> attributes
    ){

        Map<String, Object> response = (Map<String, Object>)attributes.get("response");

        return OAuthAttribute.builder()
                .attributes(response)
                .nameAttributeKey("response")
                .nameAttributeValue(response)
                .name((String)response.get("name"))
                .email((String)response.get("email"))
                .profile((String)response.get("profile_image"))
                .role(Role.USER)
                .provider(provider)
                .build();
    }

    public Member toEntity(){
        return Member.builder()
                .name(name)
                .email(email)
                .profile(profile)
                .role(role)
                .build();
    }

    public Map<String, Object> convertToMap() {

        Map<String, Object> map = new HashMap<>();
        map.put(nameAttributeKey, nameAttributeValue);
        map.put("name", name);
        map.put("email", email);
        map.put("profile", profile);
        return map;
    }


}
