export interface Clientes {
  id?: number;
  nome: string;
  categoriaId: number;
  responsavel: string;
  contato: string;
  cidadeId: number;
  dataCadastro: Date;
  criadoPor?: string;
  saldoDevedor: number;
  nomeCidade: string;
  nomeCategoria: string;
}