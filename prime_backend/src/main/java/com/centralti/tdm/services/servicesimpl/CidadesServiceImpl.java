package com.centralti.tdm.services.servicesimpl;

import com.centralti.tdm.domain.usuarios.DTO.CidadesDTO;
import com.centralti.tdm.domain.usuarios.DTO.ClientesDTO;
import com.centralti.tdm.domain.usuarios.DTO.VendasDTO;
import com.centralti.tdm.domain.usuarios.entidades.Cidades;
import com.centralti.tdm.domain.usuarios.entidades.Clientes;
import com.centralti.tdm.domain.usuarios.entidades.Vendas;
import com.centralti.tdm.domain.usuarios.repositories.CidadesRepository;
import com.centralti.tdm.domain.usuarios.repositories.ClientesRepository;
import com.centralti.tdm.domain.usuarios.repositories.VendasRepository;
import com.centralti.tdm.services.servicesinterface.CidadesService;
import com.centralti.tdm.services.servicesinterface.ClientesService;
import com.centralti.tdm.services.servicesinterface.VendasService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CidadesServiceImpl implements CidadesService {

    @Autowired
    CidadesRepository cidadesRepository;

    @Override
    public void create(CidadesDTO cidadesDTO) {
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        String emailUsuario = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Cidades> optionalCidades = cidadesRepository.findById(cidadesDTO.id());
        Cidades cidades;
        if (optionalCidades.isPresent()) {
            cidades = optionalCidades.get();
            cidades.setNome(cidadesDTO.nome());
        } else {
            cidades = new Cidades(cidadesDTO);
            cidades.setDataCadastro(dataHoraAtual);
            cidades.setCriadoPor(emailUsuario);
        }
        cidadesRepository.save(cidades);
    }


    @Override
    public List<CidadesDTO> listar() {
        List<Cidades> allCidades = cidadesRepository.findAll();
        return allCidades.stream()
                .map(CidadesDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public void deletar(Long id) {
        Cidades cidades = cidadesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cidade não encontrada com o ID: " + id));

        cidadesRepository.delete(cidades);
    }

}
