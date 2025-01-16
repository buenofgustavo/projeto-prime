package com.centralti.tdm.services.servicesimpl;

import com.centralti.tdm.domain.usuarios.DTO.CidadesDTO;
import com.centralti.tdm.domain.usuarios.DTO.VendedoresDTO;
import com.centralti.tdm.domain.usuarios.entidades.Cidades;
import com.centralti.tdm.domain.usuarios.entidades.Vendedores;
import com.centralti.tdm.domain.usuarios.repositories.CidadesRepository;
import com.centralti.tdm.domain.usuarios.repositories.VendedoresRepository;
import com.centralti.tdm.services.servicesinterface.CidadesService;
import com.centralti.tdm.services.servicesinterface.VendedoresService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendedoresServiceImpl implements VendedoresService {

    @Autowired
    VendedoresRepository vendedoresRepository;

    @Override
    public void create(VendedoresDTO vendedoresDTO) {
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        String emailUsuario = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Vendedores> optionalVendedores = vendedoresRepository.findById(vendedoresDTO.id());
        Vendedores vendedores;
        if (optionalVendedores.isPresent()) {
            vendedores = optionalVendedores.get();
            vendedores.setNome(vendedoresDTO.nome());
        } else {
            vendedores = new Vendedores(vendedoresDTO);
            vendedores.setDataCadastro(dataHoraAtual);
            vendedores.setCriadoPor(emailUsuario);
        }
        vendedoresRepository.save(vendedores);
    }


    @Override
    public List<VendedoresDTO> listar() {
        List<Vendedores> allVendedores = vendedoresRepository.findAll();
        return allVendedores.stream()
                .map(VendedoresDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public void deletar(Long id) {
        Vendedores vendedores = vendedoresRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vendedor não encontrado com o ID: " + id));

        vendedoresRepository.delete(vendedores);
    }

}
