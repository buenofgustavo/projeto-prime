package com.centralti.tdm.services.servicesimpl;

import com.centralti.tdm.domain.usuarios.DTO.PagamentosDTO;
import com.centralti.tdm.domain.usuarios.DTO.VendasDTO;
import com.centralti.tdm.domain.usuarios.entidades.Clientes;
import com.centralti.tdm.domain.usuarios.entidades.Pagamentos;
import com.centralti.tdm.domain.usuarios.entidades.Vendas;
import com.centralti.tdm.domain.usuarios.repositories.ClientesRepository;
import com.centralti.tdm.domain.usuarios.repositories.PagamentosRepository;
import com.centralti.tdm.domain.usuarios.repositories.ProdutosVendidosRepository;
import com.centralti.tdm.domain.usuarios.repositories.VendasRepository;
import com.centralti.tdm.services.servicesinterface.PagamentosService;
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
public class VendasServiceImpl implements VendasService {

    @Autowired
    VendasRepository vendasRepository;

    @Autowired
    ClientesRepository clientesRepository;

    @Autowired
    PagamentosService pagamentosService;

    @Autowired
    PagamentosRepository pagamentosRepository;

    @Autowired
    ProdutosVendidosRepository produtosVendidosRepository;

    @Override
    public Long create(VendasDTO vendasDTO, Double valorTotalVenda) {
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        String emailUsuario = SecurityContextHolder.getContext().getAuthentication().getName();

        Vendas vendas;

        if (vendasDTO.id() != null && vendasDTO.id() != 0 ) {

            // Buscar a venda existente para atualização
            vendas = vendasRepository.findById(vendasDTO.id())
                    .orElseThrow(() -> new EntityNotFoundException("Venda não encontrada com o ID: " + vendasDTO.id()));
            vendas.setDataVenda(vendasDTO.dataVenda());
            vendas.setValorPago(vendasDTO.valorPago());
            vendas.setAtualizadoPor(emailUsuario);
            vendas.setObservacao(vendas.getObservacao());

        } else {
            // Criar nova venda
            vendas = new Vendas(vendasDTO);

            vendas.setDataCadastro(dataHoraAtual);
            vendas.setCriadoPor(emailUsuario);
        }
        vendas.setValorTotalVenda(valorTotalVenda);
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

        SaldoDevedor(vendas.getClienteId());

        return vendas.getId();
    }

    @Override
    public void pagar(VendasDTO vendasDTO) {
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        String emailUsuario = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Vendas> vendasOptional = vendasRepository.findById(vendasDTO.id());

        if (vendasOptional.isPresent()) {
            Vendas vendas = vendasOptional.get();

            // Pega o valor pago atual e subtrai o valor que já foi pago
            Double valorASerPago = vendasDTO.valorPago() - vendas.getValorPago();

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
                PagamentosDTO pagamentosDTO = new PagamentosDTO(
                        vendas.getId(), valorASerPago, dataHoraAtual, emailUsuario
                );
                pagamentosService.create(pagamentosDTO);

            } else if (vendasDTO.valorPago() < vendas.getValorPago()) {

                // Remover pagamentos associados à venda
                List<Pagamentos> pagamentos = pagamentosRepository.findPagamentosByVendaId(vendas.getId());
                if (pagamentos != null && !pagamentos.isEmpty()) {
                    pagamentosRepository.deleteAll(pagamentos);
                }

                PagamentosDTO pagamentosDTO = new PagamentosDTO(
                        vendas.getId(), vendasDTO.valorPago(), dataHoraAtual, emailUsuario
                );

                pagamentosService.create(pagamentosDTO);
            }


            vendasRepository.save(vendas);



            SaldoDevedor(vendas.getClienteId());
        }
    }
    @Override
    public void pagarSaldoDevedor(VendasDTO vendasDTO, Double valorPago) {
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
                PagamentosDTO pagamentosDTO = new PagamentosDTO(
                        vendas.getId(), valorPago, dataHoraAtual, emailUsuario
                );

                pagamentosService.create(pagamentosDTO);
            }

            vendasRepository.save(vendas);

            SaldoDevedor(vendas.getClienteId());
        }
    }

    @Override
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

                    // Passe o nomeCliente diretamente no construtor do DTO
                    return new VendasDTO(
                            venda.getId(), venda.getClienteId(), venda.getMotorista(), venda.getValorTotalVenda(),
                            venda.getValorPago(), venda.getStatus(), venda.getDataUltimoPagamento(),
                            venda.getDataCadastro(), venda.getDataVenda(), venda.getCriadoPor(),
                            venda.getAtualizadoPor(), venda.getValorPendente(), venda.getObservacao(), nomeCliente
                    );
                })
                .collect(Collectors.toList());
    }

}
