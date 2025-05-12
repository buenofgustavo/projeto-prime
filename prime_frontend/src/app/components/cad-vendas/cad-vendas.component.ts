import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { NbSidebarService, NbToastrService } from '@nebular/theme';
import { map, Observable, startWith } from 'rxjs';
import { Clientes } from 'src/app/interface/clientes';
import { Produtos } from 'src/app/interface/produtos';
import { ProdutosVendidos } from 'src/app/interface/produtosVendidos';
import { VendasComProdutosDTO } from 'src/app/interface/vendasComProdutos';
import { Vendedores } from 'src/app/interface/vendedores';
import { ClientesService } from 'src/app/services/clientes/clientes.service';
import { ProdutosService } from 'src/app/services/produtos/produtos.service';
import { VendasService } from 'src/app/services/vendas/vendas.service';
import { VendedoresService } from 'src/app/services/vendedores/vendedores.service';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
    selector: 'app-cad-vendas',
    templateUrl: './cad-vendas.component.html',
    styleUrls: ['./cad-vendas.component.scss']
})
export class CadVendasComponent implements OnInit {

    vendas: VendasComProdutosDTO = {
        vendasDTO: {
            id: 0,
            clienteId: 0,
            vendedorId: 0,
            valorTotalVenda: 0,
            valorPago: 0,
            status: '',
            dataVenda: new Date(),
            valorPendente: 0,
            observacao: '',
            nomeCliente: '',
            nomeVendedor: ''
        },
        produtosVendidosDTO: []
    }

    produto: ProdutosVendidos = {
        vendaId: 0,
        produtoId: 0,
        quantidade: 0,
        valorUnitario: 0,
        valorTotalProduto: 0,
        nomeProduto: ""
    }

    produtosOpcoes = [
        { valor: 'Oxigênio 10m', descricao: 'Oxigênio 10m' },
        { valor: 'Oxigênio 7m', descricao: 'Oxigênio 7m' },
        { valor: 'Gás Mig 10m', descricao: 'Gás Mig 10m' },
        { valor: 'Gás Mig 7m', descricao: 'Gás Mig 7m' },
        { valor: 'Argônio 10m', descricao: 'Argônio 10m' },
        { valor: 'Argônio 7m', descricao: 'Argônio 7m' },
        { valor: 'Outros', descricao: 'Outros' },
    ];

    ngOnInit() {
        this.getClientes();
        this.getProdutos();
        this.getVendedores();
    }

    toggle() {
        this.sidebarService.toggle();
    }

    inputFormControl: FormControl;
    filteredClientes$: Observable<Clientes[]>;
    constructor(
        private vendasService: VendasService,
        private clientesService: ClientesService,
        private vendedoresService: VendedoresService,
        private produtosService: ProdutosService,
        private toastrService: NbToastrService, private router: Router,
        private sidebarService: NbSidebarService,
        private dialog: MatDialog
    ) {
        this.inputFormControl = new FormControl();
        this.filteredClientes$ = this.inputFormControl.valueChanges.pipe(
            startWith(''),
            map(value => this.filterClientes(value))
        );
        // this.toggle()
    }

    clientes: Clientes[] = [];
    getClientes(): void {
        this.clientesService.getClientes().subscribe(clientes => {
            this.clientes = clientes;
        });
    }

    produtos: Produtos[] = [];
    getProdutos(): void {
        this.produtosService.get().subscribe(produtos => {
            this.produtos = produtos;
        });
    }

    vendedores: Vendedores[] = [];
    getVendedores(): void {
        this.vendedoresService.get().subscribe(vendedores => {
            this.vendedores = vendedores;
        });
    }

    private filterClientes(value: string): Clientes[] {
        const filterValue = value.toLowerCase();

        return this.clientes.filter(cliente => cliente.nome.toLowerCase().includes(filterValue));
    }

    setClienteId(clienteId: any) {
        this.vendas.vendasDTO.clienteId = clienteId;
    }

    atualizarValorTotalProduto() {
        if (this.produto.quantidade && this.produto.valorUnitario) {
            this.produto.valorTotalProduto = this.produto.quantidade * this.produto.valorUnitario;
        } else {
            this.produto.valorTotalProduto = 0; // Garantir que o valor total seja 0 se faltar algum valor
        }
    }

    totalVenda: number = 0
    addProduto() {
        if (this.produto.produtoId == 0 || this.produto.quantidade < 1 || this.produto.valorUnitario < 1) {
            this.toastrService.danger(`Insira valores válidos`, "Danger");
        } else {
            const novoProduto = { ...this.produto }; // Cria uma cópia dos dados do produto
            novoProduto.valorTotalProduto = novoProduto.quantidade * novoProduto.valorUnitario; // Calcula o valor total do produto
            this.vendas.produtosVendidosDTO.push(novoProduto); // Adiciona ao array de produtos
            this.totalVenda += novoProduto.valorTotalProduto
            // Limpa os campos de produto após adicionar
            this.produto = {
                vendaId: 0,
                produtoId: 0,
                quantidade: 0,
                valorUnitario: 0,
                valorTotalProduto: 0,
                nomeProduto: ""
            };
        }
    }

    removeProduto() {
        const ultimoProduto = this.vendas.produtosVendidosDTO.pop();
      
        if (ultimoProduto) {
          this.totalVenda -= ultimoProduto.valorTotalProduto;
        }
    }

    create() {
        const dialogRef = this.dialog.open(ConfirmDialogComponent, {
            width: '300px',
            data: {
                title: 'Confirmar cadastro',
                message: 'Tem certeza que deseja cadastrar esta venda?'
            }
        });
        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                console.log(this.vendas)
                if (this.vendas.vendasDTO.valorPago > this.totalVenda) {
                    this.toastrService.danger(`Valor pago deve ser menor que R$${this.totalVenda}`, "Danger");
                } else {

                    this.vendasService.cadastrarVendas(this.vendas).subscribe(
                        response => {
                            this.toastrService.success("Venda cadastrada com sucesso!", "Sucesso");
                            setTimeout(() => {
                                this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
                                    this.router.navigate(['/home']); // Navega para a rota de cadastro de armazém
                                });
                            }, 1000);

                        },
                        (error) => {
                            console.log('Erro ao cadastrar venda:', error);
                            if (error.status === 403) {
                                setTimeout(() => {
                                    location.reload(); // Recarrega a página após1 segundos
                                }, 2000);
                            } else {
                                this.toastrService.danger('Erro ao cadastrar venda.', 'Erro');
                            }

                        }
                    )
                }
            }
        })
    }
}
