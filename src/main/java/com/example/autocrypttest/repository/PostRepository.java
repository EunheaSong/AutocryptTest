package com.example.autocrypttest.repository;

import com.example.autocrypttest.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByOrderByIdDesc(Pageable pageable);

    Page<Post> findAllByLockFalseOrderByIdDesc(Pageable pageable);

}
