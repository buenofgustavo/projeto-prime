import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { NbSidebarService, NbToastrService } from '@nebular/theme';
import { Clientes } from 'src/app/interface/clientes';
import { ClientesService } from 'src/app/services/clientes/clientes.service';
import { DatePipe } from '@angular/common';
import { Cidades } from 'src/app/interface/cidades';
import { CidadesService } from 'src/app/services/cidades/cidades.service';

@Component({
  selector: 'app-cad-cidades',
  templateUrl: './cad-cidades.component.html',
  styleUrls: ['./cad-cidades.component.scss'],
  providers: [DatePipe]
})
export class CadCidadesComponent {

  cidades: Cidades = {
    id: 0,
    nome: '',
    dataCadastro: new Date(),
  }

  telefoneMask = ['(', /[1-9]/, /\d/, ')', ' ', /\d/, ' ', /\d/, /\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/, /\d/];

  toggle() {
    this.sidebarService.toggle();
  }

  constructor(
    private cidadesService: CidadesService,
    private datePipe: DatePipe,
    private toastrService: NbToastrService, private router: Router,
    private sidebarService: NbSidebarService
  ) {
    this.toggle()
  }

  create() {
    this.cidadesService.cadastrar(this.cidades).subscribe(
      response => {
        this.toastrService.success("Cidade cadastrado com sucesso!", "Sucesso");
        setTimeout(() => {
          this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
            this.router.navigate(['/cad-cidades']);
          });
        }, 1000);

      },
      (error) => {
        console.log('Erro ao cadastrar cidade:', error);
        if (error.status === 403) {
          setTimeout(() => {
            location.reload(); // Recarrega a página após 2 segundos
          }, 2000);
        } else {
          this.toastrService.danger('Erro ao cadastrar cidade.', 'Erro');
        }

      }
    )

  }

}
