import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { NbSidebarService, NbToastrService } from '@nebular/theme';
import { Clientes } from 'src/app/interface/clientes';
import { ClientesService } from 'src/app/services/clientes/clientes.service';
import { DatePipe } from '@angular/common';
import { Cidades } from 'src/app/interface/cidades';
import { CidadesService } from 'src/app/services/cidades/cidades.service';
import { VendedoresService } from 'src/app/services/vendedores/vendedores.service';
import { Vendedores } from 'src/app/interface/vendedores';
import { Categorias } from 'src/app/interface/categorias';
import { CategoriasService } from 'src/app/services/categorias/categorias.service';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-cad-categorias',
  templateUrl: './cad-categorias.component.html',
  styleUrls: ['./cad-categorias.component.scss'],
  providers: [DatePipe]
})
export class CadCategoriasComponent {

  categorias: Categorias = {
    id: 0,
    nome: '',
    dataCadastro: new Date(),
  }

  telefoneMask = ['(', /[1-9]/, /\d/, ')', ' ', /\d/, ' ', /\d/, /\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/, /\d/];

  toggle() {
    this.sidebarService.toggle();
  }

  constructor(
    private categoriasService: CategoriasService,
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
        message: 'Tem certeza que deseja cadastrar esta categoria?'
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.categoriasService.cadastrar(this.categorias).subscribe(
          response => {
            this.toastrService.success("Categoria cadastrada com sucesso!", "Sucesso");
            setTimeout(() => {
              this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
                this.router.navigate(['/home']);
              });
            }, 1000);
          
          },
          (error) => {
            console.log('Erro ao cadastrar categoria:', error);
            if (error.status === 403) {
              setTimeout(() => {
                location.reload(); // Recarrega a página após 2 segundos
              }, 2000);
            } else {
              this.toastrService.danger('Erro ao cadastrar categoria.', 'Erro');
            }
          }
        )
      }
    })  
  }
}
