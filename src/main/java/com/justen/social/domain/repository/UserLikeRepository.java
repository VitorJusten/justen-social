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
	 * @param username
	 * @return
	 */
    boolean existsByIdPostIdAndIdUsername(UUID postId, String username);
    
    /**
     * 
     * @param postId
     * @param username
     */
    void deleteByIdPostIdAndIdUsername(UUID postId, String username);

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