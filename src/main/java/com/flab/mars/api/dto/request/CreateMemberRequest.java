package com.flab.mars.api.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CreateMemberRequest {

    @NotBlank(message = "필수 항목입니다.")
    @Length(min = 2, max = 20, message = "아이디를 2~8자 사이로 입력해주세요.")
    private String name;

    @NotBlank(message = "필수 항목입니다.")
    @Length(min = 5, max = 40, message = "이메일은 5자 이상 40자 이하여야합니다.")
    @Email(message = "이메일 형식을 지켜주세요. (예. test@test.com)")
    private String email;

    @NotBlank(message = "필수 항목입니다.")
    @Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[#?!@$%^&*-]).{8,}$",
            message = "패스워드는 영문자, 숫자, 특수기호를 조합하여 최소 8자 이상을 입력하셔야 합니다."
    )
    private String pw;


    @NotBlank(message = "필수 항목입니다.")
    private String passwordCheck;


}
