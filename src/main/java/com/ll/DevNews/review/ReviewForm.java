package com.ll.DevNews.review;

import com.ll.DevNews.news.News;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewForm {

    @NotEmpty(message = "제목은 필수항목입니다.")
    @Size(max = 200)
    private String subject;

    @NotEmpty(message = "내용은 필수항목입니다.")
    private String content;

    private News article;
}
