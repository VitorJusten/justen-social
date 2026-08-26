package com.justen.infrastructure.enums;

import java.util.UUID;

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

	DEV(UUID.fromString("0a5982c8-baa4-4e66-8614-01cf35ad4cfc"), "DEV"),
	ADM(UUID.fromString("0b5982c8-baa4-4e66-8614-01cf35ad4cfc"), "ADM");

	private UUID id;

	private String name;

}
