package com.ll.DevNews.reviewAnswer;

import com.ll.DevNews.review.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RARepository extends JpaRepository<ReviewAnswer, Integer> {
    Page<ReviewAnswer> findByReviewId(int reviewId, Pageable pageable);
}
