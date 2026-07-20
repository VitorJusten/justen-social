package com.justen.social.domain.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.justen.social.core.dto.PostSummaryDto;
import com.justen.social.domain.model.Post;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-social
 * @Year 2026
 *
 */
public interface PostRepository extends JpaRepository<Post, UUID> {

	/**
	 * 
	 * @param pageable
	 * @param filters
	 * @return
	 */
	@Query("""
			SELECT DISTINCT p
				FROM Post p
				LEFT JOIN FETCH p.medias m
				LEFT JOIN FETCH m.mediaType
			""")
    Page<PostSummaryDto> findAllPosts(Pageable pageable, String filters);
	
}