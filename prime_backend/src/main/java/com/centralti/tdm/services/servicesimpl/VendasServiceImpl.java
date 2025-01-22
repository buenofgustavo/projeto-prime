package com.centralti.tdm.services.servicesimpl;

import com.centralti.tdm.domain.usuarios.DTO.PagamentosVendaDTO;
import com.centralti.tdm.domain.usuarios.DTO.VendasDTO;
import com.centralti.tdm.domain.usuarios.entidades.*;
import com.centralti.tdm.domain.usuarios.repositories.*;
import com.centralti.tdm.services.servicesinterface.PagamentosVendaService;
import com.centralti.tdm.services.servicesinterface.VendasService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendasServiceImpl implements VendasService {

    @Autowired
    VendasRepository vendasRepository;

    @Autowired
    ClientesRepository clientesRepository;

    @Autowired
    PagamentosVendaService pagamentosVendaService;

    @Autowired
    PagamentosVendaRepository pagamentosVendaRepository;

    @Autowired
    VendedoresRepository vendedoresRepository;

    @Autowired
    @Lazy
    VendasService vendasService;

    @Override
    public Long create(VendasDTO vendasDTO, Double valorTotalVenda, Long pagamentoId) {
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        String emailUsuario = SecurityContextHolder.getContext().getAuthentication().getName();

        Vendas vendas;
        Double valorParaPagamento = 0d;
        Optional<Vendedores> optionalVendedores = vendedoresRepository.findById(vendasDTO.vendedorId());
        if(optionalVendedores.isPresent()) {

            if (vendasDTO.id() != null && vendasDTO.id() != 0 ) {

                // Buscar a venda existente para atualização
                vendas = vendasRepository.findById(vendasDTO.id())
                        .orElseThrow(() -> new EntityNotFoundException("Venda não encontrada com o ID: " + vendasDTO.id()));
                vendas.setDataVenda(vendasDTO.dataVenda());
                vendas.setVendedorId(vendasDTO.vendedorId());
                vendas.setAtualizadoPor(emailUsuario);
                vendas.setObservacao(vendas.getObservacao());

            } else {
                // Criar nova venda
                vendas = new Vendas(vendasDTO);

                vendas.setDataCadastro(dataHoraAtual);
                vendas.setCriadoPor(emailUsuario);
                valorParaPagamento = vendas.getValorPago();

            }
            vendas.setValorTotalVenda(valorTotalVenda);
            if(vendas.getValorPago() == null){
                vendas.setValorPago(0d);
            }
            Double valorRestante = vendas.getValorTotalVenda() - vendas.getValorPago();
            vendas.setValorPendente(valorRestante);
            if (vendas.getValorPago().equals(vendas.getValorTotalVenda())){
                vendas.setStatus("Pago");
            } else if (vendas.getValorPago() < vendas.getValorTotalVenda() && vendas.getValorPago() > 0) {
                vendas.setStatus("Pendente - Parcial");
            } else if (vendas.getValorPago() > vendas.getValorTotalVenda()){
                vendas.setStatus("Pago a mais");
            }
            else {
                vendas.setStatus("Pendente");
            }
            if(vendasDTO.valorPago() != 0){
                vendas.setDataUltimoPagamento(dataHoraAtual);
            }

            vendas = vendasRepository.save(vendas); // O ID será atribuído automaticamente após o save

            vendasService.SaldoDevedor(vendas.getClienteId());

            if (valorParaPagamento > 0) {
                PagamentosVendaDTO pagamentosDTO = new PagamentosVendaDTO(
                        pagamentoId, vendas.getId(), valorParaPagamento, dataHoraAtual, emailUsuario
                );
                pagamentosVendaService.create(pagamentosDTO);
            }

            return vendas.getId();


        } else {
            throw new EntityNotFoundException("Vendedor não encontrado");
        }

    }

    @Override
    @Transactional
    public void pagar(VendasDTO vendasDTO, Double valorASerPago, Long pagamentoId) {
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        String emailUsuario = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Vendas> vendasOptional = vendasRepository.findById(vendasDTO.id());

        if (vendasOptional.isPresent()) {
            Vendas vendas = vendasOptional.get();

            // Define o valor pago
            vendas.setValorPago(vendasDTO.valorPago());

            // Calcula o valor restante
            Double valorRestante = vendas.getValorTotalVenda() - vendas.getValorPago();
            vendas.setValorPendente(valorRestante);

            // Define o usuário e data de atualização
            vendas.setAtualizadoPor(emailUsuario);
            vendas.setDataUltimoPagamento(dataHoraAtual);

            if (vendas.getValorPago().equals(vendas.getValorTotalVenda())){
                vendas.setStatus("Pago");
            } else if (vendas.getValorPago() < vendas.getValorTotalVenda() && vendas.getValorPago() > 0) {
                vendas.setStatus("Pendente - Parcial");
            } else if (vendas.getValorPago() > vendas.getValorTotalVenda()){
                vendas.setStatus("ERRO! Pago a mais");
            }
            else {
                vendas.setStatus("Pendente");
            }

            if (valorASerPago > 0) {
                PagamentosVendaDTO pagamentosVendaDTO = new PagamentosVendaDTO(
                       pagamentoId ,vendas.getId(), valorASerPago, dataHoraAtual, emailUsuario
                );
                pagamentosVendaService.create(pagamentosVendaDTO);

            } else if (vendasDTO.valorPago() < vendas.getValorPago()) {

                // Remover pagamentos associados à venda
                List<PagamentosVenda> pagamentosVenda = pagamentosVendaRepository.findPagamentosVendaByVendaId(vendas.getId());
                if (pagamentosVenda != null && !pagamentosVenda.isEmpty()) {
                    pagamentosVendaRepository.deleteAll(pagamentosVenda);
                }

                PagamentosVendaDTO pagamentosVendaDTO = new PagamentosVendaDTO(
                       pagamentoId, vendas.getId(), vendasDTO.valorPago(), dataHoraAtual, emailUsuario
                );

                pagamentosVendaService.create(pagamentosVendaDTO);
            }


            vendasRepository.save(vendas);



            SaldoDevedor(vendas.getClienteId());
        }
    }

    @Override
    @Transactional
    public void pagarSaldoDevedor(VendasDTO vendasDTO, Double valorPago, Long pagamentoId) {
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        String emailUsuario = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Vendas> vendasOptional = vendasRepository.findById(vendasDTO.id());

        if (vendasOptional.isPresent()) {
            Vendas vendas = vendasOptional.get();

            // Acumula o valor pago com o valor existente
            Double valorPagoAtualizado = vendas.getValorPago() + valorPago;
            vendas.setValorPago(valorPagoAtualizado);

            // Calcula o valor restante
            Double valorRestante = vendas.getValorTotalVenda() - vendas.getValorPago();
            vendas.setValorPendente(valorRestante);

            // Define o usuário e data de atualização
            vendas.setAtualizadoPor(emailUsuario);
            vendas.setDataUltimoPagamento(dataHoraAtual);

            // Atualiza o status de pagamento com tolerância de 0.01 para evitar problemas de arredondamento
            if (Math.abs(vendas.getValorPago() - vendas.getValorTotalVenda()) < 0.01) {
                vendas.setStatus("Pago");
            } else if (vendas.getValorPago() < vendas.getValorTotalVenda() && vendas.getValorPago() > 0) {
                vendas.setStatus("Pendente - Parcial");
            } else if (vendas.getValorPago() > vendas.getValorTotalVenda()){
                vendas.setStatus("ERRO! Pago a mais");
            }
            else {
                vendas.setStatus("Pendente");
            }

            if (valorPago > 0) {
                PagamentosVendaDTO pagamentosDTO = new PagamentosVendaDTO(
                        pagamentoId, vendas.getId(), valorPago, dataHoraAtual, emailUsuario
                );

                pagamentosVendaService.create(pagamentosDTO);
            }

            vendasRepository.save(vendas);

            SaldoDevedor(vendas.getClienteId());
        }
    }

    @Override
    @Transactional
    public void SaldoDevedor(Long idCliente){
        List<Vendas> vendas = vendasRepository.findByClienteId(idCliente);
        Optional<Clientes> clienteOptional = clientesRepository.findById(idCliente);

        Double totalValorRestante = 0d;

        for (Vendas venda : vendas) {
            totalValorRestante += venda.getValorPendente();
        }

        // Atualiza o saldo devedor do cliente, se ele for encontrado
        if (clienteOptional.isPresent()) {
            Clientes cliente = clienteOptional.get();
            cliente.setSaldoDevedor(totalValorRestante);
            clientesRepository.save(cliente);
        } else {
            System.out.println("Cliente não encontrado!");
        }

    }

    @Override
    public List<VendasDTO> listar() {
        List<Vendas> allVendas = vendasRepository.findAll();
        return allVendas.stream()

                .map(venda -> {
                    // Obtenha o nome do cliente de outra fonte
                    String nomeCliente = clientesRepository.findById(venda.getClienteId())
                            .map(Clientes::getNome) // Caso o cliente seja encontrado
                            .orElse("Cliente Desconhecido"); // Valor padrão caso não seja encontrado

                    String nomeVendedor = vendedoresRepository.findById(venda.getVendedorId())
                            .map(Vendedores::getNome) // Caso o cliente seja encontrado
                            .orElse("Vendedor Desconhecido"); // Valor padrão caso não seja encontrado

                    // Passe o nomeCliente diretamente no construtor do DTO
                    return new VendasDTO(
                            venda.getId(), venda.getClienteId(), venda.getVendedorId(), venda.getValorTotalVenda(),
                            venda.getValorPago(), venda.getStatus(), venda.getDataUltimoPagamento(),
                            venda.getDataCadastro(), venda.getDataVenda(), venda.getCriadoPor(),
                            venda.getAtualizadoPor(), venda.getValorPendente(),
                            venda.getObservacao(), nomeCliente, nomeVendedor
                    );
                })
                .collect(Collectors.toList());
    }

}
