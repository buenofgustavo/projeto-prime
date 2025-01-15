export interface Clientes {
  id?: number;
  nome: string;
  categoria: string;
  responsavel: string;
  contato: string;
  cidadeId: number;
  dataCadastro: Date;
  criadoPor?: string;
  saldoDevedor: number;
  nomeCidade: string;
}