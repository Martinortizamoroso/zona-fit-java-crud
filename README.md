# 🏋️ Zona Fit Gym - CRUD System

Sistema de gestión de clientes desarrollado en **Java 25**. Este proyecto implementa una arquitectura limpia separando la lógica de negocio de la persistencia de datos.

## 🌟 Características Destacadas
- **Arquitectura DAO:** Uso de interfaces para un código desacoplado y mantenible.
- **Seguridad:** Gestión de credenciales mediante variables de entorno (`.env`).
- **Optimización:** Implementación de *Dirty Checking* para evitar actualizaciones innecesarias en la DB.
- **Robustez:** Validación de buffers de entrada y manejo de excepciones SQL.

## 🛠️ Requisitos
- JDK 25+
- MySQL Server 8.0+
- Conector JDBC de MySQL

## ⚙️ Configuración
1. Clona el repositorio.
2. Crea una base de datos usando el archivo `schema.sql`.
3. Renombra el archivo `.env.example` a `.env` y coloca tus credenciales reales.
4. (Opcional) Configura las variables de entorno en tu IDE: `DB_USER`, `DB_PASS`.