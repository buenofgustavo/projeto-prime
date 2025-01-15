package com.centralti.tdm.services.servicesimpl;

import com.centralti.tdm.domain.usuarios.DTO.ClientesDTO;
import com.centralti.tdm.domain.usuarios.DTO.ProdutosVendidosDTO;
import com.centralti.tdm.domain.usuarios.DTO.VendasComProdutosDTO;
import com.centralti.tdm.domain.usuarios.DTO.VendasDTO;
import com.centralti.tdm.domain.usuarios.entidades.*;
import com.centralti.tdm.domain.usuarios.repositories.*;
import com.centralti.tdm.services.servicesinterface.ProdutosService;
import com.centralti.tdm.services.servicesinterface.ProdutosVendidosService;
import com.centralti.tdm.services.servicesinterface.VendasComProdutosService;
import com.centralti.tdm.services.servicesinterface.VendasService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendasComProdutosServiceImpl implements VendasComProdutosService {

    @Autowired
    VendasService vendasService;

    @Autowired
    ProdutosVendidosService produtosVendidosService;

    @Autowired
    VendasRepository vendasRepository;

    @Autowired
    ProdutosVendidosRepository produtosVendidosRepository;

    @Autowired
    PagamentosRepository pagamentosRepository;

    @Autowired
    ClientesRepository clientesRepository;

    @Autowired
    ProdutosRepository produtosRepository;

    @Override
    @Transactional
    public void create(VendasComProdutosDTO vendasComProdutosDTO) {
        Double totalVenda = 0.0;

        for (ProdutosVendidosDTO produtoVendidoDTO : vendasComProdutosDTO.produtosVendidosDTO()) {
            totalVenda += produtoVendidoDTO.valorUnitario() * produtoVendidoDTO.quantidade();
        }

        Long vendaId = vendasService.create(vendasComProdutosDTO.vendasDTO(), totalVenda);

        for (ProdutosVendidosDTO produtoVendidoDTO : vendasComProdutosDTO.produtosVendidosDTO()) {
            produtosVendidosService.create(produtoVendidoDTO, vendaId);
        }

    }

    @Override
    public void pagar(VendasComProdutosDTO vendasComProdutosDTO) {
        vendasService.pagar(vendasComProdutosDTO.vendasDTO());
    }

    @Override
    public List<VendasComProdutosDTO> listar() {
        List<Vendas> vendas = vendasRepository.findAll();
        List<VendasComProdutosDTO> vendasComProdutosList = new ArrayList<>();

        for (Vendas venda : vendas) {
            List<ProdutosVendidos> produtosVendidos = produtosVendidosRepository.findByVendaId(venda.getId());

            if (produtosVendidos.isEmpty()) {
                System.out.println("Nenhum produto encontrado para a venda ID: " + venda.getId());
            }

            List<ProdutosVendidosDTO> produtosVendidosDTO = produtosVendidos.stream()
                    .map(produtos -> {
                        // Obtenha o nome do produto de outra fonte
                        String nomeProduto = produtosRepository.findById(produtos.getProdutoId())
                                .map(Produtos::getNome) // Caso o produto seja encontrado
                                .orElse("Produto Desconhecido"); // Valor padrão caso não seja encontrado

                        // Passe o nome do produto diretamente no construtor do DTO
                        return new ProdutosVendidosDTO(
                                produtos.getId(), produtos.getVendaId(), produtos.getProdutoId(), produtos.getQuantidade(),
                                produtos.getValorUnitario(), produtos.getValorTotalProduto(), nomeProduto
                        );
                    })
                    .collect(Collectors.toList());

            String nomeCliente = clientesRepository.findById(venda.getClienteId())
                    .map(Clientes::getNome)
                    .orElse("Cliente Desconhecido");

            VendasComProdutosDTO vendasComProdutosDTO = new VendasComProdutosDTO(
                    new VendasDTO(
                            venda.getId(), venda.getClienteId(), venda.getMotorista(), venda.getValorTotalVenda(),
                            venda.getValorPago(), venda.getStatus(), venda.getDataUltimoPagamento(),
                            venda.getDataCadastro(), venda.getDataVenda(), venda.getCriadoPor(),
                            venda.getAtualizadoPor(), venda.getValorPendente(), venda.getObservacao(), nomeCliente
                    ),
                    produtosVendidosDTO
            );

            vendasComProdutosList.add(vendasComProdutosDTO);
        }

        return vendasComProdutosList;
    }

    @Override
    public void deletar(Long id) {
        // Buscar a entidade de vendas pelo ID
        Vendas vendas = vendasRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Venda não encontrada com o ID: " + id));

        // Remover pagamentos associados à venda
        List<Pagamentos> pagamentos = pagamentosRepository.findPagamentosByVendaId(vendas.getId());
        if (pagamentos != null && !pagamentos.isEmpty()) {
            pagamentosRepository.deleteAll(pagamentos);
        }

        // Remover produtos associados à venda
        List<ProdutosVendidos> produtosVendidos = produtosVendidosRepository.findByVendaId(vendas.getId());
        if (produtosVendidos != null && !produtosVendidos.isEmpty()) {
            produtosVendidosRepository.deleteAll(produtosVendidos);
        }

        // Remover a venda
        vendasRepository.delete(vendas);

        vendasService.SaldoDevedor(vendas.getClienteId());
    }
    

}
