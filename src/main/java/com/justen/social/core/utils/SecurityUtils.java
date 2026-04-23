package com.justen.social.core.utils;

import java.util.Collections;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import com.justen.infrastructure.AppProperties;
import com.justen.social.domain.exception.BusinessException;

import lombok.AllArgsConstructor;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-social
 * @Year 2026
 *
 */
@Component("coreSecurityUtils")
@AllArgsConstructor
public class SecurityUtils {

	private final AppProperties properties;
	
	public void validateRoles(List<String> roles) {

		for (String role : roles) {
			if (isAnyRole(role)) {
				return;
			}
		}

		throw new BusinessException("unauthorizedUserMessage");
	}

	public boolean hasLoggedUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth != null && auth.isAuthenticated()
				&& !(auth.getPrincipal() instanceof String s && s.equals("anonymousUser"));
	}

	public Authentication getLoggedUser() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public boolean isLoggedUserDev() {
		return this.getLoggedUserRoles().stream().anyMatch(role -> role.equals("ROLE_DEV"));
	}

	public List<String> getLoggedUserRoles() {
		if (hasLoggedUser()) {
			Authentication loggedUser = getLoggedUser();

			if (loggedUser instanceof JwtAuthenticationToken token) {
				List<String> roles = token.getToken().getClaimAsStringList("roles");
				if (roles != null)
					return roles;
			}
			return loggedUser.getAuthorities().stream()
					.map(a -> a.getAuthority())
					.filter(a -> a.startsWith("ROLE_"))
					.toList();
		}

		return Collections.emptyList();
	}

	public boolean isRole(String role) {
		String normalizedRole = role.startsWith("ROLE_") ? role : "ROLE_" + role;
		return this.getLoggedUserRoles().stream().anyMatch(r -> r.equals(normalizedRole));
	}

	public boolean isAnyRole(String... roles) {
		List<String> userRoles = this.getLoggedUserRoles();
		for (String role : roles) {
			String normalizedRole = role.startsWith("ROLE_") ? role : "ROLE_" + role;
			if (userRoles.contains(normalizedRole))
				return true;
		}
		return false;
	}

	public String getLoggedUserId() {
		if (hasLoggedUser()) {
			Authentication loggedUser = getLoggedUser();

			if (loggedUser instanceof JwtAuthenticationToken token) {
				return token.getToken().getClaimAsString("user_id");
			}
			return loggedUser.getName();
		}

		return "";
	}

	public String getLoggedUserName() {
		if (hasLoggedUser()) {
			Authentication loggedUser = getLoggedUser();

			if (loggedUser instanceof JwtAuthenticationToken token) {
				return token.getToken().getClaimAsString("user_name");
			}
			return loggedUser.getName();
		}

		return "";
	}

	public String getLoggedUserEmail() {
		if (hasLoggedUser()) {
			Authentication loggedUser = getLoggedUser();

			if (loggedUser instanceof JwtAuthenticationToken token) {
				return token.getToken().getClaimAsString("email");
			}
		}

		return "";
	}

	public String getLoggedUserAccessToken() {

		if (hasLoggedUser()) {
			Authentication loggedUser = getLoggedUser();

			if (loggedUser instanceof JwtAuthenticationToken token) {
				return token.getToken().getTokenValue();
			}
		}

		return null;
	}
}
