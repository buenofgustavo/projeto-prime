package com.centralti.tdm.domain.usuarios.repositories;

import com.centralti.tdm.domain.usuarios.entidades.Cidades;
import com.centralti.tdm.domain.usuarios.entidades.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadesRepository extends JpaRepository<Cidades, Long> {
}
