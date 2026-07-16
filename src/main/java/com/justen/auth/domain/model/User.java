package com.justen.auth.domain.model;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User implements UserDetails, Serializable {

	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	private UUID id;

	private String username;

	private List<UserCredential> credentials = new ArrayList<>();

	private String password;

	private Boolean accountLocked = false;

	private Integer failedLoginAttempts = 0;

	private OffsetDateTime lockUntil;

	private OffsetDateTime lastLoginAt;

	private OffsetDateTime createdAt;

	private OffsetDateTime updatedAt;

	private Set<Role> roles = new HashSet<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream()
				.map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName()))
				.toList();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		if (Boolean.TRUE.equals(accountLocked)) {
			if (lockUntil == null) {
				return false;
			}
			return lockUntil.isBefore(OffsetDateTime.now());
		}

		if (lockUntil != null) {
			return lockUntil.isBefore(OffsetDateTime.now());
		}

		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		if (lockUntil != null) {
			return lockUntil.isBefore(OffsetDateTime.now());
		}
		return !Boolean.TRUE.equals(accountLocked);
	}

}
