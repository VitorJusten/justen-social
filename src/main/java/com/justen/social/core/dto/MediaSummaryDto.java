package com.justen.social.core.dto;

import java.util.UUID;

import com.justen.social.core.enums.MediaTypeEnum;
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
public class MediaSummaryDto {

	private UUID id;
	
	private UUID postId;
	
	private MediaTypeEnum mediaType;

	public MediaSummaryDto(Media media) {
		super();
		this.id = media.getId();
		this.postId = media.getPost().getId();
		this.mediaType = MediaTypeEnum.fromId(media.getMediaType().getId());
	}
	
}
