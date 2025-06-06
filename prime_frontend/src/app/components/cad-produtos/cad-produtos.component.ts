import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { NbSidebarService, NbToastrService } from '@nebular/theme';
import { DatePipe } from '@angular/common';
import { Produtos } from 'src/app/interface/produtos';
import { ProdutosService } from 'src/app/services/produtos/produtos.service';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-cad-produtos',
  templateUrl: './cad-produtos.component.html',
  styleUrls: ['./cad-produtos.component.scss'],
  providers: [DatePipe]
})
export class CadProdutosComponent {

  produtos: Produtos = {
    id: 0,
    nome: '',
    dataCadastro: new Date(),
  }

  telefoneMask = ['(', /[1-9]/, /\d/, ')', ' ', /\d/, ' ', /\d/, /\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/, /\d/];

  toggle() {
    this.sidebarService.toggle();
  }

  constructor(
    private produtosService: ProdutosService,
    private datePipe: DatePipe,
    private toastrService: NbToastrService, private router: Router,
    private sidebarService: NbSidebarService,
    private dialog: MatDialog
  ) {
    // this.toggle()
  }

  create() {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '300px',
      data: {
        title: 'Confirmar cadastro',
        message: 'Tem certeza que deseja cadastrar este produto?'
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.produtosService.cadastrar(this.produtos).subscribe(
          response => {
            this.toastrService.success("Produto cadastrado com sucesso!", "Sucesso");
            setTimeout(() => {
              this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
                this.router.navigate(['/home']);
              });
            }, 1000);

          },
          (error) => {
            console.log('Erro ao cadastrar produto:', error);
            if (error.status === 403) {
              setTimeout(() => {
                location.reload(); // Recarrega a página após 2 segundos
              }, 2000);
            } else {
              this.toastrService.danger('Erro ao cadastrar produto.', 'Erro');
            }

          }
        )

      }
    })
  }
}
