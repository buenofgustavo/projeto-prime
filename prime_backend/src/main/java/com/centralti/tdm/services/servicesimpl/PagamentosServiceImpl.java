package com.centralti.tdm.services.servicesimpl;

import com.centralti.tdm.domain.usuarios.DTO.PagamentosDTO;
import com.centralti.tdm.domain.usuarios.entidades.Pagamentos;
import com.centralti.tdm.domain.usuarios.repositories.PagamentosRepository;
import com.centralti.tdm.services.servicesinterface.PagamentosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagamentosServiceImpl implements PagamentosService {

    @Autowired
    PagamentosRepository pagamentosRepository;

    @Override
    public void create(PagamentosDTO pagamentosDTO) {

        Pagamentos pagamentos = new Pagamentos(pagamentosDTO);

        pagamentosRepository.save(pagamentos);

    }

    @Override
    public List<PagamentosDTO> listar() {
        List<Pagamentos> allPagamentos = pagamentosRepository.findAll();
        return allPagamentos.stream()
                .map(PagamentosDTO::new)
                .collect(Collectors.toList());
    }

}
