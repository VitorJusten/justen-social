package com.justen.social.domain.model.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "post_media")
public class PostMedia {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@EqualsAndHashCode.Include
	@Column(name = "pome_cd_id")
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_cd_id", nullable = false)
	private Post post;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "pome_by_content")
	private byte[] content;

	@ManyToMany
	@JoinTable(name = "post_media_post_media_type", joinColumns = @JoinColumn(name = "pmpt_cd_post_media"), inverseJoinColumns = @JoinColumn(name = "pmpt_cd_post_media_type"))
	private Set<PostMediaType> types = new HashSet<>();

}