package com.sparta.lcknews.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostListResponse {

    private String name;  //작성자 이름

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}
