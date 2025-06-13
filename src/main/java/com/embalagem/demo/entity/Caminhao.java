package com.embalagem.demo.entity;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "tb_caminhao")

public class Caminhao {

    @Id
    private String placa;

    @Column(nullable = false)
    private String tipo; // "3/4", "truck", "carreta"

    @Column(nullable = false)
    private double volumeMaximo;

    @OneToMany(mappedBy = "caminhao", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Cacamba> caminhaoEmbalagens = new HashSet<>();      // | PLACA  | EMBALAGEM | QUANTIDADE |
                                                                    // | 1089   |  4892     |     3      | for : volumeOcupado += embalagem.getVolume * quantidade

    @ManyToMany
    @JoinTable(
            name = "caminhao_fornecedores",
            joinColumns = @JoinColumn(name = "caminhao_placa"),
            inverseJoinColumns = @JoinColumn(name = "fornecedor_codigo")
    )
    private Set<Fornecedor> fornecedores = new HashSet<>();

    @Column(nullable = false)
    private double volumeOcupado;

    private double calcularVolumePorTipo(String tipo) {
        return switch (tipo.toLowerCase()) {
            case "3/4" -> 2.1 * 2.2 * 5.1;
            case "truck" -> 2.9 * 2.4 * 7.8;
            case "carreta" -> 2.9 * 2.48 * 13.6;
            default -> throw new IllegalArgumentException("Tipo de caminhão inválido: " + tipo);
        };
    }

    public void adicionarEmbalagem(Embalagem embalagem, int quantidade) {
        Optional<Cacamba> existente = caminhaoEmbalagens.stream()
                .filter(ce -> ce.getEmbalagem().equals(embalagem))
                .findFirst();

        if (existente.isPresent()) {
            Cacamba ce = existente.get();
            ce.setQuantidade(ce.getQuantidade() + quantidade);
        } else {
            Cacamba novo = new Cacamba(this, embalagem, quantidade);
            caminhaoEmbalagens.add(novo);
        }

        volumeOcupado += embalagem.getVolume() * quantidade;
        atualizarFornecedores();
    }

    public void removerEmbalagem(Embalagem embalagem, int quantidade) {
        Optional<Cacamba> existente = caminhaoEmbalagens.stream()
                .filter(ce -> ce.getEmbalagem().equals(embalagem))
                .findFirst();

        if (existente.isPresent()) {
            Cacamba ce = existente.get();
            int atual = ce.getQuantidade();

            if (quantidade >= atual) {
                caminhaoEmbalagens.remove(ce);
            } else {
                ce.setQuantidade(atual - quantidade);
            }

            volumeOcupado -= embalagem.getVolume() * Math.min(quantidade, atual);
            atualizarFornecedores();
        }
    }


    private void atualizarFornecedores() {
        this.fornecedores.clear();
        for (Cacamba ce : caminhaoEmbalagens) {
            this.fornecedores.addAll(ce.getEmbalagem().getFornecedores());
        }
    }


    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
        this.volumeMaximo = calcularVolumePorTipo(tipo);
    }

    public double getVolumeMaximo() {
        return volumeMaximo;
    }

    public void setVolumeMaximo(double volumeMaximo) {
        this.volumeMaximo = volumeMaximo;
    }

    public Map<String, Integer> getCaminhaoEmbalagens() {
        Map<String, Integer> resumo = new HashMap<>();
        for (Cacamba ce : caminhaoEmbalagens) {
            resumo.put(ce.getEmbalagem().getCodigo(), ce.getQuantidade());
        }
        return resumo;
    }


    public void setCaminhaoEmbalagens(Set<Cacamba> caminhaoEmbalagens) {
        this.caminhaoEmbalagens.clear();
        this.caminhaoEmbalagens.addAll(caminhaoEmbalagens);

        this.fornecedores.clear();
        this.volumeOcupado = 0.0;

        for (Cacamba ce : caminhaoEmbalagens) {
            Embalagem embalagem = ce.getEmbalagem();
            int quantidade = ce.getQuantidade();

            this.fornecedores.addAll(embalagem.getFornecedores());
            this.volumeOcupado += embalagem.getVolume() * quantidade;
        }
    }


    public Set<Fornecedor> getFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(Set<Fornecedor> fornecedores) {
        this.fornecedores = fornecedores;
    }

    public double getVolumeOcupado() {
        return volumeOcupado;
    }

    public void setVolumeOcupado(double volumeOcupado) {
        this.volumeOcupado = volumeOcupado;
    }
    public Caminhao() {}

    public Caminhao(String placa, String tipo) {
        this.placa = placa;
        this.tipo = tipo;
        this.volumeMaximo = calcularVolumePorTipo(tipo);
        this.volumeOcupado = 0.0;
    }

}
