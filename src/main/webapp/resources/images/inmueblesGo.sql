
INSERT INTO `tercero` (`id`, `version`, `ter_apellido1`, `ter_apellido2`, `ter_direccion`, `ter_email`, `ter_identificacion`, `ter_nombres`, `ter_telefono`, `TIPO_IDENTIFICACION_ID`, `LUGAR_EXPEDICION_ID`) VALUES
(3, 0, 'CAMARGO', 'LOZANO', 'CRA 5 # 34-50', 'cgalianza@hotmail.com', '73132095', 'JAVIER RAFAEL', '7827750', 1, NULL),
(4, 1, 'CHOPERENA', 'GARNICA', 'CRA 10 # 22A-49 BARRIO CENTRO DE MONTERIA', NULL, '25804697', 'NANCY GERONIMA', '7827919 - 3016350045', 1, NULL),
(5, 0, 'OTALVARO', 'SEPULVEDA', 'CALLE 61-A #1328 ALTOS DE CASTILLA 1RA ETAPA', 'ani_337@hotmail.com', '41558313', 'ANA CARLINA', '7852813 - 3008437254', 1, NULL),
(6, 0, 'ESPINOSA', 'PASTRANA', 'CALLE 22 CRA 66 EDIFICIO SANTÉ APT 404 BARRIO CUCHURUBÍ', 'lep_02@hotmail.com', '6872717', 'LUZ ESTHER', '3017867431', 1, NULL);

-- ---------


INSERT INTO `empresa` (`id`, `version`, `emp_direccion`, `emp_email`, `emp_email_host`, `emp_email_pass`, `emp_email_port`, `emp_logo`, `emp_nit`, `emp_nombre`, `emp_telefono`, `POBLADO_ID`, `REPRESENTANTE_ID`) VALUES
(3, 0, 'CRA-5 # 34-50', 'cgalianza@hotmail.com', NULL, NULL, NULL, NULL, '900352601-1', 'CONSTRUCTORA GRAN ALIANZA S.A.S.', '7827750', 4782, 3);


INSERT INTO `oferta` (`id`, `version`, `ofer_nombre`, `ofer_numero_cuotas`, `ofer_periodicidad`, `ofer_porcentaje`, `ofer_valor_separacion`) VALUES
(3, 0, 'Plan 30/70 20', '20', '30', 30, 5000000);


INSERT INTO `proyecto` (`id`, `version`, `proyecto_codigo`, `proyecto_nombre`, `EMPRESA_ID`, `OFERTA_ID`, `POBLADO_ID`) VALUES

(5, 0, '014', 'SAN SEBASTIAN', 3, 1, NULL);



INSERT INTO `torre` (`id`, `version`, `torre_direccion`, `torre_nombre`, `torre_numero`, `proyecto_id`) VALUES
(2, 0,'Esta es la direccion', 'Torre2', '02', 5);



INSERT INTO `tipo_inmueble` (`id`, `version`, `ti_area`, `ti_descripcion`, `ti_valor_metro_cuadrado`, `ti_valor_separacion`) VALUES
(1, 1, 10, 'uno', 27000, 56),
(3, 0, 250, 'Tipo Inmueble 2', 10000, 5000000),
(5, 1, 129.5, 'TipoInmueble01', 2400000, 2000000),
(6, 1, 89, 'TipoInmueble02', 2400000, 2000000),
(7, 1, 137, 'TipoInmueble03', 2400000, 2000000),
(8, 1, 126, 'TipoInmueble04', 2400000, 2000000),
(9, 1, 153, 'TipoInmueble05', 2400000, 2000000);

INSERT INTO `tipo_planta` (`id`, `version`, `tipo_planta_descripcion`, `tipo_planta_numero_inmuebles`) VALUES
(1, 0, 'este es un tipo', 5),
(2, 1, 'Tipo Planta General', 4),
(4, 0, 'PlantaTipo01', 5);


INSERT INTO `tipo_planta_detalle` (`id`, `version`, `tp_detalle_numero`, `TIPO_INMUEBLE_ID`, `TIPO_PLANTA_id`) VALUES
(1, 0, 1, 1, 1),
(2, 0, 2, 3, 1),
(3, 15, 3, 1, 1),
(4, 0, 4, 3, 1),
(5, 0, 5, 1, 1),
(6, 0, 1, 1, 2),
(7, 0, 2, 1, 2),
(8, 0, 3, 1, 2),
(9, 0, 4, 3, 2),
(10, 0, 1, 5, 4),
(11, 0, 2, 6, 4),
(12, 0, 3, 7, 4),
(13, 0, 4, 8, 4),
(14, 0, 5, 9, 4);


