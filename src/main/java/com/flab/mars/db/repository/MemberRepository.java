package com.flab.mars.db.repository;

import com.flab.mars.db.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface MemberRepository extends JpaRepository<MemberEntity, Long> {


    @Modifying
    @Query("DELETE FROM MemberEntity m WHERE m.id = :id")
    int deleteByIdAndReturnCount(@Param("id") Long id);

    long countByEmail(String email);
}
