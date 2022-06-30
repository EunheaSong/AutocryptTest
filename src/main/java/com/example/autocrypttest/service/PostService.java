package com.example.autocrypttest.service;

import com.example.autocrypttest.exHandler.UserNotMatchException;
import com.example.autocrypttest.dto.PostDto;
import com.example.autocrypttest.dto.PostResponseDto;
import com.example.autocrypttest.entity.Post;
import com.example.autocrypttest.repository.PostRepository;
import com.example.autocrypttest.security.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    //게시글 등록
    @Transactional
    public Long newPost(PostDto dto, UserDetailsImpl userDetails) {
        if(dto.getTitle().isEmpty()||dto.getContent().isEmpty()){
            throw new NullPointerException("Add Post Exception.");
        }
        Post post = new Post(dto, userDetails.getUser());
        postRepository.save(post);
        return post.getId();
    }

    //게시글 수정
    @Transactional
    public Long editPost(Long postId, PostDto dto, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NoSuchElementException("Not Found Post.")
        );
        if (!userDetails.getUsername().equals(post.getUser().getUsername())) {
            throw new UserNotMatchException(userDetails.getUsername(), post.getUser().getUsername());
        }
        if(dto.getTitle().isEmpty()||dto.getContent().isEmpty()){
            throw new NullPointerException("Edit Post Exception.");
        }
        post.editPost(dto);
        postRepository.save(post);
        return post.getId();
    }

    //게시글 삭제
    @Transactional
    public void removePost(Long postId, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NoSuchElementException("Not Found Post.")
        );
        if (!userDetails.getUsername().equals(post.getUser().getUsername())) {
            throw new UserNotMatchException(userDetails.getUsername(), post.getUser().getUsername());
        }
        postRepository.delete(post);
    }

    //특정 게시글 조회
    public PostResponseDto getPost(Long postId, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NoSuchElementException("Not Found Post.")
        );
        //게시물에 lock = true 인 경우 , 작성자 본인만 조회가능.
        if (post.isLock()) {
            if (!userDetails.getUsername().equals(post.getUser().getUsername())) {
                throw new IllegalArgumentException("Usernames do not match.");
            }
        }
        return new PostResponseDto(post);
    }

    //전체 게시글 조회
    public List<PostResponseDto> getPostList(Pageable pageable) {
        Page<Post> postList = postRepository.findAllByLockFalseOrderByIdDesc(pageable);
        List<PostResponseDto> dtoList = new ArrayList<>();
        for(Post p : postList){
            PostResponseDto dto = new PostResponseDto(p);
            dtoList.add(dto);
        }
        return dtoList;
    }

}
