package com.br.fiap.postech.ht_video_api.domain.usecase;

import com.br.fiap.postech.ht_video_api.application.dto.VideoDto;

public interface AtualizarVideoUseCase {
	VideoDto executar(VideoDto videoDto);
}
