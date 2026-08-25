package com.justen.social.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.justen.social.core.enums.FollowStatusEnum;
import com.justen.social.domain.model.Follow;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-social
 * @Year 2026
 *
 */
public interface FollowRepository extends JpaRepository<Follow, UUID> {

	Optional<Follow> findByFollowerIdAndFollowedId(UUID followerId, UUID followedId);

	boolean existsByFollowerIdAndFollowedId(UUID followerId, UUID followedId);

	long countByFollowedIdAndStatus(UUID followedId, FollowStatusEnum status);

	long countByFollowerIdAndStatus(UUID followerId, FollowStatusEnum status);

	Page<Follow> findAllByFollowedIdAndStatus(UUID followedId, FollowStatusEnum status, Pageable pageable);

	Page<Follow> findAllByFollowerIdAndStatus(UUID followerId, FollowStatusEnum status, Pageable pageable);

	void deleteByFollowerIdAndFollowedId(UUID followerId, UUID followedId);

}