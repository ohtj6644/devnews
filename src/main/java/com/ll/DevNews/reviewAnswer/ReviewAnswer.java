package com.ll.DevNews.reviewAnswer;


import com.ll.DevNews.review.Review;
import com.ll.DevNews.user.SiteUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ReviewAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(columnDefinition = "text")
    private String content;



        private LocalDateTime createDate;

        @ManyToOne
        private Review review;
        // 질문과 연결된 상품

        @ManyToOne
        private SiteUser author;

    }
