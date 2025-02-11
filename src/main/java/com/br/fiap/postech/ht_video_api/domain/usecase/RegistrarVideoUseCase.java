package com.br.fiap.postech.ht_video_api.domain.usecase;

import java.util.List;

import com.br.fiap.postech.ht_video_api.application.dto.VideoDto;

public interface RegistrarVideoUseCase {
	List<VideoDto> executar(List<VideoDto> listaVideoDto);
}
