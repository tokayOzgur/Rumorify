package com.rumorify.ws.dto.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetPostByIdResponse {
    private String content;
    private String imageUrl;
    private String videoUrl;
    private String createdAt;
    private int userId;
}
