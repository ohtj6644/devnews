package com.ll.DevNews.news;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {

    Page<News> findAll(Specification<News> spec, Pageable pageable);

    News findById(int id);
}
