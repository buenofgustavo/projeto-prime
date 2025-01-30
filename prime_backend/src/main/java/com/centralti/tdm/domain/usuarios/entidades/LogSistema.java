package com.centralti.tdm.domain.usuarios.entidades;

import com.centralti.tdm.domain.usuarios.DTO.CategoriasDTO;
import com.centralti.tdm.domain.usuarios.DTO.LogSistemaDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "log_sistema")
@Entity(name = "log_sistema")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class LogSistema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mensagem")
    private String mensagem;

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "cliente_id")
    private Long clienteId;

    @Column(name = "venda_id")
    private Long vendaId;

    @Column(name = "categoria_log")
    private String categoriaLog;

    @Column(name = "datahora")
    private LocalDateTime datahora;


    public LogSistema(@Valid LogSistemaDTO logSistemaDTO) {
        this.id = logSistemaDTO.id();
        this.mensagem = logSistemaDTO.mensagem();
        this.usuario = logSistemaDTO.usuario();
        this.clienteId = logSistemaDTO.clienteId();
        this.vendaId = logSistemaDTO.vendaId();
        this.categoriaLog = logSistemaDTO.categoriaLog();
        this.datahora = logSistemaDTO.datahora();
    }

}
