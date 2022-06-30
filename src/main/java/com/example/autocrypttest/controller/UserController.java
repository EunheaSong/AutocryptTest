package com.example.autocrypttest.controller;

import com.example.autocrypttest.dto.UserJoinDto;
import com.example.autocrypttest.security.UserDetailsImpl;
import com.example.autocrypttest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/user/join")
    public ResponseEntity<String> userJoin (@Valid @RequestBody UserJoinDto dto){
        userService.registerUser(dto);
        return new ResponseEntity<>("Join Success", HttpStatus.OK);
    }

    //아이디 중복확인
    @GetMapping("/check/user")
    public ResponseEntity<String> checkUsername (@RequestParam("name") String username) {
        userService.checkUsername(username);
        return new ResponseEntity<>("ID check Success", HttpStatus.OK);
    }

    //닉네임 중복확인
    @GetMapping("/check/nick")
    public ResponseEntity<String> checkNickname (@RequestParam("name") String nickname) {
        userService.checkNickname(nickname);
        return new ResponseEntity<>("Nick check Success", HttpStatus.OK);
    }

    //회원 탈퇴
    @DeleteMapping("/resign/{username}")
    public ResponseEntity<String>userResign(@PathVariable String username, @AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.secessionUser(username, userDetails);
        return new ResponseEntity<>("GoodBye Success", HttpStatus.OK);
    }
}
