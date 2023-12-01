package com.hit.community.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class JwtUtil {

    private final JwtProperties jwtProperties;
    private final RefreshTokenService tokenService;
    private String secretKey;


    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(jwtProperties.getSecret().getBytes())
    }

    public GeneratedToken generalToken(String email, String role){
        String refreshToken = generateRefreshToken(email, role);
        String accessToken = generateAccessToken(email, role);

        tokenService.saveTokenInfo(email, refreshToken, accessToken);
        return new GeneratedToken(accessToken, refreshToken);
    }

    public String generateRefreshToken(String email, String role){
        long refreshPeriod = 1000L + 60L + 60L * 24L * 14; // 2주

        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", role);

        Date now = new Date();


        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshPeriod))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

    }

    public String generateAccessToken(String email, String role){
        long tokenPeriod = 1000L * 60L * 30L;
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", role);

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenPeriod))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean verifyToken(String token){
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build().parseClaimsJws(token);

            return claims.getBody()
                    .getExpiration()
                    .after(new Date());
        }catch (Exception e){
            return false;
        }
    }

    public String getUid(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String getRole(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("role", String.class);
    }
}
