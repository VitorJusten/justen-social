package com.justen.social.domain.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.justen.social.domain.exception.BusinessException;
import com.justen.social.domain.exception.EntityNotFoundException;
import com.justen.social.domain.model.Comment;
import com.justen.social.domain.model.CommentLike;
import com.justen.social.domain.model.Profile;
import com.justen.social.domain.repository.CommentLikeRepository;
import com.justen.social.domain.repository.CommentRepository;

import lombok.AllArgsConstructor;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-social
 * @Year 2026
 *
 */
@Service
@AllArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final ProfileService profileService;

    @Transactional
    public CommentLike like(CommentLike commentLike) {

        UUID commentId = commentLike.getId().getCommentId();
        Profile profile = profileService.getMyProfile();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("commentNotFound"));

        if (commentLikeRepository.existsByIdCommentIdAndIdProfileId(commentId, profile.getId())) {
            throw new BusinessException("commentAlreadyLiked");
        }

        commentLike.setComment(comment);
        commentLike.setProfile(profile);
        commentLike.getId().setProfileId(profile.getId());
        commentLike.setCreatedAt(OffsetDateTime.now());

        commentRepository.incrementLikes(commentId);

        return commentLikeRepository.save(commentLike);
    }

    @Transactional
    public void unlike(UUID commentId) {

        Profile profile = profileService.getMyProfile();

        if (!commentRepository.existsById(commentId)) {
            throw new EntityNotFoundException("commentNotFound");
        }

        if (!commentLikeRepository.existsByIdCommentIdAndIdProfileId(commentId, profile.getId())) {
            throw new BusinessException("commentNotLiked");
        }

        commentLikeRepository.deleteByIdCommentIdAndIdProfileId(commentId, profile.getId());
        commentRepository.decrementLikes(commentId);
    }

    public List<CommentLike> getByComment(UUID commentId) {

        if (!commentRepository.existsById(commentId)) {
            throw new EntityNotFoundException("commentNotFound");
        }

        return commentLikeRepository.findAllByIdCommentId(commentId);
    }

    public boolean isLiked(UUID commentId) {

        Profile profile = profileService.getMyProfile();
        return commentLikeRepository.existsByIdCommentIdAndIdProfileId(commentId, profile.getId());
    }

}