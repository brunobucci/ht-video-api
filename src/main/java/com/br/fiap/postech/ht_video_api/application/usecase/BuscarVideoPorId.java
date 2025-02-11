package com.br.fiap.postech.ht_video_api.application.usecase;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.br.fiap.postech.ht_video_api.application.dto.VideoDto;
import com.br.fiap.postech.ht_video_api.domain.entity.Video;
import com.br.fiap.postech.ht_video_api.domain.repository.IVideoDatabaseAdapter;
import com.br.fiap.postech.ht_video_api.domain.usecase.BuscarVideoPorIdUseCase;

@Service
public class BuscarVideoPorId implements BuscarVideoPorIdUseCase{

	private final IVideoDatabaseAdapter videoDatabaseAdapter;
    
    public BuscarVideoPorId(IVideoDatabaseAdapter videoDatabaseAdapter) {
    	this.videoDatabaseAdapter = videoDatabaseAdapter;
    }
    
	public VideoDto executar(String idVideo) {
		Video video = videoDatabaseAdapter.findVideoById(idVideo);
		if(Objects.nonNull(video)) {
			return toVideoDto(video);
		}
		return null;
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