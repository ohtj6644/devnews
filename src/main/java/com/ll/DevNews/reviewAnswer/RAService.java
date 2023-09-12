package com.ll.DevNews.reviewAnswer;


import com.ll.DevNews.review.Review;
import com.ll.DevNews.review.ReviewService;
import com.ll.DevNews.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RAService {

    final private RARepository raRepository;
    final private ReviewService reviewService;



    public Page<ReviewAnswer> getListByReviewId(int reviewId, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.raRepository.findByReviewId(reviewId, pageable);
    }

    public void create(String content, SiteUser siteUser, int reviewId) {
        ReviewAnswer ra = new ReviewAnswer();
        Review review = reviewService.getReview(reviewId);
        ra.setCreateDate(LocalDateTime.now());
        ra.setContent(content);
        ra.setAuthor(siteUser);
        ra.setReview(review);
        this.raRepository.save(ra);
    }
}
