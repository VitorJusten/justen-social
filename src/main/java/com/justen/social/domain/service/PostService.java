package com.justen.social.domain.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.justen.infrastructure.enums.RoleEnum;
import com.justen.infrastructure.utils.SecurityUtils;
import com.justen.social.core.dto.PostSummaryDto;
import com.justen.social.domain.exception.BusinessException;
import com.justen.social.domain.exception.EntityNotFoundException;
import com.justen.social.domain.model.Media;
import com.justen.social.domain.model.Post;
import com.justen.social.domain.model.Profile;
import com.justen.social.domain.repository.PostRepository;

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
public class PostService {

    private final PostRepository postRepository;
    private final ProfileService profileService;
    private final SecurityUtils securityUtils;

    public Post create(Post post) {
        Profile profile = profileService.getMyProfile();
        post.setProfile(profile);
        post.setCreatedAt(OffsetDateTime.now());
        
        return postRepository.save(post);
    }

    public Post getById(UUID id) {

        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("postNotFound"));
    }

    public Page<PostSummaryDto> getAll(Pageable pageable, String filters) {
        return postRepository.findAllPosts(pageable, filters);
    }
    
    public Page<PostSummaryDto> getAllByUser(Pageable pageable, String authorName) {
    	return postRepository.findAllPostsByUser(pageable, authorName);
    }
    
    public Page<PostSummaryDto> getAllByProfile(Pageable pageable, UUID profileId) {
    	return postRepository.findAllPostsByProfile(pageable, profileId);
    }
    
	public Page<PostSummaryDto> getMyPosts(Pageable pageable) {
		Profile myProfile = profileService.getMyProfile();
		return postRepository.findAllPostsByProfile(pageable, myProfile.getId());
	}
    
    public Post update(UUID id, Post postInput) {

        Post post = getById(id);
        validatePostOwnerOrAdmin(post);
        
        BeanUtils.copyProperties(postInput, post, "id", "createdAt", "medias", "profile");
        
        post.getMedias().clear();

        for (Media media : postInput.getMedias()) {
            media.setPost(post);
            post.getMedias().add(media);
        }

        post.setUpdatedAt(OffsetDateTime.now());

        return postRepository.save(post);
    }

    public void delete(UUID id) {
        Post post = getById(id);
        validatePostOwnerOrAdmin(post);
        postRepository.delete(post);
    }

    public Post changeVisibility(UUID id, Boolean isPublic) {

        Post post = getById(id);
        validatePostOwnerOrAdmin(post);

        post.setPublished(isPublic);
        post.setUpdatedAt(OffsetDateTime.now());

        return postRepository.save(post);
    }

    public Post pin(UUID id, Boolean isPinned) {

        Post post = getById(id);
        validatePostOwnerOrAdmin(post);

        post.setFixed(isPinned);
        post.setUpdatedAt(OffsetDateTime.now());

        return postRepository.save(post);
    }

    private void validatePostOwnerOrAdmin(Post post) {
        Profile myProfile = profileService.getMyProfile();
        boolean isOwner = post.getProfile() != null && post.getProfile().getId().equals(myProfile.getId());
        Boolean isAdmOrDev = securityUtils.validateRoles(List.of(RoleEnum.ADM, RoleEnum.DEV));
        if (!isOwner && !Boolean.TRUE.equals(isAdmOrDev)) {
            throw new BusinessException("unauthorizedAction");
        }
    }

}