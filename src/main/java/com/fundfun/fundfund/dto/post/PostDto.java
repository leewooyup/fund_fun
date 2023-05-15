package com.fundfun.fundfund.dto.post;
import lombok.Data;
import org.w3c.dom.ls.LSOutput;

import java.util.UUID;

public class PostDto {
    private UUID voteId;
    private String title;
    private String content;
    private int like;
    private String postDate;
    private String updateDate;
    private String category;
    private String status;

}

