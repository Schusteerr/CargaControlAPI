package com.embalagem.demo.controller;

import com.embalagem.demo.entity.Caminhao;
import com.embalagem.demo.service.CaminhaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/caminhoes")
public class CaminhaoController {

    private final CaminhaoService service;

    public CaminhaoController(CaminhaoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> criarCaminhao(@RequestBody CaminhaoDTO dto) {
        try {
            Caminhao caminhao = service.criarCaminhao(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Caminhao "+ dto.placa() + " cadastrado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro ao criar caminhao" + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Caminhao>> listarCaminhao() {
        try {
        var caminhaoes = service.listarCaminhao();
        return ResponseEntity.ok(caminhaoes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<Caminhao>());
        }
    }

    @GetMapping({"/{codigoCaminhao}" })
    public ResponseEntity<Caminhao> listarPorId(@PathVariable("codigoCaminhao") String codigoCaminhao) {
        try {
            var caminhaoes = service.listarPorId(codigoCaminhao);
            return ResponseEntity.ok(caminhaoes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{codigoCaminhao}/embalagens/adicionar")
    public ResponseEntity<String> adicionarEmbalagensAoCaminhao(
            @PathVariable String codigoCaminhao,
            @RequestBody EmbalagemQuantidadeDTO dto) {
        try {
            Caminhao atualizado = service.atualizarEmbalagens(codigoCaminhao, dto);
            return ResponseEntity.ok("Carga adicionada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro ao adicionar carga: " + e.getMessage());
        }
    }
    @PutMapping("/{codigoCaminhao}/embalagens/remover")
    public ResponseEntity<String> removerEmbalagensDoCaminhao(
            @PathVariable String codigoCaminhao,
            @RequestBody EmbalagemQuantidadeDTO dto) {
        try {
            Caminhao atualizado = service.removerEmbalagens(codigoCaminhao, dto);
            return ResponseEntity.ok("Carga removida com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro ao remover carga: " + e.getMessage());
        }
    }

}

