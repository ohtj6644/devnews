package com.ll.DevNews.review;

import com.ll.DevNews.news.News;
import com.ll.DevNews.news.NewsService;
import com.ll.DevNews.user.SiteUser;
import com.ll.DevNews.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ReviewController {

final private NewsService newsService;
final private UserService userService;
final private ReviewService reviewService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/review/create/{id}")
    public String reviewCreate(ReviewForm reviewForm, @PathVariable("id") int id, Model model){

        News news = this.newsService.getNews(id);
        reviewForm.setArticle(news);

        model.addAttribute("reviewForm",reviewForm);
        model.addAttribute("news1",news);
        return "review_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/review/create/{id}")
    public String reviewCreate(ReviewForm reviewForm, BindingResult bindingResult ,@PathVariable("id") int id, Principal principal){
        News news = this.newsService.getNews(id);

        if (bindingResult.hasErrors()) {
            return "news_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());

        this.reviewService.create(reviewForm, siteUser,news);
        return "redirect:/news/list";
    }

    @GetMapping("/review/detail/{id}")
    public String reviewDetail(Model model, @PathVariable("id")int id){
        Review review = this.reviewService.getReview(id);

        model.addAttribute("review",review);
        return "review_detail";

    }

    @GetMapping("/review/list")
    public  String reviewList(Model model , @RequestParam(value = "page",defaultValue = "0")int page){


        Page<Review> paging = this.reviewService.getList(page);

        model.addAttribute("paging",paging);
        return "review_list";

    }
}
