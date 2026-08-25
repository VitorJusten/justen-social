package com.justen.social.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.justen.social.domain.model.UserLike;
import com.justen.social.domain.model.UserLikeId;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-social
 * @Year 2026
 *
 */
public interface UserLikeRepository extends JpaRepository<UserLike, UserLikeId> {

	/**
	 * 
	 * @param postId
	 * @param profileId
	 * @return
	 */
    boolean existsByIdPostIdAndIdProfileId(UUID postId, UUID profileId);
    
    /**
     * 
     * @param postId
     * @param profileId
     */
    void deleteByIdPostIdAndIdProfileId(UUID postId, UUID profileId);

    /**
     * 
     * @param postId
     * @return
     */
    long countByIdPostId(UUID postId);

    /**
     * 
     * @param postId
     * @return
     */
    List<UserLike> findAllByIdPostId(UUID postId);

}