
import { LoginComponent } from './components/login/login.component';
import { AuthGuard } from './auth/auth.guard';
import { HomeComponent } from './components/home/home.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NavbarComponent } from './components/navbar/navbar.component';
import { CadastrarUsuarioComponent } from './components/cadastrar-usuario/cadastrar-usuario.component';
import { CadClienteComponent } from './components/cad-cliente/cad-cliente.component';
import { CadVendasComponent } from './components/cad-vendas/cad-vendas.component';
import { ListaVendasComponent } from './components/lista-vendas/lista-vendas.component';
import { ListaClientesComponent } from './components/lista-clientes/lista-clientes.component';
import { CadCidadesComponent } from './components/cad-cidades/cad-cidades.component';
import { CadProdutosComponent } from './components/cad-produtos/cad-produtos.component';
import { CadVendedoresComponent } from './components/cad-vendedores/cad-vendedores.component';
import { CadCategoriasComponent } from './components/cad-categorias/cad-categorias.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: '',
        redirectTo:'login',
        pathMatch: 'prefix'
      },
      {
        path: 'login',
        component: LoginComponent,
      }
    ],
    },
  {
    path: '', component: NavbarComponent,
    canActivate:[AuthGuard],
    children: [
      {path: 'home',component: HomeComponent},
      {path: 'cadastrar-usuario', component: CadastrarUsuarioComponent},
      {path: 'cad-cliente', component: CadClienteComponent},
      {path: 'cad-venda', component: CadVendasComponent},
      {path: 'cad-cidades', component: CadCidadesComponent},
      {path: 'cad-produtos', component: CadProdutosComponent},
      {path: 'cad-vendedores', component: CadVendedoresComponent},
      {path: 'cad-categorias', component: CadCategoriasComponent},
      {path: 'list-vendas', component: ListaVendasComponent},
      {path: 'list-clientes', component: ListaClientesComponent},
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
