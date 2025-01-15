package com.centralti.tdm.controllers;

import com.centralti.tdm.domain.usuarios.DTO.ClientesDTO;
import com.centralti.tdm.domain.usuarios.DTO.ProdutosDTO;
import com.centralti.tdm.errors.ErrorResponses;
import com.centralti.tdm.services.servicesinterface.ClientesService;
import com.centralti.tdm.services.servicesinterface.ProdutosService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
public class ProdutosController {

    @Autowired
    ProdutosService produtosService;

    @PostMapping()
    public ResponseEntity create(@RequestBody @Valid ProdutosDTO produtosDTO) {
        try {
            produtosService.create(produtosDTO);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            ErrorResponses errorResponses = new ErrorResponses(e.getMessage());
            return ResponseEntity.badRequest().body(errorResponses);
        }
    }

    @GetMapping()
    public ResponseEntity findByProdutos(){
        try {
            var dados = produtosService.listar();
            return ResponseEntity.ok(dados);
        } catch (EntityNotFoundException e) {
            ErrorResponses errorResponses = new ErrorResponses(e.getMessage());
            return ResponseEntity.badRequest().body(errorResponses);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            produtosService.deletar(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            ErrorResponses errorResponses = new ErrorResponses(e.getMessage());
            return ResponseEntity.badRequest().body(errorResponses);
        }
    }

}
