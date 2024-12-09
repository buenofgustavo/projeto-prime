package com.centralti.tdm.domain.usuarios.entidades;

import com.centralti.tdm.domain.usuarios.DTO.ClientesDTO;
import com.centralti.tdm.domain.usuarios.DTO.ProdutosVendidosDTO;
import com.centralti.tdm.domain.usuarios.DTO.VendasDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "produtos_vendidos")
@Entity(name = "produtos_vendidos")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class ProdutosVendidos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "venda_id")
    private Long vendaId;

    @Column(name = "produto")
    private String produto;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "valor_unitario")
    private Double valorUnitario;

    @Column(name = "valor_total_produto")
    private Double valorTotalProduto;

    public ProdutosVendidos(@Valid ProdutosVendidosDTO produtosVendidosDTO) {
        this.id = produtosVendidosDTO.id();
        this.vendaId = produtosVendidosDTO.vendaId();
        this.produto = produtosVendidosDTO.produto();
        this.quantidade = produtosVendidosDTO.quantidade();
        this.valorUnitario = produtosVendidosDTO.valorUnitario();
        this.valorTotalProduto = produtosVendidosDTO.valorTotalProduto();
    }

}
