package com.centralti.tdm.controllers;

import com.centralti.tdm.domain.usuarios.DTO.ClientesDTO;
import com.centralti.tdm.domain.usuarios.DTO.VendasComProdutosDTO;
import com.centralti.tdm.errors.ErrorResponses;
import com.centralti.tdm.services.servicesinterface.ClientesService;
import com.centralti.tdm.services.servicesinterface.VendasComProdutosService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vendas")
public class VendasComProdutosController {
    @Autowired
    VendasComProdutosService vendasComProdutosService;

    @PostMapping()
    public ResponseEntity create(@RequestBody @Valid VendasComProdutosDTO vendasComProdutosDTO) {
        try {
            vendasComProdutosService.create(vendasComProdutosDTO);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            ErrorResponses errorResponses = new ErrorResponses(e.getMessage());
            return ResponseEntity.badRequest().body(errorResponses);
        }
    }

    @PutMapping()
    public ResponseEntity pagar(@RequestBody @Valid VendasComProdutosDTO vendasComProdutosDTO) {
        try {
            vendasComProdutosService.pagar(vendasComProdutosDTO);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            ErrorResponses errorResponses = new ErrorResponses(e.getMessage());
            return ResponseEntity.badRequest().body(errorResponses);
        }
    }

    @GetMapping()
    public ResponseEntity findByTipo(){
        try {
            var dados = vendasComProdutosService.listar();
            return ResponseEntity.ok(dados);
        } catch (EntityNotFoundException e) {
            ErrorResponses errorResponses = new ErrorResponses(e.getMessage());
            return ResponseEntity.badRequest().body(errorResponses);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            vendasComProdutosService.deletar(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            ErrorResponses errorResponses = new ErrorResponses(e.getMessage());
            return ResponseEntity.badRequest().body(errorResponses);
        }
    }

}
