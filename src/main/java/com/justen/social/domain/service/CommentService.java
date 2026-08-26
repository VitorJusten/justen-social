package com.justen.social.domain.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.justen.infrastructure.enums.RoleEnum;
import com.justen.infrastructure.utils.SecurityUtils;
import com.justen.social.domain.exception.BusinessException;
import com.justen.social.domain.exception.EntityNotFoundException;
import com.justen.social.domain.model.Comment;
import com.justen.social.domain.model.Post;
import com.justen.social.domain.model.Profile;
import com.justen.social.domain.repository.CommentRepository;
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
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ProfileService profileService;
    private final SecurityUtils securityUtils;

    public Comment create(Comment comment) {

        Post post = postRepository.findById(comment.getPost().getId())
                .orElseThrow(() -> new EntityNotFoundException("postNotFound"));

        comment.setPost(post);

        if (comment.getCommentFather() != null) {

            Comment father = getById(comment.getCommentFather().getId());

            comment.setCommentFather(father);

        }

        Profile profile = profileService.getMyProfile();
        comment.setProfile(profile);
        comment.setCreatedAt(OffsetDateTime.now());

        postRepository.incrementComments(post.getId());

        return commentRepository.save(comment);

    }

    public Comment getById(UUID id) {

        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("commentNotFound"));

    }

    public List<Comment> getByPost(UUID postId) {

        return commentRepository.findAllByPostIdOrderByCreatedAtAsc(postId);

    }

    public Comment update(UUID id, Comment input) {

        Comment comment = getById(id);
        validateCommentModification(comment);

        BeanUtils.copyProperties(input, comment,
                "id",
                "profile",
                "createdAt",
                "post",
                "commentFather");

        comment.setUpdatedAt(OffsetDateTime.now());

        return commentRepository.save(comment);

    }

    public void delete(UUID id) {

        Comment comment = getById(id);
        validateCommentModification(comment);

        postRepository.decrementComments(comment.getPost().getId());

        commentRepository.delete(comment);

    }

    private void validateCommentModification(Comment comment) {
        Profile myProfile = profileService.getMyProfile();
        boolean isCommentOwner = comment.getProfile() != null && comment.getProfile().getId().equals(myProfile.getId());
        boolean isPostOwner = comment.getPost() != null && comment.getPost().getProfile() != null && comment.getPost().getProfile().getId().equals(myProfile.getId());
        Boolean isAdmOrDev = securityUtils.validateRoles(List.of(RoleEnum.ADM, RoleEnum.DEV));
        if (!isCommentOwner && !isPostOwner && !Boolean.TRUE.equals(isAdmOrDev)) {
            throw new BusinessException("unauthorizedAction");
        }
    }

}