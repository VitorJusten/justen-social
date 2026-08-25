package com.justen.social.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import com.justen.social.core.enums.ProfileStatusEnum;

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
@Table(name = "profile")
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@EqualsAndHashCode.Include
	@Column(name = "prof_cd_id")
	private UUID id;

	@Column(name = "prof_tx_name", nullable = false, length = 150)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "prof_tx_status", nullable = false)
	private ProfileStatusEnum status = ProfileStatusEnum.PUBLIC;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "prof_bt_perfil_image")
	private byte[] perfilImage;

	@Column(name = "prof_tx_bio")
	private String bio;

	@Column(name = "prof_dt_created_at", nullable = false)
	private OffsetDateTime createdAt;

	@Column(name = "prof_dt_updated_at")
	private OffsetDateTime updatedAt;

	@Column(name = "usac_tx_username", nullable = false)
	private String userName;

	@Column(name = "usac_cd_id", nullable = false)
	private UUID userId;

}