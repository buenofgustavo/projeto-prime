package com.centralti.tdm.domain.usuarios.DTO;


import com.centralti.tdm.domain.usuarios.entidades.Produtos;
import com.centralti.tdm.domain.usuarios.entidades.Vendas;

import java.time.LocalDateTime;

public record ProdutosDTO(

        Long id,
        String nome,
        LocalDateTime dataCadastro,
        String criadoPor

) {
    public ProdutosDTO(Produtos produtos){
        this(
                produtos.getId(), produtos.getNome(), produtos.getDataCadastro(), produtos.getCriadoPor()
        );
    }

}
