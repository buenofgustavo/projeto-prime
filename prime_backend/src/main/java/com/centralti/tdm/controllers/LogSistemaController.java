package com.centralti.tdm.controllers;

import com.centralti.tdm.domain.usuarios.DTO.CategoriasDTO;
import com.centralti.tdm.domain.usuarios.DTO.LogSistemaDTO;
import com.centralti.tdm.errors.ErrorResponses;
import com.centralti.tdm.services.servicesinterface.CategoriasService;
import com.centralti.tdm.services.servicesinterface.LogSistemaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/log")
public class LogSistemaController {

    @Autowired
    LogSistemaService logSistemaService;

    @PostMapping()
    public ResponseEntity create(@RequestBody LogSistemaDTO logSistemaDTO) {
        try {
            logSistemaService.create(logSistemaDTO);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            ErrorResponses errorResponses = new ErrorResponses(e.getMessage());
            return ResponseEntity.badRequest().body(errorResponses);
        }
    }

    @GetMapping()
    public ResponseEntity findByLogs(){
        try {
            var dados = logSistemaService.listar();
            return ResponseEntity.ok(dados);
        } catch (EntityNotFoundException e) {
            ErrorResponses errorResponses = new ErrorResponses(e.getMessage());
            return ResponseEntity.badRequest().body(errorResponses);
        }
    }

}
