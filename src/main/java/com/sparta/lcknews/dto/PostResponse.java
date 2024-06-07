package com.sparta.lcknews.dto;


import com.sparta.lcknews.model.Post;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse {

    private Long id;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.content = post.getContent();
    }


}
