package com.br.fiap.postech.ht_video_api.domain.usecase;

import java.util.List;

import com.br.fiap.postech.ht_video_api.application.dto.VideoDto;

public interface ListarVideosPorUsuarioUseCase {
	List<VideoDto> executar(String idUsuario);
}
