package com.justen.social.domain.service;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.justen.social.core.utils.SecurityUtils;
import com.justen.social.domain.exception.BusinessException;
import com.justen.social.domain.exception.EntityNotFoundException;
import com.justen.social.domain.model.Profile;
import com.justen.social.domain.repository.ProfileRepository;

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
public class ProfileService {

	private final ProfileRepository profileRepository;
	private final SecurityUtils securityUtils;

	public Profile create(Profile profile) {
		UUID userId = securityUtils.getLoggedUserId();
		String userName = securityUtils.getLoggedUsername();

		if (userId != null && profileRepository.existsByUserId(userId)) {
			throw new BusinessException("profileAlreadyExistsForUser");
		}

		profile.setUserId(userId);
		profile.setUserName(userName);
		profile.setCreatedAt(OffsetDateTime.now());

		return profileRepository.save(profile);
	}

	public Profile getById(UUID id) {
		return profileRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("profileNotFound"));
	}

	public Profile getByUserId(UUID userId) {
		return profileRepository.findByUserId(userId)
				.orElseThrow(() -> new EntityNotFoundException("profileNotFound"));
	}

	public Profile getByUserName(String userName) {
		return profileRepository.findByUserName(userName)
				.orElseThrow(() -> new EntityNotFoundException("profileNotFound"));
	}

	public Profile getMyProfile() {
		UUID userId = securityUtils.getLoggedUserId();
		if (userId != null) {
			return profileRepository.findByUserId(userId)
					.orElseThrow(() -> new EntityNotFoundException("profileNotFound"));
		}
		String userName = securityUtils.getLoggedUsername();
		return profileRepository.findByUserName(userName)
				.orElseThrow(() -> new EntityNotFoundException("profileNotFound"));
	}

	public Page<Profile> getAll(Pageable pageable) {
		return profileRepository.findAll(pageable);
	}

	public Profile update(UUID id, Profile input) {
		Profile profile = getById(id);

		BeanUtils.copyProperties(input, profile, "id", "createdAt", "userId", "userName");
		profile.setUpdatedAt(OffsetDateTime.now());

		return profileRepository.save(profile);
	}

	public void delete(UUID id) {
		profileRepository.deleteById(id);
	}

}