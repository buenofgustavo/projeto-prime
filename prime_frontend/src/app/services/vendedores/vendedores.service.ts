import { Injectable } from '@angular/core';
import { API_CONFIG } from 'src/app/config/api.config';
import { AuthserviceService } from '../authservice.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Vendedores } from 'src/app/interface/vendedores';

@Injectable({
  providedIn: 'root'
})
export class VendedoresService {

  private apiUrl = `${API_CONFIG.baseUrl}/vendedores`;

  authToken: string | null;

  constructor(private http: HttpClient, private authService: AuthserviceService) {
    this.authToken = this.authService.extractAuthToken();
  }

  cadastrar(vendedores: Vendedores): Observable<Vendedores>{

    if(!this.authToken){
      throw new Error('Token JWT não encontrado, refaça o Login!');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer${this.authToken}`);
    return this.http.post<Vendedores>(this.apiUrl, vendedores, { headers });

  }

  get(): Observable<Vendedores[]> {
    if(!this.authToken){
      throw new Error('Token JWT não encontrado, refaça o Login!');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer${this.authToken}`);

    return this.http.get<Vendedores[]>(`${this.apiUrl}`, { headers });
  }

  delete(vendedores: Vendedores): Observable<Vendedores[]> {
    
    if(!this.authToken){
      throw new Error('Token JWT não encontrado, refaça o Login!');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer${this.authToken}`);

    return this.http.delete<Vendedores[]>(`${this.apiUrl}/${vendedores.id}`,  { headers });
  }

}
