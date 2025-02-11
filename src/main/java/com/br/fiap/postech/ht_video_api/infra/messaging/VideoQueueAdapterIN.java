package com.br.fiap.postech.ht_video_api.infra.messaging;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.br.fiap.postech.ht_video_api.application.dto.VideoDto;
import com.br.fiap.postech.ht_video_api.domain.entity.StatusEdicao;
import com.br.fiap.postech.ht_video_api.domain.repository.IVideoQueueAdapterIN;
import com.br.fiap.postech.ht_video_api.domain.usecase.AtualizarVideoUseCase;
import com.br.fiap.postech.ht_video_api.domain.usecase.BuscarVideoPorIdUseCase;
import com.google.gson.Gson;

@Service
public class VideoQueueAdapterIN implements IVideoQueueAdapterIN{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private Gson gson;
	
	private AtualizarVideoUseCase atualizarVideoUsecase;
	private BuscarVideoPorIdUseCase buscarVideoPorIdUseCase;
	
	public VideoQueueAdapterIN(AtualizarVideoUseCase atualizarVideoUsecase, BuscarVideoPorIdUseCase buscarVideoPorIdUseCase) {
		this.atualizarVideoUsecase = atualizarVideoUsecase;
		this.buscarVideoPorIdUseCase = buscarVideoPorIdUseCase;
	}
	
	@SuppressWarnings("unchecked")
	@RabbitListener(queues = {"${queue2.name}"})
	public void receive(@Payload String message) {
		HashMap<String, String> mensagem = new Gson().fromJson(message, HashMap.class);
		VideoDto videoMenssagem = fromMessageToDto(mensagem);
		
		VideoDto videoSalvo = buscarVideoPorIdUseCase.executar(videoMenssagem.getId());
		
		if(videoMenssagem.getStatusEdicao().equals(StatusEdicao.COM_ERRO)) {
			long nroTentativas = Long.parseLong(videoSalvo.getTentativasDeEdicao());
			if(nroTentativas < 3l) {
				nroTentativas++;
				videoSalvo.setTentativasDeEdicao(nroTentativas+"");
				videoSalvo.setStatusEdicao(StatusEdicao.COM_ERRO);
				atualizarVideoUsecase.executar(videoSalvo);
			}
		}else {
			videoSalvo.setStatusEdicao(StatusEdicao.FINALIZADA);
			atualizarVideoUsecase.executar(videoSalvo);
		}
		
	}
	
	static VideoDto fromMessageToDto(Map<String, String> mensagem) {
		return new VideoDto(
				mensagem.get("id"),
				null,
				mensagem.get("codigoEdicao"),
				mensagem.get("nomeVideo"), 
				mensagem.get("tentativasDeEdicao"), 
				StatusEdicao.valueOf(mensagem.get("statusEdicao")));
	}
}
