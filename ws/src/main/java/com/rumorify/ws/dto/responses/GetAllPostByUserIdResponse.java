package com.rumorify.ws.dto.responses;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAllPostByUserIdResponse {
    private Long id;
    private String content;
    private String imageUrl;
    private String videoUrl;
    private Date creationTime;
}
