package com.infrastructure.shared.core.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.infrastructure.shared.domain.exception.BusinessException;

/**
 * Utilitário de validação de arquivos para proteção contra executáveis e controle de tipos de mídia.
 *
 * @Author GitHub - VitorJusten
 * @ProjectName justen-infrastructure
 * @Year 2026
 */
@Component
public class FileValidationUtils {

	private static final List<String> DANGEROUS_EXTENSIONS = Arrays.asList(
			"exe", "bat", "cmd", "sh", "bin", "com", "msi", "scr", "vbs", "wsf", "ps1", "jar", "pif"
	);

	/**
	 * Valida que o nome do arquivo não possui extensões executáveis perigosas.
	 */
	public static void validateNotExecutable(String fileName) {
		if (fileName == null || fileName.isBlank()) {
			return;
		}

		String lower = fileName.toLowerCase(Locale.ROOT).trim();
		for (String ext : DANGEROUS_EXTENSIONS) {
			if (lower.endsWith("." + ext)) {
				throw new BusinessException("executableFilesNotAllowed");
			}
		}
	}

	/**
	 * Valida que os bytes do arquivo não correspondem a cabeçalhos de executáveis (Magic Numbers).
	 * MZ para executáveis DOS/PE (exe, dll, etc), ELF para binários Unix, scripts (#!) etc.
	 */
	public static void validateNotExecutable(byte[] fileBytes) {
		if (fileBytes == null || fileBytes.length < 2) {
			return;
		}

		// 'M' (0x4D) 'Z' (0x5A) -> DOS / Windows PE Executable
		if (fileBytes[0] == 0x4D && fileBytes[1] == 0x5A) {
			throw new BusinessException("executableFilesNotAllowed");
		}

		// ELF header: 0x7F 'E' 'L' 'F'
		if (fileBytes.length >= 4 && fileBytes[0] == 0x7F && fileBytes[1] == 0x45 && fileBytes[2] == 0x4C && fileBytes[3] == 0x46) {
			throw new BusinessException("executableFilesNotAllowed");
		}

		// Script shebang: "#!"
		if (fileBytes[0] == 0x23 && fileBytes[1] == 0x21) {
			throw new BusinessException("executableFilesNotAllowed");
		}
	}

	/**
	 * Valida que os bytes correspondem estritamente a uma imagem suportada (JPEG, PNG, GIF, WEBP, BMP).
	 */
	public static void validateIsImage(byte[] imageBytes) {
		if (imageBytes == null || imageBytes.length < 4) {
			throw new BusinessException("invalidImageFormat");
		}

		// Garante que não é executável
		validateNotExecutable(imageBytes);

		// JPEG: FF D8 FF
		boolean isJpeg = (imageBytes[0] == (byte) 0xFF && imageBytes[1] == (byte) 0xD8 && imageBytes[2] == (byte) 0xFF);

		// PNG: 89 50 4E 47
		boolean isPng = (imageBytes[0] == (byte) 0x89 && imageBytes[1] == (byte) 0x50 && imageBytes[2] == (byte) 0x4E && imageBytes[3] == (byte) 0x47);

		// GIF: 47 49 46 38
		boolean isGif = (imageBytes[0] == (byte) 0x47 && imageBytes[1] == (byte) 0x49 && imageBytes[2] == (byte) 0x46 && imageBytes[3] == (byte) 0x38);

		// WEBP: "RIFF" .... "WEBP"
		boolean isWebp = imageBytes.length >= 12
				&& imageBytes[0] == (byte) 'R' && imageBytes[1] == (byte) 'I' && imageBytes[2] == (byte) 'F' && imageBytes[3] == (byte) 'F'
				&& imageBytes[8] == (byte) 'W' && imageBytes[9] == (byte) 'E' && imageBytes[10] == (byte) 'B' && imageBytes[11] == (byte) 'P';

		// BMP: 42 4D ("BM")
		boolean isBmp = (imageBytes[0] == (byte) 0x42 && imageBytes[1] == (byte) 0x4D);

		if (!isJpeg && !isPng && !isGif && !isWebp && !isBmp) {
			throw new BusinessException("invalidImageFormat");
		}
	}

	/**
	 * Valida arquivo de regulamento (PDF, Office, Imagens, Texto, etc.), impedindo executáveis.
	 */
	public static void validateRegulationFile(byte[] fileBytes, String fileName) {
		if (fileBytes == null || fileBytes.length == 0) {
			return;
		}

		validateNotExecutable(fileBytes);
		if (fileName != null) {
			validateNotExecutable(fileName);
		}
	}

}
