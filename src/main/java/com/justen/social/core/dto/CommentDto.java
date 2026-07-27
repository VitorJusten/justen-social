package com.justen.social.core.dto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.justen.social.domain.model.Comment;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-social
 * @Year 2026
 *
 */
@Data
@NoArgsConstructor
public class CommentDto {

    private UUID id;
    private String authorName;
    private String comment;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private UUID commentFatherId;
    private UUID postId;

    private List<CommentDto> replies;

    public CommentDto(Comment comment) {

        this.id = comment.getId();
        this.authorName = comment.getAuthorName();
        this.comment = comment.getComment();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();

        if (comment.getCommentFather() != null) {
            this.commentFatherId = comment.getCommentFather().getId();
        }

        this.postId = comment.getPost().getId();

        if (comment.getReplies() != null) {
            this.replies = comment.getReplies()
                    .stream()
                    .map(CommentDto::new)
                    .collect(Collectors.toList());
        }

    }

}