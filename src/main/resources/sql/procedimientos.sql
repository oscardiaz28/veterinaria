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

// Para listar las citas

DELIMITER //

CREATE PROCEDURE listarCitas()
BEGIN
    SELECT id_cita, cliente_dni, codigo_mascota, dni_trabajador, fecha_registro, fecha, hora, mensaje
    FROM cita;
END //

DELIMITER ;

CREATE DEFINER=`root`@`localhost` PROCEDURE `registrarVenta`(
    IN p_cliente_dni CHAR(8),
    IN p_trabajador_dni CHAR(8),
    IN p_fecha DATE,
    IN p_metodo_pago VARCHAR(100),
    OUT p_codigo_venta INT
)
BEGIN
    INSERT INTO venta (cliente_dni, trabajador_dni, fecha, metodo_pago) VALUES (p_cliente_dni, p_trabajador_dni,p_fecha,p_metodo_pago);
    SET p_codigo_venta = LAST_INSERT_ID();
END

Procedimiento para listar citas servicios

DELIMITER //

CREATE PROCEDURE listarCitaServicios()
BEGIN
    SELECT id, cita_id, servicio_id, estado
    FROM cita_servicio;
END //

DELIMITER ;
-- Dump completed on 2024-07-13 13:42:37