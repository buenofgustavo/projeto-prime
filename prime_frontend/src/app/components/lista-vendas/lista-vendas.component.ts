import { Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { NbSidebarService, NbToastrService } from '@nebular/theme';
import { VendasComProdutosDTO } from 'src/app/interface/vendasComProdutos';
import { VendasService } from 'src/app/services/vendas/vendas.service';
import { ModalVendaComponent } from './modal-venda/modal-venda.component';

@Component({
  selector: 'app-lista-vendas',
  templateUrl: './lista-vendas.component.html',
  styleUrls: ['./lista-vendas.component.scss']
})
export class ListaVendasComponent {
  vendasComProdutosDTO: VendasComProdutosDTO[] = [];


  dataSource = new MatTableDataSource<VendasComProdutosDTO>(this.vendasComProdutosDTO);
  displayedColumns: string[] = ['nomeCliente', 'data', 'valorPendente', 'acao'];


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
    private router: Router, private vendasService: VendasService,
    private sidebarService: NbSidebarService
  ) {
    this.getVendas();
    this.toggle()
  }
  
  openDialogVisualizar(vendasComProdutosDTO: VendasComProdutosDTO) {
    const dialogRef = this.dialog.open(ModalVendaComponent, { data: { vendasComProdutosDTO: vendasComProdutosDTO, isEdit: false } });
    dialogRef.afterClosed().subscribe(result => {
    });
  }

  openDialogEditar(vendasComProdutosDTO: VendasComProdutosDTO) {
    const dialogRef = this.dialog.open(ModalVendaComponent, { data: { vendasComProdutosDTO: vendasComProdutosDTO, isEdit: true } });
    dialogRef.afterClosed().subscribe(result => {
    });
  }

  loading: boolean = true;
  pagos: boolean = false;
  getVendas() {
    this.loading = true;
    this.vendasService.getVendas().subscribe(
      (data: VendasComProdutosDTO[] | null) => {
        try {
          if (data) {
            this.vendasComProdutosDTO = data
            this.vendasComProdutosDTO.reverse();
            if (this.pagos === true) {
              localStorage.setItem('pagos-vendas', this.pagos.toString());

              this.vendasComProdutosDTO = data.filter(vendas => vendas.vendasDTO.status === "Pago");
            } else {
              localStorage.removeItem('pagos-vendas');
              this.vendasComProdutosDTO = data.filter(vendas => vendas.vendasDTO.status === "Pendente" || vendas.vendasDTO.status === "Pendente - Parcial");
            }

            this.dataSource.data = this.vendasComProdutosDTO;
          } else {
            throw new Error('Array de vendas é nulo.');
          }
        } catch (error) {
          console.log('Erro ao filtrar vendas:', error);
          this.toastrService.danger('Erro ao filtrar vendas.', 'Erro');
        } finally {
          this.loading = false; // Finaliza o estado de carregamento após tentar obter e filtrar os dados
        }
      },
      (error) => {
        console.log('Erro ao obter vendas:', error);
        if (error.status === 403) {
          setTimeout(() => {
            location.reload(); // Recarrega a página após1 segundos
          }, 2000);
        } else {
          this.toastrService.danger('Erro ao obter vendas.', 'Erro');
        }
        this.loading = false; // Finaliza o estado de carregamento em caso de erro
      }
    );
  }



  selectedFilter: string = '';
  filterValue: string = '';

  ngOnInit() {
    // Recupera os valores do filtro do localStorage
    const storedSelectedFilter = localStorage.getItem('selectedFilter-vendas');
    const storedFilterValue = localStorage.getItem('filterValue-vendas');
    const storedPagos = localStorage.getItem('pagos-vendas');

    if (storedSelectedFilter) {
      this.selectedFilter = storedSelectedFilter;
    }

    if (storedFilterValue) {
      this.filterValue = storedFilterValue;
      this.applyFilterWithValue(storedFilterValue);
    }

    if (storedPagos !== null) {
      this.pagos = storedPagos === 'true';
    }
  }

  applyFilter(event: any) {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
    this.applyFilterWithValue(filterValue);

    // Salva os valores no localStorage
    localStorage.setItem('selectedFilter-vendas', this.selectedFilter!);
    localStorage.setItem('filterValue-vendas', filterValue);
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
      case 'cliente':
        return this.normalizeString(data.vendasDTO.nomeCliente.toLowerCase());
      case 'data':
        return this.normalizeString(data.vendasDTO.dataCadastro);
      default:
        return ''; // Retorna uma string vazia se nenhum campo for selecionado
    }
  }

  normalizeString(str: string): string {
    return str.normalize("NFD").replace(/[\u0300-\u036f]/g, "");
  }


}