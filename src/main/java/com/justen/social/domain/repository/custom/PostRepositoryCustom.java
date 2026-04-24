package com.justen.social.domain.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.justen.social.domain.model.dto.PostDto;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-social
 * @Year 2026
 *
 */
public interface PostRepositoryCustom {

	/**
	 * 
	 * @param pageable
	 * @param filters
	 * @return
	 */
    Page<PostDto> findAllPosts(Pageable pageable, String filters);

    /**
     * 
     * @param pageable
     * @param filters
     * @return
     */
    Page<PostDto> findUnpublishedPosts(Pageable pageable, String filters);

}
