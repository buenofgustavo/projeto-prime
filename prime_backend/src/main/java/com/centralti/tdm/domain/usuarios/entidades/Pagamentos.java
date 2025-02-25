package com.centralti.tdm.domain.usuarios.entidades;

import com.centralti.tdm.domain.usuarios.DTO.PagamentosDTO;
import com.centralti.tdm.domain.usuarios.DTO.PagamentosVendaDTO;
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

    @Column(name = "cliente_id")
    private Long clienteId;

    @Column(name = "valor_pago")
    private Double valorPago;

    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;

    @Column(name = "atualizado_por")
    private String atualizadoPor;

    public Pagamentos(@Valid PagamentosDTO pagamentosVendaDTO) {
        this.clienteId = pagamentosVendaDTO.clienteId();
        this.valorPago = pagamentosVendaDTO.valorPago();
        this.dataPagamento = pagamentosVendaDTO.dataPagamento();
        this.atualizadoPor = pagamentosVendaDTO.atualizadoPor();
    }

}
