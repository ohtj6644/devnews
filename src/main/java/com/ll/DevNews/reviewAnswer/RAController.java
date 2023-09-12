package com.ll.DevNews.reviewAnswer;


import com.ll.DevNews.review.Review;
import com.ll.DevNews.user.SiteUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import com.ll.DevNews.review.ReviewService;
import com.ll.DevNews.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.Valid;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class RAController {

    private final RAService raService;
    private final UserService userService;
    private final ReviewService reviewService;
    private int reviewId;


    @GetMapping("/answer/list/{reviewId}")
    public String list(Model model,
                       @PathVariable("reviewId") int reviewId,
                       @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<ReviewAnswer> paging = this.raService.getListByReviewId(reviewId, page);
        model.addAttribute("paging", paging);
        model.addAttribute("reviewId",reviewId);
        return "question_list";
    }




    @PreAuthorize("isAuthenticated()")
    @GetMapping("/answer/create")
    public String questionCreate( @RequestParam(value = "productId") int reviewId, Model model, RAForm raForm) {
        // productId를 사용하여 ProductService에서 Product 정보를 가져옴

        // QuestionForm 생성 후 product.id 값 추가
        raForm.setReviewId(reviewId);

        /* Thymeleaf &#xD15C;&#xD50C;&#xB9BF;&#xC73C;&#xB85C; QuestionForm&#xACFC; Product &#xD568;&#xAED8; &#xC804;&#xB2EC; */
        model.addAttribute("raForm", raForm);
        model.addAttribute("reviewId",reviewId);

        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/answer/create")
    public String questionCreate(@RequestParam(value = "productId") int reviewId, @Valid RAForm raForm,
                                 BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.raService.create(raForm.getContent(), siteUser, reviewId);
        return "redirect:/answer/list/1";
    }

}
