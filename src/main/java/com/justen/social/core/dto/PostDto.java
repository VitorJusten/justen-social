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
public class PostDto {

	private UUID id;
	private ProfileSummaryDto profile;
	private String title;
	private String description; //artigo!
	private Boolean published = false;
	private Boolean fixed = false;
	private OffsetDateTime createdAt;
	private OffsetDateTime updatedAt;
	private byte[] thumbImage;
	private List<MediaDto> medias;
    
    public PostDto(Post post) {

        this.id = post.getId();
        this.profile = post.getProfile() != null ? new ProfileSummaryDto(post.getProfile()) : null;
        this.title = post.getTitle();
        this.description = post.getDescription();
        this.published = post.getPublished();
        this.fixed = post.getFixed();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.thumbImage = post.getThumbImage();
        this.medias = post.getMedias()
                .stream()
                .map(MediaDto::new)
                .collect(Collectors.toList());

    }
	
}
