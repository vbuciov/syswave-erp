CREATE TABLE IF NOT EXISTS `miempresa`.`condiciones` (
  `id` INT NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `es_activo` BIT NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `miempresa`.`condicion_componentes` (
  `id` INT NOT NULL,
  `valor` INT NOT NULL,
  `es_unidad` SMALLINT NOT NULL,
  `es_tipo` SMALLINT NOT NULL,
  `id_condicion` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_condicion_componentes_idx_condicion` (`id_condicion` ASC),
  CONSTRAINT `fk_condicion_componentes_id_condicion`
    FOREIGN KEY (`id_condicion`)
    REFERENCES `miempresa`.`condiciones` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB




CREATE DEFINER=`root`@`localhost` PROCEDURE `condicion_insert`(
  in vnombre varchar(45), 
  in ves_activo bit
)
BEGIN
  insert into condiciones(nombre, es_activo) values
  (vnombre, ves_activo);
  
  select id, nombre, es_activo from condiciones
  WHERE id = LAST_INSERT_ID();
END