package com.justen.social.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.justen.social.domain.model.Post;
import com.justen.social.domain.repository.custom.PostRepositoryCustom;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-social
 * @Year 2026
 *
 */
public interface PostRepository extends JpaRepository<Post, UUID>, PostRepositoryCustom  {

}