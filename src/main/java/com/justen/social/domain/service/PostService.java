package com.justen.social.domain.service;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.justen.social.core.dto.PostSummaryDto;
import com.justen.social.core.utils.SecurityUtils;
import com.justen.social.domain.exception.EntityNotFoundException;
import com.justen.social.domain.model.Media;
import com.justen.social.domain.model.Post;
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
    	post.setAuthorName(securityUtils.getLoggedUsername());
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
    
	public Page<PostSummaryDto> getMyPosts(Pageable pageable) {
		return postRepository.findAllPostsByUser(pageable, securityUtils.getLoggedUsername());
	}
    
    public Post update(UUID id, Post postInput) {

        Post post = getById(id);
        
        BeanUtils.copyProperties(postInput, post, "id", "createdAt", "medias", "authorName");
        
        post.getMedias().clear();

        for (Media media : postInput.getMedias()) {
            media.setPost(post);
            post.getMedias().add(media);
        }

        post.setUpdatedAt(OffsetDateTime.now());

        return postRepository.save(post);
    }

    public void delete(UUID id) {
        postRepository.deleteById(id);
    }

    public Post changeVisibility(UUID id, Boolean isPublic) {

        Post post = getById(id);

        post.setPublished(isPublic);
        post.setUpdatedAt(OffsetDateTime.now());

        return postRepository.save(post);
    }

    public Post pin(UUID id, Boolean isPinned) {

        Post post = getById(id);

        post.setFixed(isPinned);
        post.setUpdatedAt(OffsetDateTime.now());

        return postRepository.save(post);
    }

}