package com.example.autocrypttest.service;

import com.example.autocrypttest.dto.UserJoinDto;
import com.example.autocrypttest.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


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
    @DisplayName("아이디 중복 체크")
    void checkUsername() {
        //given
        String username = "Auto123";
        //when
        String name = userService.checkUsername(username);
        //then
        assertEquals("Auto123", name);
    }

    @Test
    @DisplayName("닉네임 중복 체크")
    void checkNickname() {
        //given
        String nickname = "아우토크립트";

        //when
        String nick = userService.checkNickname(nickname);

        //then
        assertEquals("아우토크립트", nick);
    }

    @Test
    @DisplayName("비밀번호 재확인 확인")
    void registerUser() {
        //given
        UserJoinDto dto = UserJoinDto.builder()
                .username("Auto123")
                .password("!crypt123")
                .passwordCheck("crypt123")
                .nickname("아우토크립트")
                .build();
        //when

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(dto);
        });
        //then
        assertEquals("Passwords do not match.",exception.getMessage());
    }

}