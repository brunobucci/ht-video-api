package com.br.fiap.postech.ht_video_api.domain.repository;

import java.util.List;

import com.br.fiap.postech.ht_video_api.domain.entity.Video;

public interface IVideoDatabaseAdapter {

	Video save(Video video);
	Video findVideoById(String idVideo);

	List<Video> findVideosByIdUsuario(String idUsuario);
    
	List<Video> findAll();
}
