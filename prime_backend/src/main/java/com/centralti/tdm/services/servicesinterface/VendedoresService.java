package com.centralti.tdm.services.servicesinterface;

import com.centralti.tdm.domain.usuarios.DTO.CidadesDTO;
import com.centralti.tdm.domain.usuarios.DTO.VendedoresDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VendedoresService {
    void create(VendedoresDTO vendedoresDTO);
    List<VendedoresDTO> listar();
    void deletar(Long id);
}
