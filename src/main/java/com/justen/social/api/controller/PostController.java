package com.justen.social.api.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.justen.social.core.dto.PostDto;
import com.justen.social.core.dto.PostSummaryDto;
import com.justen.social.core.dto.input.PostInputDto;
import com.justen.social.domain.service.PostService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/post")
@AllArgsConstructor
public class PostController {

	private final PostService postService;

	@PostMapping
	public PostDto create(@RequestBody PostInputDto input) {

		return new PostDto(postService.create(input.toEntity()));
	}

	@GetMapping("/{id}")
	public PostDto getById(@PathVariable UUID id) {

		return new PostDto(postService.getById(id));
	}

	@GetMapping
	public Page<PostSummaryDto> getAll(Pageable pageable, @RequestParam(required = false) String filters) {

		return postService.getAll(pageable, filters);
	}
	
	@GetMapping("/by-user")
	public Page<PostSummaryDto> getAllByUser(Pageable pageable, @RequestParam(required = false) String authorName) {
		
		return postService.getAllByUser(pageable, authorName);
	}
	
	@GetMapping("/self")
	public Page<PostSummaryDto> getMyPosts(Pageable pageable) {
		
		return postService.getMyPosts(pageable);
	}
	

	@PutMapping("/{id}")
	public PostDto update(@PathVariable UUID id, @RequestBody PostInputDto input) {

		return new PostDto(postService.update(id, input.toEntity()));
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable UUID id) {
		postService.delete(id);
	}
	
	@PatchMapping("/{id}/publish")
	public PostDto changeVisibility(@PathVariable UUID id, @RequestParam Boolean isPublic) {

		return new PostDto(postService.changeVisibility(id, isPublic));
	}

	@PatchMapping("/{id}/pin")
	public PostDto pin(@PathVariable UUID id, @RequestParam Boolean isPinned) {

		return new PostDto(postService.pin(id, isPinned));
	}

}