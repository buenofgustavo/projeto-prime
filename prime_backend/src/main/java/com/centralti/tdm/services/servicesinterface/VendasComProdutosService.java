package com.centralti.tdm.services.servicesinterface;

import com.centralti.tdm.domain.usuarios.DTO.ClientesDTO;
import com.centralti.tdm.domain.usuarios.DTO.ProdutosVendidosDTO;
import com.centralti.tdm.domain.usuarios.DTO.VendasComProdutosDTO;
import com.centralti.tdm.domain.usuarios.DTO.VendasDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VendasComProdutosService {

    void create(VendasComProdutosDTO vendasComProdutosDTO);

    void pagar(VendasComProdutosDTO vendasComProdutosDTO);

    void deletar(Long id);

    List<VendasComProdutosDTO> listar();

}
