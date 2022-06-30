package com.example.autocrypttest.entity;

import com.example.autocrypttest.dto.PostDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(indexes = @Index(name = "postIndex", columnList = "id"))
public class Post extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private boolean lock;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public Post (PostDto dto, User user){
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.lock = dto.isLock();
        this.user = user;
        user.addPost(this);
    }

    public void editPost (PostDto dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.lock = dto.isLock();
    }
}
