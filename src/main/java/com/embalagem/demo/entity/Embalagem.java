package com.embalagem.demo.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_embalagem")
public class Embalagem {

    @Id
    private String codigo;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @ManyToMany
    @JoinTable(
            name = "embalagem_fornecedor",
            joinColumns = @JoinColumn(name = "embalagem_codigo"),
            inverseJoinColumns = @JoinColumn(name = "fornecedor_codigo")
    )
    private Set<Fornecedor> fornecedores = new HashSet<>();

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "criticalidade", nullable = false)
    private String criticalidade;

    @Column(name = "altura", nullable = false)
    private double altura;

    @Column(name = "largura", nullable = false)
    private double largura;

    @Column(name = "comprimento", nullable = false)
    private double comprimento;

    @Column(name = "volume", nullable = false)
    private double volume;

    public Embalagem() {
    }

    @PrePersist
    @PreUpdate
    private void calcularVolume() {
        this.volume = this.altura * this.largura * this.comprimento;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<Fornecedor> getFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(Set<Fornecedor> fornecedores) {
        this.fornecedores = fornecedores;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCriticalidade() {
        return criticalidade;
    }

    public void setCriticalidade(String criticalidade) {
        this.criticalidade = criticalidade;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getLargura() {return largura;}

    public void setLargura(double largura) {
        this.largura = largura;
    }

    public double getComprimento() {
        return comprimento;
    }

    public void setComprimento(double comprimento) {
        this.comprimento = comprimento;
    }

    public double getVolume() {
        return volume;
    }


    public Embalagem(String codigo, String descricao, Set<Fornecedor> fornecedores,
                     String tipo, String criticalidade, double altura,
                     double largura, double comprimento) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.fornecedores = fornecedores;
        this.tipo = tipo;
        this.criticalidade = criticalidade;
        this.altura = altura;
        this.largura = largura;
        this.comprimento = comprimento;
        this.calcularVolume();
    }
}
