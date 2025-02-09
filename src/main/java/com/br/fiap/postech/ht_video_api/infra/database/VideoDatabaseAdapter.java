package com.br.fiap.postech.ht_video_api.infra.database;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.fiap.postech.ht_video_api.domain.entity.Video;
import com.br.fiap.postech.ht_video_api.domain.repository.IVideoDatabaseAdapter;
import com.br.fiap.postech.ht_video_api.infra.database.entity.VideoEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VideoDatabaseAdapter implements IVideoDatabaseAdapter {
	
	@Autowired
	private final VideoRepository videoRepository;

	
	@Override
	public Video save(Video video) {
		VideoEntity videoEntity = toVideoEntity(video);
		VideoEntity entity = videoRepository.save(videoEntity);
        return toVideo(entity);
	}

	@Override
	public List<Video> findVideosByIdUsuario(String idUsuario) {
		return videoRepository.findVideosByIdUsuario(idUsuario);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List findAll() {
		return videoRepository.findAll();
	}

	private Video toVideo(VideoEntity videoEntity) {
		Video video = null;
		video = new Video(videoEntity.getId(), 
				videoEntity.getIdUsuario(), 
				videoEntity.getCodigoEdicao(), 
				videoEntity.getNome(), 
				videoEntity.getTentativasDeEdicao(), 
				videoEntity.getStatusEdicao());
		
		return video;
	}

	private VideoEntity toVideoEntity(Video video) {
		VideoEntity videoEntity = null;
		videoEntity = new VideoEntity(
				video.getId(), 
				video.getIdUsuario(), 
				video.getCodigoEdicao(),
				video.getNome(),
				video.getTentativasDeEdicao(),
				video.getStatusEdicao());
		return videoEntity;
	}

	@Override
	public Video findVideoById(String idVideo) {
		return videoRepository.findVideoById(idVideo);
	}
	
}
