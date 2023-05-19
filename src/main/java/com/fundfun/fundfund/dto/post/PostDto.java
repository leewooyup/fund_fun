package com.fundfun.fundfund.dto.post;
import lombok.Data;
import lombok.Setter;
import org.w3c.dom.ls.LSOutput;

import java.util.UUID;

@Data
public class PostDto {
    //private UUID postId;
    private String title;
    private String content;
//    private int like;//
//    private String postDate;//
//    private String updateDate;//
//    private String category;//
//    private String status;//
}

