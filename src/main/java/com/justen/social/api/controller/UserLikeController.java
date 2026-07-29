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

import com.justen.social.core.dto.UserLikeDto;
import com.justen.social.core.dto.input.UserLikeInputDto;
import com.justen.social.domain.service.UserLikeService;

import lombok.AllArgsConstructor;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-social
 * @Year 2026
 *
 */
@RestController
@RequestMapping("/like")
@AllArgsConstructor
public class UserLikeController {

    private final UserLikeService userLikeService;

    @PostMapping
    public UserLikeDto like(@RequestBody UserLikeInputDto input) {

        return new UserLikeDto(userLikeService.like(input.toEntity()));
    }

    @DeleteMapping("/{postId}")
    public void unlike(@PathVariable UUID postId) {

        userLikeService.unlike(postId);
    }

    @GetMapping("/post/{postId}")
    public List<UserLikeDto> getByPost(@PathVariable UUID postId) {

        return userLikeService.getByPost(postId)
                .stream()
                .map(UserLikeDto::new)
                .toList();
    }

    @GetMapping("/post/{postId}/is-liked")
    public boolean isLiked(@PathVariable UUID postId) {

        return userLikeService.isLiked(postId);
    }

}
