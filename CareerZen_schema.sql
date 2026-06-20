-- MySQL dump 10.13  Distrib 8.4.0, for macos13.2 (x86_64)
--
-- Host: localhost    Database: CareerZen
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `CareerZen`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `CareerZen` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `CareerZen`;

--
-- Table structure for table `Amministratore`
--

DROP TABLE IF EXISTS `Amministratore`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Amministratore` (
  `ID_Amministratore` int NOT NULL AUTO_INCREMENT,
  `NomeUtente` varchar(50) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Nome` varchar(50) NOT NULL,
  `Cognome` varchar(50) NOT NULL,
  `Email` varchar(100) NOT NULL,
  PRIMARY KEY (`ID_Amministratore`),
  UNIQUE KEY `NomeUtente` (`NomeUtente`),
  UNIQUE KEY `Email` (`Email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Amministratore`
--

LOCK TABLES `Amministratore` WRITE;
/*!40000 ALTER TABLE `Amministratore` DISABLE KEYS */;
INSERT INTO `Amministratore` VALUES (1,'admin_elena','admin123','Elena','Lungu','admin@careerzen.it');
/*!40000 ALTER TABLE `Amministratore` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Azienda`
--

DROP TABLE IF EXISTS `Azienda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Azienda` (
  `ID_Azienda` int NOT NULL AUTO_INCREMENT,
  `Nome_Azienda` varchar(100) NOT NULL,
  `Settore` varchar(100) NOT NULL,
  `Sede_Principale` varchar(100) NOT NULL,
  PRIMARY KEY (`ID_Azienda`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Azienda`
--

LOCK TABLES `Azienda` WRITE;
/*!40000 ALTER TABLE `Azienda` DISABLE KEYS */;
INSERT INTO `Azienda` VALUES (1,'Tech StartUp Srl','Informatica','Milano'),(2,'DataCore Inc.','Informatica','Milano'),(3,'CloudNet Solutions','Consulenza','Roma'),(4,'FinTech Global','Finanza','Torino'),(5,'Innovazione Tech','Software','Bologna'),(6,'Logistica Avanzata','Trasporti','Verona'),(7,'Green Energy Solutions','Energia','Firenze'),(8,'HealthCare Systems','Sanità','Roma'),(9,'ExperRevolution','Assicurazioni','Roma'),(10,'Fiat','automobilistico','Torino');
/*!40000 ALTER TABLE `Azienda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Candidato`
--

DROP TABLE IF EXISTS `Candidato`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Candidato` (
  `ID_Candidato` int NOT NULL AUTO_INCREMENT,
  `NomeUtente` varchar(50) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Nome` varchar(50) NOT NULL,
  `Cognome` varchar(50) NOT NULL,
  `Email` varchar(100) NOT NULL,
  PRIMARY KEY (`ID_Candidato`),
  UNIQUE KEY `NomeUtente` (`NomeUtente`),
  UNIQUE KEY `Email` (`Email`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Candidato`
--

LOCK TABLES `Candidato` WRITE;
/*!40000 ALTER TABLE `Candidato` DISABLE KEYS */;
INSERT INTO `Candidato` VALUES (1,'elena_dev','password123','Elena','Lungu','elena.lungu@careerzen.it'),(2,'mario_data','pwd123','Mario','Rossi','mario.rossi@email.it'),(3,'giulia_cloud','pwd123','Giulia','Bianchi','giulia.bianchi@email.it'),(4,'luigi_code','pwd123','Luigi','Verdi','luigi.v@email.it'),(5,'anna_data','pwd123','Anna','Neri','anna.n@email.it'),(6,'marco_cloud','pwd123','Marco','Gialli','marco.g@email.it'),(7,'sofia_dev','pwd123','Sofia','Bianchi','sofia.b@email.it'),(8,'corry_mary','ciaociao','Corrado','Mariani','corrado.mariani@gmail.com'),(9,'fraoly','francesco','Francesco','Olivier','fraoly@gmail.com');
/*!40000 ALTER TABLE `Candidato` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Candidatura`
--

DROP TABLE IF EXISTS `Candidatura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Candidatura` (
  `ID_Candidatura` int NOT NULL AUTO_INCREMENT,
  `Data_Creazione` date NOT NULL,
  `ID_Candidato` int NOT NULL,
  `ID_Posizione` int NOT NULL,
  PRIMARY KEY (`ID_Candidatura`),
  KEY `idx_candidatura_candidato` (`ID_Candidato`),
  KEY `idx_candidatura_posizione` (`ID_Posizione`),
  CONSTRAINT `fk_cand_candidato` FOREIGN KEY (`ID_Candidato`) REFERENCES `Candidato` (`ID_Candidato`) ON DELETE CASCADE,
  CONSTRAINT `fk_cand_posizione` FOREIGN KEY (`ID_Posizione`) REFERENCES `PosizioneLavorativa` (`ID_Posizione`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Candidatura`
--

LOCK TABLES `Candidatura` WRITE;
/*!40000 ALTER TABLE `Candidatura` DISABLE KEYS */;
INSERT INTO `Candidatura` VALUES (1,'2026-06-18',1,1),(2,'2026-06-01',2,2),(3,'2026-06-10',4,5),(4,'2026-06-12',5,6),(5,'2026-06-14',6,7),(6,'2026-06-15',7,8),(7,'2026-06-18',8,2),(8,'2026-06-18',8,4),(9,'2026-06-18',8,9);
/*!40000 ALTER TABLE `Candidatura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Colloquio`
--

DROP TABLE IF EXISTS `Colloquio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Colloquio` (
  `ID_Colloquio` int NOT NULL AUTO_INCREMENT,
  `Data_Ora` datetime NOT NULL,
  `Tipo_Colloquio` varchar(50) NOT NULL,
  `Esito` varchar(50) DEFAULT NULL,
  `Recruiter` varchar(100) DEFAULT NULL,
  `Note` text,
  `ID_Candidatura` int NOT NULL,
  PRIMARY KEY (`ID_Colloquio`),
  KEY `fk_coll_cand` (`ID_Candidatura`),
  CONSTRAINT `fk_coll_cand` FOREIGN KEY (`ID_Candidatura`) REFERENCES `Candidatura` (`ID_Candidatura`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Colloquio`
--

LOCK TABLES `Colloquio` WRITE;
/*!40000 ALTER TABLE `Colloquio` DISABLE KEYS */;
INSERT INTO `Colloquio` VALUES (1,'2026-06-18 10:00:00','Tecnico','Positivo','Marco Verdi HR','Il candidato ha un ottimo SQL',2),(2,'2026-06-19 14:00:00','Conoscitivo','Positivo','Laura Rossi','Ottimo profilo tecnico',5),(3,'2026-07-01 11:00:00','Conoscitivo','Buono, da valutare','Sara Lazzini','-',7);
/*!40000 ALTER TABLE `Colloquio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Competenza`
--

DROP TABLE IF EXISTS `Competenza`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Competenza` (
  `ID_Candidato` int NOT NULL,
  `ID_Skill` int NOT NULL,
  `Livello` int NOT NULL,
  PRIMARY KEY (`ID_Candidato`,`ID_Skill`),
  KEY `fk_comp_skill` (`ID_Skill`),
  CONSTRAINT `fk_comp_cand` FOREIGN KEY (`ID_Candidato`) REFERENCES `Candidato` (`ID_Candidato`) ON DELETE CASCADE,
  CONSTRAINT `fk_comp_skill` FOREIGN KEY (`ID_Skill`) REFERENCES `Skill` (`ID_Skill`) ON DELETE RESTRICT,
  CONSTRAINT `competenza_chk_1` CHECK ((`Livello` between 1 and 5))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Competenza`
--

LOCK TABLES `Competenza` WRITE;
/*!40000 ALTER TABLE `Competenza` DISABLE KEYS */;
INSERT INTO `Competenza` VALUES (1,1,4),(1,2,5),(1,4,4),(1,9,3),(2,2,5),(2,3,4),(2,6,3),(2,7,4),(4,1,5),(4,4,3),(4,9,4),(5,2,4),(5,3,2),(5,7,5),(6,6,4),(6,10,5),(7,1,3),(7,5,4),(8,2,5),(8,7,2),(8,8,4),(8,9,4),(9,8,5);
/*!40000 ALTER TABLE `Competenza` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Offerta`
--

DROP TABLE IF EXISTS `Offerta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Offerta` (
  `ID_Offerta` int NOT NULL AUTO_INCREMENT,
  `RAL_Proposta` decimal(10,2) NOT NULL,
  `Benefit` text,
  `Data_Scadenza_Offerta` date NOT NULL,
  `Stato_Offerta` varchar(50) NOT NULL,
  `ID_Candidatura` int NOT NULL,
  PRIMARY KEY (`ID_Offerta`),
  KEY `fk_off_cand` (`ID_Candidatura`),
  CONSTRAINT `fk_off_cand` FOREIGN KEY (`ID_Candidatura`) REFERENCES `Candidatura` (`ID_Candidatura`) ON DELETE CASCADE,
  CONSTRAINT `offerta_chk_1` CHECK ((`RAL_Proposta` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Offerta`
--

LOCK TABLES `Offerta` WRITE;
/*!40000 ALTER TABLE `Offerta` DISABLE KEYS */;
INSERT INTO `Offerta` VALUES (1,38000.00,'Buoni pasto, Assicurazione Sanitaria','2026-06-30','In Negoziazione',2),(2,32000.00,'Assicurazione','2026-07-01','Emessa',5),(3,30000.00,'assicurazione','2026-01-01','Accettata',1);
/*!40000 ALTER TABLE `Offerta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PosizioneLavorativa`
--

DROP TABLE IF EXISTS `PosizioneLavorativa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PosizioneLavorativa` (
  `ID_Posizione` int NOT NULL AUTO_INCREMENT,
  `Titolo` varchar(100) NOT NULL,
  `Modalita` varchar(50) NOT NULL,
  `Range_RAL` varchar(50) DEFAULT NULL,
  `Data_Scadenza_Annuncio` date NOT NULL,
  `ID_Azienda` int NOT NULL,
  PRIMARY KEY (`ID_Posizione`),
  KEY `idx_posizione_azienda` (`ID_Azienda`),
  CONSTRAINT `fk_pos_azienda` FOREIGN KEY (`ID_Azienda`) REFERENCES `Azienda` (`ID_Azienda`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PosizioneLavorativa`
--

LOCK TABLES `PosizioneLavorativa` WRITE;
/*!40000 ALTER TABLE `PosizioneLavorativa` DISABLE KEYS */;
INSERT INTO `PosizioneLavorativa` VALUES (1,'Junior Java Developer','Ibrido','25.000 - 30.000','2026-12-31',1),(2,'Data Engineer Mid-Level','Ibrido','35.000 - 45.000','2026-10-31',2),(3,'Cloud Architect','Remoto','45.000 - 60.000','2026-11-15',3),(4,'Backend Developer (Spring)','In Sede','28.000 - 35.000','2026-09-30',4),(5,'Full Stack Developer','Ibrido','30.000 - 40.000','2026-12-01',2),(6,'Junior Data Analyst','Remoto','24.000 - 28.000','2026-11-20',5),(7,'DevOps Engineer','Ibrido','40.000 - 55.000','2026-10-15',3),(8,'Python Developer','In Sede','32.000 - 38.000','2026-12-15',6),(9,'Sviluppatore Java','Ibrido','28000-35000','2027-12-01',5),(10,'Data engineer','Ufficio','30000-40000','2026-08-30',2);
/*!40000 ALTER TABLE `PosizioneLavorativa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Requisito`
--

DROP TABLE IF EXISTS `Requisito`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Requisito` (
  `ID_Posizione` int NOT NULL,
  `ID_Skill` int NOT NULL,
  `Livello_Minimo` int NOT NULL,
  PRIMARY KEY (`ID_Posizione`,`ID_Skill`),
  KEY `fk_req_skill` (`ID_Skill`),
  CONSTRAINT `fk_req_pos` FOREIGN KEY (`ID_Posizione`) REFERENCES `PosizioneLavorativa` (`ID_Posizione`) ON DELETE CASCADE,
  CONSTRAINT `fk_req_skill` FOREIGN KEY (`ID_Skill`) REFERENCES `Skill` (`ID_Skill`) ON DELETE RESTRICT,
  CONSTRAINT `requisito_chk_1` CHECK ((`Livello_Minimo` between 1 and 5))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Requisito`
--

LOCK TABLES `Requisito` WRITE;
/*!40000 ALTER TABLE `Requisito` DISABLE KEYS */;
INSERT INTO `Requisito` VALUES (2,2,4),(2,3,3),(2,6,3),(3,6,4),(3,10,4),(4,1,3),(4,4,3),(4,9,2),(9,1,4),(9,9,4),(10,7,1);
/*!40000 ALTER TABLE `Requisito` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Skill`
--

DROP TABLE IF EXISTS `Skill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Skill` (
  `ID_Skill` int NOT NULL AUTO_INCREMENT,
  `Nome_Skill` varchar(50) NOT NULL,
  PRIMARY KEY (`ID_Skill`),
  UNIQUE KEY `Nome_Skill` (`Nome_Skill`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Skill`
--

LOCK TABLES `Skill` WRITE;
/*!40000 ALTER TABLE `Skill` DISABLE KEYS */;
INSERT INTO `Skill` VALUES (6,'AWS'),(7,'Data Analysis'),(10,'Docker'),(9,'Git'),(1,'Java'),(8,'Machine Learning'),(3,'Python'),(5,'React'),(4,'Spring Boot'),(2,'SQL');
/*!40000 ALTER TABLE `Skill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `StoricoStati`
--

DROP TABLE IF EXISTS `StoricoStati`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `StoricoStati` (
  `ID_Candidatura` int NOT NULL,
  `Data_Cambio` datetime NOT NULL,
  `Stato` varchar(50) NOT NULL,
  PRIMARY KEY (`ID_Candidatura`,`Data_Cambio`),
  KEY `idx_storico_stato` (`Stato`),
  CONSTRAINT `fk_storico_cand` FOREIGN KEY (`ID_Candidatura`) REFERENCES `Candidatura` (`ID_Candidatura`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `StoricoStati`
--

LOCK TABLES `StoricoStati` WRITE;
/*!40000 ALTER TABLE `StoricoStati` DISABLE KEYS */;
INSERT INTO `StoricoStati` VALUES (2,'2026-06-15 09:15:00','Colloquio Fissato'),(5,'2026-06-18 08:30:00','Colloquio Fissato'),(2,'2026-06-05 14:30:00','In Valutazione'),(3,'2026-06-16 09:00:00','In Valutazione'),(4,'2026-06-17 10:00:00','In Valutazione'),(1,'2026-06-18 12:16:11','Inviata'),(2,'2026-06-01 10:00:00','Inviata'),(3,'2026-06-10 09:00:00','Inviata'),(4,'2026-06-12 10:30:00','Inviata'),(5,'2026-06-14 11:00:00','Inviata'),(6,'2026-06-15 15:00:00','Inviata'),(7,'2026-06-18 17:42:43','Inviata'),(7,'2026-06-18 17:54:57','Inviata'),(8,'2026-06-18 17:42:53','Inviata'),(9,'2026-06-18 17:42:57','Inviata');
/*!40000 ALTER TABLE `StoricoStati` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-20 12:24:30
