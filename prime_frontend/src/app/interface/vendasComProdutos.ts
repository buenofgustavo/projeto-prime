import { ProdutosVendidos } from "./produtosVendidos";
import { Vendas } from "./vendas";

export interface VendasComProdutosDTO {
    vendasDTO: Vendas;
    produtosVendidosDTO: ProdutosVendidos[];
}