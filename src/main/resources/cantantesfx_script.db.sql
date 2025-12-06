CREATE DATABASE  IF NOT EXISTS `cantantesfx` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `cantantesfx`;
-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: cantantesfx
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.32-MariaDB

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
-- Table structure for table `canciones`
--

DROP TABLE IF EXISTS `canciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `canciones` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `titulo` varchar(255) NOT NULL,
  `duracion_segundos` int(11) NOT NULL,
  `anho_lanzamiento` int(11) DEFAULT NULL,
  `cantante_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cantante_id` (`cantante_id`),
  CONSTRAINT `canciones_ibfk_1` FOREIGN KEY (`cantante_id`) REFERENCES `cantantes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `canciones`
--

LOCK TABLES `canciones` WRITE;
/*!40000 ALTER TABLE `canciones` DISABLE KEYS */;
INSERT INTO `canciones` VALUES (1,'Beef Boy',185,2017,1),(2,'El Papasito',210,2020,1),(3,'Ringui Dingui',220,2021,2),(4,'Mitad y Mitad',270,2016,2),(5,'Mina el Hammani',205,2020,3),(6,'Coquito La Pieza',188,2018,3),(7,'Lenguaje Roto',210,2019,4),(8,'Superdeportivo',180,2025,4),(9,'HUMBLE.',177,2017,5),(10,'DNA.',185,2017,5),(11,'Thriller',357,1982,6),(12,'Billie Jean',294,1982,6),(13,'Malamente',162,2018,7),(14,'Con Altura',170,2019,7);
/*!40000 ALTER TABLE `canciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cantantes`
--

DROP TABLE IF EXISTS `cantantes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cantantes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `apellido` varchar(100) DEFAULT NULL,
  `nombre_artistico` varchar(100) NOT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `genero_musical` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cantantes`
--

LOCK TABLES `cantantes` WRITE;
/*!40000 ALTER TABLE `cantantes` DISABLE KEYS */;
INSERT INTO `cantantes` VALUES (1,'Fernando','Gálvez','Yung Beef','1990-01-23','Trap/Rap'),(2,'Javier','Ibarra','Kase.O','1980-03-01','Rap'),(3,'Carlos','Bruñas','Cruz Cafuné','1993-06-25','Rap/Trap'),(4,'Danilo','Amerise','Dano','1985-11-20','Rap/Hip Hop'),(5,'Kendrick','Lamar','Kendrick Lamar','1987-06-17','Hip Hop'),(6,'Michael','Jackson','Michael Jackson','1958-08-29','Pop/R&B'),(7,'Rosalía','Vila','Rosalía','1992-09-25','Pop/Flamenco');
/*!40000 ALTER TABLE `cantantes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favoritos_cantantes`
--

DROP TABLE IF EXISTS `favoritos_cantantes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favoritos_cantantes` (
  `usuario_id` int(11) NOT NULL,
  `cantante_id` int(11) NOT NULL,
  PRIMARY KEY (`usuario_id`,`cantante_id`),
  KEY `cantante_id` (`cantante_id`),
  CONSTRAINT `favoritos_cantantes_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`usuario_id`),
  CONSTRAINT `favoritos_cantantes_ibfk_2` FOREIGN KEY (`cantante_id`) REFERENCES `cantantes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favoritos_cantantes`
--

LOCK TABLES `favoritos_cantantes` WRITE;
/*!40000 ALTER TABLE `favoritos_cantantes` DISABLE KEYS */;
INSERT INTO `favoritos_cantantes` VALUES (1,2),(1,4),(2,5),(2,6),(2,7),(3,1),(3,3),(4,2),(4,5),(4,6);
/*!40000 ALTER TABLE `favoritos_cantantes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `usuario_id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `apellido` varchar(100) DEFAULT NULL,
  `nickname` varchar(50) NOT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`usuario_id`),
  UNIQUE KEY `nickname` (`nickname`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Elena','Ruiz','Tester01','1990-01-01','elena.r@app.com','testPass'),(2,'Mario','Gálvez','MusicFan','1983-05-15','mario.g@app.com','123456'),(3,'Laura','Soto','IndieLover','2004-11-20','laura.s@app.com','LSoto'),(4,'Felipe','Torres','OldSchool','1975-02-28','felipe.t@app.com','MJFan');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-06 19:43:37
