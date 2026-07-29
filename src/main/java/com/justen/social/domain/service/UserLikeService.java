package com.justen.social.domain.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.justen.social.core.utils.SecurityUtils;
import com.justen.social.domain.exception.BusinessException;
import com.justen.social.domain.exception.EntityNotFoundException;
import com.justen.social.domain.model.Post;
import com.justen.social.domain.model.UserLike;
import com.justen.social.domain.repository.PostRepository;
import com.justen.social.domain.repository.UserLikeRepository;

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
public class UserLikeService {

    private final UserLikeRepository userLikeRepository;
    private final PostRepository postRepository;
    private final SecurityUtils securityUtils;

    @Transactional
    public UserLike like(UserLike userLike) {

        UUID postId = userLike.getId().getPostId();
        String username = securityUtils.getLoggedUsername();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("postNotFound"));

        if (userLikeRepository.existsByIdPostIdAndIdUsername(postId, username)) {
            throw new BusinessException("postAlreadyLiked");
        }

        userLike.setPost(post);
        userLike.getId().setUsername(username);
        userLike.setCreatedAt(OffsetDateTime.now());

        postRepository.incrementLikes(postId);

        return userLikeRepository.save(userLike);
    }

    @Transactional
    public void unlike(UUID postId) {

        String username = securityUtils.getLoggedUsername();

        if (!postRepository.existsById(postId)) {
            throw new EntityNotFoundException("postNotFound");
        }

        if (!userLikeRepository.existsByIdPostIdAndIdUsername(postId, username)) {
            throw new BusinessException("postNotLiked");
        }

        userLikeRepository.deleteByIdPostIdAndIdUsername(postId, username);
        postRepository.decrementLikes(postId);
    }

    public List<UserLike> getByPost(UUID postId) {

        if (!postRepository.existsById(postId)) {
            throw new EntityNotFoundException("postNotFound");
        }

        return userLikeRepository.findAllByIdPostId(postId);
    }

    public boolean isLiked(UUID postId) {

        String username = securityUtils.getLoggedUsername();
        return userLikeRepository.existsByIdPostIdAndIdUsername(postId, username);
    }

}
