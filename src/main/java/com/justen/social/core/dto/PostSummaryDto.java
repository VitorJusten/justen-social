package com.justen.social.core.dto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.justen.social.domain.model.Post;

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
public class PostSummaryDto {

	private UUID id;
    private String authorName;
    private String title;
    private String description;
    private Boolean published;
    private Boolean fixed;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
	private byte[] thumbImage;
    private List<MediaSummaryDto> medias;
    
    public PostSummaryDto(Post post) {

        this.id = post.getId();
        this.authorName = post.getAuthorName();
        this.title = post.getTitle();
        this.description = post.getDescription();
        this.published = post.getPublished();
        this.fixed = post.getFixed();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.thumbImage = post.getThumbImage();
        this.medias = post.getMedias()
                .stream()
                .map(MediaSummaryDto::new)
                .collect(Collectors.toList());

    }
	
}
