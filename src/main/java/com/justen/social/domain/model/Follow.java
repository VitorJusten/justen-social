package com.justen.social.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import com.justen.social.core.enums.FollowStatusEnum;

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
@Table(name = "profile_follow")
public class Follow {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@EqualsAndHashCode.Include
	@Column(name = "prfo_cd_id")
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prof_cd_follower_id", nullable = false)
	private Profile follower;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prof_cd_followed_id", nullable = false)
	private Profile followed;

	@Enumerated(EnumType.STRING)
	@Column(name = "prfo_tx_status", nullable = false)
	private FollowStatusEnum status;

	@Column(name = "prfo_dt_created_at", nullable = false)
	private OffsetDateTime createdAt;

	@Column(name = "prfo_dt_updated_at")
	private OffsetDateTime updatedAt;

}