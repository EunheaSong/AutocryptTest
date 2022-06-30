package com.example.autocrypttest.controller;

import com.example.autocrypttest.dto.PostDto;
import com.example.autocrypttest.dto.PostResponseDto;
import com.example.autocrypttest.exHandler.Result;
import com.example.autocrypttest.exHandler.Status;
import com.example.autocrypttest.security.UserDetailsImpl;
import com.example.autocrypttest.service.PostService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/posts")
@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //게시글 등록
    @PostMapping("/write")
    public ResponseEntity<Result> newPosts(@RequestBody PostDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long postId = postService.newPost(dto, userDetails);
        Result result = new Result(Status.SUCCESS, postId + "New post completed");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //게시글 수정
    @PutMapping("/edit/{postId}")
    public ResponseEntity<Result> editPosts(@PathVariable Long postId, @RequestBody PostDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.editPost(postId, dto, userDetails);
        Result result = new Result(Status.SUCCESS, postId + "Update post completed");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //게시글 삭제
    @DeleteMapping("/remove/{postId}")
    public ResponseEntity<Result> removePosts(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.removePost(postId, userDetails);
        Result result = new Result(Status.SUCCESS, postId + "Remove post completed");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //특정 게시물 조회
    @GetMapping("/load/{postId}")
    public ResponseEntity<PostResponseDto> getPosts(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostResponseDto postDto = postService.getPost(postId, userDetails);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    //전체 게시물 조회 - 페이징
    @GetMapping("/board")
    public ResponseEntity<List<PostResponseDto>> getBoard(Pageable pageable) {
        List<PostResponseDto> postDto = postService.getPostList(pageable);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }
}
