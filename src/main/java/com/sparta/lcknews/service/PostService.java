package com.sparta.lcknews.service;


import com.sparta.lcknews.dto.PostCreateRequest;
import com.sparta.lcknews.dto.PostResponse;
import com.sparta.lcknews.model.Post;
import com.sparta.lcknews.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

   public PostService(PostRepository postRepository) {
       this.postRepository = postRepository;
   }

   @Transactional
    public List<Post> getAllPost(){
       return postRepository.findAll();
   }

   @Transactional
   public PostResponse getPostById(Long id){
       Post post  = postRepository.findById(id).orElseThrow(()->new IllegalArgumentException("잘못된 Post ID"));
       return new PostResponse(post);
   }

   @Transactional
    public Post createPost(PostCreateRequest post){
       return postRepository.save(post.toEntity());
   }

   @Transactional
    public Post updatePost(Long id, PostCreateRequest updatedpost){
       Post post = postRepository.findById(id).orElseThrow(()->new IllegalArgumentException("잘못된 POST ID"));
       post.update(updatedpost.getContent());
       return postRepository.save(post);
   }

   @Transactional
    public void deletePost(Long id){
       postRepository.deleteById(id);
   }
}
