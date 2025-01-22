package com.centralti.tdm.services.servicesimpl;

import com.centralti.tdm.domain.usuarios.DTO.ProdutosVendidosDTO;
import com.centralti.tdm.domain.usuarios.entidades.Produtos;
import com.centralti.tdm.domain.usuarios.entidades.ProdutosVendidos;
import com.centralti.tdm.domain.usuarios.repositories.ProdutosRepository;
import com.centralti.tdm.domain.usuarios.repositories.ProdutosVendidosRepository;
import com.centralti.tdm.services.servicesinterface.ProdutosVendidosService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutosVendidosServiceImpl implements ProdutosVendidosService {

    @Autowired
    ProdutosVendidosRepository produtosVendidosRepository;

    @Autowired
    ProdutosRepository produtosRepository;

    @Override
    public void create(ProdutosVendidosDTO produtosVendidosDTO) {

        Optional<Produtos> optionalProdutos = produtosRepository.findById(produtosVendidosDTO.produtoId());
        if(optionalProdutos.isPresent()){

            Double totalProdutos = produtosVendidosDTO.valorUnitario() * produtosVendidosDTO.quantidade();
            ProdutosVendidos produtosVendidos = new ProdutosVendidos(produtosVendidosDTO);
            produtosVendidos.setValorTotalProduto(totalProdutos);
            produtosVendidosRepository.save(produtosVendidos);

        } else {
            throw new EntityNotFoundException("Produto não encontrado");
        }

    }

    @Override
    public void create(ProdutosVendidosDTO produtosVendidosDTO, Long vendaId) {

        Optional<Produtos> optionalProdutos = produtosRepository.findById(produtosVendidosDTO.produtoId());
        if(optionalProdutos.isPresent()){
            Double totalProdutos = produtosVendidosDTO.valorUnitario() * produtosVendidosDTO.quantidade();
            ProdutosVendidos produtosVendidos = new ProdutosVendidos(produtosVendidosDTO);
            produtosVendidos.setVendaId(vendaId);
            produtosVendidos.setValorTotalProduto(totalProdutos);
            produtosVendidosRepository.save(produtosVendidos);

        } else {
            throw new EntityNotFoundException("Produto não encontrado");
        }

    }

}
