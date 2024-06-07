package com.sparta.lcknews.dto;


import com.sparta.lcknews.model.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostCreateRequest {
    private String content;

    public Post toEntity(){
        return new Post(content);
    }
}
