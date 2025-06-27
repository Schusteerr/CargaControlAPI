-- MySQL dump 10.13  Distrib 9.3.0, for Linux (x86_64)
--
-- Host: localhost    Database: demopackage
-- ------------------------------------------------------
-- Server version	9.3.0

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
-- Table structure for table `cacamba`
--

DROP TABLE IF EXISTS `cacamba`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cacamba` (
                           `quantidade` int NOT NULL,
                           `caminhao_placa` varchar(255) NOT NULL,
                           `embalagem_codigo` varchar(255) NOT NULL,
                           PRIMARY KEY (`caminhao_placa`,`embalagem_codigo`),
                           KEY `FKbb04p6oekxo16rs3oleg8pr6e` (`embalagem_codigo`),
                           CONSTRAINT `FKbb04p6oekxo16rs3oleg8pr6e` FOREIGN KEY (`embalagem_codigo`) REFERENCES `tb_embalagem` (`codigo`),
                           CONSTRAINT `FKisouir2o31qa85w7n8mj96ue8` FOREIGN KEY (`caminhao_placa`) REFERENCES `tb_caminhao` (`placa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `caminhao_embalagens`
--

DROP TABLE IF EXISTS `caminhao_embalagens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `caminhao_embalagens` (
                                       `quantidade` int NOT NULL,
                                       `caminhao_placa` varchar(255) NOT NULL,
                                       `embalagem_codigo` varchar(255) NOT NULL,
                                       PRIMARY KEY (`caminhao_placa`,`embalagem_codigo`),
                                       KEY `FK4437glto1iqjn3k9p88nkm30k` (`embalagem_codigo`),
                                       CONSTRAINT `FK4437glto1iqjn3k9p88nkm30k` FOREIGN KEY (`embalagem_codigo`) REFERENCES `tb_embalagem` (`codigo`),
                                       CONSTRAINT `FKnyryjd09ensmtx8asp6qv9ukv` FOREIGN KEY (`caminhao_placa`) REFERENCES `tb_caminhao` (`placa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `caminhao_fornecedores`
--

DROP TABLE IF EXISTS `caminhao_fornecedores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `caminhao_fornecedores` (
                                         `caminhao_placa` varchar(255) NOT NULL,
                                         `fornecedor_codigo` varchar(255) NOT NULL,
                                         PRIMARY KEY (`caminhao_placa`,`fornecedor_codigo`),
                                         KEY `FKsn4ic8c9jcjgcrvb779rbbip` (`fornecedor_codigo`),
                                         CONSTRAINT `FK1by86fckieyb17031j9dgxox3` FOREIGN KEY (`caminhao_placa`) REFERENCES `tb_caminhao` (`placa`),
                                         CONSTRAINT `FKsn4ic8c9jcjgcrvb779rbbip` FOREIGN KEY (`fornecedor_codigo`) REFERENCES `tb_fornecedores` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `embalagem_fornecedor`
--

DROP TABLE IF EXISTS `embalagem_fornecedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `embalagem_fornecedor` (
                                        `embalagem_codigo` varchar(255) NOT NULL,
                                        `fornecedor_codigo` varchar(255) NOT NULL,
                                        PRIMARY KEY (`embalagem_codigo`,`fornecedor_codigo`),
                                        KEY `FKslqty8395drlc14t0hxym4f84` (`fornecedor_codigo`),
                                        CONSTRAINT `FKmh9nmm3aog4vaqdton24kpnws` FOREIGN KEY (`embalagem_codigo`) REFERENCES `tb_embalagem` (`codigo`),
                                        CONSTRAINT `FKslqty8395drlc14t0hxym4f84` FOREIGN KEY (`fornecedor_codigo`) REFERENCES `tb_fornecedores` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_caminhao`
--

DROP TABLE IF EXISTS `tb_caminhao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_caminhao` (
                               `placa` varchar(255) NOT NULL,
                               `tipo` varchar(255) NOT NULL,
                               `volume_maximo` double NOT NULL,
                               `volume_ocupado` double NOT NULL,
                               PRIMARY KEY (`placa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_embalagem`
--

DROP TABLE IF EXISTS `tb_embalagem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_embalagem` (
                                `codigo` varchar(255) NOT NULL,
                                `altura` double NOT NULL,
                                `comprimento` double NOT NULL,
                                `criticalidade` varchar(255) NOT NULL,
                                `descricao` varchar(255) NOT NULL,
                                `largura` double NOT NULL,
                                `tipo` varchar(255) NOT NULL,
                                `volume` double NOT NULL,
                                PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_fornecedores`
--

DROP TABLE IF EXISTS `tb_fornecedores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_fornecedores` (
                                   `codigo` varchar(255) NOT NULL,
                                   `frete` varchar(255) NOT NULL,
                                   `nome` varchar(255) NOT NULL,
                                   PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-27  0:30:35