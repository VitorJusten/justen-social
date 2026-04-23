package com.justen.social.domain.model.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	@Column(name = "post_cd_author", nullable = false)
	private UUID author;

	@Column(name = "post_tx_title", nullable = false, length = 150)
	private String title;

	@Column(name = "post_tx_description")
	private String description;

	@Column(name = "post_nm_published", nullable = false)
	private Boolean published;

	@Column(name = "post_nm_highlight", nullable = false)
	private Boolean highlight;

	@Column(name = "post_dt_created_at", nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "post_dt_updated_at")
	private LocalDateTime updatedAt;

}