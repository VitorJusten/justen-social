package com.justen.social.domain.service;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.justen.social.core.enums.FollowStatusEnum;
import com.justen.social.core.enums.ProfileStatusEnum;
import com.justen.social.domain.exception.BusinessException;
import com.justen.social.domain.exception.EntityNotFoundException;
import com.justen.social.domain.model.Follow;
import com.justen.social.domain.model.Profile;
import com.justen.social.domain.repository.FollowRepository;

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
public class FollowService {

	private final FollowRepository followRepository;
	private final ProfileService profileService;

	@Transactional
	public Follow follow(UUID followedProfileId) {
		Profile follower = profileService.getMyProfile();

		if (follower.getId().equals(followedProfileId)) {
			throw new BusinessException("cannotFollowSelf");
		}

		Profile followed = profileService.getById(followedProfileId);

		Optional<Follow> existingFollow = followRepository.findByFollowerIdAndFollowedId(follower.getId(), followedProfileId);

		if (existingFollow.isPresent()) {
			Follow follow = existingFollow.get();
			if (follow.getStatus() == FollowStatusEnum.BLOCKED) {
				throw new BusinessException("followBlocked");
			}
			if (follow.getStatus() == FollowStatusEnum.COMPLETED) {
				throw new BusinessException("alreadyFollowing");
			}
			if (follow.getStatus() == FollowStatusEnum.REQUESTED) {
				throw new BusinessException("followRequestAlreadySent");
			}
		}

		Follow follow = new Follow();
		follow.setFollower(follower);
		follow.setFollowed(followed);
		follow.setCreatedAt(OffsetDateTime.now());

		if (followed.getStatus() == ProfileStatusEnum.PUBLIC) {
			follow.setStatus(FollowStatusEnum.COMPLETED);
		} else {
			follow.setStatus(FollowStatusEnum.REQUESTED);
		}

		return followRepository.save(follow);
	}

	@Transactional
	public Follow acceptRequest(UUID followId) {
		Follow follow = getById(followId);
		Profile logged = profileService.getMyProfile();

		if (!follow.getFollowed().getId().equals(logged.getId())) {
			throw new BusinessException("notAuthorizedToAcceptFollowRequest");
		}

		if (follow.getStatus() != FollowStatusEnum.REQUESTED) {
			throw new BusinessException("followRequestNotPending");
		}

		follow.setStatus(FollowStatusEnum.COMPLETED);
		follow.setUpdatedAt(OffsetDateTime.now());

		return followRepository.save(follow);
	}

	@Transactional
	public void removeRequest(UUID followId) {
		Follow follow = getById(followId);
		Profile logged = profileService.getMyProfile();

		boolean isFollower = follow.getFollower().getId().equals(logged.getId());
		boolean isFollowed = follow.getFollowed().getId().equals(logged.getId());

		if (!isFollower && !isFollowed) {
			throw new BusinessException("notAuthorizedToRemoveFollow");
		}

		followRepository.delete(follow);
	}

	@Transactional
	public void unfollow(UUID followedProfileId) {
		Profile logged = profileService.getMyProfile();

		Follow follow = followRepository.findByFollowerIdAndFollowedId(logged.getId(), followedProfileId)
				.orElseThrow(() -> new EntityNotFoundException("followNotFound"));

		followRepository.delete(follow);
	}

	@Transactional
	public void removeFollower(UUID followerProfileId) {
		Profile logged = profileService.getMyProfile();

		Follow follow = followRepository.findByFollowerIdAndFollowedId(followerProfileId, logged.getId())
				.orElseThrow(() -> new EntityNotFoundException("followNotFound"));

		followRepository.delete(follow);
	}

	@Transactional
	public Follow block(UUID targetProfileId) {
		Profile logged = profileService.getMyProfile();

		if (logged.getId().equals(targetProfileId)) {
			throw new BusinessException("cannotBlockSelf");
		}

		Profile target = profileService.getById(targetProfileId);

		Optional<Follow> existing = followRepository.findByFollowerIdAndFollowedId(target.getId(), logged.getId());

		Follow follow;
		if (existing.isPresent()) {
			follow = existing.get();
			follow.setStatus(FollowStatusEnum.BLOCKED);
			follow.setUpdatedAt(OffsetDateTime.now());
		} else {
			follow = new Follow();
			follow.setFollower(target);
			follow.setFollowed(logged);
			follow.setStatus(FollowStatusEnum.BLOCKED);
			follow.setCreatedAt(OffsetDateTime.now());
		}

		// Se o usuário logado seguia o target, remover também essa relação
		followRepository.findByFollowerIdAndFollowedId(logged.getId(), target.getId())
				.ifPresent(followRepository::delete);

		return followRepository.save(follow);
	}

	public Follow getById(UUID id) {
		return followRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("followNotFound"));
	}

	public long countFollowersByProfile(UUID profileId) {
		return followRepository.countByFollowedIdAndStatus(profileId, FollowStatusEnum.COMPLETED);
	}

	public long countFollowingByProfile(UUID profileId) {
		return followRepository.countByFollowerIdAndStatus(profileId, FollowStatusEnum.COMPLETED);
	}

	public Page<Follow> getFollowers(UUID profileId, Pageable pageable) {
		return followRepository.findAllByFollowedIdAndStatus(profileId, FollowStatusEnum.COMPLETED, pageable);
	}

	public Page<Follow> getFollowing(UUID profileId, Pageable pageable) {
		return followRepository.findAllByFollowerIdAndStatus(profileId, FollowStatusEnum.COMPLETED, pageable);
	}

	public Page<Follow> getPendingRequests(Pageable pageable) {
		Profile logged = profileService.getMyProfile();
		return followRepository.findAllByFollowedIdAndStatus(logged.getId(), FollowStatusEnum.REQUESTED, pageable);
	}

}