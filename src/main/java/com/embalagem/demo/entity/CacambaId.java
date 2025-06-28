package com.embalagem.demo.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CacambaId implements Serializable {

    private String caminhaoPlaca;
    private String embalagemCodigo;

    public CacambaId() {}

    public CacambaId(String caminhaoPlaca, String embalagemCodigo) {
        this.caminhaoPlaca = caminhaoPlaca;
        this.embalagemCodigo = embalagemCodigo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CacambaId)) return false;
        CacambaId that = (CacambaId) o;
        return Objects.equals(caminhaoPlaca, that.caminhaoPlaca) &&
                Objects.equals(embalagemCodigo, that.embalagemCodigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(caminhaoPlaca, embalagemCodigo);
    }

    public String getCaminhaoPlaca() {
        return caminhaoPlaca;
    }

    public void setCaminhaoPlaca(String caminhaoPlaca) {
        this.caminhaoPlaca = caminhaoPlaca;
    }

    public String getEmbalagemCodigo() {
        return embalagemCodigo;
    }

    public void setEmbalagemCodigo(String embalagemCodigo) {
        this.embalagemCodigo = embalagemCodigo;
    }
}

