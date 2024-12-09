package com.centralti.tdm.domain.usuarios.repositories;

import com.centralti.tdm.domain.usuarios.entidades.Clientes;
import com.centralti.tdm.domain.usuarios.entidades.ProdutosVendidos;
import com.centralti.tdm.domain.usuarios.entidades.Vendas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendasRepository extends JpaRepository<Vendas, Long> {
    List<Vendas> findByClienteIdOrderByDataVendaAsc(Long clienteId);
    List<Vendas> findByClienteId(Long clienteId);

}
