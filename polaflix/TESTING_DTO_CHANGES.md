# Pruebas de Cambios DTO y Vistas

## Resumen de Cambios Realizados

Se han implementado los siguientes cambios para resolver el error `ConcurrentModificationException`:

### 1. **Métodos toDTO en Controladores**
- Cada controlador ahora tiene métodos privados `toDTO()` para convertir entidades a DTOs
- Se utilizan métodos específicos para `toResumenDTO()` y `toCompletoDTO()` en algunos controladores
- Esto permite control granular de qué datos se incluyen en la respuesta

### 2. **Controladores Actualizados**
- ✅ **UsuarioController**: `toResumenDTO()` (login) y `toCompletoDTO()` (login, password, iban, plan)
- ✅ **SerieController**: `toResumenDTO()` (id, titulo, inicial, categoria) y `toCompletoDTO()` (todos los campos excepto temporadas)
- ✅ **FacturaController**: `toResumenDTO()` (id, mes, año, fechaEmision, importeTotal) y `toCompletoDTO()` (+ usuario)
- ✅ **PersonaController**: `toDTO()` (id, nombre, apellido)
- ✅ **SuscripcionController**: `toDTO()` (id, tipo, costoMensual)

### 3. **Servicios Simplificados**
- Se han removido las llamadas `Hibernate.initialize()` de los servicios
- Los servicios ahora devuelven las entidades sin inicializar colecciones
- La inicialización selectiva ocurre en los controladores según necesidad

### 4. **Vistas de Jackson (@JsonView)**
Estructura de vistas disponibles en `Vistas.java`:

```
Public
├── UsuarioResumen
│   └── UsuarioCompleto
├── SerieResumen
│   └── SerieCompleto
├── FacturaResumen
│   └── FacturaCompleto
├── PersonaResumen
│   └── PersonaCompleto
├── TipoSuscripcionResumen
│   └── TipoSuscripcionCompleto
├── TemporadaResumen
│   └── TemporadaCompleto
├── CapituloResumen
│   └── CapituloCompleto
└── VisualizacionResumen
    └── VisualizacionCompleto
```

## Casos de Prueba

### Test 1: GET /api/usuarios (Resumen)
**Endpoint**: `GET /api/usuarios`
**@JsonView**: `Vistas.UsuarioResumen.class`
**Campos esperados**:
```json
[
  {
    "login": "usuario1"
  },
  {
    "login": "usuario2"
  }
]
```
**¿Por qué funciona?**: El método `toResumenDTO()` solo devuelve el login, evitando cargar colecciones perezosas.

---

### Test 2: GET /api/usuarios/{login} (Completo)
**Endpoint**: `GET /api/usuarios/usuario1`
**@JsonView**: `Vistas.UsuarioCompleto.class`
**Campos esperados**:
```json
{
  "login": "usuario1",
  "password": "contraseña123",
  "iban": "ES1234567890",
  "plan": {
    "id": 1,
    "tipo": "Premium"
  }
}
```
**¿Por qué funciona?**: El método `toCompletoDTO()` solo mapea login, password, iban y el plan (sin listas de series).

---

### Test 3: GET /api/series (Resumen)
**Endpoint**: `GET /api/series`
**@JsonView**: `Vistas.SerieResumen.class`
**Campos esperados**:
```json
[
  {
    "id": 1,
    "titulo": "Breaking Bad",
    "inicial": "Pilot",
    "categoria": {
      "id": 1,
      "nombre": "Drama"
    }
  }
]
```
**¿Por qué funciona?**: Solo se cargan datos básicos sin listas de creadores/actores/temporadas.

---

### Test 4: GET /api/series/{id} (Completo)
**Endpoint**: `GET /api/series/1`
**@JsonView**: `Vistas.SerieCompleto.class`
**Campos esperados**:
```json
{
  "id": 1,
  "titulo": "Breaking Bad",
  "inicial": "Pilot",
  "sinopsis": "...",
  "creadores": [
    {
      "id": 1,
      "nombre": "Vince",
      "apellido": "Gilligan"
    }
  ],
  "actores": [...],
  "categoria": {...}
}
```
**¿Por qué funciona?**: El método `toCompletoDTO()` incluye listas pero sin anidar temporadas/capítulos.

---

### Test 5: GET /api/facturas (Resumen)
**Endpoint**: `GET /api/facturas`
**@JsonView**: `Vistas.FacturaResumen.class`
**Campos esperados**:
```json
[
  {
    "id": 1,
    "mes": 5,
    "año": 2024,
    "fechaEmision": "2024-05-15",
    "importeTotal": 15.99
  }
]
```

