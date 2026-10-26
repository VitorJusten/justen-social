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

import jakarta.transaction.Transactional;

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
				JOIN FETCH p.profile prof
				LEFT JOIN FETCH p.medias m
				LEFT JOIN FETCH m.mediaType
				WHERE p.published IS TRUE
				AND (p.blocked IS FALSE OR p.blocked IS NULL)
				ORDER BY p.fixed DESC, p.createdAt DESC
			""")
    Page<PostSummaryDto> findAllPosts(Pageable pageable, String filters);
	
	/**
	 * 
	 * @param pageable
	 * @param authorName
	 * @return
	 */
	@Query("""
			SELECT DISTINCT p
				FROM Post p
				JOIN FETCH p.profile prof
				LEFT JOIN FETCH p.medias m
				LEFT JOIN FETCH m.mediaType
				WHERE p.published IS TRUE
				AND (p.blocked IS FALSE OR p.blocked IS NULL)
				AND prof.userName = :paramAuthor
				ORDER BY p.fixed DESC, p.createdAt DESC
			""")
	Page<PostSummaryDto> findAllPostsByUser(Pageable pageable, @Param(value = "paramAuthor") String authorName);

	/**
	 * 
	 * @param pageable
	 * @param profileId
	 * @return
	 */
	@Query("""
			SELECT DISTINCT p
				FROM Post p
				JOIN FETCH p.profile prof
				LEFT JOIN FETCH p.medias m
				LEFT JOIN FETCH m.mediaType
				WHERE p.published IS TRUE
				AND (p.blocked IS FALSE OR p.blocked IS NULL)
				AND prof.id = :paramProfileId
				ORDER BY p.fixed DESC, p.createdAt DESC
			""")
	Page<PostSummaryDto> findAllPostsByProfile(Pageable pageable, @Param(value = "paramProfileId") UUID profileId);

	/**
	 * Posts feitos por usuários com role ADM ou DEV
	 * @param pageable
	 * @return
	 */
	@Query("""
			SELECT DISTINCT p
				FROM Post p
				JOIN FETCH p.profile prof
				LEFT JOIN FETCH p.medias m
				LEFT JOIN FETCH m.mediaType
				WHERE p.published IS TRUE
				AND (p.blocked IS FALSE OR p.blocked IS NULL)
				AND EXISTS (
					SELECT 1 FROM User u
					JOIN u.roles r
					WHERE u.id = prof.userId
					AND r.name IN ('ADM', 'DEV')
				)
				ORDER BY p.fixed DESC, p.createdAt DESC
			""")
	Page<PostSummaryDto> findAllFeaturedAdminOrDevPosts(Pageable pageable);
	
	/**
	 * 
	 * @param postId
	 */
	@Modifying
	@Transactional
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
	@Transactional
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
	@Transactional
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
	@Transactional
	@Query("""
	    update Post p
	       set p.commentsCount = p.commentsCount - 1
	     where p.id = :postId
	""")
	void decrementComments(UUID postId);

	/**
	 * 
	 * @param postId
	 */
	@Modifying
	@Transactional
	@Query("""
	    update Post p
	       set p.views = p.views + 1
	     where p.id = :postId
	""")
	void incrementViews(UUID postId);
	
}