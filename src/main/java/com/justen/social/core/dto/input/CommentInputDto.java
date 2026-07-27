package com.justen.social.core.dto.input;

import java.util.UUID;

import com.justen.social.domain.model.Comment;
import com.justen.social.domain.model.Post;

import lombok.Data;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-social
 * @Year 2026
 *
 */
@Data
public class CommentInputDto {

    private String comment;

    private UUID postId;

    private UUID commentFatherId;

    public Comment toEntity() {

        Comment entity = new Comment();

        entity.setComment(comment);

        Post post = new Post();
        post.setId(postId);

        entity.setPost(post);

        if (commentFatherId != null) {

            Comment father = new Comment();
            father.setId(commentFatherId);

            entity.setCommentFather(father);

        }

        return entity;
    }

}