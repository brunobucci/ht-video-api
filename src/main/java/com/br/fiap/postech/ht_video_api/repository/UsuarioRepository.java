package com.br.fiap.postech.ht_video_api.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.br.fiap.postech.ht_video_api.infra.database.entity.UsuarioEntity;


public interface UsuarioRepository extends MongoRepository<UsuarioEntity, String>{

	@Query("{username:'?0'}")
	Optional<UsuarioEntity> findUsuarioByUsername(String username);

}