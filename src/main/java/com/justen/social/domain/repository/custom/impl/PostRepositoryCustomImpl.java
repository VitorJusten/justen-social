package com.justen.social.domain.repository.custom.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.justen.social.core.dto.MediaTypeDto;
import com.justen.social.core.dto.PostDto;
import com.justen.social.core.utils.DateUtils;
import com.justen.social.domain.repository.custom.PostRepositoryCustom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

	@PersistenceContext
	private final EntityManager entityManager;
	private final DateUtils dateUtils;

	@Override
	public Page<PostDto> findAllPosts(Pageable pageable, String filters) {

		StringBuilder sql = new StringBuilder();

		sql.append("""
				    SELECT
				        p.post_cd_id,
				        p.usac_tx_username,
				        p.post_tx_title,
				        p.post_tx_description,
				        p.post_nm_published,
				        p.post_nm_fixed,
				        p.post_dt_created_at,
				        p.post_dt_updated_at,
				        mt.pomt_cd_id,
				        mt.pomt_tx_name
				    FROM post p JOIN post_media_type mt ON mt.pomt_cd_id = p.pomt_cd_id
				    WHERE 1=1
				""");

		if (filters != null && !filters.isBlank()) {
			sql.append("""
					    AND (
					        post_tx_title ILIKE :filter
					        OR post_tx_description ILIKE :filter
					        OR CAST(post_cd_author AS TEXT) ILIKE :filter
					    )
					""");
		}

		sql.append(" ORDER BY post_dt_created_at DESC ");
		sql.append(" LIMIT :limit OFFSET :offset ");

		Query query = entityManager.createNativeQuery(sql.toString());

		if (filters != null && !filters.isBlank()) {
			query.setParameter("filter", "%" + filters + "%");
		}

		query.setParameter("limit", pageable.getPageSize());
		query.setParameter("offset", pageable.getOffset());

		List<Object[]> result = query.getResultList();

		List<PostDto> posts = new ArrayList<>();

		for (Object[] row : result) {

			PostDto dto = new PostDto();

			dto.setId((UUID) row[0]);
			dto.setAuthorName((String) row[1]);
			dto.setTitle((String) row[2]);
			dto.setDescription((String) row[3]);
			dto.setPublished((Boolean) row[4]);
			dto.setHighlight((Boolean) row[5]);
			dto.setCreatedAt(dateUtils.toOffsetDateTime(row[6]));
			dto.setUpdatedAt(dateUtils.toOffsetDateTime(row[7]));

			MediaTypeDto type = new MediaTypeDto();

			type.setId((UUID) row[8]);
			type.setName((String) row[9]);

			dto.setMediaType(type);

			posts.add(dto);
		}

		Long total = countPosts(filters);

		return new PageImpl<>(posts, pageable, total);
	}

	private Long countPosts(String filters) {

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT COUNT(*) FROM post WHERE 1=1 ");

		if (filters != null && !filters.isBlank()) {
			sql.append("""
					    AND (
					        post_tx_title ILIKE :filter
					        OR post_tx_description ILIKE :filter
					        OR CAST(post_cd_author AS TEXT) ILIKE :filter
					    )
					""");
		}

		Query query = entityManager.createNativeQuery(sql.toString());

		if (filters != null && !filters.isBlank()) {
			query.setParameter("filter", "%" + filters + "%");
		}

		return ((Number) query.getSingleResult()).longValue();
	}

}