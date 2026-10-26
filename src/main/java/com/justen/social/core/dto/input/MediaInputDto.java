package com.justen.social.core.dto.input;

import com.justen.social.core.enums.MediaTypeEnum;
import com.justen.social.domain.model.Media;
import com.justen.social.domain.model.MediaType;

import lombok.Data;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-social
 * @Year 2026
 *
 */
@Data
public class MediaInputDto {

	private byte[] content;
	private MediaTypeEnum mediaType;
	private Boolean canDownload = true;
	private String fileName;

	public Media toEntity() {

		Media m = new Media();

		m.setContent(content);
		m.setCanDownload(canDownload != null ? canDownload : true);
		m.setFileName(fileName);
		
		MediaType mt = new MediaType();
		mt.setId(mediaType.getId());
		m.setMediaType(mt);

		return m;
	}
	
}
