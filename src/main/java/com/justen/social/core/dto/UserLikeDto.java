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
    private String username;
    private OffsetDateTime createdAt;

    public UserLikeDto(UserLike userLike) {

        this.postId = userLike.getId().getPostId();
        this.username = userLike.getId().getUsername();
        this.createdAt = userLike.getCreatedAt();
    }

}
