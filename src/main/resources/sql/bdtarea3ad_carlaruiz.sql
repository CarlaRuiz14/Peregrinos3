-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 31-01-2025 a las 18:58:25
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bdtarea3ad_carlaruiz`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carnets`
--

CREATE TABLE `carnets` (
  `id` bigint(20) NOT NULL,
  `distancia` double DEFAULT NULL,
  `fecha_expedicion` date DEFAULT NULL,
  `numero_vips` int(11) DEFAULT NULL,
  `parada_inicial` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `carnets`
--

INSERT INTO `carnets` (`id`, `distancia`, `fecha_expedicion`, `numero_vips`, `parada_inicial`) VALUES
(1, 15, '2025-01-30', 0, 1),
(2, 30, '2025-01-30', 1, 1),
(3, 15, '2025-01-30', 0, 1),
(4, 15, '2025-01-30', 0, 1),
(5, 15, '2025-01-30', 0, 2),
(6, 15, '2025-01-30', 0, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estancias`
--

CREATE TABLE `estancias` (
  `id` bigint(20) NOT NULL,
  `fecha` date DEFAULT NULL,
  `vip` tinyint(1) DEFAULT 0,
  `id_parada` bigint(20) NOT NULL,
  `id_peregrino` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `estancias`
--

INSERT INTO `estancias` (`id`, `fecha`, `vip`, `id_parada`, `id_peregrino`) VALUES
(1, '2025-01-31', 0, 1, 1),
(2, '2025-01-31', 1, 1, 2),
(3, '2025-01-31', 0, 1, 3),
(4, '2025-01-31', 0, 1, 5),
(5, '2025-01-31', 0, 1, 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `paradas`
--

CREATE TABLE `paradas` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `region` char(1) NOT NULL,
  `id_usuario` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `paradas`
--

INSERT INTO `paradas` (`id`, `nombre`, `region`, `id_usuario`) VALUES
(1, 'Sarria', 'l', 1),
(2, 'Parada A', 'A', 6);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `paradas_peregrinos`
--

CREATE TABLE `paradas_peregrinos` (
  `fecha` date NOT NULL,
  `id_parada` bigint(20) NOT NULL,
  `id_peregrino` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `paradas_peregrinos`
--

INSERT INTO `paradas_peregrinos` (`fecha`, `id_parada`, `id_peregrino`) VALUES
('2025-01-30', 1, 1),
('2025-01-30', 1, 2),
('2025-01-30', 1, 3),
('2025-01-30', 1, 4),
('2025-01-30', 1, 6),
('2025-01-30', 2, 2),
('2025-01-30', 2, 5),
('2025-01-31', 1, 1),
('2025-01-31', 1, 2),
('2025-01-31', 1, 3),
('2025-01-31', 1, 4),
('2025-01-31', 1, 5),
('2025-01-31', 1, 6);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `peregrinos`
--

CREATE TABLE `peregrinos` (
  `id` bigint(20) NOT NULL,
  `apellidos` varchar(255) DEFAULT NULL,
  `nacionalidad` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) NOT NULL,
  `id_carnet` bigint(20) NOT NULL,
  `id_usuario` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `peregrinos`
--

INSERT INTO `peregrinos` (`id`, `apellidos`, `nacionalidad`, `nombre`, `id_carnet`, `id_usuario`) VALUES
(1, 'Ruiz', 'ES', 'Juan Francisco', 1, 2),
(2, 'Ramirez', 'CH', 'Hector', 2, 3),
(3, 'Menasalvas', 'DK', 'Ramon', 3, 4),
(4, 'Roman', 'SA', 'Noel', 4, 5),
(5, 'Menasalvas', 'AT', 'Ramon', 5, 7),
(6, 'Ruiz', 'DE', 'Carla', 6, 8);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` bigint(20) NOT NULL,
  `contraseña` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `nombre_usuario` varchar(255) NOT NULL,
  `perfil` enum('ADMINISTRADOR','INVITADO','PARADA','PEREGRINO') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `contraseña`, `email`, `nombre_usuario`, `perfil`) VALUES
(1, '$2a$10$EflsDPDqgTBVKMSVZqjAb.RTc11KR/qfwggahSvHAQr4J2L0bk0uC', 'pepe@parada.com', 'pepeparada', 'PARADA'),
(2, '$2a$10$42p8mSdYd3AUVaqXP1qy7utAHU.loPbv0qOihRcSzLkUHhlYpY38i', 'paco@peregrino.com', 'pacoperegrino', 'PEREGRINO'),
(3, '$2a$10$zbB0fH39SSlh31irEABzFuNDtBGw/YLxtyVsARpIfMC50NeANM7Oy', 'hector@peregrino.com', 'hector', 'PEREGRINO'),
(4, '$2a$10$6bbsh3v4GnMt2qoqg5OA/Oq5ufNZw8WRjaPUrxMg2G9XqlDPUoz4.', 'ramon@gmail.com', 'Ramon', 'PEREGRINO'),
(5, '$2a$10$Zkm2C6IeifpoQHr89cwndedS6wAAuyPsgNd.eMt9Ri/deMARMit.2', 'noel@noel.com', 'noel', 'PEREGRINO'),
(6, '$2a$10$t626AyDRixJhDW06dQN8uOYIL0RO.n41NPaLa6p2NLEHlFNa.01v2', 'hectorc@gamil.com', 'hectorc', 'PARADA'),
(7, '$2a$10$qt.aI852/KOIUykHQbSX3ujUUR7nU2E5e7hQbGlrOc/acEAwoqpBC', 'rm@gmail.com', 'RamonM', 'PEREGRINO'),
(8, '$2a$10$GA/8qmH45ZyrztvicDM8guoNSvYT6ACNlyGNrX0cHIIfAWIByX0di', 'carla@gmail.com', 'carla', 'PEREGRINO');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `carnets`
--
ALTER TABLE `carnets`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKl8lr8nt79o4mxj2mwicdjio88` (`parada_inicial`);

--
-- Indices de la tabla `estancias`
--
ALTER TABLE `estancias`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKi1fam1r85nkth28g6kjlg08rf` (`id_parada`),
  ADD KEY `FKds6ch16adx2qcy06s5pax58j6` (`id_peregrino`);

--
-- Indices de la tabla `paradas`
--
ALTER TABLE `paradas`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK6atrch75wh3cjxtmcadp1vv91` (`id_usuario`);

--
-- Indices de la tabla `paradas_peregrinos`
--
ALTER TABLE `paradas_peregrinos`
  ADD PRIMARY KEY (`fecha`,`id_parada`,`id_peregrino`),
  ADD KEY `FKseyoa0nsajdpum3gu27dtod77` (`id_parada`),
  ADD KEY `FKqu36429reeknsmwcl5lxhfmls` (`id_peregrino`);

--
-- Indices de la tabla `peregrinos`
--
ALTER TABLE `peregrinos`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKmr5n5apd4twdqq9m27dycnyul` (`id_carnet`),
  ADD UNIQUE KEY `UKfyubip2dbh2k8ob3vlp9jurca` (`id_usuario`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKof5vabgukahdwmgxk4kjrbu98` (`nombre_usuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `carnets`
--
ALTER TABLE `carnets`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `estancias`
--
ALTER TABLE `estancias`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `paradas`
--
ALTER TABLE `paradas`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `peregrinos`
--
ALTER TABLE `peregrinos`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `carnets`
--
ALTER TABLE `carnets`
  ADD CONSTRAINT `FKl8lr8nt79o4mxj2mwicdjio88` FOREIGN KEY (`parada_inicial`) REFERENCES `paradas` (`id`);

--
-- Filtros para la tabla `estancias`
--
ALTER TABLE `estancias`
  ADD CONSTRAINT `FKds6ch16adx2qcy06s5pax58j6` FOREIGN KEY (`id_peregrino`) REFERENCES `peregrinos` (`id`),
  ADD CONSTRAINT `FKi1fam1r85nkth28g6kjlg08rf` FOREIGN KEY (`id_parada`) REFERENCES `paradas` (`id`);

--
-- Filtros para la tabla `paradas`
--
ALTER TABLE `paradas`
  ADD CONSTRAINT `FKau7f47lfsuiuxwjk4vushx5v3` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`);

--
-- Filtros para la tabla `paradas_peregrinos`
--
ALTER TABLE `paradas_peregrinos`
  ADD CONSTRAINT `FKqu36429reeknsmwcl5lxhfmls` FOREIGN KEY (`id_peregrino`) REFERENCES `peregrinos` (`id`),
  ADD CONSTRAINT `FKseyoa0nsajdpum3gu27dtod77` FOREIGN KEY (`id_parada`) REFERENCES `paradas` (`id`);

--
-- Filtros para la tabla `peregrinos`
--
ALTER TABLE `peregrinos`
  ADD CONSTRAINT `FKa4r8o609006xdsgb7rshiwhco` FOREIGN KEY (`id_carnet`) REFERENCES `carnets` (`id`),
  ADD CONSTRAINT `FKlsqd746bk3cvu66kevdii494r` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
