import { Injectable } from '@angular/core';
import { API_CONFIG } from 'src/app/config/api.config';
import { AuthserviceService } from '../authservice.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Cidades } from 'src/app/interface/cidades';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CidadesService {

  private apiUrl = `${API_CONFIG.baseUrl}/cidades`;

  authToken: string | null;

  constructor(private http: HttpClient, private authService: AuthserviceService) {
    this.authToken = this.authService.extractAuthToken();
  }

  cadastrar(cidades: Cidades): Observable<Cidades>{

    if(!this.authToken){
      throw new Error('Token JWT não encontrado, refaça o Login!');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer${this.authToken}`);
    return this.http.post<Cidades>(this.apiUrl, cidades, { headers });

  }

  get(): Observable<Cidades[]> {
    if(!this.authToken){
      throw new Error('Token JWT não encontrado, refaça o Login!');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer${this.authToken}`);

    return this.http.get<Cidades[]>(`${this.apiUrl}`, { headers });
  }

  delete(cidades: Cidades): Observable<Cidades[]> {
    
    if(!this.authToken){
      throw new Error('Token JWT não encontrado, refaça o Login!');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer${this.authToken}`);

    return this.http.delete<Cidades[]>(`${this.apiUrl}/${cidades.id}`,  { headers });
  }

}
