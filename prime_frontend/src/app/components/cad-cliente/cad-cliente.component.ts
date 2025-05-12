import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NbSidebarService, NbToastrService } from '@nebular/theme';
import { Clientes } from 'src/app/interface/clientes';
import { ClientesService } from 'src/app/services/clientes/clientes.service';
import { DatePipe } from '@angular/common';
import { Cidades } from 'src/app/interface/cidades';
import { CidadesService } from 'src/app/services/cidades/cidades.service';
import { Categorias } from 'src/app/interface/categorias';
import { CategoriasService } from 'src/app/services/categorias/categorias.service';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-cad-cliente',
  templateUrl: './cad-cliente.component.html',
  styleUrls: ['./cad-cliente.component.scss'],
  providers: [DatePipe]
})
export class CadClienteComponent implements OnInit {

  cliente: Clientes = {
    id: 0,
    nome: '',
    categoriaId: 0,
    responsavel: '',
    contato: '',
    cidadeId: 0,
    dataCadastro: new Date(),
    saldoDevedor: 0,
    nomeCidade: "",
    nomeCategoria: ""
  }

  telefoneMask = ['(', /[1-9]/, /\d/, ')', ' ', /\d/, ' ', /\d/, /\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/, /\d/];

  toggle() {
    this.sidebarService.toggle();
  }

  ngOnInit() {
    this.getCidades();
    this.getCategorias()
  }

  constructor(
    private clientesService: ClientesService,
    private cidadesService: CidadesService,
    private categoriasService: CategoriasService,
    private toastrService: NbToastrService, private router: Router,
    private sidebarService: NbSidebarService,
    private dialog: MatDialog
  ) {
    // this.toggle()
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

  create() {

    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '300px',
      data: {
        title: 'Confirmar cadastro',
        message: 'Tem certeza que deseja cadastrar este cliente?'
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.clientesService.cadastrarClientes(this.cliente).subscribe(
          response => {
            this.toastrService.success("Cliente cadastrado com sucesso!", "Sucesso");
            setTimeout(() => {
              this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
                this.router.navigate(['/home']); // Navega para a rota de cadastro de armazém
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
