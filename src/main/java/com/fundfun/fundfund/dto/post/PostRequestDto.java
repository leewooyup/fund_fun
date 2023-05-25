package com.fundfun.fundfund.dto.post;

public class PostRequestDto {
    private String title;
    private String contentPost;

    public PostRequestDto() {
    }

    public PostRequestDto(String title, String contentPost) {
        this.title = title;
        this.contentPost = contentPost;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentPost() {
        return contentPost;
    }

    public void setContentPost(String contentPost) {
        this.contentPost = contentPost;
    }

    @Override
    public String toString() {
        return "PostRequestDTO{" +
                "title='" + title + '\'' +
                ", contentPost='" + contentPost + '\'' +
                '}';
    }
}
