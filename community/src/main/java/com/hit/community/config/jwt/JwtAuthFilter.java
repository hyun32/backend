package com.hit.community.config.jwt;

import com.hit.community.dto.jwt.SecurityUserDto;
import com.hit.community.entity.Member;
import com.hit.community.entity.Role;
import com.hit.community.repository.MemberRepository;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

//    private final JwtUtil jwtUtil;
//    private final MemberRepository memberRepository;
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        String authorization = request.getHeader("Authorization");
//
//        if(StringUtils.hasText(authorization)){
//            doFilter(request, response, filterChain);
//            return;
//        }
//
//        if(!jwtUtil.verifyToken(authorization)){
//            throw new JwtException("Access Token 만료");
//        }
//
//        if(jwtUtil.verifyToken(authorization)){
//            Member member = memberRepository.findByEmail(jwtUtil.getUid(authorization))
//                    .orElseThrow(IllegalAccessError::new);
//
//            SecurityUserDto userDto = SecurityUserDto.of(
//                    member.getEmail(),
//                    member.getName(),
//                    member.getProfile(),
//                    Role.USER
//            );
//
//            Authentication auth = getAuthentication(userDto);
//            SecurityContextHolder.getContext().setAuthentication(auth);
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    private static Authentication getAuthentication(SecurityUserDto userDto) {
//        return new UsernamePasswordAuthenticationToken(userDto, "",
//                List.of(new SimpleGrantedAuthority(userDto.role().name())));
//    }
}
