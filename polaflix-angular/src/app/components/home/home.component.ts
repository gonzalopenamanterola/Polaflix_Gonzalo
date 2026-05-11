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
  userName: string = 'Torrente'; 
  seriesEmpezadas: Serie[] = [];
  seriesPendientes: Serie[] = [];
  seriesTerminadas: Serie[] = [];
  isLoading: boolean = true;
  errorMessage: string = '';
  private userLogin: string = 'torrente'; 

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.loadUserSeries();
  }

  loadUserSeries(): void {
    this.isLoading = true;
    
    this.userService.getUserSeriesEmpezadas(this.userLogin).subscribe({
      next: (series) => {
        this.seriesEmpezadas = series;
      },
      error: (error) => {
        console.error('Error loading empezadas series:', error);
        this.seriesEmpezadas = [];
      }
    });

    this.userService.getUserSeriesPendientes(this.userLogin).subscribe({
      next: (series) => {
        this.seriesPendientes = series;
      },
      error: (error) => {
        console.error('Error loading pendientes series:', error);
        this.seriesPendientes = []; 
      }
    });

    this.userService.getUserSeriesTerminadas(this.userLogin).subscribe({
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

  refreshUserSeries(): void {
    console.log('Refreshing user series from backend');
    this.loadUserSeries();
  }
}
