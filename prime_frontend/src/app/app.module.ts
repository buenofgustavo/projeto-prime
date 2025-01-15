import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';

import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { NbEvaIconsModule } from '@nebular/eva-icons';
import {MatTableModule} from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { NbCalendarModule, NbContextMenuComponent, NbDatepickerModule, NbWindowModule } from '@nebular/theme'; // Importe o NbDatepickerModule
import { MatDialogModule } from '@angular/material/dialog';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {ChangeDetectionStrategy} from '@angular/core';
import { CurrencyMaskModule } from "ng2-currency-mask";

import {
  NbButtonModule,
  NbCardModule,
  NbFormFieldModule,
  NbIconModule,
  NbInputModule,
  NbLayoutModule,
  NbMenuModule,
  NbAutocompleteModule,
  NbSelectModule,
  NbSidebarModule,
  NbStepperModule,
  NbThemeModule,
  NbUserModule,
  NbContextMenuModule,
  NbPositionBuilderService,
  NbCheckboxModule,
  NbToastrModule
} from '@nebular/theme';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule } from '@angular/material/menu';
import {MatTabsModule} from '@angular/material/tabs';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {FormsModule} from '@angular/forms';
import { NavbarComponent } from './components/navbar/navbar.component';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { TextMaskModule } from 'angular2-text-mask';
import { CadastrarUsuarioComponent } from './components/cadastrar-usuario/cadastrar-usuario.component';
import { CadClienteComponent } from './components/cad-cliente/cad-cliente.component';
import { CadVendasComponent } from './components/cad-vendas/cad-vendas.component';
import { ListaVendasComponent } from './components/lista-vendas/lista-vendas.component';
import { ModalVendaComponent } from './components/lista-vendas/modal-venda/modal-venda.component';
import { MAT_DATE_LOCALE, MatNativeDateModule } from '@angular/material/core';
import { ListaClientesComponent } from './components/lista-clientes/lista-clientes.component';
import { ModalClienteComponent } from './components/lista-clientes/modal-cliente/modal-cliente.component';
import { CadCidadesComponent } from './components/cad-cidades/cad-cidades.component';
import { CadProdutosComponent } from './components/cad-produtos/cad-produtos.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    CadastrarUsuarioComponent,
    LoginComponent,
    HomeComponent,
    CadClienteComponent,
    CadVendasComponent,
    ListaVendasComponent,
    ModalVendaComponent,
    ListaClientesComponent,
    ModalClienteComponent,
    CadCidadesComponent,
    CadProdutosComponent
  ],
  imports: [
    AppRoutingModule, HttpClientModule, FormsModule, ReactiveFormsModule, BrowserModule, BrowserAnimationsModule, NbEvaIconsModule,
    NbButtonModule, NbCardModule, NbFormFieldModule, NbIconModule, NbInputModule,NbLayoutModule,NbMenuModule.forRoot(),NbSelectModule,
    NbSidebarModule.forRoot(), NbStepperModule, NbThemeModule.forRoot({ name: 'default' }),NbUserModule,MatTableModule, NbContextMenuModule,
    MatPaginatorModule,NbCheckboxModule,NbDatepickerModule.forRoot(),NbWindowModule.forRoot(),MatTabsModule,MatDialogModule,
    NbContextMenuModule,MatButtonModule,MatMenuModule,MatInputModule,MatFormFieldModule,FormsModule,NbToastrModule.forRoot(),
    MatProgressSpinnerModule, TextMaskModule, NbAutocompleteModule, NbCalendarModule, MatDatepickerModule, MatNativeDateModule,
    CurrencyMaskModule
  ],
  providers: [{ provide: MAT_DATE_LOCALE, useValue: 'pt-BR' }, ],
  bootstrap: [AppComponent]
})
export class AppModule { }
