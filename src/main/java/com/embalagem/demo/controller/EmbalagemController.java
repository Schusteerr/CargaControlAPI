package com.embalagem.demo.controller;

import com.embalagem.demo.entity.Embalagem;
import com.embalagem.demo.service.EmbalagemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/embalagens")
public class EmbalagemController{


    private EmbalagemService service;

    public EmbalagemController(EmbalagemService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> criarEmbalagem(@RequestBody EmbalagemDTO embalagemDTO) {
        try {
            var embalagemCodigo = service.createEmbalagem(embalagemDTO);
            return ResponseEntity.created(URI.create("/embalagens/" + embalagemCodigo.toString())).body("Embalagem " + embalagemDTO.codigo() + " criada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro ao criar embalagem: " + e.getMessage());
        }
    }


    @GetMapping
    public ResponseEntity<List<EmbalagemListDTO>> listarEmbalagens() {
        try{
            var embalagens = service.listarTodas();
            return ResponseEntity.ok(embalagens);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<EmbalagemListDTO>());
        }

    }

    @GetMapping("/{codigoEmbalagem}")
    public ResponseEntity<Embalagem> listarPorCodigo(@PathVariable("codigoEmbalagem") String codigoEmbalagem) {
        try {
            var embalagem = service.listarPorCodigo(codigoEmbalagem);
            return ResponseEntity.ok(embalagem);
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }

    @DeleteMapping("/{codigoEmbalagem}")
    public ResponseEntity<String> deletarEmbalagem(@PathVariable("codigoEmbalagem") String codigoEmbalagem) {
        try {
            service.deletarEmbalagem(codigoEmbalagem);
            return ResponseEntity.ok("Embalagem deletada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro ao tentar deletar embalagem: " + e.getMessage());
        }

    }

    @PutMapping("/{codigoEmbalagem}")
    public ResponseEntity<String> atualizarEmbalagem(@PathVariable("codigoEmbalagem") String codigoEmbalagem,
                                                        @RequestBody EmbalagemUpdateDTO dto) {
        try {
            var embalagemAtualizada = service.atualizarEmbalagem(codigoEmbalagem, dto);
            return ResponseEntity.ok("Embalagem atualizada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro ao tentar atualizar embalagem: " + e.getMessage());
        }

    }
}