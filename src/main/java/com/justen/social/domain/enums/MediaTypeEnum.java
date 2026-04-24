package com.justen.social.domain.enums;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-social
 * @Year 2026
 *
 */
@Getter
@AllArgsConstructor
public enum MediaTypeEnum {

	IMAGE(UUID.fromString("8a8f4c5c-0b9c-4b41-9c9e-2c6b9f7d4e0a"), "IMAGE"),

	TEXT(UUID.fromString("3f2e6c1d-9a8b-4c0e-b7f4-5d2a7e8c1b6f"), "TEXT"),

	VIDEO(UUID.fromString("b7a1d4c9-2f5e-4a3b-8c6d-1e9f0a2b3c4d"), "VIDEO"),

	DOCUMENT(UUID.fromString("5c9e2b7a-4f1d-4e8a-b6c3-7a0d9f2e1b5c"), "DOCUMENT"),

	URL(UUID.fromString("d1e3f7a9-6b4c-4d2a-9e8f-3c1b5a7d0e2f"), "URL");

	private final UUID id;
	private final String name;

	public static MediaTypeEnum fromId(UUID id) {
		for (MediaTypeEnum type : values()) {
			if (type.id.equals(id)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid MediaType id: " + id);
	}

	public static MediaTypeEnum fromName(String name) {
		for (MediaTypeEnum type : values()) {
			if (type.name.equalsIgnoreCase(name)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid MediaType name: " + name);
	}

}
