package com.centralti.tdm.services.servicesinterface;

import com.centralti.tdm.domain.usuarios.DTO.PagamentosDTO;
import com.centralti.tdm.domain.usuarios.DTO.PagamentosVendaDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PagamentosService {
    Long create(PagamentosDTO pagamentosDTO);

    List<PagamentosDTO> listar();

    void delete(Long id);

}
