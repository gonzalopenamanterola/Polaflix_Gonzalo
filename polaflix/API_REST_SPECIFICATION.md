# Especificación API REST - Polaflix

## Descripción General
API REST para la gestión de una plataforma de streaming de series de televisión. Proporciona operaciones CRUD sobre las principales entidades del dominio.

## Base URL
```
http://localhost:8080/api
```

## Endpoints

### 1. Usuarios

#### GET /usuarios
Obtiene todos los usuarios.
```
Status: 200 OK
Response: List<Usuario>
```

#### GET /usuarios/{login}
Obtiene un usuario específico por login.
```
Path: /usuarios/user1
Status: 200 OK | 404 Not Found
Response: Usuario
```

#### POST /usuarios
Crea un nuevo usuario.
```
Method: POST
Body: Usuario
Status: 201 Created
Response: Usuario
```

#### PUT /usuarios/{login}
Actualiza un usuario existente.
```
Method: PUT
Path: /usuarios/user1
Body: Usuario
Status: 200 OK | 404 Not Found
Response: Usuario
```

#### POST /usuarios/{login}/series-pendientes/{serieId}
Añade una serie a las pendientes del usuario.
```
Method: POST
Path: /usuarios/user1/series-pendientes/1
Status: 200 OK | 400 Bad Request
```

#### GET /usuarios/{login}/series-pendientes
Obtiene las series pendientes del usuario.
```
Path: /usuarios/user1/series-pendientes
Status: 200 OK | 404 Not Found
Response: Set<Serie>
```

#### GET /usuarios/{login}/series-empezadas
Obtiene las series empezadas del usuario.
```
Path: /usuarios/user1/series-empezadas
Status: 200 OK | 404 Not Found
Response: Set<Serie>
```

#### GET /usuarios/{login}/series-terminadas
Obtiene las series terminadas del usuario.
```
Path: /usuarios/user1/series-terminadas
Status: 200 OK | 404 Not Found
Response: Set<Serie>
```

---

### 2. Series

#### GET /series
Obtiene todas las series disponibles.
```
Status: 200 OK
Response: List<Serie>
```

#### GET /series/{id}
Obtiene una serie específica por ID.
```
Path: /series/1
Status: 200 OK | 404 Not Found
Response: Serie
```

#### POST /series
Crea una nueva serie.
```
Method: POST
Body: Serie
Status: 201 Created
Response: Serie
```

#### PUT /series/{id}
Actualiza una serie existente.
```
Method: PUT
Path: /series/1
Body: Serie
Status: 200 OK | 404 Not Found
Response: Serie
```

#### DELETE /series/{id}
Elimina una serie.
```
Method: DELETE
Path: /series/1
Status: 204 No Content | 404 Not Found
```

---

### 3. Facturas

#### GET /facturas
Obtiene todas las facturas.
```
Status: 200 OK
Response: List<Factura>
```

#### GET /facturas/{id}
Obtiene una factura específica por ID.
```
Path: /facturas/1
Status: 200 OK | 404 Not Found
Response: Factura
```

#### POST /facturas
Crea una nueva factura.
```
Method: POST
Body: Factura
Status: 201 Created
Response: Factura
```

#### PUT /facturas/{id}
Actualiza una factura existente.
```
Method: PUT
Path: /facturas/1
Body: Factura
Status: 200 OK | 404 Not Found
Response: Factura
```

#### DELETE /facturas/{id}
Elimina una factura.
```
Method: DELETE
Path: /facturas/1
Status: 204 No Content | 404 Not Found
```

#### GET /facturas/usuario/{login}
Obtiene las facturas de un usuario específico.
```
Path: /facturas/usuario/user1
Status: 200 OK | 404 Not Found
Response: List<Factura>
```

---

### 4. Suscripciones

#### GET /suscripciones
Obtiene todos los tipos de suscripción.
```
Status: 200 OK
Response: List<TipoSuscripcion>
```

#### GET /suscripciones/{id}
Obtiene un tipo de suscripción específico por ID.
```
Path: /suscripciones/1
Status: 200 OK | 404 Not Found
Response: TipoSuscripcion
```

#### POST /suscripciones
Crea un nuevo tipo de suscripción.
```
Method: POST
Body: TipoSuscripcion
Status: 201 Created
Response: TipoSuscripcion
```

