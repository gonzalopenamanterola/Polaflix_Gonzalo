import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario } from '../models/models';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = `${environment.apiUrl}/usuarios`;

  constructor(private http: HttpClient) { }

  /**
   * Get user by login
   */
  getUserByLogin(login: string): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.apiUrl}/${login}`);
  }

  /**
   * Get user's pending series
   */
  getUserSeriesPendientes(login: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/${login}/series-pendientes`);
  }

  /**
   * Get user's started series
   */
  getUserSeriesEmpezadas(login: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/${login}/series-empezadas`);
  }

  /**
   * Get user's finished series
   */
  getUserSeriesTerminadas(login: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/${login}/series-terminadas`);
  }

  /**
   * Add series to user's pending list
   * Sends POST request to: /usuarios/{login}/series-pendientes/{serieId}
   */
  addSeriesToPendientes(login: string, serieId: number): Observable<any> {
    const url = `${this.apiUrl}/${login}/series-pendientes/${serieId}`;
    console.log('POST request to:', url);
    return this.http.post(url, null, {
      responseType: 'text'
    });
  }
}
