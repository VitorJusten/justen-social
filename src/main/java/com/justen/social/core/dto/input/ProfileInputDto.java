package com.justen.social.core.dto.input;

import com.justen.social.core.enums.ProfileStatusEnum;
import com.justen.social.domain.model.Profile;

import lombok.Data;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-social
 * @Year 2026
 *
 */
@Data
public class ProfileInputDto {

	private String name;
	private ProfileStatusEnum status;
	private byte[] perfilImage;
	private String bio;

	public Profile toEntity() {
		Profile profile = new Profile();
		profile.setName(this.name);
		if (this.status != null) {
			profile.setStatus(this.status);
		}
		profile.setPerfilImage(this.perfilImage);
		profile.setBio(this.bio);
		return profile;
	}

}