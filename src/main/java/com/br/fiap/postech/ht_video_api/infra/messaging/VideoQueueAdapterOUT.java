package com.br.fiap.postech.ht_video_api.infra.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.br.fiap.postech.ht_video_api.domain.repository.IVideoQueueAdapterOUT;

@Service
public class VideoQueueAdapterOUT implements IVideoQueueAdapterOUT{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Value("${queue1.name}")
	private String videosPendentes;
	
	public void publish(String message) {
		rabbitTemplate.convertAndSend(videosPendentes, message);
		logger.info("Publicação na fila de videos pendentes realizada com sucesso.");
	}
}
