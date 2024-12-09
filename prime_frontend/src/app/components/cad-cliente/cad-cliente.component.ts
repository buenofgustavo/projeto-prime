import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { NbSidebarService, NbToastrService } from '@nebular/theme';
import { Clientes } from 'src/app/interface/clientes';
import { ClientesService } from 'src/app/services/clientes/clientes.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-cad-cliente',
  templateUrl: './cad-cliente.component.html',
  styleUrls: ['./cad-cliente.component.scss'],
  providers: [DatePipe]
})
export class CadClienteComponent {

  cliente: Clientes = {
    id: 0,
    nome: '',
    categoria: '',
    responsavel: '',
    contato: '',
    cidade: '',
    dataCadastro: new Date(),
    saldoDevedor: 0
  }

  telefoneMask = ['(', /[1-9]/, /\d/, ')', ' ', /\d/, ' ', /\d/, /\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/, /\d/];

  toggle() {
    this.sidebarService.toggle();
  }

  constructor(
    private clientesService: ClientesService,
    private datePipe: DatePipe,
    private toastrService: NbToastrService, private router: Router,
    private sidebarService: NbSidebarService
  ) {
    this.toggle()
  }

  create() {
    this.clientesService.cadastrarClientes(this.cliente).subscribe(
      response => {
        this.toastrService.success("Cliente cadastrado com sucesso!", "Sucesso");
        setTimeout(() => {
          this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
            this.router.navigate(['/cad-cliente']); // Navega para a rota de cadastro de armazém
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

}
