package com.example.autocrypttest.dto;


import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Builder
@Getter
public class UserJoinDto {

    @NotBlank(message = "아이디를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])[0-9a-zA-Z]{5,15}$", message = "아이디 형식을 확인해주세요.")
    private String username; //회원 ID

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&*])[0-9a-zA-Z!@#$%^&*]{8,20}$", message = "비밀번호 형식을 확인해주세요.")
    private String password; //회원 비밀번호

    @NotBlank(message = "비밀번호 재확인을 해주세요.")
    private String passwordCheck; //비밀번호 재확인

    @NotBlank(message = "닉네임을 입력해주세요")
    @Pattern(regexp = "^[a-zA-Z가-힣0-9]{2,10}$", message = "닉네임 형식을 확인해주세요.")
    private String nickname; //회원 닉네임

}
