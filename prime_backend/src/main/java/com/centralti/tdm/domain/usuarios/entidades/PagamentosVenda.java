package com.centralti.tdm.domain.usuarios.entidades;

import com.centralti.tdm.domain.usuarios.DTO.PagamentosVendaDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "pagamentos_venda")
@Entity(name = "pagamentos_venda")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class PagamentosVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "pagamento_id")
    private Long pagamentoId;

    @Setter
    @Column(name = "venda_id")
    private Long vendaId;

    @Column(name = "valor_pago")
    private Double valorPago;

    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;

    @Column(name = "atualizado_por")
    private String atualizadoPor;

    public PagamentosVenda(@Valid PagamentosVendaDTO pagamentosVendaDTO) {
        this.pagamentoId = pagamentosVendaDTO.pagamentoId();
        this.vendaId = pagamentosVendaDTO.vendaId();
        this.valorPago = pagamentosVendaDTO.valorPago();
        this.dataPagamento = pagamentosVendaDTO.dataPagamento();
        this.atualizadoPor = pagamentosVendaDTO.atualizadoPor();
    }

}
