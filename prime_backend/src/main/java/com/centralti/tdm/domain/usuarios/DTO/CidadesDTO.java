package com.centralti.tdm.domain.usuarios.DTO;


import com.centralti.tdm.domain.usuarios.entidades.Cidades;

import java.time.LocalDateTime;

public record CidadesDTO(

        Long id,
        String nome,
        LocalDateTime dataCadastro,
        String criadoPor

) {
    public CidadesDTO(Cidades cidades){
        this(
                cidades.getId(), cidades.getNome(), cidades.getDataCadastro(), cidades.getCriadoPor()
        );
    }
}
