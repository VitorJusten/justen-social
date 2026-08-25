package com.justen.social.api.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justen.social.core.dto.FollowDto;
import com.justen.social.domain.service.FollowService;

import lombok.AllArgsConstructor;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-social
 * @Year 2026
 *
 */
@RestController
@RequestMapping("/follow")
@AllArgsConstructor
public class FollowController {

	private final FollowService followService;

	@PostMapping("/{followedProfileId}")
	public FollowDto follow(@PathVariable UUID followedProfileId) {
		return new FollowDto(followService.follow(followedProfileId));
	}

	@PatchMapping("/request/{followId}/accept")
	public FollowDto acceptRequest(@PathVariable UUID followId) {
		return new FollowDto(followService.acceptRequest(followId));
	}

	@DeleteMapping("/request/{followId}")
	public void removeRequest(@PathVariable UUID followId) {
		followService.removeRequest(followId);
	}

	@DeleteMapping("/unfollow/{followedProfileId}")
	public void unfollow(@PathVariable UUID followedProfileId) {
		followService.unfollow(followedProfileId);
	}

	@DeleteMapping("/follower/{followerProfileId}")
	public void removeFollower(@PathVariable UUID followerProfileId) {
		followService.removeFollower(followerProfileId);
	}

	@PatchMapping("/block/{profileId}")
	public FollowDto block(@PathVariable UUID profileId) {
		return new FollowDto(followService.block(profileId));
	}

	@GetMapping("/profile/{profileId}/followers/count")
	public long countFollowers(@PathVariable UUID profileId) {
		return followService.countFollowersByProfile(profileId);
	}

	@GetMapping("/profile/{profileId}/following/count")
	public long countFollowing(@PathVariable UUID profileId) {
		return followService.countFollowingByProfile(profileId);
	}

	@GetMapping("/profile/{profileId}/followers")
	public Page<FollowDto> getFollowers(@PathVariable UUID profileId, Pageable pageable) {
		return followService.getFollowers(profileId, pageable).map(FollowDto::new);
	}

	@GetMapping("/profile/{profileId}/following")
	public Page<FollowDto> getFollowing(@PathVariable UUID profileId, Pageable pageable) {
		return followService.getFollowing(profileId, pageable).map(FollowDto::new);
	}

	@GetMapping("/requests")
	public Page<FollowDto> getPendingRequests(Pageable pageable) {
		return followService.getPendingRequests(pageable).map(FollowDto::new);
	}

}