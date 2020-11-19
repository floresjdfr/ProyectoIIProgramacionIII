USE `bd_chat`;

DELETE FROM `bd_chat`.`usuario`;
INSERT INTO  `bd_chat`.`usuario`
	(nombre, nombre_completo, clave, ultimoAccesso)
	VALUES
	('jdflores', 'Jose David Flores Rodriguez', '123', '2020-11-19 00:00:00'),
	('mari12', 'Maria Alvarado', '123', '2020-11-19 00:00:00'),
	('marv00', 'Marvin Aguilar Fuentes', '123', '2020-11-19 00:00:00'),
	('Pablo33', 'Pablo Brenes', '123', '2020-11-19 00:00:00')
	;

SELECT * FROM `bd_chat`.`usuario`