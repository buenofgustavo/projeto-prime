package com.centralti.tdm.domain.usuarios.DTO;

import java.util.List;

public record VendasComProdutosDTO
        (
                VendasDTO vendasDTO,
                List<ProdutosVendidosDTO> produtosVendidosDTO

        ) {

}
