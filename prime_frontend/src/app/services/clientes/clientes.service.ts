import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_CONFIG } from 'src/app/config/api.config';
import { AuthserviceService } from '../authservice.service';
import { Clientes } from 'src/app/interface/clientes';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClientesService {

  private apiUrl = `${API_CONFIG.baseUrl}/clientes`;

  authToken: string | null;

  constructor(private http: HttpClient, private authService: AuthserviceService) {
    this.authToken = this.authService.extractAuthToken();
  }

  cadastrarClientes(clientes: Clientes): Observable<Clientes>{

    if(!this.authToken){
      throw new Error('Token JWT não encontrado, refaça o Login!');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer${this.authToken}`);
    return this.http.post<Clientes>(this.apiUrl, clientes, { headers });

  }

  pagarSaldoDevedor(id: any, valorASerPago: number): Observable<Clientes>{
    if(!this.authToken){
      throw new Error('Token JWT não encontrado, refaça o Login!');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer${this.authToken}`);
    return this.http.post<Clientes>(`${this.apiUrl}/pagar-saldo-devedor/${id}/${valorASerPago}`, null, { headers });
  }

  getClientes(): Observable<Clientes[]> {
    if(!this.authToken){
      throw new Error('Token JWT não encontrado, refaça o Login!');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer${this.authToken}`);

    return this.http.get<Clientes[]>(`${this.apiUrl}`, { headers });
  }

}
