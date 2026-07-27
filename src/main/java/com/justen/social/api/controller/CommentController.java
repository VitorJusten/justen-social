package com.justen.social.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justen.social.core.dto.CommentDto;
import com.justen.social.core.dto.input.CommentInputDto;
import com.justen.social.domain.service.CommentService;

import lombok.AllArgsConstructor;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-social
 * @Year 2026
 *
 */
@RestController
@RequestMapping("/comment")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public CommentDto create(@RequestBody CommentInputDto input) {

        return new CommentDto(commentService.create(input.toEntity()));

    }

    @GetMapping("/{id}")
    public CommentDto getById(@PathVariable UUID id) {

        return new CommentDto(commentService.getById(id));

    }

    @GetMapping("/post/{postId}")
    public List<CommentDto> getByPost(@PathVariable UUID postId) {

        return commentService.getByPost(postId)
                .stream()
                .map(CommentDto::new)
                .toList();

    }

    @PutMapping("/{id}")
    public CommentDto update(@PathVariable UUID id,
                             @RequestBody CommentInputDto input) {

        return new CommentDto(commentService.update(id, input.toEntity()));

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {

        commentService.delete(id);

    }

}