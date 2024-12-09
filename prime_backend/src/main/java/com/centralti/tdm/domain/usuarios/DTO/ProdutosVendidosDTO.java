package com.centralti.tdm.domain.usuarios.DTO;


import com.centralti.tdm.domain.usuarios.entidades.ProdutosVendidos;
import com.centralti.tdm.domain.usuarios.entidades.Vendas;

import java.time.LocalDateTime;

public record ProdutosVendidosDTO(

        Long id,
        Long vendaId,
        String produto,
        Integer quantidade,
        Double valorUnitario,
        Double valorTotalProduto

) {
    public ProdutosVendidosDTO(ProdutosVendidos produtosVendidos){
        this(
                produtosVendidos.getId(), produtosVendidos.getVendaId(), produtosVendidos.getProduto(), produtosVendidos.getQuantidade(),
                produtosVendidos.getValorUnitario(), produtosVendidos.getValorTotalProduto()
        );
    }

}