#### PUT /suscripciones/{id}
Actualiza un tipo de suscripción.
```
Method: PUT
Path: /suscripciones/1
Body: TipoSuscripcion
Status: 200 OK | 404 Not Found
Response: TipoSuscripcion
```

#### DELETE /suscripciones/{id}
Elimina un tipo de suscripción.
```
Method: DELETE
Path: /suscripciones/1
Status: 204 No Content | 404 Not Found
```

---

### 5. Personas

#### GET /personas
Obtiene todas las personas.
```
Status: 200 OK
Response: List<Persona>
```

#### GET /personas/{id}
Obtiene una persona específica por ID.
```
Path: /personas/1
Status: 200 OK | 404 Not Found
Response: Persona
```

#### POST /personas
Crea una nueva persona.
```
Method: POST
Body: Persona
Status: 201 Created
Response: Persona
```

#### PUT /personas/{id}
Actualiza una persona existente.
```
Method: PUT
Path: /personas/1
Body: Persona
Status: 200 OK | 404 Not Found
Response: Persona
```

#### DELETE /personas/{id}
Elimina una persona.
```
Method: DELETE
Path: /personas/1
Status: 204 No Content | 404 Not Found
```
Status: 200 OK | 404 Not Found
Response: Capitulo
```

#### POST /capitulos
Crea un nuevo capítulo.
```
Method: POST
Body: Capitulo
Status: 201 Created
Response: Capitulo
```

#### PUT /capitulos/{id}
Actualiza un capítulo.
```
Method: PUT
Path: /capitulos/1
Body: Capitulo
Status: 200 OK | 404 Not Found
Response: Capitulo
```

#### DELETE /capitulos/{id}
Elimina un capítulo.
```
Method: DELETE
Path: /capitulos/1
Status: 204 No Content | 404 Not Found
```

---

### 5. Facturas

#### GET /facturas
Obtiene todas las facturas.
```
Status: 200 OK
Response: List<Factura>
```

#### GET /facturas/{id}
Obtiene una factura específica.
```
Path: /facturas/1
Status: 200 OK | 404 Not Found
Response: Factura
```

#### POST /facturas
Crea una nueva factura.
```
Method: POST
Body: Factura
Status: 201 Created
Response: Factura
```

#### GET /facturas/usuario/{login}
Obtiene las facturas de un usuario.
```
Path: /facturas/usuario/user1
Status: 200 OK | 404 Not Found
Response: List<Factura>
```

---

### 6. Suscripciones

#### GET /suscripciones
Obtiene todos los tipos de suscripción.
```
Status: 200 OK
Response: List<TipoSuscripcion>
```

#### GET /suscripciones/{id}
Obtiene un tipo de suscripción específico.
```
Path: /suscripciones/1
Status: 200 OK | 404 Not Found
Response: TipoSuscripcion
```

#### POST /suscripciones
Crea un nuevo tipo de suscripción.
```
Method: POST
Body: TipoSuscripcion
Status: 201 Created
Response: TipoSuscripcion
```

---

### 7. Visualizaciones

#### GET /visualizaciones
Obtiene todas las visualizaciones.
```
Status: 200 OK
Response: List<Visualizacion>
```

#### POST /visualizaciones
Crea una nueva visualización.
```
Method: POST
Body: Visualizacion
Status: 201 Created
Response: Visualizacion
```

#### GET /visualizaciones/factura/{facturaId}
Obtiene las visualizaciones de una factura.
```
Path: /visualizaciones/factura/1
Status: 200 OK
Response: List<Visualizacion>
```

---

## Códigos de Estado HTTP

- **200 OK**: La solicitud ha tenido éxito
- **201 Created**: Recurso creado exitosamente
- **204 No Content**: Recurso eliminado exitosamente
- **400 Bad Request**: Datos inválidos
- **404 Not Found**: Recurso no encontrado
- **500 Internal Server Error**: Error del servidor

---

## Formatos de Respuesta

### Éxito
```json
{
  "id": 1,
  "titulo": "Breaking Bad",
  ...
}
```

### Error
```json
{
  "error": "Recurso no encontrado",
  "status": 404,
  "timestamp": "2026-04-17T10:30:00Z"
}
```
