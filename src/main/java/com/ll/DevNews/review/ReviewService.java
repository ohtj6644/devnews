package com.ll.DevNews.review;

import com.ll.DevNews.DataNotFoundException;
import com.ll.DevNews.news.News;
import com.ll.DevNews.newsAnswer.NewsAnswer;
import com.ll.DevNews.reviewAnswer.ReviewAnswer;
import com.ll.DevNews.user.SiteUser;
import jakarta.persistence.criteria.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Review getReview(int id) {
        Optional<Review> product = this.reviewRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new DataNotFoundException("product not found");
        }
    }

    private Specification<Review> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Review> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                Join<Review, SiteUser> u1 = q.join("author", JoinType.LEFT);
                Join<Review, NewsAnswer> a = q.join("answerList", JoinType.LEFT);
                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
                        cb.like(q.get("content"), "%" + kw + "%"),      // 내용
                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자
                        cb.like(a.get("content"), "%" + kw + "%"));   // 답변 작성자
            }
        };
    }
    public Page<Review> getList(int page) {
        List<Sort.Order> sorts= new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page,12,Sort.by(sorts));
        return this.reviewRepository.findAll(pageable);
    }

    public Page<Review> getNewsReviewList(News news,int page) {
        List<Sort.Order> sorts= new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page,10,Sort.by(sorts));
        return this.reviewRepository.findByArticle(news , pageable);
    }


}
