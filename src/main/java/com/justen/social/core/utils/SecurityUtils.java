package com.justen.social.core.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import com.justen.social.core.enums.RoleEnum;
import com.justen.social.domain.exception.BusinessException;

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
		try {
			return UUID.fromString(getJwt().getSubject());
		} catch (Exception e) {
			return null;
		}
	}

	public String getLoggedUsername() {
		try {
			return getJwt().getClaimAsString("username");

		} catch (Exception e) {
			return "";
		}
	}

	public List<String> getLoggedUserRoles() {
		try {
			return getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.map(role -> role.replace("ROLE_", "")).toList();
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	public void validateRoles(List<RoleEnum> roles) {
		List<String> userRoles = getLoggedUserRoles();
		boolean ok = roles.stream().map(RoleEnum::getName).anyMatch(userRoles::contains);
		if (!ok) {
			throw new BusinessException("You do not have permission to perform this action");
		}
	}
}