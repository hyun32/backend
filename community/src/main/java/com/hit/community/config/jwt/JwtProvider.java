package com.hit.community.config.jwt;

import com.hit.community.service.CustomOAuth2UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import javax.security.sasl.AuthorizeCallback;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String salt;
    private Key secretKey;
    // 만료시간 1시간
    private static final long expired = 1000L * 60 * 60;

    private final CustomOAuth2UserService userService;

    @PostConstruct
    protected void init(){
        secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
    }

    // 토큰생성
    public String createToken(String email, List<GrantedAuthority> roles){

        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", roles);

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expired))
                .compact();
    }

    // 권한 정보 획득
    // Spring Security 인증과정에서 권한확인을 위한 기능
//    public Authentication getAuthentication(String token){
//        UserDetails userDetails = userService
//    }

    
}
