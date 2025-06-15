package com.embalagem.demo.service;

import com.embalagem.demo.controller.CaminhaoDTO;
import com.embalagem.demo.controller.EmbalagemQuantidadeDTO;
import com.embalagem.demo.controller.CacambaDTO;
import com.embalagem.demo.entity.Caminhao;
import com.embalagem.demo.entity.Embalagem;
import com.embalagem.demo.repository.CaminhaoRepository;
import com.embalagem.demo.repository.EmbalagemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class CaminhaoService {

    private final CaminhaoRepository repository;
    private final EmbalagemRepository embalagemRepository;

    private static final Logger logger = LoggerFactory.getLogger(EmbalagemService.class);

    @Autowired
    public CaminhaoService(CaminhaoRepository repository, EmbalagemRepository embalagemRepository) {
        this.repository = repository;
        this.embalagemRepository = embalagemRepository;
    }

    public Caminhao criarCaminhao(CaminhaoDTO dto) {

        if (repository.existsById(dto.placa())) {
            logger.error("Caminhao j<UNK> existente");
            throw new RuntimeException("Já existe um caminhão com a placa: " + dto.placa());
        }

        if (!"3/4".equalsIgnoreCase(dto.tipo()) && !"truck".equalsIgnoreCase(dto.tipo()) && !"carreta".equalsIgnoreCase(dto.tipo())) {
            throw new RuntimeException("Caminhao invalido");
        }

        Caminhao caminhao = new Caminhao();
        caminhao.setPlaca(dto.placa());
        caminhao.setTipo(dto.tipo());
        double volume = calcularVolumePorTipo(dto.tipo());

        caminhao.setVolumeMaximo(volume);

        caminhao.setVolumeOcupado(0.0);

        return repository.save(caminhao);
    }

    private double calcularVolumePorTipo(String tipo) {
        return switch (tipo.toLowerCase()) {
            case "3/4" -> 2.1 * 2.2 * 5.1;
            case "truck" -> 2.9 * 2.4 * 7.8;
            case "carreta" -> 2.9 * 2.48 * 13.6;
            default -> throw new IllegalArgumentException("Tipo de caminhão inválido: " + tipo);
        };
    }

    public List<Caminhao> listarCaminhao(){

        return repository.findAll();
    }

    public Caminhao listarPorId(String codigoCaminhao){
        return repository.findById(codigoCaminhao)
                .orElseThrow(() -> new RuntimeException("Fornecedor com código " + codigoCaminhao + " não encontrado"));
    }

    @Transactional
    public Caminhao atualizarEmbalagens(String codigoCaminhao, EmbalagemQuantidadeDTO dto) {

        Caminhao caminhao = repository.findById(codigoCaminhao)
                .orElseThrow(() -> new RuntimeException("Caminhão com placa " + codigoCaminhao + " não encontrado"));

        for (CacambaDTO item : dto.embalagens()) {
            String codigoEmbalagem = item.codigo();
            int quantidade = item.quantidade();

            Embalagem embalagem = embalagemRepository.findById(codigoEmbalagem)
                    .orElseThrow(() -> new RuntimeException("Embalagem com código " + codigoEmbalagem + " não encontrada"));

            double volumeAdicional = embalagem.getVolume() * quantidade;

            if (caminhao.getVolumeOcupado() + volumeAdicional > caminhao.getVolumeMaximo()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Não é possível adicionar a embalagem " + codigoEmbalagem +
                                " (quantidade: " + quantidade + ") por exceder o volume do caminhão.");
            }

            caminhao.adicionarEmbalagem(embalagem, quantidade);
        }

        return repository.save(caminhao);
    }

    @Transactional
    public Caminhao removerEmbalagens(String codigoCaminhao, EmbalagemQuantidadeDTO dto) {
        Caminhao caminhao = repository.findById(codigoCaminhao)
                .orElseThrow(() -> new RuntimeException("Caminhão com placa " + codigoCaminhao + " não encontrado"));

        for (CacambaDTO item : dto.embalagens()) {
            String codigoEmbalagem = item.codigo();
            int quantidade = item.quantidade();

            Embalagem embalagem = embalagemRepository.findById(codigoEmbalagem)
                    .orElseThrow(() -> new RuntimeException("Embalagem com código " + codigoEmbalagem + " não encontrada"));

            caminhao.removerEmbalagem(embalagem, quantidade);
        }

        return repository.save(caminhao);
    }

}
