package com.centralti.tdm.services.servicesinterface;

import com.centralti.tdm.domain.usuarios.DTO.ClientesDTO;
import com.centralti.tdm.domain.usuarios.DTO.VendasDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VendasService {

    Long create(VendasDTO vendasDTO, Double valorTotalVenda);

    void pagar(VendasDTO vendasDTO);

    void pagarSaldoDevedor(VendasDTO vendasDTO, Double valorPago);

    void SaldoDevedor(Long idCliente);

    List<VendasDTO> listar();

}
