package com.justen.social.domain.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
				WHERE p.published IS TRUE
				ORDER BY p.fixed DESC, p.createdAt DESC
			""")
    Page<PostSummaryDto> findAllPosts(Pageable pageable, String filters);
	
	/**
	 * 
	 * @param pageable
	 * @param username
	 * @return
	 */
	@Query("""
			SELECT DISTINCT p
				FROM Post p
				LEFT JOIN FETCH p.medias m
				LEFT JOIN FETCH m.mediaType
				WHERE p.published IS TRUE
				AND p.authorName = :paramAuthor
				ORDER BY p.fixed DESC, p.createdAt DESC
			""")
	Page<PostSummaryDto> findAllPostsByUser(Pageable pageable, @Param(value = "paramAuthor") String authorName);
	
	/**
	 * 
	 * @param postId
	 */
	@Modifying
	@Query("""
	    update Post p
	       set p.likesCount = p.likesCount + 1
	     where p.id = :postId
	""")
	void incrementLikes(UUID postId);

	/**
	 * 
	 * @param postId
	 */
	@Modifying
	@Query("""
	    update Post p
	       set p.likesCount = p.likesCount - 1
	     where p.id = :postId
	""")
	void decrementLikes(UUID postId);

	/**
	 * 
	 * @param postId
	 */
	@Modifying
	@Query("""
	    update Post p
	       set p.commentsCount = p.commentsCount + 1
	     where p.id = :postId
	""")
	void incrementComments(UUID postId);
	
	/**
	 * 
	 * @param postId
	 */
	@Modifying
	@Query("""
	    update Post p
	       set p.commentsCount = p.commentsCount - 1
	     where p.id = :postId
	""")
	void decrementComments(UUID postId);
	
}