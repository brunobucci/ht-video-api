package com.br.fiap.postech.ht_video_api.domain.usecase;

import com.br.fiap.postech.ht_video_api.application.dto.VideoDto;

public interface BuscarVideoPorIdUseCase {
	VideoDto executar(String codigoEdicao);
}