---

### Test 6: GET /api/facturas/{id} (Completo)
**Endpoint**: `GET /api/facturas/1`
**@JsonView**: `Vistas.FacturaCompleto.class`
**Campos esperados**:
```json
{
  "id": 1,
  "mes": 5,
  "año": 2024,
  "fechaEmision": "2024-05-15",
  "importeTotal": 15.99,
  "usuario": {
    "login": "usuario1"
  }
}
```

---

### Test 7: POST /api/usuarios (Crear)
**Endpoint**: `POST /api/usuarios`
**Body**:
```json
{
  "login": "nuevoUsuario",
  "password": "password123",
  "iban": "ES9876543210"
}
```
**@JsonView**: `Vistas.UsuarioCompleto.class`
**Respuesta esperada**: Usuario creado con los datos mapeados.

---

### Test 8: Verificar No ConcurrentModificationException
**Comando**: Ejecutar desde Postman o curl:
```bash
curl http://localhost:8080/api/usuarios
```
**Resultado esperado**: 
- ✅ Status 200
- ✅ JSON válido con usuarios
- ✅ NO hay error 500 con ConcurrentModificationException

---

## Cómo Ejecutar las Pruebas

### Opción 1: Postman
1. Abrir Postman
2. Crear colección con los endpoints anteriores
3. Ejecutar cada request
4. Verificar respuestas JSON

### Opción 2: Curl
```bash
# Test 1: Usuarios (Resumen)
curl -X GET http://localhost:8080/api/usuarios

# Test 2: Usuario por login (Completo)
curl -X GET http://localhost:8080/api/usuarios/usuario1

# Test 3: Series (Resumen)
curl -X GET http://localhost:8080/api/series

# Test 4: Serie por ID (Completo)
curl -X GET http://localhost:8080/api/series/1

# Test 5: Facturas (Resumen)
curl -X GET http://localhost:8080/api/facturas

# Test 6: Factura por ID (Completo)
curl -X GET http://localhost:8080/api/facturas/1
```

### Opción 3: Ejecutar Aplicación
```bash
cd c:\Users\gopem\OneDrive\Escritorio\Estudios\Master\TECNOLOGIAS DE DESARROLLO\Github\Polaflix_Gonzalo\polaflix
.\mvnw.cmd spring-boot:run
```

Luego acceder a los endpoints en el navegador o Postman.

---

## Puntos Clave Implementados

### ✅ Solución al ConcurrentModificationException
**Problema**: DTOMapper intentaba mapear todas las colecciones perezosas simultáneamente, causando error.
**Solución**: Los controladores controlan qué datos mapear, evitando cargar colecciones innecesarias.

### ✅ Separación de Responsabilidades
- **Servicios**: Solo obtienen datos de la BD
- **Controladores**: Deciden qué datos exponer mediante toDTO()
- **@JsonView**: Filtra respuesta JSON según vista

### ✅ DTOs Validados
Todos los DTOs tienen validaciones con anotaciones de Jakarta:
- `@NotBlank`: Para campos de texto
- `@NotNull`: Para referencias obligatorias
- `@Min`: Para campos numéricos

### ✅ Patrón Consistente
Todos los controladores siguen el mismo patrón:
```java
// En GET
List<Entity> entities = service.getAll();
return ResponseEntity.ok(entities.stream()
    .map(this::toDTO)
    .collect(Collectors.toList()));
```

---

## Verificación Final

Para asegurar que todo funciona correctamente:

1. **Compilación**: ✅ `mvn clean compile` sin errores
2. **Estructura**: ✅ Todos los controladores tienen métodos toDTO()
3. **Imports**: ✅ Removidos imports de Hibernate innecesarios
4. **Servicios**: ✅ Simplificados sin Hibernate.initialize()
5. **DTOs**: ✅ Completos con validaciones

---

## Notas Importantes

### Para Entidades sin Controlador
Entidades como `Capitulo`, `Temporada`, `Visualizacion` tienen DTOs pero no controladores propios. 
Se mapean desde el controlador de la entidad padre (Serie, Usuario, etc.).

### Sobre Colecciones
Los controladores **intencionalmente NO cargan** colecciones perezosas para evitar el error.
Si necesita incluir colecciones completas, hacerlo explícitamente en el método toDTO().

### Jackson @JsonView
Solo los campos con `@JsonView` se serializan. Sin anotación, el campo se excluye.

