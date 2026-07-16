package com.justen.social.core.dto.input;

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
	private byte[] content;

	public Post toEntity() {

		Post post = new Post();

		post.setTitle(title);
		post.setDescription(description);
		post.setContent(content);

		return post;
	}

}
