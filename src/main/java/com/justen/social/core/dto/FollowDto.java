package com.justen.social.core.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.justen.social.core.enums.FollowStatusEnum;
import com.justen.social.domain.model.Follow;

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
public class FollowDto {

	private UUID id;
	private ProfileSummaryDto follower;
	private ProfileSummaryDto followed;
	private FollowStatusEnum status;
	private OffsetDateTime createdAt;
	private OffsetDateTime updatedAt;

	public FollowDto(Follow follow) {
		this.id = follow.getId();
		this.follower = follow.getFollower() != null ? new ProfileSummaryDto(follow.getFollower()) : null;
		this.followed = follow.getFollowed() != null ? new ProfileSummaryDto(follow.getFollowed()) : null;
		this.status = follow.getStatus();
		this.createdAt = follow.getCreatedAt();
		this.updatedAt = follow.getUpdatedAt();
	}

}