package com.justen.social.domain.model.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.justen.social.domain.model.entity.Post;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-social
 * @Year 2026
 *
 */
@Data
@NoArgsConstructor
public class PostDto {

	private UUID id;
    private UUID authorId;
    private String title;
    private String description;
    private Boolean published;
    private Boolean highlight;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private MediaTypeDto mediaType;
    
    public PostDto(Post post) {

        this.id = post.getId();
        this.authorId = post.getAuthorId();
        this.title = post.getTitle();
        this.description = post.getDescription();
        this.published = post.getPublished();
        this.highlight = post.getHighlight();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.mediaType = new MediaTypeDto(post.getMediaType());

    }
	
}
