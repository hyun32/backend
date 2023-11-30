package com.hit.community.service;

import com.hit.community.dto.OAuthAttribute;
import com.hit.community.entity.Member;
import com.hit.community.entity.Role;
import com.hit.community.repository.MemberRepository;
import com.hit.community.session.UserSession;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate =
                new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);


        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttribute oAuthAttribute =
                OAuthAttribute.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        updateOrSave(oAuthAttribute);
        Map<String, Object> convertToMap = oAuthAttribute.convertToMap();

        return new DefaultOAuth2User(
                    Collections.singleton(
                            new SimpleGrantedAuthority(Role.USER.name())
                    ),
                convertToMap,
                userNameAttributeName
            );
    }

    private void updateOrSave(OAuthAttribute authAttribute) {
        Optional<Member> opMember = memberRepository.findByEmail(authAttribute.getEmail());

        Member member = opMember.map(entity -> entity.update(
                authAttribute.getName(),
                authAttribute.getEmail(),
                authAttribute.getProfile(),
                Role.USER
                )
        ).orElse(authAttribute.toEntity());
        memberRepository.save(member);
        httpSession.setAttribute("member", new UserSession(member));

    }
}
