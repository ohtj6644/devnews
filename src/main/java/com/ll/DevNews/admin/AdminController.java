package com.ll.DevNews.admin;


import com.ll.DevNews.news.NewsForm;
import com.ll.DevNews.news.NewsService;
import com.ll.DevNews.user.SiteUser;
import com.ll.DevNews.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {


    private final NewsService newsService;

    private final UserService userService;

    @GetMapping("/news/create")
    public String newsCreate(NewsForm newsForm){
    return "news_form";
    }
    @PostMapping("/news/create")
    public String articleCreate(@Valid NewsForm newsForm,
                                BindingResult bindingResult,
                                Principal principal,
                                @RequestParam("files") MultipartFile[] files) throws Exception {
        if (bindingResult.hasErrors()) {
            return "news_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());

        this.newsService.create(newsForm, siteUser, files);
        return "redirect:/news/list";
    }
}
