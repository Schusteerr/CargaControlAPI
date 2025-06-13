package com.embalagem.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_fornecedores")
public class Fornecedor {

    @Id
    private String codigo;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "frete", nullable = false)
    private String frete; // lembrar que é CIF ou FOB

    public Fornecedor() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFrete() {
        return frete;
    }

    public void setFrete(String frete) {
        this.frete = frete;
    }

    public Fornecedor(String codigo, String nome, String frete) {
        this.codigo = codigo;
        this.nome = nome;
        this.frete = frete;
    }
}

