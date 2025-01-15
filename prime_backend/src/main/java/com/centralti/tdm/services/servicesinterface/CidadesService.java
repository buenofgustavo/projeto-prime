package com.centralti.tdm.services.servicesinterface;

import com.centralti.tdm.domain.usuarios.DTO.CidadesDTO;
import com.centralti.tdm.domain.usuarios.DTO.ClientesDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CidadesService {
    void create(CidadesDTO cidadesDTO);
    List<CidadesDTO> listar();
    void deletar(Long id);
}
