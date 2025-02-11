package com.br.fiap.postech.ht_video_api.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

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

class RegistrarVideoTest {

	@Mock
    private IVideoDatabaseAdapter videoDatabaseAdapter;

    @Mock
    private IVideoQueueAdapterOUT videoQueueAdapterOUT;

    @Mock
    private Gson gson;

    @InjectMocks
    private RegistrarVideo registrarVideo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecutar() {
        // Arrange
        VideoDto videoDto1 = new VideoDto("", "1", "codigo1", "Video 1", "0", StatusEdicao.CRIADA);
        VideoDto videoDto2 = new VideoDto("", "2", "codigo2", "Video 2", "0", StatusEdicao.CRIADA);
        List<VideoDto>  listaVideoDto = Arrays.asList(videoDto1, videoDto2);

        Video videoSalvo1 = new Video("Video 1", "1");
        videoSalvo1.setId("1");
        Video videoSalvo2 = new Video("Video 2", "2");
        videoSalvo2.setId("2");

        when(videoDatabaseAdapter.save(any(Video.class))).thenReturn(videoSalvo1).thenReturn(videoSalvo2);
        when(gson.toJson(any())).thenReturn("jsonMessage");

        // Act
        List<VideoDto> resultado = registrarVideo.executar(listaVideoDto);

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("Video 1", resultado.get(0).getNome());
        assertEquals("Video 2", resultado.get(1).getNome());

        // Verifica se o método save foi chamado duas vezes
        verify(videoDatabaseAdapter, times(2)).save(any(Video.class));

        // Verifica se o método publish foi chamado duas vezes
        verify(videoQueueAdapterOUT, times(2)).publish(any(String.class));
    }
}
