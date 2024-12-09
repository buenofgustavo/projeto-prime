package com.centralti.tdm.domain.usuarios.repositories;

import com.centralti.tdm.domain.usuarios.entidades.Clientes;
import com.centralti.tdm.domain.usuarios.entidades.Pagamentos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagamentosRepository extends JpaRepository<Pagamentos, Long> {
    List<Pagamentos> findPagamentosByVendaId(Long vendaId);
    
}
