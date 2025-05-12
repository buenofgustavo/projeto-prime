import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NbToastrService } from '@nebular/theme';
import { Categorias } from 'src/app/interface/categorias';
import { Cidades } from 'src/app/interface/cidades';
import { Clientes } from 'src/app/interface/clientes';
import { VendasComProdutosDTO } from 'src/app/interface/vendasComProdutos';
import { CategoriasService } from 'src/app/services/categorias/categorias.service';
import { CidadesService } from 'src/app/services/cidades/cidades.service';
import { ClientesService } from 'src/app/services/clientes/clientes.service';
import { VendasService } from 'src/app/services/vendas/vendas.service';
import { ConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-modal-cliente',
  templateUrl: './modal-cliente.component.html',
  styleUrls: ['./modal-cliente.component.scss']
})
export class ModalClienteComponent implements OnInit {

  telefoneMask = ['(', /[1-9]/, /\d/, ')', ' ', /\d/, ' ', /\d/, /\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/, /\d/];

  constructor(private dialogRef: MatDialogRef<ModalClienteComponent>,
    private toastrService: NbToastrService,
    private clientesService: ClientesService,
    private categoriasService: CategoriasService,
    private cidadesService: CidadesService,
    private router: Router,
    private dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: { clientes: Clientes, acao: string }) {
  }

  ngOnInit() {
    this.getCidades();
    this.getCategorias()
  }

  cidades: Cidades[] = [];
  getCidades(): void {
    this.cidadesService.get().subscribe(cidades => {
      this.cidades = cidades;
    });
  }

  categorias: Categorias[] = [];
  getCategorias(): void {
    this.categoriasService.get().subscribe(categorias => {
      this.categorias = categorias;
    });
  }

  valorASerPago: number = 0;

  pagarSaldoDevedor() {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '300px',
      data: {
        title: 'Confirmar cadastro',
        message: 'Tem certeza que deseja cadastrar este pagamento?'
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        if (this.valorASerPago > this.data.clientes.saldoDevedor) {
          this.toastrService.danger(`Valor pago deve ser menor que R$${this.data.clientes.saldoDevedor}`, "Danger");
        }
        else {
          this.clientesService.pagarSaldoDevedor(this.data.clientes.id, this.valorASerPago).subscribe(
            response => {
              this.toastrService.success("Pagamento cadastrado com sucesso!", "Sucesso");
              setTimeout(() => {
                this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
                  this.router.navigate(['/list-clientes']); // Navega para a rota de cadastro de armazém
                });
              }, 1000);

            },
            (error) => {
              console.log('Erro ao cadastrar pagamento:', error);
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

  atualizarCliente() {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '300px',
      data: {
        title: 'Confirmar cadastro',
        message: 'Tem certeza que deseja atualizar este cliente?'
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.clientesService.cadastrarClientes(this.data.clientes).subscribe(
          response => {
            this.toastrService.success("Cliente cadastrado com sucesso!", "Sucesso");
            setTimeout(() => {
              this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
                this.router.navigate(['/list-clientes']); // Navega para a rota de cadastro de armazém
              });
            }, 1000);

          },
          (error) => {
            console.log('Erro ao cadastrar cliente:', error);
            if (error.status === 403) {
              setTimeout(() => {
                location.reload(); // Recarrega a página após1 segundos
              }, 2000);
            } else {
              this.toastrService.danger('Erro ao cadastrar cliente.', 'Erro');
            }

          }
        )
      }
    })
  }

}
