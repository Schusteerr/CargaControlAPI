package com.embalagem.demo.controller;

import com.embalagem.demo.entity.Fornecedor;
import com.embalagem.demo.service.FornecedorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    private FornecedorService service;

    public FornecedorController(FornecedorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> criarFornecedor(@RequestBody FornecedorDTO dto) {
        try {
            var codigoFornecedor = service.criarFornecedor(dto);
            return ResponseEntity.created(URI.create("/fornecedores/" + codigoFornecedor)).body("Fornecedor " +dto.codigo()+ " criado com sucesso");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro ao criar o fornecedor: "+e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Fornecedor>> listarFornecedores(){
        try {
            var fornecedores = service.listarFornecedor();
            return ResponseEntity.ok(fornecedores);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<Fornecedor>());
        }
    }

    @GetMapping("/{codigoFornecedor}")
    public ResponseEntity<Fornecedor> listarPorCodigo(@PathVariable("codigoFornecedor") String codigoFornecedor) {
        try {
            var fornecedor = service.listarPorCodigo(codigoFornecedor);
            return ResponseEntity.ok(fornecedor);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

     @PutMapping("/{codigoFornecedor}")
    public ResponseEntity<String> atualizarFornecedor(@PathVariable("codigoFornecedor") String codigoFornecedor,
                                                          @RequestBody FornecedorUpdateDTO dto) {
        try {
            var fornecedorAtualizado = service.atualizarFornecedor(codigoFornecedor, dto);
            return ResponseEntity.ok("Fornecedor " +codigoFornecedor +" atualizado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro ao atualizar o fornecedor: "+e.getMessage());
        }
    }

    @DeleteMapping("/{codigoFornecedor}")
    public ResponseEntity<String> deletarFornecedor(@PathVariable("codigoFornecedor") String codigoFornecedor) {
        try {
            service.deletarFornecedor(codigoFornecedor);
            return ResponseEntity.noContent().build();
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro ao deletar o fornecedor: "+e.getMessage());
        }
    }
}
