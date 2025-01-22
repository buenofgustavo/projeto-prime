package com.centralti.tdm.services.servicesimpl;

import com.centralti.tdm.domain.usuarios.DTO.PagamentosVendaDTO;
import com.centralti.tdm.domain.usuarios.entidades.PagamentosVenda;
import com.centralti.tdm.domain.usuarios.repositories.PagamentosVendaRepository;
import com.centralti.tdm.services.servicesinterface.PagamentosVendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagamentosVendaServiceImpl implements PagamentosVendaService {

    @Autowired
    PagamentosVendaRepository pagamentosVendaRepository;

    @Override
    public void create(PagamentosVendaDTO pagamentosDTO) {

        PagamentosVenda pagamentosVenda = new PagamentosVenda(pagamentosDTO);

        pagamentosVendaRepository.save(pagamentosVenda);

    }

    @Override
    public List<PagamentosVendaDTO> listar() {
        List<PagamentosVenda> allPagamentosVenda = pagamentosVendaRepository.findAll();
        return allPagamentosVenda.stream()
                .map(PagamentosVendaDTO::new)
                .collect(Collectors.toList());
    }

}
