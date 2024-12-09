package com.centralti.tdm.domain.usuarios.entidades;

import com.centralti.tdm.domain.usuarios.DTO.ClientesDTO;
import com.centralti.tdm.domain.usuarios.DTO.PagamentosDTO;
import com.centralti.tdm.domain.usuarios.DTO.ProdutosVendidosDTO;
import com.centralti.tdm.domain.usuarios.DTO.VendasDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "pagamentos")
@Entity(name = "pagamentos")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Pagamentos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "venda_id")
    private Long vendaId;

    @Column(name = "valor_pago")
    private Double valorPago;

    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;

    @Column(name = "atualizado_por")
    private String atualizadoPor;

    public Pagamentos(@Valid PagamentosDTO pagamentosDTO) {
        this.vendaId = pagamentosDTO.vendaId();
        this.valorPago = pagamentosDTO.valorPago();
        this.dataPagamento = pagamentosDTO.dataPagamento();
        this.atualizadoPor = pagamentosDTO.atualizadoPor();
    }

}
