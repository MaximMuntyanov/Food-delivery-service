-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: fooddeliveryservice
-- ------------------------------------------------------
-- Server version	8.0.36

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
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client` (
  `client_id` int NOT NULL,
  `document` int NOT NULL,
  `name` varchar(40) NOT NULL,
  `surname` varchar(40) NOT NULL,
  `patronymics` varchar(40) DEFAULT NULL,
  `phone_number` text NOT NULL,
  `City` varchar(30) NOT NULL,
  `Street` varchar(60) NOT NULL,
  `Apartments` varchar(30) NOT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (1,123456,'Иван','Иванов','Иванович','1234567890','Kyiv','Borcanska','1');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `couriers`
--

DROP TABLE IF EXISTS `couriers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `couriers` (
  `courier_id` int NOT NULL,
  `courier_document` int NOT NULL,
  `phone` bigint NOT NULL,
  `status` enum('busy','free','delivering') NOT NULL,
  `name` varchar(40) NOT NULL,
  `surname` varchar(40) NOT NULL,
  `patronymics` varchar(40) DEFAULT NULL,
  `expirience` varchar(10) NOT NULL,
  `type_of_courier` enum('walking','bicycling','by car') DEFAULT NULL,
  `delivery_price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`courier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `couriers`
--

LOCK TABLES `couriers` WRITE;
/*!40000 ALTER TABLE `couriers` DISABLE KEYS */;
INSERT INTO `couriers` VALUES (1,1234567890,123456789,'free','Иван','Иванов','Иванович','3 года','bicycling',30.00);
/*!40000 ALTER TABLE `couriers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu` (
  `position_id` int NOT NULL,
  `restaraunts_id` int NOT NULL,
  `position_types_id` int NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `food_name` varchar(60) NOT NULL,
  PRIMARY KEY (`position_id`,`restaraunts_id`,`position_types_id`),
  KEY `fk_menu_restaraunts1_idx` (`restaraunts_id`),
  KEY `fk_menu_position_types1_idx` (`position_types_id`),
  CONSTRAINT `fk_menu_position_types1` FOREIGN KEY (`position_types_id`) REFERENCES `position_types` (`position_type_id`),
  CONSTRAINT `fk_menu_restaraunts1` FOREIGN KEY (`restaraunts_id`) REFERENCES `restaraunts` (`restaraunt_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES (1,1,1,8.99,'Pancakes'),(2,1,2,12.99,'Grilled Chicken Sandwich'),(3,2,3,15.99,'Spaghetti Carbonara'),(4,2,4,6.99,'Mozzarella Sticks'),(5,3,5,7.99,'Chocolate Lava Cake'),(6,3,6,2.49,'Soft Drink'),(7,4,7,9.99,'Caesar Salad'),(8,4,8,11.99,'Pesto Pasta'),(9,5,9,14.99,'Margherita Pizza'),(10,5,10,18.99,'California Roll'),(11,1,11,20.99,'Grilled Salmon'),(12,1,12,22.99,'Filet Mignon'),(13,2,13,5.99,'Fruit Salad'),(14,2,14,4.99,'Steamed Vegetables'),(15,3,15,3.49,'Croissant');
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordered_food`
--

DROP TABLE IF EXISTS `ordered_food`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ordered_food` (
  `order_id` int NOT NULL,
  `menu_position_id` int NOT NULL,
  `count` int NOT NULL,
  PRIMARY KEY (`order_id`,`menu_position_id`),
  KEY `fk_ordered_food_orders1_idx` (`order_id`),
  KEY `fk_ordered_food_menu1_idx` (`menu_position_id`),
  CONSTRAINT `fk_ordered_food_menu1` FOREIGN KEY (`menu_position_id`) REFERENCES `menu` (`position_id`),
  CONSTRAINT `fk_ordered_food_orders1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordered_food`
--

LOCK TABLES `ordered_food` WRITE;
/*!40000 ALTER TABLE `ordered_food` DISABLE KEYS */;
INSERT INTO `ordered_food` VALUES (1,1,2),(1,3,1),(1,7,1);
/*!40000 ALTER TABLE `ordered_food` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` int NOT NULL,
  `client_id` int NOT NULL,
  `couriers_id` int NOT NULL,
  `datetime` date NOT NULL,
  `status` enum('created','delivery','in progress','delivered') NOT NULL,
  `price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`order_id`,`client_id`,`couriers_id`),
  KEY `fk_orders_client1_idx` (`client_id`),
  KEY `fk_orders_couriers1_idx` (`couriers_id`),
  CONSTRAINT `fk_orders_client1` FOREIGN KEY (`client_id`) REFERENCES `client` (`client_id`),
  CONSTRAINT `fk_orders_couriers1` FOREIGN KEY (`couriers_id`) REFERENCES `couriers` (`courier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,1,1,'2024-05-02','created',73.96);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position_types`
--

DROP TABLE IF EXISTS `position_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `position_types` (
  `position_type_id` int NOT NULL,
  `food_type` varchar(255) NOT NULL,
  PRIMARY KEY (`position_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position_types`
--

LOCK TABLES `position_types` WRITE;
/*!40000 ALTER TABLE `position_types` DISABLE KEYS */;
INSERT INTO `position_types` VALUES (1,'Breakfast'),(2,'Lunch'),(3,'Dinner'),(4,'Appetizer'),(5,'Dessert'),(6,'Beverage'),(7,'Salad'),(8,'Pasta'),(9,'Pizza'),(10,'Sushi'),(11,'Fish'),(12,'Meat'),(13,'Fruits'),(14,'Vegetables'),(15,'Bakery');
/*!40000 ALTER TABLE `position_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reports`
--

DROP TABLE IF EXISTS `reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reports` (
  `order_id` int NOT NULL,
  `compl_datetime` date NOT NULL,
  `grade` smallint DEFAULT NULL,
  `comment_cl` varchar(150) DEFAULT NULL,
  `result` varchar(4000) NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `fk_reports_orders1_idx` (`order_id`),
  CONSTRAINT `fk_reports_orders1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reports`
--

LOCK TABLES `reports` WRITE;
/*!40000 ALTER TABLE `reports` DISABLE KEYS */;
/*!40000 ALTER TABLE `reports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaraunts`
--

DROP TABLE IF EXISTS `restaraunts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaraunts` (
  `restaraunt_id` int NOT NULL,
  `address` varchar(255) NOT NULL,
  `name` varchar(40) NOT NULL,
  PRIMARY KEY (`restaraunt_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaraunts`
--

LOCK TABLES `restaraunts` WRITE;
/*!40000 ALTER TABLE `restaraunts` DISABLE KEYS */;
INSERT INTO `restaraunts` VALUES (1,'Main Street','Delicious Delights'),(2,'Elm Street','Tasty Treats'),(3,'Oak Street','Savory Eats'),(4,'Maple Street','Yummy Yums'),(5,'Pine Street','Trendy Tastes');
/*!40000 ALTER TABLE `restaraunts` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-02 12:09:10
