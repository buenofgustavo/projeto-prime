package com.centralti.tdm.services.servicesinterface;

import com.centralti.tdm.domain.usuarios.DTO.ClientesDTO;
import com.centralti.tdm.domain.usuarios.DTO.ProdutosVendidosDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProdutosVendidosService {
    void create(ProdutosVendidosDTO produtosVendidosDTO);

    void create(ProdutosVendidosDTO produtosVendidosDTO, Long vendaId);

    List<ProdutosVendidosDTO> listar();
}
