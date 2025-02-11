package com.br.fiap.postech.ht_video_api.infra.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.br.fiap.postech.ht_video_api.domain.entity.StatusEdicao;
import com.br.fiap.postech.ht_video_api.domain.entity.Video;
import com.br.fiap.postech.ht_video_api.infra.database.entity.VideoEntity;

class VideoDatabaseAdapterTest {

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private VideoDatabaseAdapter videoDatabaseAdapter;

    private Video video;
    private VideoEntity videoEntity;
    private String videoId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa os mocks

        // Inicializando dados de exemplo
        videoId = "12345";
        video = new Video(videoId, "usuario1", UUID.fromString("674ef3ac-8826-4d3f-8704-93d14f4ac93a"), "Nome do Vídeo", "3", StatusEdicao.CRIADA);
        videoEntity = new VideoEntity(videoId, "usuario1", UUID.fromString("674ef3ac-8826-4d3f-8704-93d14f4ac93a"), "Nome do Vídeo", "3", StatusEdicao.CRIADA);
    }

    @Test
    void testSave() {
        // Configurando o comportamento do mock
        when(videoRepository.save(any(VideoEntity.class))).thenReturn(videoEntity);

        // Chamando o método a ser testado
        Video savedVideo = videoDatabaseAdapter.save(video);

        // Verificando se o método do repositório foi chamado corretamente
        verify(videoRepository).save(any(VideoEntity.class));

        // Verificando o retorno
        assertNotNull(savedVideo);
        assertEquals(videoId, savedVideo.getId());
        assertEquals("usuario1", savedVideo.getIdUsuario());
        assertEquals(UUID.fromString("674ef3ac-8826-4d3f-8704-93d14f4ac93a"), savedVideo.getCodigoEdicao());
        assertEquals("Nome do Vídeo", savedVideo.getNome());
        assertEquals("3", savedVideo.getTentativasDeEdicao());
        assertEquals(StatusEdicao.CRIADA, savedVideo.getStatusEdicao());
    }

    @Test
    void testFindVideosByIdUsuario() {
        // Convertendo a lista de VideoEntity para Video
        VideoEntity videoEntity = new VideoEntity(videoId, "usuario1", UUID.fromString("674ef3ac-8826-4d3f-8704-93d14f4ac93a"), "Nome do Vídeo", "3", StatusEdicao.CRIADA);
        
        List<Video> videoList = Arrays.asList(
                new Video(videoEntity.getId(), videoEntity.getIdUsuario(), videoEntity.getCodigoEdicao(), 
                        videoEntity.getNome(), videoEntity.getTentativasDeEdicao(), videoEntity.getStatusEdicao())
        );

        // Configurando o comportamento do mock
        when(videoRepository.findVideosByIdUsuario("usuario1")).thenReturn(videoList);

        // Chamando o método a ser testado
        List<Video> videos = videoDatabaseAdapter.findVideosByIdUsuario("usuario1");

        // Verificando se o método do repositório foi chamado corretamente
        verify(videoRepository).findVideosByIdUsuario("usuario1");

        // Verificando o retorno
        assertNotNull(videos);
        assertEquals(1, videos.size());
        assertEquals(videoId, videos.get(0).getId());
        assertEquals("usuario1", videos.get(0).getIdUsuario());
    }


    @Test
    void testFindAll() {
        // Criando um VideoEntity
        VideoEntity videoEntity = new VideoEntity(videoId, "usuario1", UUID.fromString("674ef3ac-8826-4d3f-8704-93d14f4ac93a"), "Nome do Vídeo", "3", StatusEdicao.CRIADA);

        // Convertendo VideoEntity para Video, pois o método espera uma lista de Video
        Video video = new Video(videoEntity.getId(), videoEntity.getIdUsuario(), videoEntity.getCodigoEdicao(),
                                videoEntity.getNome(), videoEntity.getTentativasDeEdicao(), videoEntity.getStatusEdicao());

        // Configurando o comportamento do mock para retornar uma lista de VideoEntity
        when(videoRepository.findAll()).thenReturn(Arrays.asList(videoEntity));

        // Chamando o método a ser testado
        List<VideoEntity> videos = videoDatabaseAdapter.findAll();

        // Verificando se o método do repositório foi chamado corretamente
        verify(videoRepository).findAll();

        // Verificando o retorno
        assertNotNull(videos);
        assertEquals(1, videos.size());
        assertEquals(videoId, videos.get(0).getId());
        assertEquals("usuario1", videos.get(0).getIdUsuario());
        assertEquals(UUID.fromString("674ef3ac-8826-4d3f-8704-93d14f4ac93a"), videos.get(0).getCodigoEdicao());
        assertEquals("Nome do Vídeo", videos.get(0).getNome());
        assertEquals("3", videos.get(0).getTentativasDeEdicao());
        assertEquals(StatusEdicao.CRIADA, videos.get(0).getStatusEdicao());
    }


    @Test
    void testFindVideoById() {
        // Criando um VideoEntity
        VideoEntity videoEntity = new VideoEntity(videoId, "usuario1", UUID.fromString("674ef3ac-8826-4d3f-8704-93d14f4ac93a"), "Nome do Vídeo", "3", StatusEdicao.CRIADA);
        
        // Convertendo VideoEntity para Video, pois o método espera um Video
        Video video = new Video(videoEntity.getId(), videoEntity.getIdUsuario(), videoEntity.getCodigoEdicao(), 
                                videoEntity.getNome(), videoEntity.getTentativasDeEdicao(), videoEntity.getStatusEdicao());

        // Configurando o comportamento do mock para retornar um VideoEntity (que será convertido em Video)
        when(videoRepository.findVideoById(videoId)).thenReturn(video);

        // Chamando o método a ser testado
        Video foundVideo = videoDatabaseAdapter.findVideoById(videoId);

        // Verificando se o método do repositório foi chamado corretamente
        verify(videoRepository).findVideoById(videoId);

        // Verificando o retorno
        assertNotNull(foundVideo);
        assertEquals(videoId, foundVideo.getId());
        assertEquals("usuario1", foundVideo.getIdUsuario());
        assertEquals(UUID.fromString("674ef3ac-8826-4d3f-8704-93d14f4ac93a"), foundVideo.getCodigoEdicao());
        assertEquals("Nome do Vídeo", foundVideo.getNome());
        assertEquals("3", foundVideo.getTentativasDeEdicao());
        assertEquals(StatusEdicao.CRIADA, foundVideo.getStatusEdicao());
    }





    @Test
    void testFindVideoByIdNotFound() {
        // Configurando o comportamento do mock para quando não encontrar o vídeo
        when(videoRepository.findVideoById(videoId)).thenReturn(null);

        // Chamando o método a ser testado
        Video foundVideo = videoDatabaseAdapter.findVideoById(videoId);

        // Verificando se o retorno é null
        assertNull(foundVideo);
    }
}

