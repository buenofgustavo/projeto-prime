package com.centralti.tdm.domain.usuarios.DTO;


import com.centralti.tdm.domain.usuarios.entidades.Categorias;
import com.centralti.tdm.domain.usuarios.entidades.Vendedores;

import java.time.LocalDateTime;

public record VendedoresDTO(

        Long id,
        String nome,
        LocalDateTime dataCadastro,
        String criadoPor

) {
    public VendedoresDTO(Vendedores vendedores){
        this(
                vendedores.getId(), vendedores.getNome(), vendedores.getDataCadastro(), vendedores.getCriadoPor()
        );
    }
}
