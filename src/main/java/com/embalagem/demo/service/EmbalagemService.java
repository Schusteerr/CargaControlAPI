package com.embalagem.demo.service;

import com.embalagem.demo.controller.EmbalagemDTO;
import com.embalagem.demo.controller.EmbalagemListDTO;
import com.embalagem.demo.controller.EmbalagemUpdateDTO;
import com.embalagem.demo.entity.Embalagem;
import com.embalagem.demo.entity.Fornecedor;
import com.embalagem.demo.repository.EmbalagemRepository;
import com.embalagem.demo.repository.FornecedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmbalagemService {

    private final EmbalagemRepository repository;
    private final FornecedorRepository fornecedorRepository;  // <---- injete o repo

    public EmbalagemService(EmbalagemRepository repository, FornecedorRepository fornecedorRepository) {
        this.repository = repository;
        this.fornecedorRepository = fornecedorRepository;
    }

    public String createEmbalagem(EmbalagemDTO dto) {
        Set<Fornecedor> fornecedores = dto.fornecedores().stream()
                .map(codigo -> fornecedorRepository.findById(codigo)
                        .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado: " + codigo))
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
        return embalagemSalva.getCodigo();
    }

    public List<EmbalagemListDTO> listarTodas() {
        List<Embalagem> embalagens = repository.findAll();

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
                .orElseThrow(() -> new RuntimeException("Embalagem com código " + codigoEmbalagem + " não encontrada"));
    }

    public void deletarEmbalagem(String codigoEmbalagem) {
        if (!repository.existsById(codigoEmbalagem)) {
            throw new RuntimeException("Embalagem com código " + codigoEmbalagem + " não encontrada.");
        }
        repository.deleteById(codigoEmbalagem);
    }

    public Embalagem atualizarEmbalagem(String codigoEmbalagem, EmbalagemUpdateDTO dto) {

        Embalagem embalagemExistente = repository.findById(codigoEmbalagem)
                .orElseThrow(() -> new RuntimeException("Embalagem com código " + codigoEmbalagem + " não encontrada."));

        embalagemExistente.setDescricao(dto.descricao());

        Set<Fornecedor> fornecedores = dto.fornecedores().stream()
                .map(id -> fornecedorRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Fornecedor com id " + id + " não encontrado")))
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