package com.justen.social.core.dto.input;

import java.util.UUID;

import com.justen.social.domain.model.Post;
import com.justen.social.domain.model.UserLike;
import com.justen.social.domain.model.UserLikeId;

import lombok.Data;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-social
 * @Year 2026
 *
 */
@Data
public class UserLikeInputDto {

    private UUID postId;

    public UserLike toEntity() {

        UserLike entity = new UserLike();

        UserLikeId id = new UserLikeId();
        id.setPostId(postId);
        entity.setId(id);

        Post post = new Post();
        post.setId(postId);
        entity.setPost(post);

        return entity;
    }

}
