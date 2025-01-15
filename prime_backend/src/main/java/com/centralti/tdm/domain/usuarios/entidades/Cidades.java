package com.centralti.tdm.domain.usuarios.entidades;

import com.centralti.tdm.domain.usuarios.DTO.CidadesDTO;
import com.centralti.tdm.domain.usuarios.DTO.ProdutosDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "cidades")
@Entity(name = "cidades")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Cidades {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @Column(name = "criado_por")
    private String criadoPor;

    public Cidades(@Valid CidadesDTO cidadesDTO) {
        this.id = cidadesDTO.id();
        this.nome = cidadesDTO.nome();
        this.dataCadastro = cidadesDTO.dataCadastro();
        this.criadoPor = cidadesDTO.criadoPor();
    }

}
