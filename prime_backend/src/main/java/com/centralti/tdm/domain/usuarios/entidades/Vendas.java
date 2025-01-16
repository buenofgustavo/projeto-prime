package com.centralti.tdm.domain.usuarios.entidades;

import com.centralti.tdm.domain.usuarios.DTO.ClientesDTO;
import com.centralti.tdm.domain.usuarios.DTO.VendasDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "vendas")
@Entity(name = "vendas")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Vendas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cliente_id")
    private Long clienteId;

    @Column(name = "vendedor_id")
    private Long vendedorId;

    @Column(name = "valor_total_venda")
    private Double valorTotalVenda;

    @Column(name = "valor_pago")
    private Double valorPago;

    @Column(name = "status")
    private String status;

    @Column(name = "data_ultimo_pagamento")
    private LocalDateTime dataUltimoPagamento;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @Column(name = "data_venda")
    private LocalDateTime dataVenda;

    @Column(name = "criado_por")
    private String criadoPor;

    @Column(name = "atualizado_por")
    private String atualizadoPor;

    @Column(name = "valor_pendente")
    private Double valorPendente;

    @Column(name = "observacao")
    private String observacao;

    public Vendas(@Valid VendasDTO vendasDTO) {
        this.id = vendasDTO.id();
        this.clienteId = vendasDTO.clienteId();
        this.vendedorId = vendasDTO.vendedorId();
        this.valorTotalVenda = vendasDTO.valorTotalVenda();
        this.valorPago = vendasDTO.valorPago();
        this.status = vendasDTO.status();
        this.dataUltimoPagamento = vendasDTO.dataUltimoPagamento();
        this.dataCadastro = vendasDTO.dataCadastro();
        this.dataVenda = vendasDTO.dataVenda();
        this.criadoPor = vendasDTO.criadoPor();
        this.atualizadoPor = vendasDTO.atualizadoPor();
        this.valorPendente = vendasDTO.valorPendente();
        this.observacao = vendasDTO.observacao();
    }
}
