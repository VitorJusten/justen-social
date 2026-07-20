package com.justen.social.domain.model;

import java.util.UUID;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "media")
public class Media {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@EqualsAndHashCode.Include
	@Column(name = "medi_cd_id")
	private UUID id;
	
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "medi_bt_content", nullable = false)
	private byte[] content;
	
	@ManyToOne
	@JoinColumn(name = "mety_cd_id")
	private MediaType mediaType;
	
	@ManyToOne
	@JoinColumn(name = "post_cd_id")
	private Post post;
	
}
