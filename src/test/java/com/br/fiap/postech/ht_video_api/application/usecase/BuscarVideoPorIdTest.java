package com.br.fiap.postech.ht_video_api.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.br.fiap.postech.ht_video_api.application.dto.VideoDto;
import com.br.fiap.postech.ht_video_api.domain.entity.StatusEdicao;
import com.br.fiap.postech.ht_video_api.domain.entity.Video;
import com.br.fiap.postech.ht_video_api.domain.repository.IVideoDatabaseAdapter;

class BuscarVideoPorIdTest {

    @Mock
    private IVideoDatabaseAdapter videoDatabaseAdapter;

    @InjectMocks
    private BuscarVideoPorId buscarVideoPorId;

    private Video video;
    private String videoId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa os mocks

        // Preparando o cenário para o teste
        videoId = "12345";
        video = new Video();
        video.setId(videoId);
        video.setIdUsuario("usuario1");
        video.setCodigoEdicao(UUID.fromString("674ef3ac-8826-4d3f-8704-93d14f4ac93a"));
        video.setNome("Nome do Vídeo");
        video.setTentativasDeEdicao("3");
        video.setStatusEdicao(StatusEdicao.CRIADA);
    }

    @Test
    void testExecutar() {
        // Definindo comportamento do mock
        when(videoDatabaseAdapter.findVideoById(videoId)).thenReturn(video);

        // Executando o método a ser testado
        VideoDto videoDto = buscarVideoPorId.executar(videoId);

        // Verificando se o método foi chamado corretamente
        verify(videoDatabaseAdapter).findVideoById(videoId);

        // Verificando o conteúdo do retorno
        assertNotNull(videoDto);
        assertEquals(videoId, videoDto.getId());
        assertEquals("usuario1", videoDto.getIdUsuario());
        assertEquals("674ef3ac-8826-4d3f-8704-93d14f4ac93a", videoDto.getCodigoEdicao());
        assertEquals("Nome do Vídeo", videoDto.getNome());
        assertEquals("3", videoDto.getTentativasDeEdicao());
        assertEquals(StatusEdicao.CRIADA, videoDto.getStatusEdicao());
    }

    @Test
    void testExecutarVideoNaoEncontrado() {
        // Quando o vídeo não for encontrado, o mock deve retornar null
        when(videoDatabaseAdapter.findVideoById(videoId)).thenReturn(null);

        // Executando o método a ser testado
        VideoDto videoDto = buscarVideoPorId.executar(videoId);

        // Verificando se o retorno é null
        assertNull(videoDto);
    }
}

