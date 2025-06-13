package com.embalagem.demo.repository;

import com.embalagem.demo.entity.Embalagem;
import com.embalagem.demo.entity.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmbalagemRepository extends JpaRepository<Embalagem, String> {
    List<Embalagem> findByFornecedoresContaining(Fornecedor fornecedor);
}
