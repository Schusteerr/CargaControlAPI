package com.embalagem.demo.controller;

import com.embalagem.demo.entity.Caminhao;
import com.embalagem.demo.service.CaminhaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/caminhoes")
public class CaminhaoController {

    private final CaminhaoService service;

    public CaminhaoController(CaminhaoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Caminhao> criarCaminhao(@RequestBody CaminhaoDTO dto) {
        Caminhao caminhao = service.criarCaminhao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(caminhao);
    }

    @GetMapping
    public ResponseEntity<List<Caminhao>> listarCaminhao() {
        var caminhaoes = service.listarCaminhao();
        return ResponseEntity.ok(caminhaoes);
    }

    @GetMapping({"/{codigoCaminhao}" })
    public ResponseEntity<Caminhao> listarPorId(@PathVariable("codigoCaminhao") String codigoCaminhao) {
        var caminhaoes = service.listarPorId(codigoCaminhao);
        return ResponseEntity.ok(caminhaoes);
    }

    @PutMapping("/{codigoCaminhao}/embalagens/adicionar")
    public ResponseEntity<Caminhao> adicionarEmbalagensAoCaminhao(
            @PathVariable String codigoCaminhao,
            @RequestBody EmbalagemQuantidadeDTO dto) {
        Caminhao atualizado = service.atualizarEmbalagens(codigoCaminhao, dto);
        return ResponseEntity.ok(atualizado);
    }
    @PutMapping("/{codigoCaminhao}/embalagens/remover")
    public ResponseEntity<Caminhao> removerEmbalagensDoCaminhao(
            @PathVariable String codigoCaminhao,
            @RequestBody EmbalagemQuantidadeDTO dto) {
        Caminhao atualizado = service.removerEmbalagens(codigoCaminhao, dto);
        return ResponseEntity.ok(atualizado);
    }

}

