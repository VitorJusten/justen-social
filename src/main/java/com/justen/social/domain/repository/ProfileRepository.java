package com.justen.social.domain.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

	@Query("select p.userId as userId, p.name as name, p.userName as userName from Profile p where p.userId in :userIds")
	List<ProfileNameView> findNamesByUserIdIn(@Param("userIds") Collection<UUID> userIds);

	interface ProfileNameView {

		UUID getUserId();

		String getName();

		String getUserName();

	}

	boolean existsByUserName(String userName);

}