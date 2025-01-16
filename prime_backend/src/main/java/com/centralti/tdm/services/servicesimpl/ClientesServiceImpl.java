package com.centralti.tdm.services.servicesimpl;

import com.centralti.tdm.domain.usuarios.DTO.ClientesDTO;
import com.centralti.tdm.domain.usuarios.DTO.VendasDTO;
import com.centralti.tdm.domain.usuarios.entidades.Categorias;
import com.centralti.tdm.domain.usuarios.entidades.Cidades;
import com.centralti.tdm.domain.usuarios.entidades.Clientes;
import com.centralti.tdm.domain.usuarios.entidades.Vendas;
import com.centralti.tdm.domain.usuarios.repositories.CategoriasRepository;
import com.centralti.tdm.domain.usuarios.repositories.CidadesRepository;
import com.centralti.tdm.domain.usuarios.repositories.ClientesRepository;
import com.centralti.tdm.domain.usuarios.repositories.VendasRepository;
import com.centralti.tdm.services.servicesinterface.ClientesService;
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
    VendasService vendasService;

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
        Optional<Clientes> clienteOptional = clientesRepository.findById(id);

        if (clienteOptional.isPresent()) {
            Clientes cliente = clienteOptional.get();
            List<Vendas> vendas = vendasRepository.findByClienteIdOrderByDataVendaAsc(cliente.getId());

            for (Vendas venda : vendas) {

                if (valorPago == 0){
                    break;
                }

                VendasDTO vendasDTO = new VendasDTO(venda);
                double valorPendente = vendasDTO.valorPendente();

                if(valorPago >= valorPendente) {
                    valorPago -= vendasDTO.valorPendente();
                    vendasService.pagarSaldoDevedor(vendasDTO, vendasDTO.valorPendente());
                } else {
                    vendasService.pagarSaldoDevedor(vendasDTO, valorPago);
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
