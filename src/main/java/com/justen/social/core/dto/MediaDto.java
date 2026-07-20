package com.justen.social.core.dto;

import java.util.UUID;

import com.justen.social.domain.model.Media;

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
public class MediaDto {

	private UUID id;
	
	private byte[] content;
	
	private UUID postId;
	
	private MediaTypeDto mediaTypeDto;

	public MediaDto(Media media) {
		super();
		this.id = media.getId();
		this.content = media.getContent();
		this.postId = media.getPost().getId();
		this.mediaTypeDto = new MediaTypeDto(media.getMediaType());
	}
	
}
