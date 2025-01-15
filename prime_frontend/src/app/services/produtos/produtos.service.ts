import { Injectable } from '@angular/core';
import { API_CONFIG } from 'src/app/config/api.config';
import { AuthserviceService } from '../authservice.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Produtos } from 'src/app/interface/produtos';

@Injectable({
  providedIn: 'root'
})
export class ProdutosService {

  private apiUrl = `${API_CONFIG.baseUrl}/produtos`;

  authToken: string | null;

  constructor(private http: HttpClient, private authService: AuthserviceService) {
    this.authToken = this.authService.extractAuthToken();
  }

  cadastrar(produtos: Produtos): Observable<Produtos>{

    if(!this.authToken){
      throw new Error('Token JWT não encontrado, refaça o Login!');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer${this.authToken}`);
    return this.http.post<Produtos>(this.apiUrl, produtos, { headers });

  }

  get(): Observable<Produtos[]> {
    if(!this.authToken){
      throw new Error('Token JWT não encontrado, refaça o Login!');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer${this.authToken}`);

    return this.http.get<Produtos[]>(`${this.apiUrl}`, { headers });
  }

  delete(produtos: Produtos): Observable<Produtos[]> {
    
    if(!this.authToken){
      throw new Error('Token JWT não encontrado, refaça o Login!');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer${this.authToken}`);

    return this.http.delete<Produtos[]>(`${this.apiUrl}/${produtos.id}`,  { headers });
  }

}
