package com.embalagem.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cacamba")
public class Cacamba {

    @EmbeddedId
    private CacambaId id = new CacambaId();

    @ManyToOne
    @MapsId("caminhaoPlaca")
    private Caminhao caminhao;

    @ManyToOne
    @MapsId("embalagemCodigo")
    private Embalagem embalagem;

    @Column(nullable = false)
    private int quantidade;  // | PLACA  | EMBALAGEM | QUANTIDADE |
                            //  | 1089   |  4892     |     3      | for : volumeOcupado += embalagem.getVolume * quantidade

    public Cacamba() {}

    public Cacamba(Caminhao caminhao, Embalagem embalagem, int quantidade) {
        this.caminhao = caminhao;
        this.embalagem = embalagem;
        this.quantidade = quantidade;
        this.id = new CacambaId(caminhao.getPlaca(), embalagem.getCodigo());
    }

    public CacambaId getId() {
        return id;
    }

    public void setId(CacambaId id) {
        this.id = id;
    }

    public Caminhao getCaminhao() {
        return caminhao;
    }

    public void setCaminhao(Caminhao caminhao) {
        this.caminhao = caminhao;
    }

    public Embalagem getEmbalagem() {
        return embalagem;
    }

    public void setEmbalagem(Embalagem embalagem) {
        this.embalagem = embalagem;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}

