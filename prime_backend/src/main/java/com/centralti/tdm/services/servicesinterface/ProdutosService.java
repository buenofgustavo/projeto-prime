package com.centralti.tdm.services.servicesinterface;

import com.centralti.tdm.domain.usuarios.DTO.CidadesDTO;
import com.centralti.tdm.domain.usuarios.DTO.ProdutosDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProdutosService {
    void create(ProdutosDTO produtosDTO);
    List<ProdutosDTO> listar();
    void deletar(Long id);
}
