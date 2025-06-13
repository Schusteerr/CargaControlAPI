package com.embalagem.demo.controller;

import com.embalagem.demo.entity.Fornecedor;
import com.embalagem.demo.service.FornecedorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    private FornecedorService service;

    public FornecedorController(FornecedorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Fornecedor> criarFornecedor(@RequestBody FornecedorDTO dto) {
        var codigoFornecedor = service.criarFornecedor(dto);
        return ResponseEntity.created(URI.create("/fornecedores/"+codigoFornecedor)).build();
    }

    @GetMapping
    public ResponseEntity<List<Fornecedor>> listarFornecedores(){
        var fornecedores = service.listarFornecedor();
        return ResponseEntity.ok(fornecedores);
    }

    @GetMapping("/{codigoFornecedor}")
    public ResponseEntity<Fornecedor> listarPorCodigo(@PathVariable("codigoFornecedor") String codigoFornecedor) {
        var fornecedor = service.listarPorCodigo(codigoFornecedor);
        return ResponseEntity.ok(fornecedor);
    }

     @PutMapping("/{codigoFornecedor}")
    public ResponseEntity<Fornecedor> atualizarFornecedor(@PathVariable("codigoFornecedor") String codigoFornecedor,
                                                          @RequestBody FornecedorUpdateDTO dto) {

        var fornecedorAtualizado = service.atualizarFornecedor(codigoFornecedor, dto);
        return ResponseEntity.ok(fornecedorAtualizado);
    }

    @DeleteMapping("/{codigoFornecedor}")
    public ResponseEntity<Void> deletarFornecedor(@PathVariable("codigoFornecedor") String codigoFornecedor) {
        service.deletarFornecedor(codigoFornecedor);
        return ResponseEntity.noContent().build();
    }

}
