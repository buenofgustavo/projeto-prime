package com.centralti.tdm.domain.usuarios.DTO;

import com.centralti.tdm.domain.usuarios.entidades.Pagamentos;
import com.centralti.tdm.domain.usuarios.entidades.PagamentosVenda;

import java.time.LocalDateTime;

public record PagamentosDTO(

        Long clienteId,
        Double valorPago,
        LocalDateTime dataPagamento,
        String atualizadoPor

) {
    public PagamentosDTO(Pagamentos pagamentos){
        this(
                pagamentos.getClienteId(), pagamentos.getValorPago(),
                pagamentos.getDataPagamento(), pagamentos.getAtualizadoPor()
        );
    }

}
