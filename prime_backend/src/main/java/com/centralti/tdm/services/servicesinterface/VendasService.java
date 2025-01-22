package com.centralti.tdm.services.servicesinterface;

import com.centralti.tdm.domain.usuarios.DTO.ClientesDTO;
import com.centralti.tdm.domain.usuarios.DTO.VendasDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VendasService {

    Long create(VendasDTO vendasDTO, Double valorTotalVenda, Long pagamentoId);

    void pagar(VendasDTO vendasDTO, Double valorASerPago, Long pagamentoId);

    void pagarSaldoDevedor(VendasDTO vendasDTO, Double valorPago, Long pagamentoId);

    void SaldoDevedor(Long idCliente);

    List<VendasDTO> listar();

}
