package com.centralti.tdm.services.servicesinterface;

import com.centralti.tdm.domain.usuarios.DTO.CategoriasDTO;
import com.centralti.tdm.domain.usuarios.DTO.VendedoresDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoriasService {
    void create(CategoriasDTO categoriasDTO);
    List<CategoriasDTO> listar();
    void deletar(Long id);
}
