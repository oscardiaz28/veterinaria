CREATE DATABASE  IF NOT EXISTS bd_veterinaria  /*!80016 DEFAULT ENCRYPTION='N' */;
USE bd_veterinaria;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: bd_veterinaria
-- ------------------------------------------------------
-- Server version	8.0.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table categoria
--

DROP TABLE IF EXISTS categoria;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE categoria (
  id_categoria int NOT NULL AUTO_INCREMENT,
  nombre varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  img varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (id_categoria)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table categoria
--

LOCK TABLES categoria WRITE;
/*!40000 ALTER TABLE categoria DISABLE KEYS */;
INSERT INTO categoria VALUES (5,'Alimento para Perros','alimentos_perros.png'),(6,'Juguetes para Gatos','juguetes_gatos.png'),(7,'Medicamentos','medicamentos.jpeg'),(8,'Accesorios','accesorios.jpg'),(9,'Camas','camas.jpg'),(10,'Juguetes para Perros','juguetes_perros.png'),(11,'Alimentos para Gatos','alimentos_gatos.jpg');
/*!40000 ALTER TABLE categoria ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table cita
--

DROP TABLE IF EXISTS cita;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE cita (
  id_cita int NOT NULL AUTO_INCREMENT,
  cliente_dni char(8) COLLATE utf8mb4_general_ci NOT NULL,
  codigo_mascota int DEFAULT NULL,
  dni_trabajador char(8) COLLATE utf8mb4_general_ci DEFAULT NULL,
  fecha_registro date NOT NULL,
  fecha date DEFAULT NULL,
  hora time NOT NULL,
  mensaje mediumtext COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (id_cita),
  KEY dni_cliente (cliente_dni),
  KEY dni_trabajador (dni_trabajador),
  KEY codigo_mascota (codigo_mascota),
  CONSTRAINT cita_ibfk_1 FOREIGN KEY (codigo_mascota) REFERENCES mascota (codigo),
  CONSTRAINT cita_ibfk_2 FOREIGN KEY (cliente_dni) REFERENCES cliente (dni_cliente),
  CONSTRAINT cita_ibfk_3 FOREIGN KEY (dni_trabajador) REFERENCES trabajador (dni_trabajador)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table cita
--

LOCK TABLES cita WRITE;
/*!40000 ALTER TABLE cita DISABLE KEYS */;
INSERT INTO cita VALUES (3,'43256789',NULL,NULL,'2024-07-20',NULL,'10:00:00','LLego antes de la hora'),(4,'43256789',NULL,NULL,'2024-07-26',NULL,'13:00:00','LLego antes de la hora'),(5,'43256789',NULL,NULL,'2024-07-19',NULL,'16:00:00','Ire antes de la hora indicada'),(6,'43256789',NULL,NULL,'2024-07-31',NULL,'12:00:00','Ire antes  de la hora indicada'),(7,'43256789',NULL,NULL,'2024-07-10',NULL,'15:00:00','Tiene 3 meses , nececita su primera vacuna');
/*!40000 ALTER TABLE cita ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table cita_servicio
--

DROP TABLE IF EXISTS cita_servicio;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE cita_servicio (
  id int NOT NULL AUTO_INCREMENT,
  cita_id int NOT NULL,
  servicio_id int NOT NULL,
  estado varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (id),
  KEY cita_id (cita_id),
  KEY servicio_id (servicio_id),
  CONSTRAINT cita_servicio_ibfk_1 FOREIGN KEY (servicio_id) REFERENCES servicio (id_servicio),
  CONSTRAINT cita_servicio_ibfk_2 FOREIGN KEY (cita_id) REFERENCES cita (id_cita)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table cita_servicio
--

LOCK TABLES cita_servicio WRITE;
/*!40000 ALTER TABLE cita_servicio DISABLE KEYS */;
/*!40000 ALTER TABLE cita_servicio ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table cliente
--

DROP TABLE IF EXISTS cliente;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE cliente (
  dni_cliente char(8) COLLATE utf8mb4_general_ci NOT NULL,
  codigo_mascota int DEFAULT NULL,
  usuario_id int NOT NULL,
  nombre varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  apellidos varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  direccion varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  celular char(9) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (dni_cliente),
  KEY usuario_id (usuario_id),
  KEY codigo_mascota (codigo_mascota),
  CONSTRAINT cliente_ibfk_1 FOREIGN KEY (usuario_id) REFERENCES usuario (id),
  CONSTRAINT cliente_ibfk_2 FOREIGN KEY (codigo_mascota) REFERENCES mascota (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table cliente
--

LOCK TABLES cliente WRITE;
/*!40000 ALTER TABLE cliente DISABLE KEYS */;
/*!40000 ALTER TABLE cliente ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table detalle_venta
--

DROP TABLE IF EXISTS detalle_venta;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE detalle_venta (
  codigo_detalle int NOT NULL AUTO_INCREMENT,
  codigo_venta int NOT NULL,
  codigo_producto int NOT NULL,
  cantidad int NOT NULL,
  precio_unitario double NOT NULL,
  subtotal double NOT NULL,
  estado varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (codigo_detalle),
  KEY codigo_venta (codigo_venta),
  KEY dni_trabajador (codigo_producto),
  KEY codigo_producto (codigo_producto),
  CONSTRAINT detalle_venta_ibfk_1 FOREIGN KEY (codigo_producto) REFERENCES producto (codigo_producto),
  CONSTRAINT detalle_venta_ibfk_2 FOREIGN KEY (codigo_venta) REFERENCES venta (codigo_venta)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table detalle_venta
--

LOCK TABLES detalle_venta WRITE;
/*!40000 ALTER TABLE detalle_venta DISABLE KEYS */;
/*!40000 ALTER TABLE detalle_venta ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table mascota
--

DROP TABLE IF EXISTS mascota;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE mascota (
  codigo int NOT NULL AUTO_INCREMENT,
  nombre varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  especie varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  raza varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  edad varchar(35) COLLATE utf8mb4_general_ci NOT NULL,
  genero char(10) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'hembra o macho',
  foto varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (codigo)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table mascota
--

LOCK TABLES mascota WRITE;
/*!40000 ALTER TABLE mascota DISABLE KEYS */;
/*!40000 ALTER TABLE mascota ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table producto
--

DROP TABLE IF EXISTS producto;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE producto (
  codigo_producto int NOT NULL AUTO_INCREMENT,
  id_categoria int NOT NULL,
  nombre varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  descripcion varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  precio decimal(10,2) NOT NULL,
  stock int NOT NULL,
  imagen varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (codigo_producto),
  KEY id_categoria (id_categoria),
  CONSTRAINT producto_ibfk_1 FOREIGN KEY (id_categoria) REFERENCES categoria (id_categoria)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table producto
--

LOCK TABLES producto WRITE;
/*!40000 ALTER TABLE producto DISABLE KEYS */;
INSERT INTO producto VALUES (4,10,'Hueso ','Este juguete es perfecto para perros de todas las edades',27.00,10,'hueso.png'),(5,6,'Lana','juguete',25.00,3,'lana.jpg');
/*!40000 ALTER TABLE producto ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table servicio
--

DROP TABLE IF EXISTS servicio;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE servicio (
  id_servicio int NOT NULL AUTO_INCREMENT,
  nombre varchar(80) COLLATE utf8mb4_general_ci NOT NULL,
  descripcion varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  precio decimal(10,2) NOT NULL,
  imagen varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  estado varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (id_servicio)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table servicio
--

LOCK TABLES servicio WRITE;
/*!40000 ALTER TABLE servicio DISABLE KEYS */;
INSERT INTO servicio VALUES (1,'Vacunacion','Priemera Vacuna',12.00,'sd','Activo'),(2,'Baño','Limpieza',15.00,'sd','Activo');
/*!40000 ALTER TABLE servicio ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table trabajador
--

DROP TABLE IF EXISTS trabajador;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE trabajador (
  dni_trabajador char(8) COLLATE utf8mb4_general_ci NOT NULL,
  usuario_id int NOT NULL,
  nombre varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  apellidos varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  cargo varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  salario decimal(10,2) NOT NULL,
  direccion varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  celular char(9) COLLATE utf8mb4_general_ci NOT NULL,
  fecha_contratacion date NOT NULL,
  estado varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'activo, inactivo',
  PRIMARY KEY (dni_trabajador),
  KEY usuario_id (usuario_id),
  CONSTRAINT trabajador_ibfk_1 FOREIGN KEY (usuario_id) REFERENCES usuario (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table trabajador
--

LOCK TABLES trabajador WRITE;
/*!40000 ALTER TABLE trabajador DISABLE KEYS */;
INSERT INTO trabajador VALUES ('0123456',1,'Franco','Carrasco Mendez','Medico',1580.00,'AV.Chiclayo','987654321','2010-10-10','Activo'),('12345678',2,'Fabricio','Santa Cruz','Doctor',1805.00,'Av.Balta','987654321','2024-07-01','Activo'),('78450589',13,'Francisco Martin','Collantes Aquino','Doctor',1985.56,'Av.Balta','987451258','2024-07-04','Activo'),('7845798',36,'Marco Alonso','Sanchez  Mendoza','Doctor',1542.00,'Calle Tacna 605','945143895','2024-07-09','Activo'),('7895000',33,'Marco','Aquino','Doctor',1520.00,'Av.Balta','9874524','2024-07-05','Activo');
/*!40000 ALTER TABLE trabajador ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table usuario
--

DROP TABLE IF EXISTS usuario;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE usuario (
  id int NOT NULL AUTO_INCREMENT,
  email varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  password varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  token varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  estado varchar(45) COLLATE utf8mb4_general_ci DEFAULT NULL,
  rol varchar(45) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table usuario
--

LOCK TABLES usuario WRITE;
/*!40000 ALTER TABLE usuario DISABLE KEYS */;
/*!40000 ALTER TABLE usuario ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table venta
--

DROP TABLE IF EXISTS venta;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE venta (
  codigo_venta int NOT NULL AUTO_INCREMENT,
  cliente_dni char(8) COLLATE utf8mb4_general_ci NOT NULL,
  trabajador_dni char(8) COLLATE utf8mb4_general_ci NOT NULL,
  fecha date NOT NULL,
  metodo_pago varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (codigo_venta),
  KEY cliente_dni (cliente_dni),
  KEY trabajador_dni (trabajador_dni),
  CONSTRAINT venta_ibfk_1 FOREIGN KEY (cliente_dni) REFERENCES cliente (dni_cliente),
  CONSTRAINT venta_ibfk_2 FOREIGN KEY (trabajador_dni) REFERENCES trabajador (dni_trabajador)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table venta
--

LOCK TABLES venta WRITE;
/*!40000 ALTER TABLE venta DISABLE KEYS */;
/*!40000 ALTER TABLE venta ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'bd_veterinaria'
--

--
-- Dumping routines for database 'bd_veterinaria'
--
/*!50003 DROP PROCEDURE IF EXISTS ListarCategoria */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
CREATE DEFINER=root@localhost PROCEDURE ListarCategoria()
BEGIN
SELECT id_categoria,nombre,img FROM categoria;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS listarMascotas */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
CREATE DEFINER=root@localhost PROCEDURE listarMascotas()
BEGIN
    SELECT codigo, nombre, especie, raza, edad, genero, foto FROM mascota;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS listarProductos */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
CREATE DEFINER=root@localhost PROCEDURE listarProductos()
BEGIN
	select p.codigo_producto as "producto_id", p.nombre, p.descripcion, p.precio, p.imagen as "producto_imagen", c.id_categoria as "categoria_id",
	p.stock, c.nombre as "categoria_nombre", c.img as "categoria_foto"  
	from producto p inner join categoria c on p.id_categoria = c.id_categoria;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS ListarUsuario */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;




/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
CREATE DEFINER=root@localhost PROCEDURE ListarUsuario()
BEGIN
SELECT id, email, token,password, estado FROM usuario;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS registrarCategoria */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
CREATE DEFINER=root@localhost PROCEDURE registrarCategoria(IN p_nombre VARCHAR(100), IN p_foto VARCHAR(100))
BEGIN
	INSERT INTO categoria(nombre, img) VALUES(p_nombre, p_foto);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS registrarCliente */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
CREATE DEFINER=root@localhost PROCEDURE registrarCliente(
    IN p_dni_cliente CHAR(8),
    IN p_codigo_mascota INT,
    IN p_usuario_id INT,
    IN p_nombre VARCHAR(50),
    IN p_apellidos VARCHAR(50),
    IN p_direccion VARCHAR(50),
    IN p_celular CHAR(9)
)
BEGIN
    INSERT INTO cliente (dni_cliente, codigo_mascota, usuario_id, nombre, apellidos, direccion, celular)
    VALUES (p_dni_cliente, p_codigo_mascota, p_usuario_id, p_nombre, p_apellidos, p_direccion, p_celular);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS registrarMascota */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=root@localhost PROCEDURE registrarMascota(
    IN nombre VARCHAR(50),
    IN especie VARCHAR(50),
    IN raza VARCHAR(50),
    IN edad VARCHAR(50),
    IN genero VARCHAR(50),
    IN foto VARCHAR(255),
    OUT codigoMascota INT
)
BEGIN
    INSERT INTO Mascota (Nombre, Especie, Raza, Edad, Genero, Foto)
    VALUES (nombre, especie, raza, edad, genero, foto);
    SET codigoMascota = LAST_INSERT_ID();
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS registrarProducto */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
CREATE DEFINER=root@localhost PROCEDURE registrarProducto(IN p_categoria INT, IN p_nombre VARCHAR(50), IN p_descripcion VARCHAR(100), IN p_precio DECIMAL(10,2), IN p_stock INT, IN p_imagen VARCHAR(100))
BEGIN
     INSERT INTO producto (id_categoria, nombre, descripcion, precio, stock, imagen)
    VALUES (p_categoria, p_nombre, p_descripcion, p_precio, p_stock, p_imagen);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS registrarUsuario */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
CREATE DEFINER=root@localhost PROCEDURE registrarUsuario(IN p_email VARCHAR(100), IN p_password VARCHAR(100), IN p_estado VARCHAR(45), OUT p_id INT)
BEGIN
    INSERT INTO usuario (email, password, token, estado)
    VALUES (p_email, p_password, 0, p_estado);
    SET p_id = LAST_INSERT_ID();
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS registrar_trabajador */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
CREATE DEFINER=root@localhost PROCEDURE registrar_trabajador(IN p_dni_trabajador CHAR(8), IN p_usuario_id INT, IN p_nombre VARCHAR(50), IN p_apellidos VARCHAR(100), IN p_cargo VARCHAR(50), IN p_salario DECIMAL(10,2), IN p_direccion VARCHAR(50), IN p_celular VARCHAR(9), IN p_fecha_contratacion DATE, IN p_estado VARCHAR(50))
BEGIN
    INSERT INTO trabajador (
        dni_trabajador, 
        usuario_id, 
        nombre, 
        apellidos, 
        cargo, 
        salario, 
        direccion, 
        celular, 
        fecha_contratacion, 
        estado
    ) VALUES (
        p_dni_trabajador, 
        p_usuario_id, 
        p_nombre, 
        p_apellidos, 
        p_cargo, 
        p_salario, 
        p_direccion, 
        p_celular, 
        p_fecha_contratacion, 
        p_estado
    );
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-13 13:42:37