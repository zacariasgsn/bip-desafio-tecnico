import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';

export interface Beneficio {
  id: number;
  nome: string;
  descricao: string;
  valor: number;
  ativo: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class BeneficioService {

  private readonly api = `${environment.apiUrl}/beneficios`;

  constructor(private http: HttpClient) {}

  listar(): Observable<Beneficio[]> {
    return this.http.get<Beneficio[]>(this.api);
  }

  buscarPorId(id: number): Observable<Beneficio> {
    return this.http.get<Beneficio>(`${this.api}/${id}`);
  }

  criar(beneficio: any) {
    return this.http.post(`${this.api}`, beneficio);
  }

  atualizar(id: number, beneficio: any) {
    return this.http.put(`${this.api}/${id}`, beneficio);
  }

  excluir(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }

  transferir(payload: {
    fromId: number | null;
    toId: number | null;
    valor: number | null;
  }) {
    return this.http.post(
      `${this.api}/transferir`,
      payload
    );
  }

}
