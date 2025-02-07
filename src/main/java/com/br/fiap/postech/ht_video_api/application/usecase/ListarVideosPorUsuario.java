package com.br.fiap.postech.ht_video_api.application.usecase;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.br.fiap.postech.ht_video_api.application.dto.VideoDto;
import com.br.fiap.postech.ht_video_api.domain.entity.Video;
import com.br.fiap.postech.ht_video_api.domain.repository.IVideoDatabaseAdapter;
import com.br.fiap.postech.ht_video_api.domain.usecase.ListarVideosPorUsuarioUseCase;

@Service
public class ListarVideosPorUsuario implements ListarVideosPorUsuarioUseCase{

	private final IVideoDatabaseAdapter videoDatabaseAdapter;
    
    public ListarVideosPorUsuario(IVideoDatabaseAdapter videoDatabaseAdapter) {
    	this.videoDatabaseAdapter = videoDatabaseAdapter;
    }
    
	public List<VideoDto> executar(String idUsuario) {
		List<Video> videosSalvos = videoDatabaseAdapter.findVideosByIdUsuario(idUsuario);
		
		if(Objects.nonNull(videosSalvos)) {
			return videosSalvos.stream()
					.map(this::toVideoDto)
					.toList();
		}
		return Collections.emptyList();
	}

	 private VideoDto toVideoDto(Video video){
		 return new VideoDto(video.getId(), 
	        			video.getIdUsuario(), 
	        			video.getCodigoEdicao().toString(), 
	        			video.getNome(), 
	        			video.getTentativasDeEdicao(), 
	        			video.getStatusEdicao()
	        		);
	 }
}
