package com.centralti.tdm.services.servicesinterface;

import com.centralti.tdm.domain.usuarios.DTO.CategoriasDTO;
import com.centralti.tdm.domain.usuarios.DTO.LogSistemaDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LogSistemaService {
    void create(LogSistemaDTO logSistemaDTO);
    List<LogSistemaDTO> listar();
}
