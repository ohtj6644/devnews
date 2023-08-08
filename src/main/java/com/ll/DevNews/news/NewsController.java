package com.ll.DevNews.news;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/list")
    public String getnewsList(Model model , @RequestParam(value = "page",defaultValue = "0")int page ,
                              @RequestParam(value = "kw",defaultValue = "")String kw){

        Page<News> paging = this.newsService.getList(page,kw);

        model.addAttribute("paging",paging);
        return "news_list";

    }

    @GetMapping(value = "/detail/{id}")
    public String newsDetail(Model model, @PathVariable("id") Integer id){
        News news=this.newsService.getNews(id);
        this.newsService.viewCountUp(news);
        model.addAttribute("news",news);
        return "news_detail";
    }
}

