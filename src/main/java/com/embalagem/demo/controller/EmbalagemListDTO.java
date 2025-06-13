package com.embalagem.demo.controller;


public record EmbalagemListDTO(
        String codigo,
        String descricao,
        String tipo,
        String criticalidade,
        double volume
) {}

