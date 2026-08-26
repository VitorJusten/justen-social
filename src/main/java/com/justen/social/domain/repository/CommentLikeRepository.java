package com.justen.social.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.justen.social.domain.model.CommentLike;
import com.justen.social.domain.model.CommentLikeId;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-social
 * @Year 2026
 *
 */
public interface CommentLikeRepository extends JpaRepository<CommentLike, CommentLikeId> {

    boolean existsByIdCommentIdAndIdProfileId(UUID commentId, UUID profileId);

    void deleteByIdCommentIdAndIdProfileId(UUID commentId, UUID profileId);

    long countByIdCommentId(UUID commentId);

    List<CommentLike> findAllByIdCommentId(UUID commentId);

}