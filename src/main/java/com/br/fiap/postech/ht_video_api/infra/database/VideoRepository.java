package com.br.fiap.postech.ht_video_api.infra.database;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.br.fiap.postech.ht_video_api.domain.entity.Video;
import com.br.fiap.postech.ht_video_api.infra.database.entity.VideoEntity;

public interface VideoRepository extends MongoRepository<VideoEntity, String>{

	@Query("{idUsuario:'?0'}")
	List<Video> findVideosByIdUsuario(String idUsuario);

}
