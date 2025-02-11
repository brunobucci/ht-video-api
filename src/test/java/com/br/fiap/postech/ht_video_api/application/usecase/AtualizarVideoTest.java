package com.br.fiap.postech.ht_video_api.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
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
import com.br.fiap.postech.ht_video_api.domain.repository.IVideoQueueAdapterOUT;
import com.google.gson.Gson;

class AtualizarVideoTest {

	@Mock
    private IVideoDatabaseAdapter videoDatabaseAdapter;

    @Mock
    private IVideoQueueAdapterOUT videoQueueAdapterOUT;

    @Mock
    private Gson gson;

    @InjectMocks
    private AtualizarVideo atualizarVideo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecutarComErro() {
        // Arrange
    	VideoDto videoDto = new VideoDto("1", "1", "74290ce2-7b98-41cf-a5c9-61fb0d9b62bc", "Video 1", "0", StatusEdicao.COM_ERRO);
        Video videoSalvo = new Video(videoDto.getId(), videoDto.getIdUsuario(), UUID.fromString(videoDto.getCodigoEdicao()), videoDto.getNome(), videoDto.getTentativasDeEdicao(), videoDto.getStatusEdicao());

        when(videoDatabaseAdapter.save(any(Video.class))).thenReturn(videoSalvo);
        when(gson.toJson(any())).thenReturn("jsonMessage");

        // Act
        VideoDto resultado = atualizarVideo.executar(videoDto);

        // Assert
        assertEquals(videoDto.getId(), resultado.getId());
        assertEquals(videoDto.getNome(), resultado.getNome());
        assertEquals(StatusEdicao.COM_ERRO, resultado.getStatusEdicao());

        // Verifica se o método publish foi chamado
        verify(videoQueueAdapterOUT, times(1)).publish(anyString());
    }

    @Test
    void testExecutarSemErro() {
        // Arrange
    	VideoDto videoDto = new VideoDto("1", "1", "74290ce2-7b98-41cf-a5c9-61fb0d9b62bc", "Video 1", "0", StatusEdicao.FINALIZADA);
        Video videoSalvo = new Video(videoDto.getId(), videoDto.getIdUsuario(), UUID.fromString(videoDto.getCodigoEdicao()), videoDto.getNome(), videoDto.getTentativasDeEdicao(), videoDto.getStatusEdicao());

        when(videoDatabaseAdapter.save(any(Video.class))).thenReturn(videoSalvo);

        // Act
        VideoDto resultado = atualizarVideo.executar(videoDto);

        // Assert
        assertEquals(videoDto.getId(), resultado.getId());
        assertEquals(videoDto.getNome(), resultado.getNome());
        assertEquals(StatusEdicao.FINALIZADA, resultado.getStatusEdicao());

        // Verifica se o método publish não foi chamado
        verify(videoQueueAdapterOUT, never()).publish(anyString());
    }
}
