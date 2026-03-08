# 🐾 VetTriage — Sistema de Triaje Inteligente para Clínicas de Mascotas

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.11-brightgreen?style=for-the-badge&logo=springboot)
![React](https://img.shields.io/badge/React-18-61DAFB?style=for-the-badge&logo=react)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-18-316192?style=for-the-badge&logo=postgresql)
![JWT](https://img.shields.io/badge/JWT-Auth-black?style=for-the-badge&logo=jsonwebtokens)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI_3-85EA2D?style=for-the-badge&logo=swagger)

> Sistema web completo para la gestión de triaje médico en clínicas veterinarias. Clasifica automáticamente la urgencia de cada paciente, asigna doctores disponibles y genera reportes clínicos en PDF.

---

## 📸 Capturas de Pantalla

| Dashboard | Triaje | Pacientes |
|-----------|--------|-----------|
| ![Dashboard](https://placehold.co/300x180?text=Dashboard) | ![Triage](https://placehold.co/300x180?text=Triaje) | ![Patients](https://placehold.co/300x180?text=Pacientes) |

---

## ✨ Funcionalidades

- 🔴🟡🟢 **Motor de Triaje Inteligente** — Clasifica automáticamente en URGENTE, MODERADO o LEVE según los síntomas seleccionados y sus pesos de gravedad
- 🩺 **Asignación Automática de Doctor** — Los casos urgentes se dirigen a Cirugía Veterinaria; los demás al primer doctor disponible
- 👤 **Gestión Completa de Pacientes** — CRUD con búsqueda en tiempo real
- 📋 **Historial Clínico** — Registro detallado con filtros por fecha y doctor
- 📄 **Generación de PDF** — Reportes clínicos profesionales por paciente
- 🔐 **Autenticación JWT** — 3 roles: ADMIN, DOCTOR, RECEPCIONISTA
- 🔑 **Cambiar Contraseña** — Gestión segura desde el propio sistema
- 📘 **API Documentada** — Swagger UI integrado con autenticación Bearer

---

## 🛠️ Stack Tecnológico

### Backend
| Tecnología | Versión | Uso |
|---|---|---|
| Java | 21 | Lenguaje principal |
| Spring Boot | 3.5.11 | Framework backend |
| Spring Security | 6.5 | Autenticación y autorización |
| JWT (jjwt) | 0.11.5 | Tokens de sesión |
| PostgreSQL | 18 | Base de datos |
| Hibernate / JPA | 6.6 | ORM |
| iText PDF | 5.5.13 | Generación de reportes |
| SpringDoc OpenAPI | 2.8.6 | Documentación Swagger |

### Frontend
| Tecnología | Versión | Uso |
|---|---|---|
| React | 18 | Librería UI |
| Vite | 5 | Bundler |
| Tailwind CSS | 3 | Estilos |
| Axios | 1.6 | Cliente HTTP |
| React Router | 6 | Navegación |
| Lucide React | 0.263 | Íconos |
| Recharts | 2 | Gráficas del dashboard |

---

## 🚀 Instalación y Ejecución

### Prerrequisitos
- Java 17+
- Node.js 20 LTS
- PostgreSQL 14+
- Maven 3.8+

### 1. Clonar el repositorio
```bash
git clone https://github.com/tu-usuario/vettriage.git
cd vettriage
```

### 2. Configurar la base de datos
Crear una base de datos en PostgreSQL:
```sql
CREATE DATABASE triage_mascotas;
```

### 3. Configurar el Backend
Editar `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/triage_mascotas
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD
```

### 4. Ejecutar el Backend
```bash
cd triage-mascotas
mvn spring-boot:run
```
El backend estará disponible en `http://localhost:8080`

### 5. Ejecutar el Frontend
```bash
cd frontend-triage
npm install
npm run dev
```
El frontend estará disponible en `http://localhost:5173`

---

## 👥 Credenciales de Prueba

| Usuario | Contraseña | Rol |
|---|---|---|
| `admin` | `admin123` | ADMIN |
| `doctor1` | `doctor123` | DOCTOR |
| `recepcion` | `recep123` | RECEPCIONISTA |

---

## 📘 Documentación de la API

Con el backend corriendo, accede a la documentación interactiva:

```
http://localhost:8080/swagger-ui/index.html
```

Para autenticarte en Swagger:
1. Ejecuta `POST /api/auth/login` con tus credenciales
2. Copia el token recibido
3. Haz clic en **Authorize** 🔒 e ingresa: `Bearer <tu-token>`

---

## 🗂️ Estructura del Proyecto

```
vettriage/
├── triage-mascotas/                  # Backend Spring Boot
│   └── src/main/java/com/clinica/
│       ├── config/                   # DataInitializer, SwaggerConfig
│       ├── controller/               # REST Controllers
│       ├── model/                    # Entidades JPA
│       ├── repository/               # JPA Repositories
│       ├── security/                 # JWT Filter, SecurityConfig
│       └── service/                  # Lógica de negocio
│
└── frontend-triage/                  # Frontend React
    └── src/
        ├── api/api.js                # Axios + interceptors JWT
        ├── pages/
        │   ├── Dashboard.jsx         # Estadísticas y gráficas
        │   ├── Pacientes.jsx         # CRUD de pacientes
        │   ├── Triage.jsx            # Motor de triaje
        │   ├── Historial.jsx         # Historial clínico
        │   ├── Doctores.jsx          # Gestión de consultas
        │   └── Login.jsx             # Autenticación
        └── App.jsx                   # Layout + rutas protegidas
```

---

## 🧠 Lógica del Motor de Triaje

El sistema evalúa los síntomas seleccionados mediante un sistema de puntaje ponderado:

| Clasificación | Puntaje | Acción |
|---|---|---|
| 🔴 URGENTE | ≥ 15 puntos | Asigna a Cirugía Veterinaria |
| 🟡 MODERADO | 7 – 14 puntos | Asigna al primer doctor disponible |
| 🟢 LEVE | < 7 puntos | Asigna al primer doctor disponible |

Los síntomas están organizados en 5 categorías: **RESPIRATORIO**, **DIGESTIVO**, **NEUROLÓGICO**, **TRAUMÁTICO** y **GENERAL**, con pesos del 1 al 10.

---

## 🔐 Seguridad

- Autenticación stateless con **JWT**
- Tokens con expiración de **24 horas**
- Validación de token en cada request vía `JwtFilter`
- CORS configurado para `localhost:5173`
- Rutas de Swagger públicas, resto protegidas

---

## 📄 Licencia

Este proyecto está bajo la licencia **MIT**. Consulta el archivo [LICENSE](LICENSE) para más detalles.

---

## 👨‍💻 Manuel Chirinos Tumba

Desarrollado con ❤️ para el portafolio profesional.

⭐ Si te gustó el proyecto, ¡dale una estrella en GitHub!
