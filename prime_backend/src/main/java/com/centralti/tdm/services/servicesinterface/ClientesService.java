package com.centralti.tdm.services.servicesinterface;

import com.centralti.tdm.domain.usuarios.DTO.ClientesDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClientesService {
    void create(ClientesDTO clientesDTO);
    void pagarSaldoDevedor(Long id, Double valorPago);
    List<ClientesDTO> listar();
}
