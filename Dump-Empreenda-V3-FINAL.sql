-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: empreenda_mais_v3
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `adicionais_perfil`
--

DROP TABLE IF EXISTS `adicionais_perfil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adicionais_perfil` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `contato1` varchar(255) DEFAULT NULL,
  `contato2` varchar(255) DEFAULT NULL,
  `contato3` varchar(255) DEFAULT NULL,
  `contato4` varchar(255) DEFAULT NULL,
  `interesse1` varchar(255) DEFAULT NULL,
  `interesse2` varchar(255) DEFAULT NULL,
  `interesse3` varchar(255) DEFAULT NULL,
  `perfil_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_mlf6xpgppxp0w1ni45b5518ji` (`perfil_id`),
  CONSTRAINT `FKm7kgwn30op7sk0jj7mi6tkvj8` FOREIGN KEY (`perfil_id`) REFERENCES `perfil` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adicionais_perfil`
--

LOCK TABLES `adicionais_perfil` WRITE;
/*!40000 ALTER TABLE `adicionais_perfil` DISABLE KEYS */;
INSERT INTO `adicionais_perfil` VALUES (1,'','x.com/teste123','twitter.com/teste123','facebook.com/teste123','Desenvolvimento','Tecnologias','Cursos e Vagas',1),(2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2),(3,'teste.com.be','@teste','@teste','@teste','Teste 1','Teste 2','',3),(4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4),(5,'https://sebrae.com.br/sites/PortalSebrae/','https://www.instagram.com/sebrae/','https://twitter.com/sebrae','https://www.facebook.com/sebraesp/','Empreendedorismo','Educação','Apoio a Empreendedores',5),(6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6),(7,'','','','','','','',7),(8,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8),(9,NULL,NULL,NULL,NULL,NULL,NULL,NULL,9);
/*!40000 ALTER TABLE `adicionais_perfil` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comentarios_postagem`
--

DROP TABLE IF EXISTS `comentarios_postagem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comentarios_postagem` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `comentario` varchar(255) DEFAULT NULL,
  `data_comentario` date DEFAULT NULL,
  `perfil_id` bigint DEFAULT NULL,
  `postagem_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkuxfdtdk04ovhvuurmnjv7rd6` (`postagem_id`),
  KEY `FKguhrh8o5ojys8slx26xqsvn17` (`perfil_id`),
  CONSTRAINT `FKguhrh8o5ojys8slx26xqsvn17` FOREIGN KEY (`perfil_id`) REFERENCES `perfil` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKkuxfdtdk04ovhvuurmnjv7rd6` FOREIGN KEY (`postagem_id`) REFERENCES `postagem` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comentarios_postagem`
--

