export interface Serie {
  id: number;
  titulo: string;
  sinopsis?: string;
  descripcion?: string; // Alias para sinopsis
  estado?: string;
  creadores?: Set<string> | string[];
  actores?: Set<string> | string[];
  categoria?: CategoriaDTO | Categoria;
  temporadas?: Temporada[];
  inicial?: string;
}

export interface CategoriaDTO {
  id?: number;
  nombre: string;
}

export interface Categoria {
  id: number;
  nombre: string;
}

export interface Temporada {
  id: number;
  numero: number;
  capitulos?: Capitulo[];
}

export interface Capitulo {
  id: number;
  numero: number;
  titulo: string;
  duracion: number;
}

export interface Usuario {
  login: string;
  nombre: string;
  apellidos: string;
  email: string;
  seriesPendientes?: Serie[];
  seriesEmpezadas?: Serie[];
  seriesTerminadas?: Serie[];
}

export interface Visualizacion {
  id: number;
  usuario?: Usuario;
  capitulo?: Capitulo;
  fechaVisualizacion: Date;
}
