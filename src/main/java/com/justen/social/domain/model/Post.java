package com.justen.social.domain.model;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-social
 * @Year 2026
 *
 */
@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "post")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@EqualsAndHashCode.Include
	@Column(name = "post_cd_id")
	private UUID id;

	@Column(name = "usac_tx_username", nullable = false)
	private String authorName;

	@Column(name = "post_tx_title", nullable = false, length = 150)
	private String title;

	@Column(name = "post_tx_description")
	private String description;

	@Column(name = "post_nm_published", nullable = false)
	private Boolean published = false;

	@Column(name = "post_nm_fixed", nullable = false)
	private Boolean fixed = false;

	@Column(name = "post_dt_created_at", nullable = false)
	private OffsetDateTime createdAt;

	@Column(name = "post_dt_updated_at")
	private OffsetDateTime updatedAt;
	
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "post_bt_thumb", nullable = false)
	private byte[] thumbImage;
	
    @Column(name = "post_nm_likes_count", nullable = false)
    private Long likesCount = 0L;

    @Column(name = "post_nm_comments_count", nullable = false)
    private Long commentsCount = 0L;
	
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserLike> likes;
	
	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> coments;
	
	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Media> medias;
	
}