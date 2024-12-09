import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NbMenuItem, NbSidebarService, NbToastrService } from '@nebular/theme';
import { Usuario } from 'src/app/interface/usuario-interface';
import { AuthserviceService } from 'src/app/services/authservice.service';
import { UsuarioService } from 'src/app/services/usuario/usuario.service';


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {

  toggle() {
    this.sidebarService.toggle();
  }

  items: NbMenuItem[] = [

    {
      title: 'Página Inicial',
      icon: 'home-outline',
      link: '/home',
    },
    {
      title: 'Cadastrar Clientes',
      icon: "clipboard-outline",
      link: "/cad-cliente",
      hidden: !this.authService.hasPermission(['ROLE_ADMIN']),
    },
    {
      title: 'Cadastrar Vendas',
      icon: "car-outline",
      link: "/cad-venda",
      hidden: !this.authService.hasPermission(['ROLE_ADMIN']),
    },
    {
      title: 'Lista Vendas',
      icon: "file-text-outline",
      link: "/list-vendas",
      hidden: !this.authService.hasPermission(['ROLE_ADMIN']),
    },
    {
      title: 'Lista Clientes',
      icon: "file-text-outline",
      link: "/list-clientes",
      hidden: !this.authService.hasPermission(['ROLE_ADMIN']),
    },
    {
      title: 'Cadastrar Usuários',
      icon: "person-add-outline",
      link: "/cadastrar-usuario",
      hidden: !this.authService.hasPermission(['ROLE_ADMIN']),
    },
  ];

  items2 = [
    { title: 'Logout', icon: '', link: '/' },
  ];

  ngOnInit() {
    this.loadUser();
  }

  constructor(
    private usuarioService: UsuarioService,
    private toastrService: NbToastrService, private router: Router,
    private authService: AuthserviceService,
    private sidebarService: NbSidebarService

  ) {

  }

  usuario: Usuario = {
    name: '',
    login: '',
    password: '',
    role: '',
  }

  loadUser() {
    this.usuarioService.getUserByEmail().subscribe(
      (user: Usuario) => {
        this.usuario = user; // Armazene os dados do Pessoa na variável local

      },
      (error) => {
        console.error('Erro ao carregar dados do Pessoa:', error);
      }
    );
  }

}