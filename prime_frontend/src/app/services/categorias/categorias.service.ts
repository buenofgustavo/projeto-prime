import { Injectable } from '@angular/core';
import { API_CONFIG } from 'src/app/config/api.config';
import { AuthserviceService } from '../authservice.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Categorias } from 'src/app/interface/categorias';

@Injectable({
  providedIn: 'root'
})
export class CategoriasService {

  private apiUrl = `${API_CONFIG.baseUrl}/categorias`;

  authToken: string | null;

  constructor(private http: HttpClient, private authService: AuthserviceService) {
    this.authToken = this.authService.extractAuthToken();
  }

  cadastrar(categorias: Categorias): Observable<Categorias>{

    if(!this.authToken){
      throw new Error('Token JWT não encontrado, refaça o Login!');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer${this.authToken}`);
    return this.http.post<Categorias>(this.apiUrl, categorias, { headers });

  }

  get(): Observable<Categorias[]> {
    if(!this.authToken){
      throw new Error('Token JWT não encontrado, refaça o Login!');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer${this.authToken}`);

    return this.http.get<Categorias[]>(`${this.apiUrl}`, { headers });
  }

  delete(categorias: Categorias): Observable<Categorias[]> {
    
    if(!this.authToken){
      throw new Error('Token JWT não encontrado, refaça o Login!');
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer${this.authToken}`);

    return this.http.delete<Categorias[]>(`${this.apiUrl}/${categorias.id}`,  { headers });
  }

}
