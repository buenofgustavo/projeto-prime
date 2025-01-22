package com.centralti.tdm.domain.usuarios.repositories;

import com.centralti.tdm.domain.usuarios.entidades.PagamentosVenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagamentosVendaRepository extends JpaRepository<PagamentosVenda, Long> {
    List<PagamentosVenda> findPagamentosVendaByPagamentoId(Long pagamentoId);
    List<PagamentosVenda> findPagamentosVendaByVendaId(Long vendaId);

}
