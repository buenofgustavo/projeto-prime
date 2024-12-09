package com.centralti.tdm.services.servicesimpl;

import com.centralti.tdm.domain.usuarios.DTO.ProdutosVendidosDTO;
import com.centralti.tdm.domain.usuarios.entidades.ProdutosVendidos;
import com.centralti.tdm.domain.usuarios.repositories.ProdutosVendidosRepository;
import com.centralti.tdm.services.servicesinterface.ProdutosVendidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutosVendidosServiceImpl implements ProdutosVendidosService {

    @Autowired
    ProdutosVendidosRepository produtosVendidosRepository;

    @Override
    public void create(ProdutosVendidosDTO produtosVendidosDTO) {
        Double totalProdutos = produtosVendidosDTO.valorUnitario() * produtosVendidosDTO.quantidade();

        ProdutosVendidos produtosVendidos = new ProdutosVendidos(produtosVendidosDTO);
        produtosVendidos.setValorTotalProduto(totalProdutos);

        produtosVendidosRepository.save(produtosVendidos);
    }

    @Override
    public void create(ProdutosVendidosDTO produtosVendidosDTO, Long vendaId) {
        Double totalProdutos = produtosVendidosDTO.valorUnitario() * produtosVendidosDTO.quantidade();

        ProdutosVendidos produtosVendidos = new ProdutosVendidos(produtosVendidosDTO);
        produtosVendidos.setVendaId(vendaId);
        produtosVendidos.setValorTotalProduto(totalProdutos);

        produtosVendidosRepository.save(produtosVendidos);
    }

    @Override
    public List<ProdutosVendidosDTO> listar() {
        List<ProdutosVendidos> allProdutosVendidos = produtosVendidosRepository.findAll();
        return allProdutosVendidos.stream()
                .map(ProdutosVendidosDTO::new)
                .collect(Collectors.toList());
    }

}
