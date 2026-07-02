USE db_registros;

CREATE TABLE IF NOT EXISTS registros (
    matricula VARCHAR(20) PRIMARY KEY,
    nombre    VARCHAR(100) NOT NULL,
    materia   VARCHAR(100) NOT NULL,
    p1        DOUBLE NOT NULL DEFAULT 0,
    p2        DOUBLE NOT NULL DEFAULT 0,
    p3        DOUBLE NOT NULL DEFAULT 0
);

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

-- TABLA DE USUARIOS
CREATE TABLE IF NOT EXISTS usuarios (
    usuario           VARCHAR(50)  PRIMARY KEY,
    clave             VARCHAR(100) NOT NULL,
    nombre            VARCHAR(100),
    correo            VARCHAR(150) UNIQUE,
    activo            TINYINT(1)   NOT NULL DEFAULT 0,
    estado            VARCHAR(20)  NOT NULL DEFAULT 'pendiente',
    correo_verificado TINYINT(1)   NOT NULL DEFAULT 0,
    token_correo      VARCHAR(64)  NULL,
    es_admin          TINYINT(1)   NOT NULL DEFAULT 0,
    fecha_registro    DATETIME     DEFAULT CURRENT_TIMESTAMP
);

-- ────────────────────────────────────────────────────────────────
-- Para ACTUALIZACIÓN de una base de datos existente:
-- Si ya tenías la tabla 'usuarios' sin estas columnas, ejecuta
-- SOLO estos ALTER TABLE (una sola vez). Requiere MySQL 8.0+.
-- Si la columna ya existe no hace nada (IF NOT EXISTS).
-- ────────────────────────────────────────────────────────────────
ALTER TABLE usuarios ADD COLUMN IF NOT EXISTS estado            VARCHAR(20) NOT NULL DEFAULT 'pendiente';
ALTER TABLE usuarios ADD COLUMN IF NOT EXISTS correo_verificado TINYINT(1)  NOT NULL DEFAULT 0;
ALTER TABLE usuarios ADD COLUMN IF NOT EXISTS token_correo      VARCHAR(64) NULL;
ALTER TABLE usuarios ADD COLUMN IF NOT EXISTS es_admin          TINYINT(1)  NOT NULL DEFAULT 0;
ALTER TABLE usuarios ADD COLUMN IF NOT EXISTS fecha_registro    DATETIME    DEFAULT CURRENT_TIMESTAMP;

-- ────────────────────────────────────────────────────────────────
-- Ajustar usuarios ya existentes:
-- El admin queda activo, verificado y con rol de administrador.
-- Los demás usuarios que ya estaban en la DB quedan activos también.
-- ────────────────────────────────────────────────────────────────
UPDATE usuarios SET activo=1, estado='activo', correo_verificado=1, es_admin=1
    WHERE usuario='admin';

UPDATE usuarios SET activo=1, estado='activo', correo_verificado=1
    WHERE estado='pendiente' AND usuario != 'admin';

-- Usuario admin por defecto (solo se inserta si no existe)
INSERT IGNORE INTO usuarios (usuario, clave, nombre, correo, activo, estado, correo_verificado, es_admin)
    VALUES ('admin', '1234', 'Administrador', 'admin@escuela.edu.mx', 1, 'activo', 1, 1);
