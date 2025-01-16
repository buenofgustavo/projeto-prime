package com.centralti.tdm.domain.usuarios.DTO;


import com.centralti.tdm.domain.usuarios.entidades.Categorias;
import com.centralti.tdm.domain.usuarios.entidades.Cidades;

import java.time.LocalDateTime;

public record CategoriasDTO(

        Long id,
        String nome,
        LocalDateTime dataCadastro,
        String criadoPor

) {
    public CategoriasDTO(Categorias categorias){
        this(
                categorias.getId(), categorias.getNome(), categorias.getDataCadastro(), categorias.getCriadoPor()
        );
    }
}