INSERT INTO `inmueble` ( `version`, `inm_area`, `inm_incremento`, `inm_numero`, `inm_valor_metro_cuadrado`, `inm_valor_separacion`, `inm_valor_total`, `ESTADO_INMUEBLE_ID`, `PROYECTO_ID`, `PISO_ID`) VALUES
( 2, 129.5, 1000000, '3 1', 2400000, 2000000, 311800000, 2, 5, NULL),
( 1, 89, NULL, '3 2', 2400000, 2000000, 213600000, 2, 5, NULL),
( 0, 137, NULL, '3 3', 2400000, 2000000, 328800000, NULL, 5, NULL),
( 0, 126, NULL, '3 4', 2400000, 2000000, 302400000, NULL, 5, NULL),
( 0, 153, NULL, '3 5', 2400000, 2000000, 367200000, NULL, 5, NULL),
( 0, 129.5, NULL, '4 1', 2400000, 2000000, 310800000, NULL, 5, NULL),
( 0, 89, NULL, '4 2', 2400000, 2000000, 213600000, NULL, 5, NULL),
( 0, 137, NULL, '4 3', 2400000, 2000000, 328800000, NULL, 5, NULL),
( 0, 126, NULL, '4 4', 2400000, 2000000, 302400000, NULL, 5, NULL),
( 0, 153, NULL, '4 5', 2400000, 2000000, 367200000, NULL, 5, NULL),
( 0, 129.5, NULL, '5 1', 2400000, 2000000, 310800000, NULL, 5, NULL),
( 0, 89, NULL, '5 2', 2400000, 2000000, 213600000, NULL, 5, NULL),
( 0, 137, NULL, '5 3', 2400000, 2000000, 328800000, NULL, 5, NULL),
( 0, 126, NULL, '5 4', 2400000, 2000000, 302400000, NULL, 5, NULL),
( 0, 153, NULL, '5 5', 2400000, 2000000, 367200000, NULL, 5, NULL),
( 0, 129.5, NULL, '6 1', 2400000, 2000000, 310800000, NULL, 5, NULL),
( 0, 89, NULL, '6 2', 2400000, 2000000, 213600000, NULL, 5, NULL),
( 0, 137, NULL, '6 3', 2400000, 2000000, 328800000, NULL, 5, NULL),
( 0, 126, NULL, '6 4', 2400000, 2000000, 302400000, NULL, 5, NULL),
( 0, 153, NULL, '6 5', 2400000, 2000000, 367200000, NULL, 5, NULL),
( 0, 129.5, NULL, '7 1', 2400000, 2000000, 310800000, NULL, 5, NULL),
( 0, 89, NULL, '7 2', 2400000, 2000000, 213600000, NULL, 5, NULL),
( 0, 137, NULL, '7 3', 2400000, 2000000, 328800000, NULL, 5, NULL),
( 0, 126, NULL, '7 4', 2400000, 2000000, 302400000, NULL, 5, NULL),
( 0, 153, NULL, '7 5', 2400000, 2000000, 367200000, NULL, 5, NULL),
( 0, 129.5, NULL, '8 1', 2400000, 2000000, 310800000, NULL, 5, NULL),
( 0, 89, NULL, '8 2', 2400000, 2000000, 213600000, NULL, 5, NULL),
( 0, 137, NULL, '8 3', 2400000, 2000000, 328800000, NULL, 5, NULL),
( 0, 126, NULL, '8 4', 2400000, 2000000, 302400000, NULL, 5, NULL),
( 0, 153, NULL, '8 5', 2400000, 2000000, 367200000, NULL, 5, NULL),
( 0, 129.5, NULL, '9 1', 2400000, 2000000, 310800000, NULL, 5, NULL),
( 0, 89, NULL, '9 2', 2400000, 2000000, 213600000, NULL, 5, NULL),
( 0, 137, NULL, '9 3', 2400000, 2000000, 328800000, NULL, 5, NULL),
( 0, 126, NULL, '9 4', 2400000, 2000000, 302400000, NULL, 5, NULL),
( 0, 153, NULL, '9 5', 2400000, 2000000, 367200000, NULL, 5, NULL),
( 0, 129.5, NULL, '10 1', 2400000, 2000000, 310800000, NULL, 5, NULL),
( 0, 89, NULL, '10 2', 2400000, 2000000, 213600000, NULL, 5, NULL),
( 0, 137, NULL, '10 3', 2400000, 2000000, 328800000, NULL, 5, NULL),
( 0, 126, NULL, '10 4', 2400000, 2000000, 302400000, NULL, 5, NULL),
( 0, 153, NULL, '10 5', 2400000, 2000000, 367200000, NULL, 5, NULL);
