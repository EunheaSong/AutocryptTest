package com.example.autocrypttest.controller;

import com.example.autocrypttest.dto.UserJoinDto;
import com.example.autocrypttest.repository.UserRepository;
import com.example.autocrypttest.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)//실제 어플리케이션을 구동할때처럼 모든 bean을 컨테이너에 등록함.
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    HttpHeaders headers;
    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp(){
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }


    @Test
    @Order(1)
    @DisplayName("아이디 중복 체크")
    void checkUsername() {
        //give

        //when
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/check/user?name=Kim123",
//                request,
                String.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        String usernameReponse = response.getBody();
        assertNotNull(usernameReponse);
    }

    @Test
    @Order(2)
    @DisplayName("닉네임 중복 체크")
    void checkNickname() {
        //give

        //when
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/check/nick?name=김크립트",
//                request,
                String.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        String UserJoinReponse = response.getBody();
        assertNotNull(UserJoinReponse);
    }

    @Test
    @Order(3)
    @DisplayName("회원가입")
    void userJoin() throws JsonProcessingException {
        //give
        UserJoinDto dto = UserJoinDto.builder()
                .username("Kim123")
                .nickname("김크립트")
                .password("qwer1234!")
                .passwordCheck("qwer1234!")
                .build();

        //when
        String requestBody = mapper.writeValueAsString(dto);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        // when
        ResponseEntity<String> response = restTemplate.postForEntity(
                "/user/join",
                request,
                String.class);

        //then

        assertEquals(HttpStatus.OK, response.getStatusCode());
        String UserJoinReponse = response.getBody();
        assertNotNull(UserJoinReponse);
//        assertEquals("SUCCESS", UserjoinReponse);
    }

    @Test
    @Order(4)
    @DisplayName("중복 아이디")
    void checkUsername중복() {
        //give

        //when
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/check/user?name=Kim123",
                String.class);

        //then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        String UserJoinReponse = response.getBody();
        assertNotNull(UserJoinReponse);
    }

    @Test
    @Order(5)
    @DisplayName("닉네임 중복")
    void checkNickname중복() {
        //give

        //when
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/check/nick?name=김크립트",
                String.class);

        //then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        String UserJoinReponse = response.getBody();
        assertNotNull(UserJoinReponse);
    }

    @Test
    @Order(6)
    @DisplayName("로그인")
    void userLogin() throws JsonProcessingException {
        //give
        UserDto dto = UserDto.builder()
                .username("Kim123")
                .password("qwer1234!")
                .build();
        //when
        String requestBody = mapper.writeValueAsString(dto);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.getForEntity(
                "/user/login",
                String.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        String UserDeleteReponse = response.getBody();
        assertNotNull(UserDeleteReponse);

    }


    @Test
    @Order(7)
    @DisplayName("회원 탈퇴")
    void userResign() {
        //give

        //when
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/resign/Kim123",
                String.class);
        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        String UserDeleteReponse = response.getBody();
        assertNotNull(UserDeleteReponse);
    }


    @Builder
    static class UserDto {
        String username;
        String password;
    }
}