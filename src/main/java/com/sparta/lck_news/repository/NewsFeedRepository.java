package com.sparta.lck_news.repository;

import com.sparta.lck_news.entity.NewsFeed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsFeedRepository extends JpaRepository<NewsFeed, Long> {
}