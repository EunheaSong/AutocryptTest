package com.example.autocrypttest.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(indexes = @Index(name = "userIndex", columnList = "id"))
public class User extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String username; //회원 ID

    @Column(nullable = false)
    private String password; //회원 비밀번호

    @Column(nullable = false, unique = true, length = 20)
    private String nickname; //회원 닉네임

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Post> posts = new ArrayList<>();

    public User (){
    }

    public User (String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    public void addPost (Post post){
        this.posts.add(post);
    }
}
