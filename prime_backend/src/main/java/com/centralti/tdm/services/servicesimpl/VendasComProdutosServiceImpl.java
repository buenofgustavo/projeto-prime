package com.centralti.tdm.services.servicesimpl;

import com.centralti.tdm.domain.usuarios.DTO.PagamentosDTO;
import com.centralti.tdm.domain.usuarios.DTO.ProdutosVendidosDTO;
import com.centralti.tdm.domain.usuarios.DTO.VendasComProdutosDTO;
import com.centralti.tdm.domain.usuarios.DTO.VendasDTO;
import com.centralti.tdm.domain.usuarios.entidades.*;
import com.centralti.tdm.domain.usuarios.repositories.*;
import com.centralti.tdm.services.servicesinterface.PagamentosService;
import com.centralti.tdm.services.servicesinterface.ProdutosVendidosService;
import com.centralti.tdm.services.servicesinterface.VendasComProdutosService;
import com.centralti.tdm.services.servicesinterface.VendasService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    PagamentosVendaRepository pagamentosVendaRepository;

    @Autowired
    ClientesRepository clientesRepository;

    @Autowired
    ProdutosRepository produtosRepository;

    @Autowired
    VendedoresRepository vendedoresRepository;

    @Autowired
    PagamentosService pagamentosService;

    @Autowired
    PagamentosRepository pagamentosRepository;

    @Override
    @Transactional
    public void create(VendasComProdutosDTO vendasComProdutosDTO) {
        Double totalVenda = 0.0;
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        String emailUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Long pagamentoId = 0L;
        Double valorASerPago = vendasComProdutosDTO.vendasDTO().valorPago();

        for (ProdutosVendidosDTO produtoVendidoDTO : vendasComProdutosDTO.produtosVendidosDTO()) {
            totalVenda += produtoVendidoDTO.valorUnitario() * produtoVendidoDTO.quantidade();
        }

        Optional<Vendas> vendasOptional = vendasRepository.findById(vendasComProdutosDTO.vendasDTO().id());
        if (vendasOptional.isPresent()) {
            Vendas vendas = vendasOptional.get();
            valorASerPago = vendasComProdutosDTO.vendasDTO().valorPago() - vendas.getValorPago();
        }

        if (valorASerPago > 0) {
            PagamentosDTO pagamentosDTO = new PagamentosDTO(
                    vendasComProdutosDTO.vendasDTO().clienteId(), valorASerPago, dataHoraAtual, emailUsuario
            );
            pagamentoId = pagamentosService.create(pagamentosDTO);
        }

        Long vendaId = vendasService.create(vendasComProdutosDTO.vendasDTO(), totalVenda, pagamentoId);

        if (vendasOptional.isEmpty()) {
            for (ProdutosVendidosDTO produtoVendidoDTO : vendasComProdutosDTO.produtosVendidosDTO()) {
                produtosVendidosService.create(produtoVendidoDTO, vendaId);
            }
        }

    }

    @Override
    public void pagar(VendasComProdutosDTO vendasComProdutosDTO) {
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        String emailUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Long pagamentoId = 0L;

        Optional<Vendas> vendasOptional = vendasRepository.findById(vendasComProdutosDTO.vendasDTO().id());

        if (vendasOptional.isPresent()) {
            Vendas vendas = vendasOptional.get();
            Double valorASerPago = vendasComProdutosDTO.vendasDTO().valorPago() - vendas.getValorPago();

            if (valorASerPago > 0) {
                PagamentosDTO pagamentosDTO = new PagamentosDTO(
                        vendas.getClienteId(), valorASerPago, dataHoraAtual, emailUsuario
                );
                pagamentoId = pagamentosService.create(pagamentosDTO);
            }

            vendasService.pagar(vendasComProdutosDTO.vendasDTO(), valorASerPago, pagamentoId);

        }

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

            String nomeVendedor = vendedoresRepository.findById(venda.getVendedorId())
                    .map(Vendedores::getNome) // Caso o cliente seja encontrado
                    .orElse("Vendedor Desconhecido"); // Valor padrão caso não seja encontrado

            VendasComProdutosDTO vendasComProdutosDTO = new VendasComProdutosDTO(
                    new VendasDTO(
                            venda.getId(), venda.getClienteId(), venda.getVendedorId(), venda.getValorTotalVenda(),
                            venda.getValorPago(), venda.getStatus(), venda.getDataUltimoPagamento(),
                            venda.getDataCadastro(), venda.getDataVenda(), venda.getCriadoPor(),
                            venda.getAtualizadoPor(), venda.getValorPendente(), venda.getObservacao(), nomeCliente, nomeVendedor
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
        List<PagamentosVenda> pagamentosVenda = pagamentosVendaRepository.findPagamentosVendaByVendaId(vendas.getId());
        if (pagamentosVenda != null && !pagamentosVenda.isEmpty()) {
            for (PagamentosVenda pagamentoVenda : pagamentosVenda) {
                Optional<Pagamentos> optionalPagamentos = pagamentosRepository.findById(pagamentoVenda.getPagamentoId());
                if (optionalPagamentos.isPresent()) {
                    Pagamentos pagamentos = optionalPagamentos.get();
                    Double valorAposSubtrairVenda = pagamentos.getValorPago() - pagamentoVenda.getValorPago();
                    pagamentos.setValorPago(valorAposSubtrairVenda);
                    pagamentosRepository.save(pagamentos);
                    if(valorAposSubtrairVenda == 0d){
                        pagamentosRepository.delete(pagamentos);
                    }
                }
            }
            pagamentosVendaRepository.deleteAll(pagamentosVenda);
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
