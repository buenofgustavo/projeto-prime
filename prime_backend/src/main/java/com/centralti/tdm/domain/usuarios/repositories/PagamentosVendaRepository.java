package com.centralti.tdm.domain.usuarios.repositories;

import com.centralti.tdm.domain.usuarios.entidades.PagamentosVenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PagamentosVendaRepository extends JpaRepository<PagamentosVenda, Long> {
    List<PagamentosVenda> findPagamentosVendaByVendaId(Long vendaId);

    List<PagamentosVenda> findPagamentosVendaByPagamentoId(Long id);
}
