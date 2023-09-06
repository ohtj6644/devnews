package com.ll.DevNews.news;


import com.ll.DevNews.review.Review;
import com.ll.DevNews.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;
    private final ReviewService reviewService;

    @GetMapping("/list")
    public String getnewsList(Model model , @RequestParam(value = "page",defaultValue = "0")int page ,
                              @RequestParam(value = "kw",defaultValue = "")String kw){

        Page<News> paging = this.newsService.getList(page,kw);

        model.addAttribute("paging",paging);
        return "news_list";

    }

    @GetMapping(value = "/detail/{id}")
    public String newsDetail(Model model, @PathVariable("id") Integer id, @RequestParam(value = "page",defaultValue = "0")int page){
        News news=this.newsService.getNews(id);
        this.newsService.viewCountUp(news);
        List<News> paging = this.newsService.getAList();
        Page<Review> reviewPage = this.reviewService.getNewsReviewList(news,page);

        model.addAttribute("reviewPaging",reviewPage);
        model.addAttribute("news1",news);
        model.addAttribute("paging",paging);
        return "news_detail";
    }

}

