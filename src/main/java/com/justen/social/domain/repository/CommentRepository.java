package com.justen.social.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.justen.social.domain.model.Comment;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-social
 * @Year 2026
 *
 */
public interface CommentRepository extends JpaRepository<Comment, UUID> {

	/**
	 * 
	 * @param postId
	 * @return
	 */
    List<Comment> findAllByPostIdOrderByCreatedAtAsc(UUID postId);

    /**
     * 
     * @param commentFatherId
     * @return
     */
    List<Comment> findAllByCommentFatherIdOrderByCreatedAtAsc(UUID commentFatherId);

    /**
     * 
     * @param postId
     * @return
     */
    long countByPostId(UUID postId);

}