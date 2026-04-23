package com.justen.social.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.justen.social.domain.model.entity.PostMedia;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-social
 * @Year 2026
 *
 */
public interface PostMediaRepository extends JpaRepository<PostMedia, UUID> {

}
