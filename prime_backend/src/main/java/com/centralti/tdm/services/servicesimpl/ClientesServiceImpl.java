package com.centralti.tdm.services.servicesimpl;

import com.centralti.tdm.domain.usuarios.DTO.*;
import com.centralti.tdm.domain.usuarios.entidades.Categorias;
import com.centralti.tdm.domain.usuarios.entidades.Cidades;
import com.centralti.tdm.domain.usuarios.entidades.Clientes;
import com.centralti.tdm.domain.usuarios.entidades.Vendas;
import com.centralti.tdm.domain.usuarios.repositories.*;
import com.centralti.tdm.services.servicesinterface.ClientesService;
import com.centralti.tdm.services.servicesinterface.LogSistemaService;
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
public class ClientesServiceImpl implements ClientesService {

    @Autowired
    ClientesRepository clientesRepository;

    @Autowired
    VendasRepository vendasRepository;

    @Autowired
    CidadesRepository cidadesRepository;

    @Autowired
    CategoriasRepository categoriasRepository;

    @Autowired
    PagamentosService pagamentosService;

    @Autowired
    VendasService vendasService;

    @Autowired
    LogSistemaService logSistemaService;

    @Override
    public void create(ClientesDTO clientesDTO) {
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        String emailUsuario = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Clientes> optionalClientes = clientesRepository.findById(clientesDTO.id());
        Clientes clientes;

        Optional<Cidades> optionalCidades = cidadesRepository.findById(clientesDTO.cidadeId());
        Optional<Categorias> optionalCategorias = categoriasRepository.findById(clientesDTO.categoriaId());
        if(optionalCidades.isPresent() && optionalCategorias.isPresent()) {
            if (optionalClientes.isPresent()) {
                clientes = optionalClientes.get();
                clientes.setCidadeId(clientesDTO.cidadeId());
                clientes.setContato(clientesDTO.contato());
                clientes.setNome(clientesDTO.nome());
                clientes.setCategoriaId(clientesDTO.categoriaId());
                clientes.setResponsavel(clientesDTO.responsavel());
            } else {
                clientes = new Clientes(clientesDTO);
                clientes.setDataCadastro(dataHoraAtual);
                clientes.setCriadoPor(emailUsuario);
            }
            clientesRepository.save(clientes);
        } else {
            throw new EntityNotFoundException("Cidade ou categoria não encontrada");
        }

    }

    @Override
    public void pagarSaldoDevedor(Long id, Double valorPago) {
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        String emailUsuario = SecurityContextHolder.getContext().getAuthentication().getName();

        String mensagem;


        Optional<Clientes> clienteOptional = clientesRepository.findById(id);
        Long pagamentoId = 0L;

        if (clienteOptional.isPresent()) {
            Clientes cliente = clienteOptional.get();

            if (valorPago > 0) {
                PagamentosDTO pagamentosDTO = new PagamentosDTO(
                       cliente.getId() , valorPago, dataHoraAtual, emailUsuario
                );
                pagamentoId = pagamentosService.create(pagamentosDTO);
            }

            List<Vendas> vendas = vendasRepository.findByClienteIdOrderByDataVendaAsc(cliente.getId());

            for (Vendas venda : vendas) {

                if (valorPago == 0){
                    break;
                }

                VendasDTO vendasDTO = new VendasDTO(venda);
                double valorPendente = vendasDTO.valorPendente();

                if(valorPago >= valorPendente) {
                    valorPago -= vendasDTO.valorPendente();
                    vendasService.pagarSaldoDevedor(vendasDTO, vendasDTO.valorPendente(), pagamentoId);
                } else {
                    vendasService.pagarSaldoDevedor(vendasDTO, valorPago, pagamentoId);
                    valorPago = 0d;
                }

            }

        } else {
            System.out.println("Cliente não encontrado");
        }
    }

    @Override
    public List<ClientesDTO> listar() {
        List<Clientes> allClientes = clientesRepository.findAll();
        return allClientes.stream()

                .map(cliente -> {
                    String nomeCidade = cidadesRepository.findById(cliente.getCidadeId())
                            .map(Cidades::getNome) // Caso a cidade seja encontrado
                            .orElse("Cidade Desconhecida"); // Valor padrão caso não seja encontrado

                    String nomeCategoria = categoriasRepository.findById(cliente.getCategoriaId())
                            .map(Categorias::getNome) // Caso a cidade seja encontrado
                            .orElse("Categoria Desconhecida"); // Valor padrão caso não seja encontrado

                    return new ClientesDTO(
                            cliente.getId(), cliente.getNome(), cliente.getCategoriaId(), cliente.getResponsavel(),
                            cliente.getContato(), cliente.getCidadeId(), cliente.getDataCadastro(),
                            cliente.getCriadoPor(), cliente.getSaldoDevedor(), nomeCidade, nomeCategoria
                    );
                })

                .collect(Collectors.toList());
    }

}
