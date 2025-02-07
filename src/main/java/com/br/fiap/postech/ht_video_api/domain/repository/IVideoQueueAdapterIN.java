package com.br.fiap.postech.ht_video_api.domain.repository;

import org.springframework.messaging.handler.annotation.Payload;

public interface IVideoQueueAdapterIN {
	void receive(@Payload String message);
}
