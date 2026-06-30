-- Script para crear la base de datos y tabla del proyecto 003_Ejemplo_Form_Servlet

CREATE DATABASE IF NOT EXISTS db_registros
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_general_ci;

USE db_registros;

CREATE TABLE IF NOT EXISTS registros (
    matricula VARCHAR(20) PRIMARY KEY,
    nombre    VARCHAR(100) NOT NULL,
    materia   VARCHAR(100) NOT NULL,
    p1        DOUBLE NOT NULL DEFAULT 0,
    p2        DOUBLE NOT NULL DEFAULT 0,
    p3        DOUBLE NOT NULL DEFAULT 0
);

-- Datos de prueba iniciales
INSERT IGNORE INTO registros VALUES ('A001', 'Juan Perez',  'Matematicas', 8.5, 9.0, 7.5);
INSERT IGNORE INTO registros VALUES ('A002', 'Maria Lopez', 'Fisica',      9.0, 8.5, 9.5);

-- Tabla para el módulo de Alumnos
CREATE TABLE IF NOT EXISTS alumnos (
    nl      INT          PRIMARY KEY,
    nombre  VARCHAR(100) NOT NULL,
    paterno VARCHAR(100) NOT NULL,
    materno VARCHAR(100) NOT NULL
);

INSERT IGNORE INTO alumnos VALUES (1, 'Abel',    'Jerónimo',   'Vargas');
INSERT IGNORE INTO alumnos VALUES (2, 'Araceli', 'Salazar',    'Jiménez');
INSERT IGNORE INTO alumnos VALUES (3, 'Filemón', 'Casarrubias','Kito');

-- Tabla de usuarios para el inicio de sesión
CREATE TABLE IF NOT EXISTS usuarios (
    usuario VARCHAR(50)  PRIMARY KEY,
    clave   VARCHAR(100) NOT NULL,
    nombre  VARCHAR(100),
    correo  VARCHAR(150) UNIQUE,
    activo  TINYINT(1)   NOT NULL DEFAULT 1
);

-- Si la tabla ya existía sin las columnas nuevas, ejecuta:
-- ALTER TABLE usuarios ADD COLUMN nombre VARCHAR(100);
-- ALTER TABLE usuarios ADD COLUMN correo VARCHAR(150) UNIQUE;
-- ALTER TABLE usuarios ADD COLUMN activo TINYINT(1) NOT NULL DEFAULT 1;

INSERT IGNORE INTO usuarios (usuario, clave, nombre, correo, activo)
    VALUES ('admin',    '1234',       'Administrador', 'admin@escuela.edu.mx',    1);
INSERT IGNORE INTO usuarios (usuario, clave, nombre, correo, activo)
    VALUES ('profesor', 'clase2024',  'Profesor',      'profesor@escuela.edu.mx', 1);
