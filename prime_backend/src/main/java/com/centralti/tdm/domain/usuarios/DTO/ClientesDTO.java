package com.centralti.tdm.domain.usuarios.DTO;

import com.centralti.tdm.domain.usuarios.entidades.Clientes;

import java.time.LocalDateTime;

public record ClientesDTO(

        Long id,
        String nome,
        String categoria,
        String responsavel,
        String contato,
        String cidade,
        LocalDateTime dataCadastro,
        String criadoPor,
        Double saldoDevedor

) {
    public ClientesDTO(Clientes clientes){
        this(
                clientes.getId(), clientes.getNome(), clientes.getCategoria(), clientes.getResponsavel(),
                clientes.getContato(), clientes.getCidade(), clientes.getDataCadastro(),
                clientes.getCriadoPor(), clientes.getSaldoDevedor()
        );
    }

}
