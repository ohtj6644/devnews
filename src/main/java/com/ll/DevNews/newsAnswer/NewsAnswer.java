package com.ll.DevNews.newsAnswer;


import com.ll.DevNews.news.News;
import com.ll.DevNews.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class NewsAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "text")
    private String content;


    private LocalDateTime createDate;

    @ManyToOne
    private News article;

    @ManyToOne
    private SiteUser author;
}
