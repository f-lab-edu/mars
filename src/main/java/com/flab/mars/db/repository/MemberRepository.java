package com.flab.mars.db.repository;

import com.flab.mars.db.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    long countByEmail(String email);
}
