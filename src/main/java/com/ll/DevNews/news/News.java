package com.ll.DevNews.news;


import com.ll.DevNews.newsAnswer.NewsAnswer;
import com.ll.DevNews.review.Review;
import com.ll.DevNews.user.SiteUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(max = 50)
    private String subject;

    private LocalDate createDate;

    @Column(columnDefinition = "text")
    private String content;

    private int viewCount;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<NewsAnswer> answerList;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<Review> reviewList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private SiteUser author;
}
