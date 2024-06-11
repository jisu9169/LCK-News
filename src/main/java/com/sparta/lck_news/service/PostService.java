package com.sparta.lck_news.service;


import com.sparta.lck_news.dto.PostCreateRequest;
import com.sparta.lck_news.dto.PostResponse;
import com.sparta.lck_news.entity.Post;
import com.sparta.lck_news.exception.CommonException;
import com.sparta.lck_news.exception.ErrorStatus;
import com.sparta.lck_news.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PostResponse getPostById(Long id) {
        Post post = findPostById(id);
        return new PostResponse(post);
    }

    @Transactional
    public Post createPost(PostCreateRequest post) {
        return postRepository.save(post.toEntity());
    }

    @Transactional
    public Post updatePost(Long id, PostCreateRequest updatedPost) {
        Post post = findPostById(id);
        post.update(updatedPost.getContent());

        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public Post findPostById(Long id) {
        return  postRepository.findById(id).orElseThrow(()-> new CommonException(
            ErrorStatus.POST_ID_NOT_FOUND));
    }

}