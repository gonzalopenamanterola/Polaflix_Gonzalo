# Polaflix Angular Frontend

Frontend web para la plataforma de streaming Polaflix, desarrollado en Angular 17.

## Descripción

Esta es la capa de presentación web del sistema Polaflix. La aplicación implementa:

- **Single Page Application (SPA)**: Navegación sin recargas de página usando Angular Router
- **Componentes Interactivos**: Interfaz de usuario responsiva y moderna
- **Comunicación HTTP**: Servicios que se comunican con la API REST del backend
- **Dos Interfaces Principales**:
  - **Página de Inicio**: Muestra las series del usuario organizadas por estado (Empezadas, Pendientes, Terminadas)
  - **Catálogo de Series**: Visualiza todas las series disponibles con filtrado alfabético y opción para añadir a pendientes

## Estructura del Proyecto

```
polaflix-angular/
├── src/
│   ├── app/
│   │   ├── components/
│   │   │   ├── home/              # Página principal del usuario
│   │   │   └── catalog/           # Catálogo de series
│   │   ├── services/
│   │   │   ├── user.service.ts    # Operaciones de usuario
│   │   │   └── series.service.ts  # Operaciones de series
│   │   ├── models/
│   │   │   └── models.ts          # Interfaces TypeScript
│   │   ├── shared/
│   │   │   └── header/            # Componente compartido del encabezado
│   │   ├── app.component.*        # Componente raíz
│   │   ├── app.config.ts          # Configuración de la aplicación
│   │   └── app.routes.ts          # Definición de rutas
│   ├── environments/              # Configuración por entorno
│   ├── assets/                    # Recursos estáticos
│   ├── index.html
│   ├── main.ts                    # Punto de entrada
│   └── styles.css                 # Estilos globales
├── angular.json                   # Configuración de Angular CLI
├── tsconfig.json                  # Configuración de TypeScript
├── package.json                   # Dependencias del proyecto
└── README.md
```

## Requisitos Previos

- Node.js (v18 o superior)
- npm (v9 o superior)
- Angular CLI (v17): `npm install -g @angular/cli@17`

## Instalación

1. Navega a la carpeta del proyecto:
```bash
cd polaflix-angular
```

2. Instala las dependencias:
```bash
npm install
```

## Ejecución

### Desarrollo

```bash
npm start
```

La aplicación se abrirá automáticamente en `http://localhost:4200/`

### Construcción para Producción

```bash
npm run build
```

Los archivos compilados estarán en la carpeta `dist/polaflix-angular/`

## Componentes

### HomeComponent
- **Ruta**: `/home`
- **Funcionalidad**: Muestra las series del usuario clasificadas en tres categorías:
  - Empezadas
  - Pendientes
  - Terminadas
- **Servicios**: UserService

### CatalogComponent
- **Ruta**: `/catalog`
- **Funcionalidad**: 
  - Muestra todas las series disponibles
  - Filtrado alfabético (A-Z, 0-9)
  - Botón "Agregar" para añadir series a pendientes
  - Búsqueda interactiva
- **Servicios**: SeriesService, UserService

## Servicios

### UserService
- `getUserByLogin(login: string)`: Obtiene datos del usuario
- `getUserSeriesPendientes(login: string)`: Series pendientes
- `getUserSeriesEmpezadas(login: string)`: Series comenzadas
- `getUserSeriesTerminadas(login: string)`: Series finalizadas
- `addSeriesToPendientes(login: string, serieId: number)`: Añade serie a pendientes

### SeriesService
- `getAllSeries()`: Obtiene todas las series
- `getSeriesById(id: number)`: Obtiene una serie específica

## Configuración de la API

La URL base de la API se configura en `src/environments/`:

```typescript
// environment.ts (desarrollo)
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api'
};
```

Asegúrate de que el servidor backend está corriendo en `http://localhost:8080/`

## Características de Diseño

- **Diseño Responsivo**: Compatible con dispositivos móviles, tablets y desktops
- **Interfaz Intuitiva**: Navegación clara y fácil de usar
- **Paleta de Colores**: Colores basados en el branding de Polaflix
  - Rojo: #e50914
  - Gris: #333, #666, #999
  - Blanco y fondos: #f5f5f5, #f9f9f9

## Patrones Implementados

1. **Single Page Application (SPA)**: Navegación sin recargas using Angular Router
2. **Componentes Standalone**: Componentes independientes sin módulos
3. **Inyección de Dependencias**: Servicios inyectados en componentes
4. **Programación Reactiva**: Uso de RxJS Observables
5. **Binding**: Two-way binding y property binding en templates

## Testing

Para ejecutar las pruebas unitarias:

```bash
npm test
```

## Despliegue

1. Construir para producción:
```bash
npm run build
```

2. Servir los archivos del directorio `dist/polaflix-angular/` usando un servidor web

## Nota Importante

Para esta versión de demostración:
- El usuario está hardcodeado como "john.nieve" / "John Nieve"
- En producción, se debería integrar un sistema de autenticación real
- Las URLs de la API deben ser configuradas según el entorno

## Autor
Gonzalo Peña Manterola

## Licencia
MIT
