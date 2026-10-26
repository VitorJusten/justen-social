package com.justen.social.domain.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.infrastructure.shared.core.enums.RoleEnum;
import com.infrastructure.shared.core.utils.FileValidationUtils;
import com.infrastructure.shared.core.utils.SecurityUtils;
import com.infrastructure.shared.domain.exception.BusinessException;
import com.infrastructure.shared.domain.exception.EntityNotFoundException;
import com.justen.social.core.dto.PostSummaryDto;
import com.justen.social.domain.model.Media;
import com.justen.social.domain.model.Post;
import com.justen.social.domain.model.Profile;
import com.justen.social.domain.repository.PostRepository;

import jakarta.transaction.Transactional;
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

    @Transactional
    public Post create(Post post) {
        Profile profile = profileService.getMyProfile();
        post.setProfile(profile);
        post.setCreatedAt(OffsetDateTime.now());

        // Validação de arquivo da Thumbnail
        if (post.getThumbImage() != null && post.getThumbImage().length > 0) {
            FileValidationUtils.validateIsImage(post.getThumbImage());
        }

        // Validação de arquivos anexados (bloqueio de executáveis)
        if (post.getMedias() != null) {
            for (Media media : post.getMedias()) {
                if (media.getContent() != null && media.getContent().length > 0) {
                    FileValidationUtils.validateNotExecutable(media.getContent());
                }
                if (media.getFileName() != null) {
                    FileValidationUtils.validateNotExecutable(media.getFileName());
                }
            }
        }

        // Apenas ADM ou DEV podem criar posts já fixados
        Boolean isAdmOrDev = securityUtils.hasAnyRole(List.of(RoleEnum.ADM, RoleEnum.DEV));
        if (!Boolean.TRUE.equals(isAdmOrDev)) {
            post.setFixed(false);
        }

        return postRepository.save(post);
    }

    public Post getById(UUID id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("postNotFound"));
        postRepository.incrementViews(id);
        post.setViews((post.getViews() != null ? post.getViews() : 0L) + 1L);
        return post;
    }

    public Page<PostSummaryDto> getAll(Pageable pageable, String filters) {
        return postRepository.findAllPosts(pageable, filters);
    }

    public Page<PostSummaryDto> findAllPostsByUserIds(List<UUID> userIds, Pageable pageable) {
        return postRepository.findAllPostsByUserIds(userIds, pageable);
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

    @Transactional
    public Post update(UUID id, Post postInput) {

        Post post = getById(id);
        validatePostOwnerOrAdmin(post);

        BeanUtils.copyProperties(postInput, post, "id", "createdAt", "medias", "profile", "fixed", "blocked");

        post.getMedias().clear();

        for (Media media : postInput.getMedias()) {
            media.setPost(post);
            post.getMedias().add(media);
        }

        post.setUpdatedAt(OffsetDateTime.now());

        return postRepository.save(post);
    }

    @Transactional
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

    @Transactional
    public Post pin(UUID id, Boolean isPinned) {

        // Apenas ADM ou DEV podem fixar/desfixar qualquer post
        securityUtils.validateRoles(List.of(RoleEnum.ADM, RoleEnum.DEV));

        Post post = getById(id);
        post.setFixed(isPinned);
        post.setUpdatedAt(OffsetDateTime.now());

        return postRepository.save(post);
    }

    @Transactional
    public Post blockPost(UUID id, Boolean isBlocked) {
        // Apenas ADM ou DEV podem bloquear/desbloquear posts
        securityUtils.validateRoles(List.of(RoleEnum.ADM, RoleEnum.DEV));

        Post post = getById(id);
        post.setBlocked(isBlocked);
        post.setUpdatedAt(OffsetDateTime.now());

        return postRepository.save(post);
    }

    private void validatePostOwnerOrAdmin(Post post) {
        Profile myProfile = profileService.getMyProfile();
        boolean isOwner = post.getProfile() != null && post.getProfile().getId().equals(myProfile.getId());
        Boolean isAdmOrDev = securityUtils.hasAnyRole(List.of(RoleEnum.ADM, RoleEnum.DEV));
        if (!isOwner && !Boolean.TRUE.equals(isAdmOrDev)) {
            throw new BusinessException("unauthorizedAction");
        }
    }

}