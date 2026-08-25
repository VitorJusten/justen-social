package com.justen.social.api.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justen.social.core.dto.ProfileDto;
import com.justen.social.core.dto.input.ProfileInputDto;
import com.justen.social.domain.service.ProfileService;

import lombok.AllArgsConstructor;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-social
 * @Year 2026
 *
 */
@RestController
@RequestMapping("/profile")
@AllArgsConstructor
public class ProfileController {

	private final ProfileService profileService;

	@PostMapping
	public ProfileDto create(@RequestBody ProfileInputDto input) {
		return new ProfileDto(profileService.create(input.toEntity()));
	}

	@GetMapping("/{id}")
	public ProfileDto getById(@PathVariable UUID id) {
		return new ProfileDto(profileService.getById(id));
	}

	@GetMapping("/me")
	public ProfileDto getMyProfile() {
		return new ProfileDto(profileService.getMyProfile());
	}

	@GetMapping("/user/{userId}")
	public ProfileDto getByUserId(@PathVariable UUID userId) {
		return new ProfileDto(profileService.getByUserId(userId));
	}

	@GetMapping
	public Page<ProfileDto> getAll(Pageable pageable) {
		return profileService.getAll(pageable).map(ProfileDto::new);
	}

	@PutMapping("/{id}")
	public ProfileDto update(@PathVariable UUID id, @RequestBody ProfileInputDto input) {
		return new ProfileDto(profileService.update(id, input.toEntity()));
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable UUID id) {
		profileService.delete(id);
	}

}