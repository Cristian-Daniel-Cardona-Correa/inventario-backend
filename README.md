# Backend — Inventario API REST

API REST para gestión de inventario de productos, desarrollada con **Spring Boot 3** y **PostgreSQL**.

## Tecnologías

- Java 17
- Spring Boot 3.2.5
- Spring Data JPA / Hibernate
- PostgreSQL 16
- Gradle 9.x
- Springdoc OpenAPI (Swagger UI)
- Bean Validation

## Requisitos previos

- Java 17+
- Docker (para levantar PostgreSQL)
- Gradle 9.x (o usar el wrapper `./gradlew`)

## Configuración de la base de datos

```bash
docker run --name inventario-pg \
  -e POSTGRES_DB=inventario_db \
  -e POSTGRES_USER=inventario_user \
  -e POSTGRES_PASSWORD=inventario123 \
  -p 5432:5432 -d postgres:16
```

## Ejecutar el proyecto

```bash
cd inventario-backend
./gradlew bootRun
```

La API estará disponible en: `http://localhost:8080`

Swagger UI: `http://localhost:8080/swagger-ui.html`

## Base de datos — Entidad Producto

| Campo       | Tipo       | Descripción                     |
|-------------|------------|---------------------------------|
| id          | Long       | Identificador único (auto)      |
| nombre      | String     | Nombre del producto             |
| codigo      | String     | Código único del producto       |
| descripcion | String     | Descripción (opcional)          |
| precio      | BigDecimal | Precio unitario (> 0)           |
| cantidad    | Integer    | Unidades disponibles (>= 0)     |
| categoria   | String     | Categoría del producto          |

## Endpoints

| Método | Endpoint              | Descripción                    | Código exitoso |
|--------|-----------------------|--------------------------------|----------------|
| GET    | /api/productos        | Listar todos los productos     | 200 OK         |
| GET    | /api/productos/{id}   | Obtener producto por ID        | 200 OK         |
| POST   | /api/productos        | Crear nuevo producto           | 201 Created    |
| PUT    | /api/productos/{id}   | Actualizar producto existente  | 200 OK         |
| DELETE | /api/productos/{id}   | Eliminar producto              | 204 No Content |

## Ejemplos de peticiones JSON

### POST /api/productos
```json
{
  "nombre": "Laptop Dell Inspiron",
  "codigo": "LAP-001",
  "descripcion": "Laptop con Intel Core i5, 8GB RAM",
  "precio": 2499000.00,
  "cantidad": 15,
  "categoria": "Computadores"
}
```

### Respuesta 201 Created
```json
{
  "id": 1,
  "nombre": "Laptop Dell Inspiron",
  "codigo": "LAP-001",
  "descripcion": "Laptop con Intel Core i5, 8GB RAM",
  "precio": 2499000.00,
  "cantidad": 15,
  "categoria": "Computadores"
}
```

### Respuesta 404 Not Found
```json
{
  "timestamp": "2026-01-01T10:00:00",
  "status": 404,
  "error": "No encontrado",
  "message": "Producto con id 99 no encontrado"
}
```

### Respuesta 422 Unprocessable Entity (validación)
```json
{
  "timestamp": "2026-01-01T10:00:00",
  "status": 422,
  "error": "Error de validación",
  "errores": {
    "nombre": "El nombre es obligatorio",
    "precio": "El precio debe ser mayor a 0"
  }
}
```

## Ejecutar datos de prueba

Al arrancar el proyecto por primera vez, ejecutar el script SQL:

```bash
psql -h localhost -U inventario_user -d inventario_db -f src/main/resources/data.sql
```
