package com.br.fiap.postech.ht_video_api.application.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.fiap.postech.ht_video_api.application.dto.VideoDto;
import com.br.fiap.postech.ht_video_api.domain.usecase.ListarVideosPorUsuarioUseCase;
import com.br.fiap.postech.ht_video_api.domain.usecase.RegistrarVideoUseCase;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/video")
@Tag(name = "Videos", description = "Recursos relacionados aos videos.")
public class VideoController {
    
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final RegistrarVideoUseCase registrarVideoPedidoUseCase;
    private final ListarVideosPorUsuarioUseCase listarVideosPorUsuarioUseCase;
	
    @Autowired
    public VideoController(RegistrarVideoUseCase registrarVideoPedidoUseCase, ListarVideosPorUsuarioUseCase listarVideosPorUsuarioUseCase) {
        this.registrarVideoPedidoUseCase = registrarVideoPedidoUseCase;
        this.listarVideosPorUsuarioUseCase = listarVideosPorUsuarioUseCase;
    }
    
    
    @SuppressWarnings("rawtypes")
	@PostMapping(value="/registrar-video", produces = "application/json")
    public ResponseEntity registrarUsuario(@RequestBody VideoDto videoDto) {
    	try {
    		VideoDto videoDtoSalvo = registrarVideoPedidoUseCase.executar(videoDto);
            return ResponseEntity.status(HttpStatus.OK).body(videoDtoSalvo);
        } catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @SuppressWarnings("rawtypes")
	@PostMapping(value="/listar-videos/{idUsuario}", produces = "application/json")
    public ResponseEntity listarVideosPorUsuario(@Parameter(description = "Id do usuario", example = "123") @PathVariable String idUsuario) {
    	try {
    		List<VideoDto> videosDtoSalvo = listarVideosPorUsuarioUseCase.executar(idUsuario);
            return ResponseEntity.status(HttpStatus.OK).body(videosDtoSalvo);
        } catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}