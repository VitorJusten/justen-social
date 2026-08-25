package com.justen.social.core.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.justen.social.domain.model.UserLike;

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
public class UserLikeDto {

    private UUID postId;
    private UUID profileId;
    private ProfileSummaryDto profile;
    private OffsetDateTime createdAt;

    public UserLikeDto(UserLike userLike) {

        this.postId = userLike.getId() != null ? userLike.getId().getPostId() : null;
        this.profileId = userLike.getId() != null ? userLike.getId().getProfileId() : null;
        this.profile = userLike.getProfile() != null ? new ProfileSummaryDto(userLike.getProfile()) : null;
        this.createdAt = userLike.getCreatedAt();
    }

}
