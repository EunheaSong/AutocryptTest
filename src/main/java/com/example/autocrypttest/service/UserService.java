package com.example.autocrypttest.service;

import com.example.autocrypttest.dto.UserJoinDto;
import com.example.autocrypttest.entity.User;
import com.example.autocrypttest.repository.UserRepository;
import com.example.autocrypttest.security.UserDetailsImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //아이디 중복 체크
    public String checkUsername (String username) {
        if(userRepository.findByUsername(username).isPresent()){
            throw new IllegalArgumentException("already exists ID.");
        }
        return username;
    }

    //닉네임 중복 체크
    public String checkNickname (String nickname) {
        if(userRepository.findByNickname(nickname).isPresent()){
            throw new IllegalArgumentException("already exists Nickname");
        }
        return nickname;
    }

    //회원 가입
    public void registerUser (UserJoinDto dto){
        String password = passwordEncoder.encode(dto.getPassword());
        String passwordCheck = passwordEncoder.encode(dto.getPasswordCheck());
        if (!passwordEncoder.matches(dto.getPassword(),passwordCheck)){
            throw new IllegalArgumentException("Passwords do not match.");
        } else {
            User user = new User(dto.getUsername(), password, dto.getNickname());
            userRepository.save(user);
        }
    }
    //회원 탈퇴
    public void secessionUser(String username, UserDetailsImpl userDetails){
        User user = userRepository.findByUsername(username).orElseThrow(
                ()-> new NoSuchElementException("No matching users.")
        );
        if(!username.equals(userDetails.getUsername())){
            throw new IllegalArgumentException("User does not match.");
        }
        userRepository.delete(user);
    }


    //아이디 규칙 -> 영문 필수 . 5~15자 내외. 숫자 가능.
    //닉네임 규칙 -> 영문, 한글, 숫자 가능 2~10자 내외.
//비밀번호 규칙 -> 8~20자 내외 . 특수문자 최소1개 + 숫자 + 알파벳(대소문자구분없음)로 입력.

}
