import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from '../../services/user.service';
import { Serie } from '../../models/models';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  userName: string = 'Torrente'; // Demo user
  seriesEmpezadas: Serie[] = [];
  seriesPendientes: Serie[] = [];
  seriesTerminadas: Serie[] = [];
  isLoading: boolean = true;
  errorMessage: string = '';

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.loadUserSeries();
  }

  loadUserSeries(): void {
    const login = 'torrente'; // Demo login, could be taken from auth service
    
    this.userService.getUserSeriesEmpezadas(login).subscribe({
      next: (series) => {
        this.seriesEmpezadas = series;
      },
      error: (error) => {
        console.error('Error loading empezadas series:', error);
        this.seriesEmpezadas = []; // Empty for demo
      }
    });

    this.userService.getUserSeriesPendientes(login).subscribe({
      next: (series) => {
        this.seriesPendientes = series;
      },
      error: (error) => {
        console.error('Error loading pendientes series:', error);
        this.seriesPendientes = []; // Empty for demo
      }
    });

    this.userService.getUserSeriesTerminadas(login).subscribe({
      next: (series) => {
        this.seriesTerminadas = series;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading terminadas series:', error);
        this.seriesTerminadas = [];
        this.isLoading = false;
      }
    });
  }
}
