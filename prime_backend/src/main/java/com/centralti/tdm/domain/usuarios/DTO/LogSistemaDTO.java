package com.centralti.tdm.domain.usuarios.DTO;


import com.centralti.tdm.domain.usuarios.entidades.Categorias;
import com.centralti.tdm.domain.usuarios.entidades.LogSistema;

import java.time.LocalDateTime;

public record LogSistemaDTO
        (
                Long id,
                String mensagem,
                String usuario,
                Long clienteId,
                Long vendaId,
                String categoriaLog,
                LocalDateTime datahora,
                String nomeCliente

        ) {

    public LogSistemaDTO(LogSistema logSistema){
        this(
                logSistema.getId(), logSistema.getMensagem(), logSistema.getUsuario(),
                logSistema.getClienteId(), logSistema.getVendaId(), logSistema.getCategoriaLog(),
                logSistema.getDatahora(), ""
        );
    }
}