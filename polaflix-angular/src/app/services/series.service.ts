import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Serie } from '../models/models';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SeriesService {
  private apiUrl = `${environment.apiUrl}/series`;

  constructor(private http: HttpClient) { }

  /**
   * Get all available series
   */
  getAllSeries(): Observable<Serie[]> {
    return this.http.get<Serie[]>(this.apiUrl);
  }

  /**
   * Get series by ID
   */
  getSeriesById(id: number): Observable<Serie> {
    return this.http.get<Serie>(`${this.apiUrl}/${id}`);
  }
}
