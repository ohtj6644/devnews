package com.ll.DevNews.news;


import com.ll.DevNews.DataNotFoundException;
import com.ll.DevNews.newsAnswer.NewsAnswer;
import com.ll.DevNews.user.SiteUser;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    private Specification<News> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<News> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                Join<News, SiteUser> u1 = q.join("author", JoinType.LEFT);
                Join<News, NewsAnswer> a = q.join("answerList", JoinType.LEFT);
                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
                        cb.like(q.get("content"), "%" + kw + "%"),      // 내용
                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자
                        cb.like(a.get("content"), "%" + kw + "%"));   // 답변 작성자
            }
        };
    }
    public Page<News> getList(int page, String kw) {
        List<Sort.Order> sorts= new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page,4,Sort.by(sorts));
        Specification<News> spec = search(kw);
        return this.newsRepository.findAll(spec,pageable);
    }

    public News getNews1() {
        return this.newsRepository.findById(0);
    }

    public void create(NewsForm articleForm, SiteUser user, MultipartFile[] files) throws IOException {
        String projectPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "files";

        List<String> filenames = new ArrayList<>();
        List<String> filepaths = new ArrayList<>();

        for (MultipartFile file : files) {
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file.getOriginalFilename();
            String filePath = "/files/" + fileName;

            File saveFile = new File(projectPath, fileName);
            file.transferTo(saveFile);

            filenames.add(fileName);
            filepaths.add(filePath);
        }

        News article = new News();
        article.setSubject(articleForm.getSubject());
        article.setContent(articleForm.getContent());
        article.setCreateDate(LocalDateTime.now());
        article.setAuthor(user);
        article.setFilenames(filenames);
        article.setFilepaths(filepaths);
        this.newsRepository.save(article);
    }

    public void viewCountUp(News article) {

        article.setViewCount(article.getViewCount() + 1);
        this.newsRepository.save(article);
    }
    public News getNews(Integer id) {
        Optional<News> article = this.newsRepository.findById(id);
        if (article.isPresent()) {
            return article.get();
        } else {
            throw new DataNotFoundException("freeNotice not found");
        }
    }

}
