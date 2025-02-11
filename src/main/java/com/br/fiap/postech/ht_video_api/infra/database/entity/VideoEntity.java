package com.br.fiap.postech.ht_video_api.infra.database.entity;

import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.br.fiap.postech.ht_video_api.domain.entity.StatusEdicao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "video")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoEntity {

	private @MongoId String id;
	private String idUsuario;
	private UUID codigoEdicao;
	private String nome;
	private Long tentativasDeEdicao;
	private StatusEdicao statusEdicao;
}