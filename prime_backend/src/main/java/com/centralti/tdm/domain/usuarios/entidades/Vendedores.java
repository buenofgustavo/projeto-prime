package com.centralti.tdm.domain.usuarios.entidades;

import com.centralti.tdm.domain.usuarios.DTO.CategoriasDTO;
import com.centralti.tdm.domain.usuarios.DTO.VendedoresDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "vendedores")
@Entity(name = "vendedores")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Vendedores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @Column(name = "criado_por")
    private String criadoPor;

    public Vendedores(@Valid VendedoresDTO vendedoresDTO) {
        this.id = vendedoresDTO.id();
        this.nome = vendedoresDTO.nome();
        this.dataCadastro = vendedoresDTO.dataCadastro();
        this.criadoPor = vendedoresDTO.criadoPor();
    }

}
