package com.sparta.lck_news.service;


import com.sparta.lck_news.dto.PostCreateRequest;
import com.sparta.lck_news.dto.PostResponse;
import com.sparta.lck_news.entity.Post;
import com.sparta.lck_news.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@AllArgsConstructor
public class PostService {


    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("잘못된 POST ID"));
        return new PostResponse(post);
    }

    @Transactional
    public Post createPost(PostCreateRequest post) {
        return postRepository.save(post.toEntity());
    }

    @Transactional
    public Post updatePost(Long id, PostCreateRequest updatedPost) {
        Post post = postRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("잘못된 Post ID"));
        post.update(updatedPost.getContent());

        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

}