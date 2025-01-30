package com.centralti.tdm.domain.usuarios.repositories;

import com.centralti.tdm.domain.usuarios.entidades.Categorias;
import com.centralti.tdm.domain.usuarios.entidades.LogSistema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogSistemaRepository extends JpaRepository<LogSistema, Long> {
}
