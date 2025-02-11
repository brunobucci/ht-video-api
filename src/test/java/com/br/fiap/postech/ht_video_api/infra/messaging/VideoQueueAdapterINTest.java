package com.br.fiap.postech.ht_video_api.infra.messaging;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.br.fiap.postech.ht_video_api.application.dto.VideoDto;
import com.br.fiap.postech.ht_video_api.domain.entity.StatusEdicao;
import com.br.fiap.postech.ht_video_api.domain.usecase.AtualizarVideoUseCase;
import com.br.fiap.postech.ht_video_api.domain.usecase.BuscarVideoPorIdUseCase;
import com.google.gson.Gson;

class VideoQueueAdapterINTest {

    @Mock
    private Gson gson;

    @Mock
    private AtualizarVideoUseCase atualizarVideoUsecase;

    @Mock
    private BuscarVideoPorIdUseCase buscarVideoPorIdUseCase;

    @InjectMocks
    private VideoQueueAdapterIN videoQueueAdapterIN;

    private String message;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Exemplo de mensagem que será simulada
        message = "{\"id\":\"1234\", \"codigoEdicao\":\"74290ce2-7b98-41cf-a5c9-61fb0d9b62bc\", \"nomeVideo\":\"Video Teste\", \"tentativasDeEdicao\":\"1\", \"statusEdicao\":\"COM_ERRO\"}";
    }

    @Test
    void testReceive_withErrorStatusAndLessThan3Retries() {
        // Dado
        HashMap<String, String> mensagem = new HashMap<>();
        mensagem.put("id", "1234");
        mensagem.put("codigoEdicao", "74290ce2-7b98-41cf-a5c9-61fb0d9b62bc");
        mensagem.put("nomeVideo", "Video Teste");
        mensagem.put("tentativasDeEdicao", "1");
        mensagem.put("statusEdicao", "COM_ERRO");

        //VideoDto videoDto = new VideoDto("1234", null, "74290ce2-7b98-41cf-a5c9-61fb0d9b62bc", "Video Teste1", "1", StatusEdicao.COM_ERRO);

        VideoDto videoSalvo = new VideoDto("1234", null, "74290ce2-7b98-41cf-a5c9-61fb0d9b62bc", "Video Teste1", "1", StatusEdicao.COM_ERRO);

        when(gson.fromJson(message, HashMap.class)).thenReturn(mensagem);
        when(buscarVideoPorIdUseCase.executar("1234")).thenReturn(videoSalvo);

        // Chama o método que deve ser testado
        videoQueueAdapterIN.receive(message);

        // Verifica se o método buscarVideoPorIdUseCase foi chamado corretamente
        verify(buscarVideoPorIdUseCase).executar("1234");

        // Verifica se o método atualizarVideoUsecase foi chamado corretamente para atualizar o status
        verify(atualizarVideoUsecase).executar(videoSalvo);

        // Verifica que as tentativas de edição foram incrementadas
        assertEquals("2", videoSalvo.getTentativasDeEdicao());
        assertEquals(StatusEdicao.COM_ERRO, videoSalvo.getStatusEdicao());
    }

    @Test
    void testReceive_withFinalizedStatus() {
        // Dado
        HashMap<String, String> mensagem = new HashMap<>();
        mensagem.put("id", "1234");
        mensagem.put("codigoEdicao", "74290ce2-7b98-41cf-a5c9-61fb0d9b62bc");
        mensagem.put("nomeVideo", "Video Teste");
        mensagem.put("tentativasDeEdicao", "1");
        mensagem.put("statusEdicao", "COM_ERRO");

        //VideoDto videoDto = new VideoDto("1234", null, "5678", "Video Teste", "1", StatusEdicao.FINALIZADA);
        VideoDto videoSalvo = new VideoDto("1234", null, "74290ce2-7b98-41cf-a5c9-61fb0d9b62bc", "Video Teste1", "1", StatusEdicao.COM_ERRO);

        when(gson.fromJson(message, HashMap.class)).thenReturn(mensagem);
        when(buscarVideoPorIdUseCase.executar("1234")).thenReturn(videoSalvo);

        // Chama o método que deve ser testado
        videoQueueAdapterIN.receive(message);

        // Verifica se o método buscarVideoPorIdUseCase foi chamado corretamente
        verify(buscarVideoPorIdUseCase).executar("1234");

        // Verifica se o método atualizarVideoUsecase foi chamado corretamente para atualizar o status para FINALIZADA
        verify(atualizarVideoUsecase).executar(videoSalvo);

        // Verifica que o status foi alterado para FINALIZADA
        assertEquals(StatusEdicao.COM_ERRO, videoSalvo.getStatusEdicao());
    }
}

