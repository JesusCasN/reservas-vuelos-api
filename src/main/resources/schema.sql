-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
-- Host: localhost    Database: reservasvuelos

-- Eliminar tablas en orden inverso a las dependencias
DROP TABLE IF EXISTS boletos;
DROP TABLE IF EXISTS reservas;
DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS vuelos;

-- Crear tabla 'roles'
CREATE TABLE roles (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(50) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY nombre (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Crear tabla 'usuarios'
CREATE TABLE usuarios (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  password VARCHAR(255) NOT NULL,
  rol_id BIGINT NOT NULL,
  fecha_registro TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY email (email),
  KEY rol_id (rol_id),
  CONSTRAINT usuarios_ibfk_1 FOREIGN KEY (rol_id) REFERENCES roles (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Crear tabla 'vuelos'
CREATE TABLE vuelos (
  id BIGINT NOT NULL AUTO_INCREMENT,
  aerolinea VARCHAR(100) NOT NULL,
  origen VARCHAR(100) NOT NULL,
  destino VARCHAR(100) NOT NULL,
  fecha_salida DATETIME NOT NULL,
  fecha_llegada DATETIME NOT NULL,
  precio DECIMAL(38,2) NOT NULL,
  asientos_disponibles INT NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Crear tabla 'reservas'
CREATE TABLE reservas (
  id BIGINT NOT NULL AUTO_INCREMENT,
  usuario_id BIGINT NOT NULL,
  vuelo_id BIGINT NOT NULL,
  estado ENUM('PENDIENTE','CONFIRMADA','CANCELADA') DEFAULT 'PENDIENTE',
  fecha_reserva TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY usuario_id (usuario_id),
  KEY vuelo_id (vuelo_id),
  CONSTRAINT reservas_ibfk_1 FOREIGN KEY (usuario_id) REFERENCES usuarios (id) ON DELETE CASCADE,
  CONSTRAINT reservas_ibfk_2 FOREIGN KEY (vuelo_id) REFERENCES vuelos (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Crear tabla 'boletos'
CREATE TABLE boletos (
  id BIGINT NOT NULL AUTO_INCREMENT,
  reserva_id BIGINT NOT NULL,
  codigo VARCHAR(50) NOT NULL,
  pdf_url VARCHAR(255) DEFAULT NULL,
  fecha_emision TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY codigo (codigo),
  KEY reserva_id (reserva_id),
  CONSTRAINT boletos_ibfk_1 FOREIGN KEY (reserva_id) REFERENCES reservas (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
