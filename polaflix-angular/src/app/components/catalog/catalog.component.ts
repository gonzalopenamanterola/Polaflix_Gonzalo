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
  isLoading: boolean = true;
  errorMessage: string = '';
  selectedLetter: string = '';
  userLogin: string = 'torrente'; // Demo user
  
  // Alphabet for filtering
  alphabet: string[] = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 
                        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0-9'];

  constructor(
    private seriesService: SeriesService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.loadAllSeries();
  }

  loadAllSeries(): void {
    this.seriesService.getAllSeries().subscribe({
      next: (series) => {
        // Map data: sinopsis -> descripcion
        this.allSeries = series.map(serie => ({
          ...serie,
          descripcion: serie.sinopsis || serie.descripcion || ''
        })).sort((a, b) => a.titulo.localeCompare(b.titulo));
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

  /**
   * Convert Set or Array of strings to comma-separated string
   */
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

  /**
   * Get categoria name safely
   */
  getCategoriaName(categoria: any): string {
    if (!categoria) return '';
    return categoria.nombre || '';
  }

  addSeriesToPendientes(serie: Serie): void {
    if (serie.id) {
      this.userService.addSeriesToPendientes(this.userLogin, serie.id).subscribe({
        next: () => {
          alert(`${serie.titulo} ha sido añadida a tu lista de pendientes`);
        },
        error: (error) => {
          console.error('Error adding series to pendientes:', error);
          console.error('Error status:', error.status);
          console.error('Error message:', error.message);
          alert(`Error al añadir la serie a pendientes. Por favor, intenta de nuevo.`);
        }
      });
    }
  }
}
