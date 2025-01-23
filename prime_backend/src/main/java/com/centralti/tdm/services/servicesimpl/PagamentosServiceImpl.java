package com.centralti.tdm.services.servicesimpl;

import com.centralti.tdm.domain.usuarios.DTO.PagamentosDTO;
import com.centralti.tdm.domain.usuarios.DTO.PagamentosVendaDTO;
import com.centralti.tdm.domain.usuarios.entidades.*;
import com.centralti.tdm.domain.usuarios.repositories.PagamentosRepository;
import com.centralti.tdm.domain.usuarios.repositories.PagamentosVendaRepository;
import com.centralti.tdm.domain.usuarios.repositories.VendasRepository;
import com.centralti.tdm.services.servicesinterface.PagamentosService;
import com.centralti.tdm.services.servicesinterface.PagamentosVendaService;
import com.centralti.tdm.services.servicesinterface.VendasService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public Long create(PagamentosDTO pagamentosDTO) {

        Pagamentos pagamentos = new Pagamentos(pagamentosDTO);

        Pagamentos pagamento = pagamentosRepository.save(pagamentos);

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

        pagamentosRepository.delete(pagamentos);

    }

}
