package com.br.fiap.postech.ht_video_api.application.usecase;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.fiap.postech.ht_video_api.application.dto.VideoDto;
import com.br.fiap.postech.ht_video_api.domain.entity.Video;
import com.br.fiap.postech.ht_video_api.domain.repository.IVideoDatabaseAdapter;
import com.br.fiap.postech.ht_video_api.domain.repository.IVideoQueueAdapterOUT;
import com.br.fiap.postech.ht_video_api.domain.usecase.RegistrarVideoUseCase;
import com.google.gson.Gson;

@Service
public class RegistrarVideo implements RegistrarVideoUseCase{

	private final IVideoDatabaseAdapter videoDatabaseAdapter;
    private final IVideoQueueAdapterOUT videoQueueAdapterOUT;
	
    @Autowired
    private Gson gson;
    
    public RegistrarVideo(IVideoDatabaseAdapter videoDatabaseAdapter, IVideoQueueAdapterOUT videoQueueAdapterOUT) {
    	this.videoDatabaseAdapter = videoDatabaseAdapter;
        this.videoQueueAdapterOUT = videoQueueAdapterOUT;
    }
    
	@Transactional
	public VideoDto executar(VideoDto videoDto) {
		Video video = new Video(videoDto.getNome(),videoDto.getIdUsuario());
		Video videoSalvo = videoDatabaseAdapter.save(video);
		videoQueueAdapterOUT.publish(toVideoMessage(videoSalvo));
        return toVideoDto(videoSalvo);
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

    private String toVideoMessage(Video video){
        Map message = new HashMap<String, String>();
        message.put("nomeVideo",video.getNome());
        message.put("codigoEdicao",video.getCodigoEdicao().toString());
        message.put("statusEdicao",video.getStatusEdicao());
        return gson.toJson(message);
    }
}
