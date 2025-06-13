package com.embalagem.demo.controller;

import java.util.Set;

public record EmbalagemDTO(
        String codigo,
        String descricao,
        Set<String> fornecedores,
        String tipo,
        String criticalidade,
        double altura,
        double largura,
        double comprimento
) {}



