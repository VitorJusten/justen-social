package com.justen.social.domain.service;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.justen.social.core.utils.SecurityUtils;
import com.justen.social.domain.exception.EntityNotFoundException;
import com.justen.social.domain.model.dto.PostDto;
import com.justen.social.domain.model.entity.Post;
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
    private final SecurityUtils securityUtils;

    public Post create(Post post) {
    	post.setAuthorId(UUID.fromString(securityUtils.getLoggedUserId()));
        post.setCreatedAt(OffsetDateTime.now());

        return postRepository.save(post);
    }

    public Post getById(UUID id) {

        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("postNotFound"));
    }

    public Page<PostDto> getAll(Pageable pageable, String filters) {
        return postRepository.findAllPosts(pageable, filters);
    }

    public Page<PostDto> getUnpublished(Pageable pageable, String filters) {
        return postRepository.findUnpublishedPosts(pageable, filters);
    }

    public Post update(UUID id, Post postInput) {

        Post post = getById(id);

        BeanUtils.copyProperties(postInput, post, "id", "createdAt");
        
        post.setUpdatedAt(OffsetDateTime.now());

        return postRepository.save(post);
    }

    public void delete(UUID id) {
        postRepository.deleteById(id);
    }

    public Post publish(UUID id) {

        Post post = getById(id);

        post.setPublished(true);
        post.setUpdatedAt(OffsetDateTime.now());

        return postRepository.save(post);
    }

    public Post pin(UUID id) {

        Post post = getById(id);

        post.setHighlight(true);
        post.setUpdatedAt(OffsetDateTime.now());

        return postRepository.save(post);
    }

}