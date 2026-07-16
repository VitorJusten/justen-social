package com.justen.auth.domain.model;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RefreshToken implements Serializable {

	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	private UUID id;

	private UUID userId;

	private UUID clientId;

	private String token;

	private OffsetDateTime expiration;

	private Boolean revoked = false;

	private String replacedBy;

}