LOCK TABLES `comentarios_postagem` WRITE;
/*!40000 ALTER TABLE `comentarios_postagem` DISABLE KEYS */;
INSERT INTO `comentarios_postagem` VALUES (1,'Teste de Comentário 2','2024-01-06',1,4),(2,'Teste de Comentário 3','2024-01-06',1,4),(3,'Teste de comentário de outro usuário','2024-01-06',3,4),(4,'Comentário Teste','2024-01-08',1,3),(5,'Teste Novamente','2024-01-11',3,4),(6,'Mais um Comentário','2024-01-11',1,3),(8,'Comentário','2024-01-18',4,11),(9,'teste comentario','2024-01-18',1,11),(10,'Teste ','2024-01-21',4,7),(11,'Demais! Obrigado por compartilhar','2024-01-23',1,12),(12,'Ótimas Dicas!','2024-01-27',4,14);
/*!40000 ALTER TABLE `comentarios_postagem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curso`
--

DROP TABLE IF EXISTS `curso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `curso` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `categoria` varchar(255) DEFAULT NULL,
  `data_criacao` date DEFAULT NULL,
  `foto_capa` longtext,
  `media_url` longtext,
  `titulo` varchar(255) NOT NULL,
  `descricao_curta` varchar(255) DEFAULT NULL,
  `descricao_longa` longtext,
  `id_colaborador` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKadtpeuborhvg1e3jgmcrvilnj` (`id_colaborador`),
  CONSTRAINT `FKadtpeuborhvg1e3jgmcrvilnj` FOREIGN KEY (`id_colaborador`) REFERENCES `perfil` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curso`
--

LOCK TABLES `curso` WRITE;
/*!40000 ALTER TABLE `curso` DISABLE KEYS */;
INSERT INTO `curso` VALUES (1,'MEI - Primeiros Passos','2024-01-09','https://respostas.sebrae.com.br/wp-content/uploads/2020/08/ideias-de-neg%C3%B3cios-sebrae-como-montar-ou-abrir-uma-empresa-959x615.jpg','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/videoseries?si=nTXMgnXm59enwB4Y&amp;list=PLnPmdlI4EGt16maxK8PEU0txJKhN20ALL\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>','Idéias de Negócios MEI','Orientações e informações fundamentais para aqueles que desejam iniciar ou aprimorar seus negócios','<p>O projeto <strong>\"Ideias de Negócios MEI\"</strong> oferecido pelo SEBRAE (Serviço Brasileiro de Apoio às Micro e Pequenas Empresas) é uma iniciativa voltada para o Microempreendedor Individual (MEI) que busca fornecer orientações e informações fundamentais para aqueles que desejam iniciar ou aprimorar seus negócios. </p>',5),(7,'Cursos','2024-01-13','https://blog.gs1br.org/wp-content/uploads/2017/01/venda-online-entenda-como-ela-influencia-nos-resultados-do-seu-negocio.jpeg','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/videoseries?si=9weh4XsH-0RLFtvB&amp;list=PLnPmdlI4EGt3aPS8T0p_hbUqj65q8jti9\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>','Vendas Online','Aprenda, se atualize ou se reinvente com essas dicas','<p>Aproveite os parceiros que se juntaram ao SEBRAE para ajudar voc&ecirc; a vender online</p>',5),(10,'Outros','2024-01-18','https://s24534.pcdn.co/carreira-sucesso/wp-content/uploads/sites/3/2022/07/qualificacao-cursos-online.jpg','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/videoseries?si=mD9n-XovUyr3VelA&amp;list=PLqAsoRqwNhsLx6iGdAiAUrohC4LuSUJ4x\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>','Curso Teste Admin Editado','Curso criado para teste na plataforma','<p>Curso criado para teste na plataforma</p>',4),(11,'Gestão de Empresa','2024-01-24','https://sebrae.com.br/Sebrae/Portal%20Sebrae/UFs/SP/Programas%20e%20Solu%C3%A7%C3%B5es/Seu%20Neg%C3%B3cio%20Digital%20em%205%20Dias/seu-negocio-5-dia-olds.jpg','<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/videoseries?si=sZsJKEjzz66zLxD2&amp;list=PLnPmdlI4EGt2nK6PuGx4LPUgsMaDJuSCm\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>','Seu Negócio Digital','Amplie seus negócios digitalmente!','<p>Tem um neg&oacute;cio e quer ampliar ele? Torne ele digital com o treinamento completo do SEBRAE.</p>\r\n<p>Aproveite tamb&eacute;m a apostila: <a href=\"https://bibliotecas.sebrae.com.br/chronus/ARQUIVOS_CHRONUS/bds/bds.nsf/47D4BCB49B5EE0CB8325768F006C7FE0/$File/NT00042F1A.pdf\" target=\"_blank\" rel=\"noopener\">Acesse aqui</a></p>',5);
/*!40000 ALTER TABLE `curso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `endereco_perfil`
--

DROP TABLE IF EXISTS `endereco_perfil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `endereco_perfil` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cep` varchar(255) DEFAULT NULL,
  `cidade` varchar(255) DEFAULT NULL,
  `logradouro` varchar(255) DEFAULT NULL,
  `uf` varchar(255) DEFAULT NULL,
  `perfil_id` bigint DEFAULT NULL,
  `compartilhar_endereco` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ewsexr0e0wkdhc4rsah7nvi7w` (`perfil_id`),
  CONSTRAINT `FK186p2li5vk834yawb5vtvdt02` FOREIGN KEY (`perfil_id`) REFERENCES `perfil` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `endereco_perfil`
--

LOCK TABLES `endereco_perfil` WRITE;
/*!40000 ALTER TABLE `endereco_perfil` DISABLE KEYS */;
INSERT INTO `endereco_perfil` VALUES (1,'12345-678','Suzano','Rua Exemplo, 2000','SP',1,_binary '\0'),(2,NULL,NULL,NULL,NULL,2,NULL),(3,'12345-678','Teste','Rua Teste, 100','SP',3,_binary '\0'),(4,'','','','MS',4,_binary ''),(5,'31441-231','São Paulo','Rua SEBRAE, 100','SP',5,_binary ''),(6,NULL,NULL,NULL,NULL,6,NULL),(7,'12345-666','São Paulo','Rua Teste Editada,100','SP',7,NULL),(8,NULL,NULL,NULL,NULL,8,NULL),(9,NULL,NULL,NULL,NULL,9,NULL);
/*!40000 ALTER TABLE `endereco_perfil` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inscricao_curso`
--

DROP TABLE IF EXISTS `inscricao_curso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inscricao_curso` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `data_inscricao` date DEFAULT NULL,
  `curso_id` bigint DEFAULT NULL,
  `perfil_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5wwmq9y2pxs701jx0mew1s0pl` (`perfil_id`),
  KEY `FK2huv3drx6r4cpk1bfubevet63` (`curso_id`),
  CONSTRAINT `FK2huv3drx6r4cpk1bfubevet63` FOREIGN KEY (`curso_id`) REFERENCES `curso` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK5wwmq9y2pxs701jx0mew1s0pl` FOREIGN KEY (`perfil_id`) REFERENCES `perfil` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inscricao_curso`
--

LOCK TABLES `inscricao_curso` WRITE;
/*!40000 ALTER TABLE `inscricao_curso` DISABLE KEYS */;
INSERT INTO `inscricao_curso` VALUES (1,'2024-01-11',1,1),(4,'2024-01-11',1,6),(7,'2024-01-13',7,1),(9,'2024-01-24',10,4);
/*!40000 ALTER TABLE `inscricao_curso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `likes_postagem`
--

DROP TABLE IF EXISTS `likes_postagem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `likes_postagem` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `postagem_id` bigint DEFAULT NULL,
  `usuario_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlfm62lrw0hu9f7omkr34oprbl` (`postagem_id`),
  KEY `FKex0oc59n89k6glep0s57tfqyp` (`usuario_id`),
  CONSTRAINT `FKex0oc59n89k6glep0s57tfqyp` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`),
  CONSTRAINT `FKlfm62lrw0hu9f7omkr34oprbl` FOREIGN KEY (`postagem_id`) REFERENCES `postagem` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `likes_postagem`
--

LOCK TABLES `likes_postagem` WRITE;
/*!40000 ALTER TABLE `likes_postagem` DISABLE KEYS */;
INSERT INTO `likes_postagem` VALUES (14,11,1),(16,3,1),(26,7,4),(28,11,4),(29,12,1),(30,13,2),(31,14,5),(32,14,4);
/*!40000 ALTER TABLE `likes_postagem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `perfil`
--

DROP TABLE IF EXISTS `perfil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `perfil` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bio` varchar(255) DEFAULT NULL,
  `data_nas` date DEFAULT NULL,
  `escolaridade` varchar(255) DEFAULT NULL,
  `foto_url` varchar(255) DEFAULT NULL,
  `ocupacao` varchar(255) DEFAULT NULL,
  `renda` varchar(255) DEFAULT NULL,
  `telefone1` varchar(255) DEFAULT NULL,
  `telefone2` varchar(255) DEFAULT NULL,
  `usuario_id` bigint DEFAULT NULL,
  `compartilhar_telefone1` bit(1) DEFAULT NULL,
  `compartilhar_telefone2` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_qimyhrxv3rmjmv7cs5fi1ek85` (`usuario_id`),
  CONSTRAINT `FKno01a8iut56nipcu6qdnxgeg5` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfil`
--

LOCK TABLES `perfil` WRITE;
/*!40000 ALTER TABLE `perfil` DISABLE KEYS */;
INSERT INTO `perfil` VALUES (1,'Bio Teste','1996-02-28','Ensino Superior Completo','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRVA_HrQLjkHiJ2Ag5RGuwbFeDKRLfldnDasw&usqp=CAU','Desenvolvedor','1 a 3 Salarios Minimos','(11) 1234-5678','(11) 91234-5678',1,_binary '',_binary '\0'),(2,NULL,NULL,NULL,'https://i.postimg.cc/T3G5j2KW/user-standard.png',NULL,NULL,NULL,NULL,2,NULL,NULL),(3,'testes','1990-10-01','Ensino Medio Completo','https://suttoninsurance.com/wp-content/uploads/2019/09/crash-testing-dummies1.jpg','teste','1 a 3 Salarios Minimos','(11) 1234-567','(11) 91234-5678',3,_binary '',_binary '\0'),(4,NULL,NULL,NULL,'https://i.postimg.cc/T3G5j2KW/user-standard.png',NULL,NULL,'','',4,_binary '\0',_binary ''),(5,'Estimulando o empreendedorismo no país!',NULL,NULL,'https://anpei.org.br/site-novo/wp-content/uploads/2019/05/91795744d2dbd3afdf18b52bdfe19fab.jpg','Serviço de Apoio a MEIs',NULL,'(11) 1234-5678','(11) 91234-5678',5,_binary '',_binary ''),(6,NULL,NULL,NULL,'https://i.postimg.cc/T3G5j2KW/user-standard.png',NULL,NULL,NULL,NULL,7,NULL,NULL),(7,'','1990-01-01','Ensino Medio Completo','https://i.postimg.cc/T3G5j2KW/user-standard.png','teste','0 a 1 Salario Minimo','(11) 2312-3131','(11) 91238-9172',8,NULL,NULL),(8,NULL,NULL,NULL,'https://i.postimg.cc/T3G5j2KW/user-standard.png',NULL,NULL,NULL,NULL,9,NULL,NULL),(9,NULL,NULL,NULL,'https://i.postimg.cc/T3G5j2KW/user-standard.png',NULL,NULL,NULL,NULL,10,NULL,NULL);
/*!40000 ALTER TABLE `perfil` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `postagem`
--

DROP TABLE IF EXISTS `postagem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `postagem` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `categoria` varchar(255) NOT NULL,
  `corpo` longtext NOT NULL,
  `data_postagem` date DEFAULT NULL,
  `denuncia` int DEFAULT NULL,
  `media_url` longtext,
  `titulo` longtext NOT NULL,
  `perfil_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3uy1sy2xxee4fiumcsl4sjpsd` (`perfil_id`),
  CONSTRAINT `FK3uy1sy2xxee4fiumcsl4sjpsd` FOREIGN KEY (`perfil_id`) REFERENCES `perfil` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `postagem`
--

LOCK TABLES `postagem` WRITE;
/*!40000 ALTER TABLE `postagem` DISABLE KEYS */;
INSERT INTO `postagem` VALUES (3,'Outros','<p>teste</p>','2024-01-04',4,'','teste',4),(4,'Dicas','<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris euismod auctor venenatis. Vivamus dolor felis, fringilla ut ligula eget, vestibulum facilisis felis. Vestibulum gravida justo eget luctus dictum. Suspendisse in felis euismod, mattis diam vitae, scelerisque augue. Aliquam dapibus hendrerit orci, quis lacinia dolor maximus tristique. Proin suscipit arcu a dolor semper cursus. Nulla sollicitudin semper diam, sit amet posuere quam mollis a. Maecenas eget ipsum posuere, pharetra justo blandit, efficitur sapien. Praesent massa massa, molestie eget lorem vitae, vestibulum commodo nulla. Duis egestas ante a purus porta vulputate. Morbi dui elit, bibendum sit amet tellus vel, commodo dapibus lacus. Nullam porttitor, lacus quis tempor pretium, ligula elit varius risus, ut luctus arcu nibh ut quam. Maecenas in posuere mi. Donec euismod sapien ac enim convallis blandit. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Etiam ultricies metus ac laoreet ullamcorper.</p>','2024-01-06',2,'https://gluby.com.br/storage/uploads/blog/posts/20230126_07295643357.png','LOREM IPSUM',1),(7,'Vagas','<p>Mais um teste de Postagem</p>','2024-01-11',1,'','Teste de Postagem 5',1),(11,'Dicas','<p>Teste de Postagem pelo ADMIN</p>','2024-01-18',0,'','Postagem Teste ADMIN',4),(12,'Vagas','<h1>Aproveite as vagas dispon&iacute;veis!&nbsp;</h1>\r\n<p>Oportunidades para todas as idades. Clique <a href=\"https://www.google.com.br\" target=\"_blank\" rel=\"noopener\">AQUI</a> para saber mais</p>','2024-01-23',0,'https://www.lagoadaprata.mg.leg.br/institucional/noticias/atencao-cidadao-5/image','Vagas de Empregos',3),(13,'Cursos','<p>Invista no seu futuro agora mesmo. Inscreva-se no Recode Pro e aproveite!</p>\r\n<p><a href=\"https://recode.org.br/trilhas-formativas/?gclid=Cj0KCQiAh8OtBhCQARIsAIkWb68XJtQWQwM_-GaSV1RqvIg_z2qrGPgxSLT45vA6GdU8031HKUmxpiwaAoQsEALw_wcB\">Conhe&ccedil;a a Recode!</a></p>','2024-01-24',0,'https://pbs.twimg.com/media/FLGDzntWQAA4E3B.jpg','Recode Pro',1),(14,'Dicas','<p>Quer dicas para se destacar no Linkedin? A <a href=\"https://www.alura.com.br/?utm_term=alura&amp;utm_campaign=%5BSearch%5D+%5BPerformance%5D+-+Institucional&amp;utm_source=adwords&amp;utm_medium=ppc&amp;hsa_acc=7964138385&amp;hsa_cam=386166608&amp;hsa_grp=21666755648&amp;hsa_ad=609948692827&amp;hsa_src=g&amp;hsa_tgt=kwd-300088401&amp;hsa_kw=alura&amp;hsa_mt=e&amp;hsa_net=adwords&amp;hsa_ver=3&amp;gad_source=1&amp;gclid=Cj0KCQiAh8OtBhCQARIsAIkWb6-z-F1pmFNqDEZxb4QFuyOQpZnSqifWlS0CssVmU9qiz5pJl6zK-zcaApFpEALw_wcB\" target=\"_blank\" rel=\"noopener\">Alura</a> separou algumas para voc&ecirc; conferir.</p>\r\n<p>Veja <a href=\"https://site.alura.com.br/artigos/como-fazer-um-perfil-de-alta-qualidade-no-linkedin-e-atrair-mais-trabalhos?utm_term=&amp;utm_campaign=%5BSearch%5D+%5BPerformance%5D+-+Dynamic+Search+Ads+-+Artigos+e+Conte%C3%BAdos&amp;utm_source=adwords&amp;utm_medium=ppc&amp;hsa_acc=7964138385&amp;hsa_cam=11384329873&amp;hsa_grp=111087461203&amp;hsa_ad=687448474447&amp;hsa_src=g&amp;hsa_tgt=dsa-843358956400&amp;hsa_kw=&amp;hsa_mt=&amp;hsa_net=adwords&amp;hsa_ver=3&amp;gad_source=1&amp;gclid=Cj0KCQiAh8OtBhCQARIsAIkWb6_IpJbwAEhHqUTcEv5Om0lMJuLL70ty4O19TD5D6CAygMZ_rImdE7caAkGWEALw_wcB\" target=\"_blank\" rel=\"noopener\">AQUI</a></p>','2024-01-24',0,'','Perfil Linkedin',2);
/*!40000 ALTER TABLE `postagem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ofx66keruapi6vyqpv6f2or37` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (2,'ROLE_ADMIN'),(5,'ROLE_COLAB'),(1,'ROLE_USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suporte`
--

DROP TABLE IF EXISTS `suporte`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `suporte` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  `texto` longtext,
  `whatsapp` bit(1) DEFAULT NULL,
  `data_contato` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suporte`
--

LOCK TABLES `suporte` WRITE;
/*!40000 ALTER TABLE `suporte` DISABLE KEYS */;
INSERT INTO `suporte` VALUES (1,'contatoSuporte@email.com','Contato para Suporte','(11) 91234-5566','Teste de texto para contato',NULL,'2024-01-18'),(2,'contatoSuporte2@email.com','Contato para Suporte 2','(11) 91234-5566','Texto de teste de suporte e ajuda 2',_binary '','2024-01-18');
/*!40000 ALTER TABLE `suporte` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_roles` (
  `user_id` bigint DEFAULT NULL,
  `role_id` bigint NOT NULL,
  KEY `FKfakbbbivhkv984s4phqktqp7w` (`user_id`),
  KEY `FKj6m8fwv7oqv74fcehir1a9ffy` (`role_id`),
  CONSTRAINT `FKfakbbbivhkv984s4phqktqp7w` FOREIGN KEY (`user_id`) REFERENCES `usuario` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `FKj6m8fwv7oqv74fcehir1a9ffy` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` VALUES (1,1),(2,1),(3,1),(4,2),(5,5),(6,5),(7,5),(8,1),(9,5),(10,1);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `compartilhar_email` bit(1) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `senha` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,_binary '','manoel@email.com','Manoel Monteiro','$2a$10$WpdIclltqWu4veZw9R6UK.IXiTaNWdlV4DJkSrlgqQnbQ6pJgtSrG'),(2,_binary '\0','usuario@teste.com','Usuario Teste','$2a$10$NTKVg5abOel3dlmDOg7EROtNZX7f3rhgWnUjd7uOQniiSgi5EuOvy'),(3,_binary '\0','usuario2@teste.com','Usuario Teste2','$2a$10$.uPvdDCUR57mrfQri7rcHejvEzEE4U.QfTmlHWKN8f4zNqvYx1zvO'),(4,_binary '','admin@email.com','admin','$2a$10$JdFdWaeIwTSajhjuinL4zO7cH2amq.ir98HzZn1GcfDaTtjgqc6PO'),(5,_binary '\0','colab1@email.com','SEBRAE','$2a$10$AZt/I6948budW5fk9TK6o.3bwDY6lQF2qAl9e2dS6iTLSAu/vQPxC'),(6,_binary '\0','colab2@email.com','Colaborador 2','$2a$10$2PDMgeKgCLlGADgzqB.9wu6IrjgpFWKvUYJ7B5ibXU2LaPRJr5dxi'),(7,_binary '\0','testeColab@email.com','Teste Colab3','$2a$10$kf2RUgRF1uufgytWZTYJqOYCQBMu5wTcDaGeB9SfcQtKtvB7UUoXC'),(8,_binary '\0','usuarioCadasAD@email.com','Usuario Cadastrado pelo ADMIN','$2a$10$unD2WWchGZRS0bPEJYoebu4fTeHFM2cdJ/HRinFkYNmh4ufD0anDC'),(9,_binary '\0','usuarioTCol@email.com','Usuario Teste Colab Admin','$2a$10$HSfTcTIgitN38fKtX/IF8uOWuEAvYT5tYWJ84JFk0Q/r9jM8DvxWC'),(10,_binary '\0','userTest@email.com','Usuario Teste Admin USER','$2a$10$8ZCLAJ4dUsJvSpXlsCV36.uvHR2jd.5vQFjG1CPR.5flDnneGnvXa');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'empreenda_mais_v3'
--

--
-- Dumping routines for database 'empreenda_mais_v3'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-27 14:47:26
