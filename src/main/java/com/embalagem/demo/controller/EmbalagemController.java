package com.embalagem.demo.controller;

import com.embalagem.demo.entity.Embalagem;
import com.embalagem.demo.service.EmbalagemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/embalagens")
public class EmbalagemController{


    private EmbalagemService service;

    public EmbalagemController(EmbalagemService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Embalagem> criarEmbalagem(@RequestBody EmbalagemDTO embalagemDTO) {
        try {
            var embalagemCodigo = service.createEmbalagem(embalagemDTO);
            return ResponseEntity.created(URI.create("/embalagens/" + embalagemCodigo.toString())).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping
    public ResponseEntity<List<EmbalagemListDTO>> listarEmbalagens() {
        var embalagens = service.listarTodas();
        return ResponseEntity.ok(embalagens);
    }

    @GetMapping("/{codigoEmbalagem}")
    public ResponseEntity<Embalagem> listarPorCodigo(@PathVariable("codigoEmbalagem") String codigoEmbalagem) {
        var embalagem = service.listarPorCodigo(codigoEmbalagem);
        return ResponseEntity.ok(embalagem);
    }

    @DeleteMapping("/{codigoEmbalagem}")
    public ResponseEntity<Void> deletarEmbalagem(@PathVariable("codigoEmbalagem") String codigoEmbalagem) {
        service.deletarEmbalagem(codigoEmbalagem);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{codigoEmbalagem}")
    public ResponseEntity<Embalagem> atualizarEmbalagem(@PathVariable("codigoEmbalagem") String codigoEmbalagem,
                                                        @RequestBody EmbalagemUpdateDTO dto) {
        var embalagemAtualizada = service.atualizarEmbalagem(codigoEmbalagem, dto);
        return ResponseEntity.ok(embalagemAtualizada);
    }
}