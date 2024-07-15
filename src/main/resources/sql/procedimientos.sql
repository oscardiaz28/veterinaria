// Para listar las citas

DELIMITER //

CREATE PROCEDURE listarCitas()
BEGIN
    SELECT id_cita, cliente_dni, codigo_mascota, dni_trabajador, fecha_registro, fecha, hora, mensaje
    FROM Citas;
END //

DELIMITER ;
