import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NbToastrService } from '@nebular/theme';
import { VendasComProdutosDTO } from 'src/app/interface/vendasComProdutos';
import { VendasService } from 'src/app/services/vendas/vendas.service';
import { ConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-modal-venda',
  templateUrl: './modal-venda.component.html',
  styleUrls: ['./modal-venda.component.scss']
})
export class ModalVendaComponent {

  valorAserPago: number = 0

  constructor(private dialogRef: MatDialogRef<ModalVendaComponent>,
    private toastrService: NbToastrService,
    private vendasService: VendasService,
    private router: Router,
    private dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: { vendasComProdutosDTO: VendasComProdutosDTO, isEdit: boolean }) {
    this.valorAserPago = this.data.vendasComProdutosDTO.vendasDTO.valorPago
  }

  atualizar() {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '300px',
      data: {
        title: 'Confirmar cadastro',
        message: 'Tem certeza que deseja atualizar esta venda?'
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.vendasService.cadastrarVendas(this.data.vendasComProdutosDTO).subscribe(
          response => {
            this.toastrService.success("Venda atualizada com sucesso!", "Sucesso");
            setTimeout(() => {
              this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
                this.router.navigate(['/list-vendas']); // Navega para a rota de cadastro de armazém
              });
            }, 1000);

          },
          (error) => {
            console.log('Erro ao atualizar venda:', error);
            if (error.status === 403) {
              setTimeout(() => {
                location.reload(); // Recarrega a página após1 segundos
              }, 2000);
            } else {
              this.toastrService.danger('Erro ao atualizar venda.', 'Erro');
            }

          }
        )
      }
    })
  }


deletar(vendasComProdutosDTO: VendasComProdutosDTO) {
  const dialogRef = this.dialog.open(ConfirmDialogComponent, {
    width: '300px',
    data: {
      title: 'Confirmar cadastro',
      message: 'Tem certeza que deseja excluir esta venda?'
    }
  });
  dialogRef.afterClosed().subscribe(result => {
    if (result) {
      this.vendasService.deleteVendas(vendasComProdutosDTO).subscribe(
        response => {
          this.toastrService.success("Venda deletada com sucesso!", "Sucesso");
          setTimeout(() => {
            this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
              this.router.navigate(['/list-vendas']); // Navega para a rota de cadastro de armazém
            });
          }, 1000);

        },
        (error) => {
          console.log('Erro ao deletar venda:', error);
          if (error.status === 403) {
            setTimeout(() => {
              location.reload(); // Recarrega a página após 2 segundos
            }, 2000);
          } else {
            this.toastrService.danger('Erro ao deletar venda.', 'Erro');
          }
        }
      )
    }
  })
}
}

