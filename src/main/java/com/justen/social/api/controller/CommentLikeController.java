package com.justen.social.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justen.social.core.dto.CommentLikeDto;
import com.justen.social.core.dto.input.CommentLikeInputDto;
import com.justen.social.domain.service.CommentLikeService;

import lombok.AllArgsConstructor;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-social
 * @Year 2026
 *
 */
@RestController
@RequestMapping("/comment-like")
@AllArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping
    public CommentLikeDto like(@RequestBody CommentLikeInputDto input) {
        return new CommentLikeDto(commentLikeService.like(input.toEntity()));
    }

    @DeleteMapping("/{commentId}")
    public void unlike(@PathVariable UUID commentId) {
        commentLikeService.unlike(commentId);
    }

    @GetMapping("/comment/{commentId}")
    public List<CommentLikeDto> getByComment(@PathVariable UUID commentId) {
        return commentLikeService.getByComment(commentId)
                .stream()
                .map(CommentLikeDto::new)
                .toList();
    }

    @GetMapping("/comment/{commentId}/is-liked")
    public boolean isLiked(@PathVariable UUID commentId) {
        return commentLikeService.isLiked(commentId);
    }

}