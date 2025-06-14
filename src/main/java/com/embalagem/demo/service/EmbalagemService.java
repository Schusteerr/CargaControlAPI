package com.embalagem.demo.service;

import com.embalagem.demo.controller.EmbalagemDTO;
import com.embalagem.demo.controller.EmbalagemListDTO;
import com.embalagem.demo.controller.EmbalagemUpdateDTO;
import com.embalagem.demo.entity.Embalagem;
import com.embalagem.demo.entity.Fornecedor;
import com.embalagem.demo.repository.EmbalagemRepository;
import com.embalagem.demo.repository.FornecedorRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmbalagemService {

    private static final Logger logger = LoggerFactory.getLogger(EmbalagemService.class);

    private final EmbalagemRepository repository;
    private final FornecedorRepository fornecedorRepository;  // <---- injete o repo

    public EmbalagemService(EmbalagemRepository repository, FornecedorRepository fornecedorRepository) {
        this.repository = repository;
        this.fornecedorRepository = fornecedorRepository;
    }

    public String createEmbalagem(EmbalagemDTO dto) {

        if (repository.existsById(dto.codigo())){
            throw new RuntimeException("Código da embalagem " + dto.codigo() + " já cadastrado");
        }

        if (!"fluxo".equalsIgnoreCase(dto.tipo()) && !"standard".equalsIgnoreCase(dto.tipo())) {
            logger.error("Tipo inválido. Apenas 'fluxo' ou 'standard' são permitidos.");
            throw new RuntimeException("Tipo inválido. Apenas 'fluxo' ou 'standard' são permitidos.");
        }

        // Validação da criticalidade da embalagem
        if (!"baixa".equalsIgnoreCase(dto.criticalidade()) &&
                !"media".equalsIgnoreCase(dto.criticalidade()) &&
                !"alta".equalsIgnoreCase(dto.criticalidade())) {
            logger.error("Criticalidade inválida. Apenas 'baixa', 'media' ou 'alta' são permitidas.");
            throw new RuntimeException("Criticalidade inválida. Apenas 'baixa', 'media' ou 'alta' são permitidas.");
        }


        Set<Fornecedor> fornecedores = dto.fornecedores().stream()
                .map(codigo -> fornecedorRepository.findById(codigo)
                        .orElseThrow(() -> {
                            logger.error("Fornecedor não encontrado: {}", codigo); // logger
                            return new RuntimeException("Fornecedor não encontrado: " + codigo);
                        })
                )
                .collect(Collectors.toSet());



        var entity = new Embalagem(
                dto.codigo(),
                dto.descricao(),
                fornecedores,
                dto.tipo(),
                dto.criticalidade(),
                dto.altura(),
                dto.largura(),
                dto.comprimento()
        );

        var embalagemSalva = repository.save(entity);
        logger.info("Embalagem criada com sucesso: {}", embalagemSalva.getCodigo()); // logger
        return embalagemSalva.getCodigo();
    }

    public List<EmbalagemListDTO> listarTodas() {
        List<Embalagem> embalagens = repository.findAll();
        logger.info("Listando todas as embalagens. Total: {}", embalagens.size()); // logger

        return embalagens.stream()
                .map(e -> new EmbalagemListDTO(
                        e.getCodigo(),
                        e.getDescricao(),
                        e.getTipo(),
                        e.getCriticalidade(),
                        e.getVolume()
                ))
                .toList();
    }

    public Embalagem listarPorCodigo(String codigoEmbalagem) {
        return repository.findById(codigoEmbalagem)
                .orElseThrow(() -> {
                    logger.error("Embalagem com código {} não encontrada", codigoEmbalagem);
                    return new RuntimeException("Embalagem com código " + codigoEmbalagem + " não encontrada");
                });
    }

    public void deletarEmbalagem(String codigoEmbalagem) {
        if (!repository.existsById(codigoEmbalagem)) {
            logger.error("Tentativa de deletar embalagem inexistente: {}", codigoEmbalagem); // 🔧
            throw new RuntimeException("Embalagem com código " + codigoEmbalagem + " não encontrada.");
        }
        repository.deleteById(codigoEmbalagem);
    }

    public Embalagem atualizarEmbalagem(String codigoEmbalagem, EmbalagemUpdateDTO dto) {

        Embalagem embalagemExistente = repository.findById(codigoEmbalagem)
                .orElseThrow(() -> {
                    logger.error("Embalagem com código {} não encontrada para atualização", codigoEmbalagem); // 🔧
                    return new RuntimeException("Embalagem com código " + codigoEmbalagem + " não encontrada.");
                });
        embalagemExistente.setDescricao(dto.descricao());

        Set<Fornecedor> fornecedores = dto.fornecedores().stream()
                .map(id -> fornecedorRepository.findById(id)
                        .orElseThrow(() -> {
                            logger.error("Fornecedor com id {} não encontrado durante atualização", id); // 🔧
                            return new RuntimeException("Fornecedor com id " + id + " não encontrado");
                        }))
                .collect(Collectors.toSet());

        embalagemExistente.setFornecedores(fornecedores);
        embalagemExistente.setTipo(dto.tipo());
        embalagemExistente.setCriticalidade(dto.criticalidade());
        embalagemExistente.setAltura(dto.altura());
        embalagemExistente.setLargura(dto.largura());
        embalagemExistente.setComprimento(dto.comprimento());

        return repository.save(embalagemExistente);
    }

}