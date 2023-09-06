package com.ll.DevNews.review;

import com.ll.DevNews.news.News;
import com.ll.DevNews.user.SiteUser;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    final private ReviewRepository reviewRepository;

    public void create(ReviewForm reviewForm, SiteUser siteUser, News news) {
        Review review = new Review();
        review.setArticle(news);
        review.setContent(reviewForm.getContent());
        review.setSubject(reviewForm.getSubject());
        review.setAuthor(siteUser);
        this.reviewRepository.save(review);

    }
}
