package com.br.fiap.postech.ht_video_api.application.dto;

import com.br.fiap.postech.ht_video_api.domain.entity.StatusEdicao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "Video")
public class VideoDto {

	@Schema(example = "123",accessMode = Schema.AccessMode.READ_ONLY)
	private String id;
	
	@Schema(example = "123",accessMode = Schema.AccessMode.READ_ONLY)
	private String idUsuario;
	
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String codigoEdicao;
	
	@Schema(example = "Video de Teste.mp4",accessMode = Schema.AccessMode.READ_ONLY)
	private String nome;

	@Schema(example = "1",accessMode = Schema.AccessMode.READ_ONLY)
	private Long tentativasDeEdicao;
	
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private StatusEdicao statusEdicao;
	
}
