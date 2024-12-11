package com.flab.mars.api.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UpdateMemberRequest {


    @NotBlank(message = "필수 항목입니다.")
    @Length(min = 2, max = 20, message = "아이디를 2~8자 사이로 입력해주세요.")
    private String name;


    @NotBlank(message = "필수 항목입니다.")
    @Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[#?!@$%^&*-]).{8,}$",
            message = "패스워드는 영문자, 숫자, 특수기호를 조합하여 최소 8자 이상을 입력하셔야 합니다."
    )
    private String pw;

    private String email;

}
