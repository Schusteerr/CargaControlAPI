DROP TABLE IF EXISTS `cacamba`;
CREATE TABLE `cacamba` (
                           `quantidade` int NOT NULL,
                           `caminhao_placa` varchar(255) NOT NULL,
                           `embalagem_codigo` varchar(255) NOT NULL,
                           PRIMARY KEY (`caminhao_placa`,`embalagem_codigo`),
                           KEY `FKbb04p6oekxo16rs3oleg8pr6e` (`embalagem_codigo`),
                           CONSTRAINT `FKbb04p6oekxo16rs3oleg8pr6e` FOREIGN KEY (`embalagem_codigo`) REFERENCES `tb_embalagem` (`codigo`),
                           CONSTRAINT `FKisouir2o31qa85w7n8mj96ue8` FOREIGN KEY (`caminhao_placa`) REFERENCES `tb_caminhao` (`placa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `caminhao_embalagens`;
CREATE TABLE `caminhao_embalagens` (
                                       `quantidade` int NOT NULL,
                                       `caminhao_placa` varchar(255) NOT NULL,
                                       `embalagem_codigo` varchar(255) NOT NULL,
                                       PRIMARY KEY (`caminhao_placa`,`embalagem_codigo`),
                                       KEY `FK4437glto1iqjn3k9p88nkm30k` (`embalagem_codigo`),
                                       CONSTRAINT `FK4437glto1iqjn3k9p88nkm30k` FOREIGN KEY (`embalagem_codigo`) REFERENCES `tb_embalagem` (`codigo`),
                                       CONSTRAINT `FKnyryjd09ensmtx8asp6qv9ukv` FOREIGN KEY (`caminhao_placa`) REFERENCES `tb_caminhao` (`placa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `caminhao_fornecedores`;
CREATE TABLE `caminhao_fornecedores` (
                                         `caminhao_placa` varchar(255) NOT NULL,
                                         `fornecedor_codigo` varchar(255) NOT NULL,
                                         PRIMARY KEY (`caminhao_placa`,`fornecedor_codigo`),
                                         KEY `FKsn4ic8c9jcjgcrvb779rbbip` (`fornecedor_codigo`),
                                         CONSTRAINT `FK1by86fckieyb17031j9dgxox3` FOREIGN KEY (`caminhao_placa`) REFERENCES `tb_caminhao` (`placa`),
                                         CONSTRAINT `FKsn4ic8c9jcjgcrvb779rbbip` FOREIGN KEY (`fornecedor_codigo`) REFERENCES `tb_fornecedores` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `embalagem_fornecedor`;
CREATE TABLE `embalagem_fornecedor` (
                                        `embalagem_codigo` varchar(255) NOT NULL,
                                        `fornecedor_codigo` varchar(255) NOT NULL,
                                        PRIMARY KEY (`embalagem_codigo`,`fornecedor_codigo`),
                                        KEY `FKslqty8395drlc14t0hxym4f84` (`fornecedor_codigo`),
                                        CONSTRAINT `FKmh9nmm3aog4vaqdton24kpnws` FOREIGN KEY (`embalagem_codigo`) REFERENCES `tb_embalagem` (`codigo`),
                                        CONSTRAINT `FKslqty8395drlc14t0hxym4f84` FOREIGN KEY (`fornecedor_codigo`) REFERENCES `tb_fornecedores` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `tb_caminhao`;
CREATE TABLE `tb_caminhao` (
                               `placa` varchar(255) NOT NULL,
                               `tipo` varchar(255) NOT NULL,
                               `volume_maximo` double NOT NULL,
                               `volume_ocupado` double NOT NULL,
                               PRIMARY KEY (`placa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `tb_embalagem`;
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

DROP TABLE IF EXISTS `tb_fornecedores`;
CREATE TABLE `tb_fornecedores` (
                                   `codigo` varchar(255) NOT NULL,
                                   `frete` varchar(255) NOT NULL,
                                   `nome` varchar(255) NOT NULL,
                                   PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;completed on 2025-06-27  0:30:35