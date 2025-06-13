package com.embalagem.demo.repository;

import com.embalagem.demo.entity.Caminhao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaminhaoRepository extends JpaRepository<Caminhao, String> {
}
