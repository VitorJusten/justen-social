package com.justen.social.core.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.justen.social.core.enums.ProfileStatusEnum;
import com.justen.social.domain.model.Profile;

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
public class ProfileDto {

	private UUID id;
	private String name;
	private ProfileStatusEnum status;
	private byte[] perfilImage;
	private String bio;
	private OffsetDateTime createdAt;
	private OffsetDateTime updatedAt;
	private String userName;
	private UUID userId;

	public ProfileDto(Profile profile) {
		this.id = profile.getId();
		this.name = profile.getName();
		this.status = profile.getStatus();
		this.perfilImage = profile.getPerfilImage();
		this.bio = profile.getBio();
		this.createdAt = profile.getCreatedAt();
		this.updatedAt = profile.getUpdatedAt();
		this.userName = profile.getUserName();
		this.userId = profile.getUserId();
	}

}