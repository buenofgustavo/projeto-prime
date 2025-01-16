package com.centralti.tdm.services.servicesimpl;

import com.centralti.tdm.domain.usuarios.DTO.CategoriasDTO;
import com.centralti.tdm.domain.usuarios.DTO.VendedoresDTO;
import com.centralti.tdm.domain.usuarios.entidades.Categorias;
import com.centralti.tdm.domain.usuarios.entidades.Vendedores;
import com.centralti.tdm.domain.usuarios.repositories.CategoriasRepository;
import com.centralti.tdm.domain.usuarios.repositories.VendedoresRepository;
import com.centralti.tdm.services.servicesinterface.CategoriasService;
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
public class CategoriasServiceImpl implements CategoriasService {

    @Autowired
    CategoriasRepository categoriasRepository;

    @Override
    public void create(CategoriasDTO categoriasDTO) {
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        String emailUsuario = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Categorias> optionalCategorias = categoriasRepository.findById(categoriasDTO.id());
        Categorias categorias;
        if (optionalCategorias.isPresent()) {
            categorias = optionalCategorias.get();
            categorias.setNome(categoriasDTO.nome());
        } else {
            categorias = new Categorias(categoriasDTO);
            categorias.setDataCadastro(dataHoraAtual);
            categorias.setCriadoPor(emailUsuario);
        }
        categoriasRepository.save(categorias);
    }


    @Override
    public List<CategoriasDTO> listar() {
        List<Categorias> allCategorias = categoriasRepository.findAll();
        return allCategorias.stream()
                .map(CategoriasDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public void deletar(Long id) {
        Categorias categorias = categoriasRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com o ID: " + id));

        categoriasRepository.delete(categorias);
    }

}
