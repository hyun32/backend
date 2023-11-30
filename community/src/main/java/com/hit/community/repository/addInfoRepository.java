package com.hit.community.repository;


import com.hit.community.entity.AddInfo;
import com.hit.community.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface addInfoRepository extends JpaRepository<AddInfo, Long> {

    @Query("select m from Member m join fetch m.addInfo")
    Optional<Member> findByEmail(String email);
}
