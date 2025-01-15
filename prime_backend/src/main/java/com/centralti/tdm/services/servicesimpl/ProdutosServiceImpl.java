package com.centralti.tdm.services.servicesimpl;

import com.centralti.tdm.domain.usuarios.DTO.CidadesDTO;
import com.centralti.tdm.domain.usuarios.DTO.ProdutosDTO;
import com.centralti.tdm.domain.usuarios.entidades.Cidades;
import com.centralti.tdm.domain.usuarios.entidades.Produtos;
import com.centralti.tdm.domain.usuarios.repositories.CidadesRepository;
import com.centralti.tdm.domain.usuarios.repositories.ProdutosRepository;
import com.centralti.tdm.domain.usuarios.repositories.VendasRepository;
import com.centralti.tdm.services.servicesinterface.CidadesService;
import com.centralti.tdm.services.servicesinterface.ProdutosService;
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
public class ProdutosServiceImpl implements ProdutosService {

    @Autowired
    ProdutosRepository produtosRepository;

    @Override
    public void create(ProdutosDTO produtosDTO) {
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        String emailUsuario = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Produtos> optionalCidades = produtosRepository.findById(produtosDTO.id());
        Produtos produtos;
        if (optionalCidades.isPresent()) {
            produtos = optionalCidades.get();
            produtos.setNome(produtosDTO.nome());
        } else {
            produtos = new Produtos(produtosDTO);
            produtos.setDataCadastro(dataHoraAtual);
            produtos.setCriadoPor(emailUsuario);
        }
        produtosRepository.save(produtos);
    }


    @Override
    public List<ProdutosDTO> listar() {
        List<Produtos> allProdutos = produtosRepository.findAll();
        return allProdutos.stream()
                .map(ProdutosDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public void deletar(Long id) {
        Produtos produtos = produtosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrada com o ID: " + id));

        produtosRepository.delete(produtos);
    }

}
