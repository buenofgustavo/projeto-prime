package com.centralti.tdm.services.servicesinterface;

import com.centralti.tdm.domain.usuarios.DTO.PagamentosVendaDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PagamentosVendaService {
    void create(PagamentosVendaDTO pagamentosDTO);

    List<PagamentosVendaDTO> listar();
}
