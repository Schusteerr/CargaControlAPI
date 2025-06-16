package com.embalagem.demo.service;

import com.embalagem.demo.controller.FornecedorDTO;
import com.embalagem.demo.controller.FornecedorUpdateDTO;
import com.embalagem.demo.entity.Embalagem;
import com.embalagem.demo.entity.Fornecedor;
import com.embalagem.demo.repository.EmbalagemRepository;
import com.embalagem.demo.repository.FornecedorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@Service
public class FornecedorService {

    private final FornecedorRepository repository;
    private final EmbalagemRepository embalagemRepository;

    @Autowired
    public FornecedorService(FornecedorRepository fornecedorRepository,
                             EmbalagemRepository embalagemRepository) {
        this.repository = fornecedorRepository;
        this.embalagemRepository = embalagemRepository;
         }
    private static final Logger logger = LoggerFactory.getLogger(FornecedorService.class);


    public String criarFornecedor (FornecedorDTO dto){

        if (repository.existsById(dto.codigo())){
            logger.error("o codigo informado ja existe no banco de dados!");
            throw new RuntimeException("Fornecedor ja existe!");
        }
        if(!"cif".equalsIgnoreCase(dto.frete())&& !"fob".equalsIgnoreCase(dto.frete())&& !"cargolift".equalsIgnoreCase(dto.frete())) {
            throw new RuntimeException("tipo de frete invalido");
        }
        var entity = new Fornecedor(dto.codigo(), dto.nome(), dto.frete());

        var userSaved = repository.save(entity);
        return userSaved.getCodigo();
    }

    public List<Fornecedor> listarFornecedor(){
        return repository.findAll();
    }

    public Fornecedor listarPorCodigo(String codigoFornecedor) {
        return repository.findById(codigoFornecedor)
                .orElseThrow(() -> {
                    new RuntimeException("Fornecedor com código " + codigoFornecedor + " não encontrado");
                    logger.error("Fornecedor com código não encontrado");
                    return null;
                });
    }

    public void deletarFornecedor(String codigoFornecedor) {

        if(!repository.existsById(codigoFornecedor)){
            throw new RuntimeException("Fornecedor com código " + codigoFornecedor + " não encontrado");
        }
        Fornecedor fornecedor = repository.findById(codigoFornecedor)
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado"));

        List<Embalagem> embalagens = embalagemRepository.findByFornecedoresContaining(fornecedor);

        List<String> bloqueios = new ArrayList<>();

        for (Embalagem embalagem : embalagens) {
            if (embalagem.getFornecedores().size() <= 1) {
                bloqueios.add(embalagem.getCodigo());
            }
        }

        if (!bloqueios.isEmpty()) {
            logger.warn("Tentativa de exclusão de fornecedor bloqueada. Vinculado exclusivamente às embalagens: {}", bloqueios);
            throw new RuntimeException("Fornecedor não pode ser removido pois está vinculado exclusivamente às embalagens: " + bloqueios);
        }

        for (Embalagem embalagem : embalagens) {
            embalagem.getFornecedores().remove(fornecedor);
            embalagemRepository.save(embalagem);
        }
        repository.delete(fornecedor);
    }

    public Fornecedor atualizarFornecedor(String codigoFornecedor, FornecedorUpdateDTO dto) {

        Fornecedor fornecedorExistente = repository.findById(codigoFornecedor)
                .orElseThrow(() -> new RuntimeException("Fornecedor com código " + codigoFornecedor + " não encontrado."));

        if(dto.nome() != null) {
            fornecedorExistente.setNome(dto.nome());
        }
        if(dto.frete() != null) {
            if(!"cif".equalsIgnoreCase(dto.frete())&& !"fob".equalsIgnoreCase(dto.frete())&& !"cargolift".equalsIgnoreCase(dto.frete())) {
                throw new RuntimeException("tipo de frete invalido");
            }
            fornecedorExistente.setFrete(dto.frete());
        }
        return repository.save(fornecedorExistente);
    }
}