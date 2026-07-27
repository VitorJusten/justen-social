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
public class UserLikeId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "post_cd_id")
    private UUID postId;

    @Column(name = "usac_tx_username")
    private String username;

}