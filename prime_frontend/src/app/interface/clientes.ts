export interface Clientes {
  id?: number;
  nome: string;
  categoria: string;
  responsavel: string;
  contato: string;
  cidade: string;
  dataCadastro: Date;
  criadoPor?: string;
  saldoDevedor: number;
}