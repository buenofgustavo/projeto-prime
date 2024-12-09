import { Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { NbSidebarService, NbToastrService } from '@nebular/theme';
import { Clientes } from 'src/app/interface/clientes';
import { VendasComProdutosDTO } from 'src/app/interface/vendasComProdutos';
import { ClientesService } from 'src/app/services/clientes/clientes.service';
import { VendasService } from 'src/app/services/vendas/vendas.service';
import { ModalClienteComponent } from './modal-cliente/modal-cliente.component';

@Component({
  selector: 'app-lista-clientes',
  templateUrl: './lista-clientes.component.html',
  styleUrls: ['./lista-clientes.component.scss']
})
export class ListaClientesComponent {

  clientes: Clientes[] = [];


  dataSource = new MatTableDataSource<Clientes>(this.clientes);
  displayedColumns: string[] = ['nome', 'contato', 'cidade', 'saldoDevedor', 'acao'];


  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  toggle() {
    this.sidebarService.toggle();
  }

  constructor(public dialog: MatDialog, private toastrService: NbToastrService,
    private router: Router, private clientesService: ClientesService,
    private sidebarService: NbSidebarService
  ) {
    this.getClientes();
    this.toggle()
  }

  openDialogVisualizar(clientes: Clientes) {
    const dialogRef = this.dialog.open(ModalClienteComponent, { data: { clientes: clientes, acao: 'visualizar' } });
    dialogRef.afterClosed().subscribe(result => {
    });
  }

  openDialogEditar(clientes: Clientes) {
    const dialogRef = this.dialog.open(ModalClienteComponent, { data: { clientes: clientes, acao: 'editar' } });
    dialogRef.afterClosed().subscribe(result => {
    });
  }
  
  openDialogPagar(clientes: Clientes) {
    const dialogRef = this.dialog.open(ModalClienteComponent, { data: { clientes: clientes, acao: 'pagar' } });
    dialogRef.afterClosed().subscribe(result => {
    });
  }

  loading: boolean = true;
  devedores: boolean = false;
  getClientes() {
    this.loading = true;
    this.clientesService.getClientes().subscribe(
      (data: Clientes[] | null) => {
        try {
          if (data) {

            this.clientes = data

            // Ordena os clientes por saldoDevedor (do maior para o menor)
            this.clientes.sort((a, b) => b.saldoDevedor - a.saldoDevedor);

            if (this.devedores === true) {
              localStorage.setItem('devedores-clientes', this.devedores.toString());

              this.clientes = data.filter(clientes => clientes.saldoDevedor > 0);
            } else {
              localStorage.removeItem('devedores-clientes');
              this.clientes = data;
            }

            this.dataSource.data = this.clientes;

          } else {
            throw new Error('Array de clientes é nulo.');
          }
        } catch (error) {
          console.log('Erro ao filtrar clientes:', error);
          this.toastrService.danger('Erro ao filtrar clientes.', 'Erro');
        } finally {
          this.loading = false; // Finaliza o estado de carregamento após tentar obter e filtrar os dados
        }
      },
      (error) => {
        console.log('Erro ao obter clientes:', error);
        if (error.status === 403) {
          setTimeout(() => {
            location.reload(); // Recarrega a página após1 segundos
          }, 2000);
        } else {
          this.toastrService.danger('Erro ao obter clientes.', 'Erro');
        }
        this.loading = false; // Finaliza o estado de carregamento em caso de erro
      }
    );
  }

  selectedFilter: string = '';
  filterValue: string = '';

  ngOnInit() {
    // Recupera os valores do filtro do localStorage
    const storedSelectedFilter = localStorage.getItem('selectedFilter-clientes');
    const storedFilterValue = localStorage.getItem('filterValue-clientes');
    const storeddevedores = localStorage.getItem('devedores-clientes');

    if (storedSelectedFilter) {
      this.selectedFilter = storedSelectedFilter;
    }

    if (storedFilterValue) {
      this.filterValue = storedFilterValue;
      this.applyFilterWithValue(storedFilterValue);
    }

    if (storeddevedores !== null) {
      this.devedores = storeddevedores === 'true';
    }
  }

  applyFilter(event: any) {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
    this.applyFilterWithValue(filterValue);

    // Salva os valores no localStorage
    localStorage.setItem('selectedFilter-clientes', this.selectedFilter!);
    localStorage.setItem('filterValue-clientes', filterValue);
  }

  applyFilterWithValue(filterValue: string) {
    const normalizedFilterValue = this.normalizeString(filterValue.toLowerCase());

    // Verifica se há um filtro selecionado
    if (this.selectedFilter) {
      // Aplica o filtro no campo selecionado
      this.dataSource.filter = normalizedFilterValue;
      this.dataSource.filterPredicate = (data: any, filter: string) => {
        const normalizedDataValue = this.getNormalizedFieldValue(data);
        return normalizedDataValue.includes(filter);
      };
    } else {
      // Se nenhum filtro estiver selecionado, limpa o filtro
      this.dataSource.filter = '';
    }
  }

  getNormalizedFieldValue(data: any): string {
    switch (this.selectedFilter) {
      case 'nome':
        return this.normalizeString(data.nome.toLowerCase());
      case 'cidade':
        return this.normalizeString(data.cidade.toLowerCase());
      case 'responsavel':
        return this.normalizeString(data.responsavel.toLowerCase());
      default:
        return ''; // Retorna uma string vazia se nenhum campo for selecionado
    }
  }

  normalizeString(str: string): string {
    return str.normalize("NFD").replace(/[\u0300-\u036f]/g, "");
  }

}
