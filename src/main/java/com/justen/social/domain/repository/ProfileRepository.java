package com.justen.social.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.justen.social.domain.model.Profile;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-social
 * @Year 2026
 *
 */
public interface ProfileRepository extends JpaRepository<Profile, UUID> {

	Optional<Profile> findByUserId(UUID userId);

	Optional<Profile> findByUserName(String userName);

	boolean existsByUserId(UUID userId);

	boolean existsByUserName(String userName);

}