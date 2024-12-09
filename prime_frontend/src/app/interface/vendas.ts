export interface Vendas {
  id?: number;
  clienteId: number;
  nomeCliente: string;
  motorista: string,
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
}