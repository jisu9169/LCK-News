package com.sparta.lck_news.repository;

import com.sparta.lck_news.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Long> {
}
