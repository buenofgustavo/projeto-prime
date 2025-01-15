package com.centralti.tdm.controllers;

import com.centralti.tdm.domain.usuarios.DTO.CidadesDTO;
import com.centralti.tdm.domain.usuarios.DTO.ProdutosDTO;
import com.centralti.tdm.errors.ErrorResponses;
import com.centralti.tdm.services.servicesinterface.CidadesService;
import com.centralti.tdm.services.servicesinterface.ProdutosService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cidades")
public class CidadesController {

    @Autowired
    CidadesService cidadesService;

    @PostMapping()
    public ResponseEntity create(@RequestBody CidadesDTO cidadesDTO) {
        try {
            cidadesService.create(cidadesDTO);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            ErrorResponses errorResponses = new ErrorResponses(e.getMessage());
            return ResponseEntity.badRequest().body(errorResponses);
        }
    }

    @GetMapping()
    public ResponseEntity findByCidades(){
        try {
            var dados = cidadesService.listar();
            return ResponseEntity.ok(dados);
        } catch (EntityNotFoundException e) {
            ErrorResponses errorResponses = new ErrorResponses(e.getMessage());
            return ResponseEntity.badRequest().body(errorResponses);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            cidadesService.deletar(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            ErrorResponses errorResponses = new ErrorResponses(e.getMessage());
            return ResponseEntity.badRequest().body(errorResponses);
        }
    }

}
