package com.justen.social.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.justen.social.domain.model.Media;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-social
 * @Year 2026
 *
 */
public interface MediaRepository extends JpaRepository<Media, UUID> {

}
