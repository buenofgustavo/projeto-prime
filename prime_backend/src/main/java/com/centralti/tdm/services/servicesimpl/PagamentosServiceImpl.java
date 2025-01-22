package com.centralti.tdm.services.servicesimpl;

import com.centralti.tdm.domain.usuarios.DTO.PagamentosDTO;
import com.centralti.tdm.domain.usuarios.DTO.PagamentosVendaDTO;
import com.centralti.tdm.domain.usuarios.entidades.Pagamentos;
import com.centralti.tdm.domain.usuarios.entidades.PagamentosVenda;
import com.centralti.tdm.domain.usuarios.repositories.PagamentosRepository;
import com.centralti.tdm.domain.usuarios.repositories.PagamentosVendaRepository;
import com.centralti.tdm.services.servicesinterface.PagamentosService;
import com.centralti.tdm.services.servicesinterface.PagamentosVendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagamentosServiceImpl implements PagamentosService {

    @Autowired
    PagamentosRepository pagamentosRepository;

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

}
