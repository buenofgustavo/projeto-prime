package com.centralti.tdm.domain.usuarios.DTO;


import com.centralti.tdm.domain.usuarios.entidades.Vendas;

import java.time.LocalDateTime;

public record VendasDTO(

        Long id,
        Long clienteId,
        String motorista,
        Double valorTotalVenda,
        Double valorPago,
        String status,
        LocalDateTime dataUltimoPagamento,
        LocalDateTime dataCadastro,
        LocalDateTime dataVenda,
        String criadoPor,
        String atualizadoPor,
        Double valorPendente,
        String observacao,
        String nomeCliente

) {
    public VendasDTO(Vendas vendas){
        this(
                vendas.getId(), vendas.getClienteId(), vendas.getMotorista(), vendas.getValorTotalVenda(),
                vendas.getValorPago(), vendas.getStatus(), vendas.getDataUltimoPagamento(),
                vendas.getDataCadastro(), vendas.getDataVenda(), vendas.getCriadoPor(),
                vendas.getAtualizadoPor(), vendas.getValorPendente(), vendas.getObservacao(), ""
        );
    }

}
