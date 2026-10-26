package com.justen.social.core.dto;

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
public class ProfileSummaryDto {

	private UUID id;
	private String name;
	private ProfileStatusEnum status;
	private byte[] perfilImage;
	private String userName;
	private UUID userId;
	private Boolean blocked = false;

	public ProfileSummaryDto(Profile profile) {
		if (profile != null) {
			this.id = profile.getId();
			this.name = profile.getName();
			this.status = profile.getStatus();
			this.perfilImage = profile.getPerfilImage();
			this.userName = profile.getUserName();
			this.userId = profile.getUserId();
			this.blocked = profile.getBlocked() != null ? profile.getBlocked() : false;
		}
	}

}