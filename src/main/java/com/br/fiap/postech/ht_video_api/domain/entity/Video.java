package com.br.fiap.postech.ht_video_api.domain.entity;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Video {
	
	public Video(String nome, String idUsuario) {
        this.codigoEdicao=UUID.randomUUID();
        this.nome = nome;
        this.idUsuario = idUsuario;
        this.statusEdicao = StatusEdicao.CRIADA;
        this.tentativasDeEdicao = "1";
    }
	private String id;
	private String idUsuario;
	private UUID codigoEdicao;
	private String nome;
	private String tentativasDeEdicao;
    private StatusEdicao statusEdicao;
}
