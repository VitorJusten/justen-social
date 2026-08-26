package com.justen.infrastructure.utils;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import com.justen.infrastructure.enums.RoleEnum;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class SecurityUtils {

	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public Jwt getJwt() {
		Authentication auth = getAuthentication();
		if (auth instanceof JwtAuthenticationToken jwtAuth) {
			return jwtAuth.getToken();
		}
		throw new RuntimeException("No JWT Authentication found in Security Context");
	}

	public String getToken() {
		return getJwt().getTokenValue();
	}

	public UUID getLoggedUserId() {
		return UUID.fromString(getJwt().getSubject());
	}

	public String getLoggedUsername() {
		return getJwt().getClaimAsString("username");
	}

	public List<String> getLoggedUserRoles() {
		return getAuthentication().getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.map(role -> role.replace("ROLE_", ""))
				.toList();
	}

	public Boolean validateRoles(List<RoleEnum> roles) {
		List<String> userRoles = getLoggedUserRoles();
		return roles.stream().map(RoleEnum::getName).anyMatch(userRoles::contains);
	}
}