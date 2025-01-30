package com.centralti.tdm.services.servicesimpl;

import com.centralti.tdm.domain.usuarios.DTO.LogSistemaDTO;
import com.centralti.tdm.domain.usuarios.DTO.PagamentosDTO;
import com.centralti.tdm.domain.usuarios.entidades.*;
import com.centralti.tdm.domain.usuarios.repositories.ClientesRepository;
import com.centralti.tdm.domain.usuarios.repositories.PagamentosRepository;
import com.centralti.tdm.domain.usuarios.repositories.PagamentosVendaRepository;
import com.centralti.tdm.domain.usuarios.repositories.VendasRepository;
import com.centralti.tdm.services.servicesinterface.LogSistemaService;
import com.centralti.tdm.services.servicesinterface.PagamentosService;
import com.centralti.tdm.services.servicesinterface.VendasService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PagamentosServiceImpl implements PagamentosService {

    @Autowired
    PagamentosRepository pagamentosRepository;

    @Autowired
    PagamentosVendaRepository pagamentosVendaRepository;

    @Autowired
    VendasRepository vendasRepository;

    @Autowired
    VendasService vendasService;

    @Autowired
    LogSistemaService logSistemaService;

    @Autowired
    ClientesRepository clientesRepository;

    @Override
    public Long create(PagamentosDTO pagamentosDTO) {
        String emailUsuario = SecurityContextHolder.getContext().getAuthentication().getName();

        Pagamentos pagamentos = new Pagamentos(pagamentosDTO);

        Pagamentos pagamento = pagamentosRepository.save(pagamentos);

        Optional<Clientes> clientesOptional = clientesRepository.findById(pagamentos.getClienteId());

        if(clientesOptional.isPresent()){
            Clientes clientes = clientesOptional.get();

            String mensagem;
            mensagem = "Pagamento efetuado para o cliente " + clientes.getNome() + " no valor de " + pagamentos.getValorPago() + ".";

            LogSistemaDTO logSistemaDTO = new LogSistemaDTO(null, mensagem, emailUsuario, pagamento.getClienteId(), null, "Cadastro de Pagamento", null, null);
            logSistemaService.create(logSistemaDTO);
        }

        return pagamento.getId();
    }

    @Override
    public List<PagamentosDTO> listar() {
        List<Pagamentos> allPagamentos = pagamentosRepository.findAll();
        return allPagamentos.stream()
                .map(PagamentosDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        String emailUsuario = SecurityContextHolder.getContext().getAuthentication().getName();

        Pagamentos pagamentos = pagamentosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado com o ID: " + id));

        List<PagamentosVenda> listPagamentosVenda = pagamentosVendaRepository.findPagamentosVendaByPagamentoId(id);
        for (PagamentosVenda pagamentoVenda : listPagamentosVenda) {
            Double valorFinal;
            Double valorPendenteFinal;
            Vendas venda;
            Optional<Vendas> vendasOptional = vendasRepository.findById(pagamentoVenda.getVendaId());
            if (vendasOptional.isPresent()) {
                venda = vendasOptional.get();

                valorFinal = venda.getValorPago() - pagamentoVenda.getValorPago();
                venda.setValorPago(valorFinal);

                valorPendenteFinal = venda.getValorTotalVenda() - venda.getValorPago();
                venda.setValorPendente(valorPendenteFinal);

                if (valorPendenteFinal == 0d){
                    venda.setStatus("Pago");
                } else if (valorPendenteFinal > 0) {
                    venda.setStatus("Pendente - Parcial");
                } else {
                    venda.setStatus("Pendente");
                }

                vendasRepository.save(venda);

                vendasService.SaldoDevedor(venda.getClienteId());

                pagamentosVendaRepository.delete(pagamentoVenda);
            }
            else {
                throw new EntityNotFoundException("Venda não encontrada com o ID: " + pagamentoVenda.getVendaId());
            }

        }

        Optional<Clientes> clientesOptional = clientesRepository.findById(pagamentos.getClienteId());

        if(clientesOptional.isPresent()){
            Clientes clientes = clientesOptional.get();

            String mensagem;
            mensagem = "Pagamento deletado do cliente " + clientes.getNome() + " no valor de " + pagamentos.getValorPago() + ".";

            LogSistemaDTO logSistemaDTO = new LogSistemaDTO(null, mensagem, emailUsuario, pagamentos.getClienteId(), null, "Exclusão de Pagamento",null, null);
            logSistemaService.create(logSistemaDTO);
        }


        pagamentosRepository.delete(pagamentos);

    }

}
