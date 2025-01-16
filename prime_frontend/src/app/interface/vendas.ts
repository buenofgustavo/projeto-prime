export interface Vendas {
  id?: number;
  clienteId: number;
  vendedorId: number,
  valorTotalVenda: number;
  valorPago: number;
  status: string;
  dataUltimoPagamento?: Date;
  dataCadastro?: Date;
  dataVenda: Date;
  criadoPor?: string;
  atualizadoPor?: string;
  valorPendente: number;
  observacao: string;
  nomeCliente: string;
  nomeVendedor: string;
}