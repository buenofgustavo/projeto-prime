package com.centralti.tdm.domain.usuarios.DTO;

import com.centralti.tdm.domain.usuarios.entidades.PagamentosVenda;

import java.time.LocalDateTime;

public record PagamentosVendaDTO(

        Long pagamentoId,
        Long vendaId,
        Double valorPago,
        LocalDateTime dataPagamento,
        String atualizadoPor

) {
    public PagamentosVendaDTO(PagamentosVenda pagamentosVenda){
        this(
                pagamentosVenda.getPagamentoId(), pagamentosVenda.getVendaId(),
                pagamentosVenda.getValorPago(), pagamentosVenda.getDataPagamento(),
                pagamentosVenda.getAtualizadoPor()
        );
    }

}
