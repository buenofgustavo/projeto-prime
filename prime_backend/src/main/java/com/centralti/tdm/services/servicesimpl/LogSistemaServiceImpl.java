package com.centralti.tdm.services.servicesimpl;

import com.centralti.tdm.domain.usuarios.DTO.CategoriasDTO;
import com.centralti.tdm.domain.usuarios.DTO.ClientesDTO;
import com.centralti.tdm.domain.usuarios.DTO.LogSistemaDTO;
import com.centralti.tdm.domain.usuarios.entidades.Categorias;
import com.centralti.tdm.domain.usuarios.entidades.Cidades;
import com.centralti.tdm.domain.usuarios.entidades.Clientes;
import com.centralti.tdm.domain.usuarios.entidades.LogSistema;
import com.centralti.tdm.domain.usuarios.repositories.CategoriasRepository;
import com.centralti.tdm.domain.usuarios.repositories.ClientesRepository;
import com.centralti.tdm.domain.usuarios.repositories.LogSistemaRepository;
import com.centralti.tdm.services.servicesinterface.CategoriasService;
import com.centralti.tdm.services.servicesinterface.LogSistemaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LogSistemaServiceImpl implements LogSistemaService {

    @Autowired
    LogSistemaRepository logSistemaRepository;

    @Autowired
    ClientesRepository clientesRepository;

    @Override
    public void create(LogSistemaDTO logSistemaDTO) {
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        String emailUsuario = SecurityContextHolder.getContext().getAuthentication().getName();

        LogSistema logSistema;
        logSistema = new LogSistema(logSistemaDTO);
        logSistema.setDatahora(dataHoraAtual);
        logSistema.setUsuario(emailUsuario);

        logSistemaRepository.save(logSistema);
    }


    @Override
    public List<LogSistemaDTO> listar() {
        List<LogSistema> allLogSistema = logSistemaRepository.findAll();
        return allLogSistema.stream()
                .map(log -> {

                    String nomeCliente = clientesRepository.findById(log.getClienteId())
                            .map(Clientes::getNome)
                            .orElse("Cliente Desconhecido");

                    return new LogSistemaDTO(
                           log.getId(), log.getMensagem(), log.getUsuario(), log.getClienteId(), log.getVendaId(),
                           log.getCategoriaLog(), log.getDatahora(), nomeCliente
                    );
                })
                .collect(Collectors.toList());
    }

}