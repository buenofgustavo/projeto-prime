package com.centralti.tdm.domain.usuarios.entidades;

import com.centralti.tdm.domain.usuarios.DTO.ClientesDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "clientes")
@Entity(name = "clientes")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Clientes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "responsavel")
    private String responsavel;

    @Column(name = "contato")
    private String contato;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @Column(name = "criado_por")
    private String criadoPor;

    @Column(name = "saldo_devedor")
    private Double saldoDevedor;


    public Clientes(@Valid ClientesDTO clientesDTO) {
        this.id = clientesDTO.id();
        this.nome = clientesDTO.nome();
        this.categoria = clientesDTO.categoria();
        this.responsavel = clientesDTO.responsavel();
        this.contato = clientesDTO.contato();
        this.cidade = clientesDTO.cidade();
        this.dataCadastro = clientesDTO.dataCadastro();
        this.criadoPor = clientesDTO.criadoPor();
        this.saldoDevedor = clientesDTO.saldoDevedor();
    }
}
