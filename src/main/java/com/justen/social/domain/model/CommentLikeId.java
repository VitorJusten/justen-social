package com.justen.social.domain.model;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-social
 * @Year 2026
 *
 */
@Data
@Embeddable
public class CommentLikeId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "comm_cd_id")
    private UUID commentId;

    @Column(name = "prof_cd_id")
    private UUID profileId;

}