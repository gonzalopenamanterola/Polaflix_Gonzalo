import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SeriesService } from '../../services/series.service';
import { UserService } from '../../services/user.service';
import { Serie } from '../../models/models';

@Component({
  selector: 'app-catalog',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './catalog.component.html',
  styleUrls: ['./catalog.component.css']
})
export class CatalogComponent implements OnInit {
  allSeries: Serie[] = [];
  filteredSeries: Serie[] = [];
  seriesPendientes: Set<number> = new Set();
  isLoading: boolean = true;
  errorMessage: string = '';
  selectedLetter: string = '';
  userLogin: string = 'torrente';
  

  alphabet: string[] = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 
                        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0-9'];

  constructor(
    private seriesService: SeriesService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.loadUserPendingSeries();
  }

  loadUserPendingSeries(): void {
    this.userService.getUserSeriesPendientes(this.userLogin).subscribe({
      next: (series) => {
        // Store IDs of pending series
        series.forEach(s => this.seriesPendientes.add(s.id));
        this.loadAllSeries();
      },
      error: (error) => {
        console.error('Error loading pending series:', error);
       
        this.loadAllSeries();
      }
    });
  }

  loadAllSeries(): void {
    this.seriesService.getAllSeries().subscribe({
      next: (series) => {
        
        this.allSeries = series
          .filter(serie => !this.seriesPendientes.has(serie.id)) 
          .map(serie => ({
            ...serie,
            descripcion: serie.sinopsis || serie.descripcion || ''
          }))
          .sort((a, b) => a.titulo.localeCompare(b.titulo));
        this.filteredSeries = this.allSeries;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading series:', error);
        this.errorMessage = 'Error cargando las series';
        this.isLoading = false;
      }
    });
  }

  filterByLetter(letter: string): void {
    this.selectedLetter = letter;
    
    if (letter === '0-9') {
      this.filteredSeries = this.allSeries.filter(serie => 
        /^[0-9]/.test(serie.titulo)
      );
    } else {
      this.filteredSeries = this.allSeries.filter(serie => 
        serie.titulo.toUpperCase().startsWith(letter)
      );
    }
  }

  showAll(): void {
    this.selectedLetter = '';
    this.filteredSeries = this.allSeries;
  }

  formatStringSet(items: Set<string> | string[] | undefined): string {
    if (!items) return '';
    
    if (items instanceof Set) {
      return Array.from(items).join(', ');
    }
    
    if (Array.isArray(items)) {
      return items.join(', ');
    }
    
    return '';
  }

  getCategoriaName(categoria: any): string {
    if (!categoria) return '';
    const catString = String(categoria).trim().toUpperCase();

    
    switch (catString) {
      case 'ESTANDAR':
        return 'Estándar';
      case 'SILVER':
        return 'Silver';
      case 'GOLD':
        return 'Gold';
      default:
        return catString.charAt(0) + catString.slice(1).toLowerCase();
    }
  }

  addSeriesToPendientes(serie: Serie): void {
    if (!serie.id) {
      console.error('Error: serie.id is undefined');
      alert('Error: la serie no tiene ID válido');
      return;
    }

    console.log('Adding to pending - User:', this.userLogin, 'Serie:', serie.titulo, 'ID:', serie.id);

    this.userService.addSeriesToPendientes(this.userLogin, serie.id).subscribe({
      next: (response) => {
        console.log('Success response:', response);
       
        this.seriesPendientes.add(serie.id!);
        this.allSeries = this.allSeries.filter(s => s.id !== serie.id);
        this.filteredSeries = this.filteredSeries.filter(s => s.id !== serie.id);
        alert(`${serie.titulo} ha sido añadida a tu lista de pendientes`);
        
        
        this.reloadPendingSeriesFromDB();
      },
      error: (error) => {
        console.error('Error adding series to pendientes:', error);
        console.error('Error status:', error.status);
        console.error('Error statusText:', error.statusText);
        console.error('Error message:', error.message);
        console.error('Error body:', error.error);
        
        let errorMessage = 'Error al añadir la serie a pendientes. ';
        if (error.status === 400) {
          errorMessage += 'Solicitud inválida - verifica que el usuario y la serie existan.';
        } else if (error.status === 404) {
          errorMessage += 'Usuario o serie no encontrados.';
        } else if (error.status === 0) {
          errorMessage += 'Error de conexión con el servidor.';
        } else {
          errorMessage += `Error ${error.status}: ${error.statusText}`;
        }
        
        alert(errorMessage);
      }
    });
  }


  private reloadPendingSeriesFromDB(): void {
    this.userService.getUserSeriesPendientes(this.userLogin).subscribe({
      next: (series) => {
        console.log('Reloaded pending series from DB:', series);
        // Update pending series set
        this.seriesPendientes.clear();
        series.forEach(s => this.seriesPendientes.add(s.id));
      },
      error: (error) => {
        console.error('Error reloading pending series:', error);
      }
    });
  }
}
