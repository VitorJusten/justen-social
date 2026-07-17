package com.justen.social.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-auth
 * @Year 2026
 *
 */
@Getter
@AllArgsConstructor
public enum RoleEnum {

	DEV("DEV"),
	ADM("ADM"),
	AUTHOR("AUTHOR"),
	COLLECTOR("COLLECTOR");

	private String name;
}
