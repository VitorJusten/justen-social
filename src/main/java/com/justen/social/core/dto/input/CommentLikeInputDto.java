package com.justen.social.core.dto.input;

import java.util.UUID;

import com.justen.social.domain.model.Comment;
import com.justen.social.domain.model.CommentLike;
import com.justen.social.domain.model.CommentLikeId;

import lombok.Data;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-social
 * @Year 2026
 *
 */
@Data
public class CommentLikeInputDto {

    private UUID commentId;

    public CommentLike toEntity() {
        CommentLike entity = new CommentLike();

        CommentLikeId id = new CommentLikeId();
        id.setCommentId(commentId);
        entity.setId(id);

        Comment comment = new Comment();
        comment.setId(commentId);
        entity.setComment(comment);

        return entity;
    }

}