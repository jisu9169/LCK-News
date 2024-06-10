package com.sparta.lck_news.controller;


import com.sparta.lck_news.dto.PostCreateRequest;
import com.sparta.lck_news.dto.PostResponse;
import com.sparta.lck_news.entity.Post;
import com.sparta.lck_news.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

  private final PostService postService;

  @GetMapping
  public List<Post> getAllPost() {
    return postService.getAllPost();
  }

  @GetMapping("/{id}")
  public PostResponse getPostById(@PathVariable Long id) {
    return postService.getPostById(id);
  }


  @PostMapping
  public Post createPost(@RequestBody PostCreateRequest postCreateRequest) {
    return postService.createPost(postCreateRequest);
  }

  @PutMapping("/{id}")
  public Post updatePost(@PathVariable Long id, @RequestBody PostCreateRequest postCreateRequest) {
    return postService.updatePost(id, postCreateRequest);
  }

  @DeleteMapping("/{id}")
  public void deletePost(@PathVariable Long id) {
    postService.deletePost(id);
  }
}
