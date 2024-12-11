package com.flab.mars.db.repository;

import com.flab.mars.db.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByEmail(String email);

    long countByEmail(String email);
}
