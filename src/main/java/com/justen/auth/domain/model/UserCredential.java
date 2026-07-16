package com.justen.auth.domain.model;

import java.io.Serializable;
import java.util.UUID;

import com.justen.auth.core.enums.CredentialTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserCredential implements Serializable {

	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	private UUID id;

	private UUID userId;

	private String credential;

	private CredentialTypeEnum credentialType;

}
