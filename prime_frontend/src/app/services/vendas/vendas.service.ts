import { Injectable } from '@angular/core';
import { API_CONFIG } from 'src/app/config/api.config';
import { AuthserviceService } from '../authservice.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { VendasComProdutosDTO } from 'src/app/interface/vendasComProdutos';
import { Observable } from 'rxjs';
import { Clientes } from 'src/app/interface/clientes';

@Injectable({
  providedIn: 'root'
})
export class VendasService {

  private apiUrl = `${API_CONFIG.baseUrl}/vendas`;

  authToken: string | null;

  constructor(private http: HttpClient, private authService: AuthserviceService) {
    this.authToken = this.authService.extractAuthToken();
  }

  cadastrarVendas(vendasComProdutosDTO: VendasComProdutosDTO): Observable<VendasComProdutosDTO>{

    if(!this.authToken){
      throw new Error('Token JWT não encontrado, refaça o Login!');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer${this.authToken}`);
    return this.http.post<VendasComProdutosDTO>(this.apiUrl, vendasComProdutosDTO, { headers });
  }

  pagarVenda(vendasComProdutosDTO: VendasComProdutosDTO): Observable<VendasComProdutosDTO>{

    if(!this.authToken){
      throw new Error('Token JWT não encontrado, refaça o Login!');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer${this.authToken}`);
    return this.http.put<VendasComProdutosDTO>(this.apiUrl, vendasComProdutosDTO, { headers });
  }

  getVendas(): Observable<VendasComProdutosDTO[]> {
    
    if(!this.authToken){
      throw new Error('Token JWT não encontrado, refaça o Login!');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer${this.authToken}`);

    return this.http.get<VendasComProdutosDTO[]>(`${this.apiUrl}`, { headers });
  }

  deleteVendas(vendasComProdutosDTO: VendasComProdutosDTO): Observable<VendasComProdutosDTO[]> {
    
    if(!this.authToken){
      throw new Error('Token JWT não encontrado, refaça o Login!');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer${this.authToken}`);

    return this.http.delete<VendasComProdutosDTO[]>(`${this.apiUrl}/${vendasComProdutosDTO.vendasDTO.id}`,  { headers });
  }

}

