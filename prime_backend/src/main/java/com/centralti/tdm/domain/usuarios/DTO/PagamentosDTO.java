package com.centralti.tdm.domain.usuarios.DTO;

import com.centralti.tdm.domain.usuarios.entidades.Pagamentos;
import com.centralti.tdm.domain.usuarios.entidades.ProdutosVendidos;
import com.centralti.tdm.domain.usuarios.entidades.Vendas;

import java.time.LocalDateTime;

public record PagamentosDTO(

        Long vendaId,
        Double valorPago,
        LocalDateTime dataPagamento,
        String atualizadoPor

) {
    public PagamentosDTO(Pagamentos pagamentos){
        this(
                pagamentos.getVendaId(), pagamentos.getValorPago(),
                pagamentos.getDataPagamento(), pagamentos.getAtualizadoPor()
        );
    }

}
