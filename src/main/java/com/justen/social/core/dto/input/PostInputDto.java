package com.justen.social.core.dto.input;

import java.util.ArrayList;
import java.util.List;

import com.justen.social.domain.model.Media;
import com.justen.social.domain.model.Post;

import lombok.Data;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-social
 * @Year 2026
 *
 */
@Data
public class PostInputDto {

	private String title;
	private String description;
	private Boolean published;
	private Boolean fixed;
	private byte[] thumbImage;
	private List<MediaInputDto> medias;

	public Post toEntity() {

		Post post = new Post();

		post.setTitle(this.title);
		post.setDescription(this.description);
		post.setPublished(this.published);
		post.setFixed(this.fixed);
		post.setThumbImage(this.thumbImage);

		List<Media> medias = new ArrayList<>(this.medias.stream().map(MediaInputDto::toEntity).toList());

		medias.forEach(media -> media.setPost(post));

		post.setMedias(medias);

		return post;
	}

}
