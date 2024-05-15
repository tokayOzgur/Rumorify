package com.rumorify.ws.dto.requests;

import com.rumorify.ws.validation.file.FileType;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePostRequest {

    @Size(max = 5000, message = "{rumorify.constraints.post.size}")
    private String content;

    @FileType(types = { "jpeg", "png" })
    private String image;

    @FileType(types = { "mp4" })
    private String video;

}
