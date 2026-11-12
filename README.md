# Backend — Inventario API REST con Autenticación JWT

API REST para gestión de inventario de productos, desarrollada con **Spring Boot 3**, **Spring Security**, **PostgreSQL** y protegida con **JSON Web Token (JWT)**.

## Tecnologías

- Java 17
- Spring Boot 3.2.5
- Spring Security 6 (Stateless JWT Filter)
- JJWT (io.jsonwebtoken 0.12.5)
- Spring Data JPA / Hibernate
- PostgreSQL 16
- Gradle 9.x
- Springdoc OpenAPI 3 (Swagger UI con Bearer Auth)
- Bean Validation

## Requisitos previos

- Java 17+
- PostgreSQL local o contenedor Docker
- Gradle 9.x (o `./gradlew`)

## Configuración de la base de datos

```bash
# Si se utiliza Docker:
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

Swagger UI interactivo con soporte JWT: `http://localhost:8080/swagger-ui.html`

---

## 🔐 Autenticación JWT

El sistema cuenta con endpoints públicos para autenticación y protege todas las operaciones CRUD de productos. Al autenticarse, el servidor retorna un token JWT firmado válido por 24 horas.

### Usuarios de Prueba Precargados

Al iniciar la aplicación por primera vez, `DataInitializer` registra automáticamente los siguientes usuarios en la base de datos con contraseñas encriptadas mediante **BCrypt**:

| Usuario | Contraseña | Nombre Completo | Correo | Rol |
|---------|------------|-----------------|--------|-----|
| `admin` | `admin123` | Administrador del Sistema | `admin@inventario.com` | `ROLE_ADMIN` |
| `empleado` | `empleado123` | Carlos Gómez | `empleado@inventario.com` | `ROLE_USER` |
| `profesor` | `uceva2026` | David Steven Durán Vallejo | `dduran@uceva.edu.co` | `ROLE_ADMIN` |

---

## Endpoints de la API

### 1. Endpoints Públicos de Autenticación (`/api/auth`)

| Método | Endpoint | Descripción | Código Exitoso |
|--------|----------|-------------|----------------|
| `POST` | `/api/auth/login` | Inicia sesión con `username` (o correo) y `password`. Retorna token JWT. | 200 OK (o 401 Unauthorized) |
| `POST` | `/api/auth/registro` | Registra una nueva cuenta de usuario con contraseña BCrypt. | 201 Created (o 422 Conflict) |
| `GET`  | `/api/auth/perfil` | Obtiene la información del usuario autenticado actual. | 200 OK (requiere Bearer Token) |

#### Ejemplo de Login (`POST /api/auth/login`):
```json
{
  "username": "admin",
  "password": "admin123"
}
```

#### Respuesta 200 OK:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImVtYWlsIjoiYWRtaW5AaW52ZW50YXJpby5jb20iLCJub21icmUiOiJBZG1pbmlzdHJhZG9yIGRlbCBTaXN0ZW1hIiwicm9sIjoiUk9MRV9BRE1JTiIsImlhdCI6MTczMTM2OTAwMCwiZXhwIjoxNzMxNDU1NDAwfQ.xyz...",
  "type": "Bearer",
  "username": "admin",
  "email": "admin@inventario.com",
  "nombre": "Administrador del Sistema",
  "rol": "ROLE_ADMIN"
}
```

#### Respuesta 401 Unauthorized (Credenciales incorrectas):
```json
{
  "timestamp": "2026-11-11T19:00:00",
  "status": 401,
  "error": "Credenciales incorrectas",
  "message": "Usuario o contraseña inválidos. Verifique sus datos."
}
```

---

### 2. Endpoints Protegidos de Productos (`/api/productos`)

> **Importante:** Cualquier petición a estos endpoints debe incluir la cabecera:  
> `Authorization: Bearer <token_jwt>`  
> Si se envía sin token o con un token inválido/expirado, la API responderá con código **HTTP 401 Unauthorized**.

| Método | Endpoint | Descripción | Requiere Auth | Código Exitoso |
|--------|----------|-------------|:-------------:|----------------|
| `GET` | `/api/productos` | Listar todos los productos | **Sí** | 200 OK / 401 |
| `GET` | `/api/productos/{id}` | Obtener detalle de un producto | **Sí** | 200 OK / 401 / 404 |
| `POST` | `/api/productos` | Registrar nuevo producto | **Sí** | 201 Created / 401 / 422 |
| `PUT` | `/api/productos/{id}` | Actualizar producto existente | **Sí** | 200 OK / 401 / 404 / 422 |
| `DELETE` | `/api/productos/{id}` | Eliminar producto | **Sí** | 204 No Content / 401 / 404 |
| `POST` | `/api/productos/upload-imagen` | Subir archivo de imagen multipart | **No** (público) | 200 OK |

---

## Probar con cURL

### 1. Intentar acceder a productos SIN token (Rechazado con 401):
```bash
curl -i http://localhost:8080/api/productos
# Respuesta: HTTP/1.1 401 Unauthorized
```

### 2. Obtener Token JWT (Login):
```bash
TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}' | grep -o '"token":"[^"]*' | cut -d'"' -f4)

echo "Token obtenido: $TOKEN"
```

### 3. Consultar productos CON token:
```bash
curl -s http://localhost:8080/api/productos \
  -H "Authorization: Bearer $TOKEN"
```
