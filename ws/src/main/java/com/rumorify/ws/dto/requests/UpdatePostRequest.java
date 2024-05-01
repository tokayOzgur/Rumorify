package com.rumorify.ws.dto.requests;

import com.rumorify.ws.validation.file.FileType;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePostRequest {

    @Size(max = 5000, message = "{rumorify.constraints.post.size}")
    private String content;
    @FileType(types = { "jpeg", "png" })
    private String imageUrl;
    @FileType(types = { "mp4" })
    private String videoUrl;

}
