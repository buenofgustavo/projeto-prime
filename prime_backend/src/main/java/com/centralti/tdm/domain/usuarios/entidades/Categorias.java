package com.centralti.tdm.domain.usuarios.entidades;

import com.centralti.tdm.domain.usuarios.DTO.CategoriasDTO;
import com.centralti.tdm.domain.usuarios.DTO.CidadesDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "categorias")
@Entity(name = "categorias")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Categorias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @Column(name = "criado_por")
    private String criadoPor;

    public Categorias(@Valid CategoriasDTO categoriasDTO) {
        this.id = categoriasDTO.id();
        this.nome = categoriasDTO.nome();
        this.dataCadastro = categoriasDTO.dataCadastro();
        this.criadoPor = categoriasDTO.criadoPor();
    }

}
