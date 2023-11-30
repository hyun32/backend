package com.hit.community.service;

import com.hit.community.dto.MemberResponse;
import com.hit.community.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponse getMember(Long id){
        return memberRepository.findById(id).map(MemberResponse::fromMemberResponse)
                .orElseThrow(RuntimeException::new);
    }


}
