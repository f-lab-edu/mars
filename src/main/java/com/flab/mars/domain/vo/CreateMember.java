package com.flab.mars.domain.vo;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Builder
@Getter
public class CreateMember {
    private Long id;

    private String name;

    private String email;

    private String pw;

    private LocalDateTime lastAccessTime;

    private LocalDateTime joinTime;


}
