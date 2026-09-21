# ToolRent - Sistema de Alquiler de Herramientas
 
ToolRent es una aplicación web de e-commerce para el alquiler de herramientas, desarrollada como Trabajo Práctico Obligatorio de la materia Aplicaciones Interactivas. Permite a los usuarios registrarse e iniciar sesión, publicar herramientas propias para alquilar (con foto, descripción y categoría), y explorar el catálogo ordenado alfabéticamente o filtrado por categoría. Cada usuario puede armar un pedido de alquiler indicando las herramientas y el período deseado; al confirmarlo, el sistema calcula el monto total y descuenta la disponibilidad de cada producto involucrado. El backend expone una API RESTful desarrollada con Spring Boot, organizada en capas (controller, service, repository, model, dto), con persistencia mediante Spring Data JPA sobre una base de datos relacional.
 
## Stack tecnológico
 
- Java + Spring Boot
- Spring Data JPA (Hibernate) para la capa de persistencia
- Lombok para reducir código repetitivo en modelos y DTOs
- Maven como gestor de dependencias y build
- Base de datos: H2 (entorno de desarrollo actual), con posibilidad de migrar a MySQL/PostgreSQL más adelante

## Arquitectura
 
El proyecto sigue una arquitectura en capas, replicada de forma consistente en cada módulo de dominio:
 
- **Controller** (`@RestController`): expone los endpoints REST de cada módulo.
- **Service** (`@Service`, `@Transactional`): contiene la lógica de negocio y las validaciones.
- **Repository** (`@Repository`, extiende `JpaRepository`): acceso a datos.
- **Model**: entidades JPA (`@Entity`) con sus relaciones (`@ManyToOne`, `@OneToMany`, `@ManyToMany`).
- **DTO**: objetos de transferencia que desacoplan las entidades de lo expuesto por la API.
- **Exception**: excepciones de negocio propias de cada módulo.

## Equipo
 
- CAIRO, MARIANA
- CERVERA, IGNACIO
- MANEIRO GEIER, MAURICIO ALEJANDRO
- MONTAÑEZ, AGUSTINA CLARA
