package com.flab.mars.domain.vo;


import com.flab.mars.db.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfoVO {

    private Long id;

    private String name;

    private String email;

    private String accessToken;

    private String appKey;

    private String appSecret;

    public static MemberInfoVO createMemberInfoVO(MemberEntity memberEntity, TokenInfo token) {
        Objects.requireNonNull(memberEntity, "MemberEntity must not be null");
        Objects.requireNonNull(token, "TokenInfo must not be null");

        MemberInfoVO memberInfoVO = new MemberInfoVO();
        memberInfoVO.id = memberEntity.getId();
        memberInfoVO.name = memberEntity.getName();
        memberInfoVO.email = memberEntity.getEmail();

        memberInfoVO.accessToken = token.getAccessToken();
        memberInfoVO.appKey = token.getAppKey();
        memberInfoVO.appSecret = token.getAppSecret();

        return memberInfoVO;
    }

}
