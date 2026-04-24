package com.justen.social.domain.model.dto;

import java.util.UUID;

import com.justen.social.domain.model.entity.MediaType;

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
public class MediaTypeDto {

	private UUID id;
	private String name;

	public MediaTypeDto(MediaType mediaType) {
		this.id = mediaType.getId();
		this.name = mediaType.getName();
	}
}
