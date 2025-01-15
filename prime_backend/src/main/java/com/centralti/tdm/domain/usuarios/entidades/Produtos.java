package com.centralti.tdm.domain.usuarios.entidades;

import com.centralti.tdm.domain.usuarios.DTO.*;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "produtos")
@Entity(name = "produtos")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Produtos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @Column(name = "criado_por")
    private String criadoPor;

    public Produtos(@Valid ProdutosDTO produtosDTO) {
        this.id = produtosDTO.id();
        this.nome = produtosDTO.nome();
        this.dataCadastro = produtosDTO.dataCadastro();
        this.criadoPor = produtosDTO.criadoPor();
    }

}
