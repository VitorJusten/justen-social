package com.justen.social.core.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.justen.social.domain.model.CommentLike;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-social
 * @Year 2026
 *
 */
@Data
@NoArgsConstructor
public class CommentLikeDto {

    private UUID commentId;
    private UUID profileId;
    private ProfileSummaryDto profile;
    private OffsetDateTime createdAt;

    public CommentLikeDto(CommentLike commentLike) {
        this.commentId = commentLike.getId() != null ? commentLike.getId().getCommentId() : null;
        this.profileId = commentLike.getId() != null ? commentLike.getId().getProfileId() : null;
        this.profile = commentLike.getProfile() != null ? new ProfileSummaryDto(commentLike.getProfile()) : null;
        this.createdAt = commentLike.getCreatedAt();
    }

}