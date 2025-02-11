package com.br.fiap.postech.ht_video_api.application.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.br.fiap.postech.ht_video_api.application.dto.VideoDto;
import com.br.fiap.postech.ht_video_api.domain.usecase.ListarVideosPorUsuarioUseCase;
import com.br.fiap.postech.ht_video_api.domain.usecase.RegistrarVideoUseCase;

class VideoControllerTest {

    @Mock
    private RegistrarVideoUseCase registrarVideoUseCase;

    @Mock
    private ListarVideosPorUsuarioUseCase listarVideosPorUsuarioUseCase;

    @InjectMocks
    private VideoController videoController;

    private List<VideoDto> listaVideos;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Criando uma lista de VideoDto para testes
        VideoDto video1 = new VideoDto("1", "user1", "1234", "Video 1", "0", null);
        VideoDto video2 = new VideoDto("2", "user1", "5678", "Video 2", "0", null);
        listaVideos = Arrays.asList(video1, video2);
    }

    @Test
    void testRegistrarVideos_Success() {
        // Configurando o comportamento do mock
        when(registrarVideoUseCase.executar(listaVideos)).thenReturn(listaVideos);

        // Chamando o método
        ResponseEntity response = videoController.registrarVideos(listaVideos);

        // Verificando se o método retorna o status 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificando se o corpo da resposta contém a lista de vídeos
        assertEquals(listaVideos, response.getBody());
    }

    @Test
    void testRegistrarVideos_InternalServerError() {
        // Configurando o comportamento do mock para lançar uma exceção
        when(registrarVideoUseCase.executar(listaVideos)).thenThrow(new RuntimeException("Erro ao registrar"));

        // Chamando o método
        ResponseEntity response = videoController.registrarVideos(listaVideos);

        // Verificando se o método retorna o status 500 Internal Server Error
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        // Verificando se o corpo da resposta contém a mensagem de erro
        assertEquals("Erro ao registrar", response.getBody());
    }

    @Test
    void testListarVideosPorUsuario_Success() {
        // Configurando o comportamento do mock
        when(listarVideosPorUsuarioUseCase.executar("user1")).thenReturn(listaVideos);

        // Chamando o método
        ResponseEntity response = videoController.listarVideosPorUsuario("user1");

        // Verificando se o método retorna o status 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificando se o corpo da resposta contém a lista de vídeos
        assertEquals(listaVideos, response.getBody());
    }

    @Test
    void testListarVideosPorUsuario_InternalServerError() {
        // Configurando o comportamento do mock para lançar uma exceção
        when(listarVideosPorUsuarioUseCase.executar("user1")).thenThrow(new RuntimeException("Erro ao listar vídeos"));

        // Chamando o método
        ResponseEntity response = videoController.listarVideosPorUsuario("user1");

        // Verificando se o método retorna o status 500 Internal Server Error
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        // Verificando se o corpo da resposta contém a mensagem de erro
        assertEquals("Erro ao listar vídeos", response.getBody());
    }
}

