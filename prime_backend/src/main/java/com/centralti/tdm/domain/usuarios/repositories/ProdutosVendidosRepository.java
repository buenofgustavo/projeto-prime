package com.centralti.tdm.domain.usuarios.repositories;

import com.centralti.tdm.domain.usuarios.entidades.Clientes;
import com.centralti.tdm.domain.usuarios.entidades.ProdutosVendidos;
import com.centralti.tdm.domain.usuarios.entidades.Vendas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutosVendidosRepository extends JpaRepository<ProdutosVendidos, Long> {

    List<ProdutosVendidos> findByVendaId(Long vendaId); // Método customizado para buscar produtos por vendaId

}
