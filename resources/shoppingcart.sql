CREATE DATABASE  IF NOT EXISTS `shoppingcart` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `shoppingcart`;
-- MySQL dump 10.13  Distrib 5.5.37, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: shoppingcart
-- ------------------------------------------------------
-- Server version	5.5.37-0ubuntu0.13.10.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tbl_category`
--

DROP TABLE IF EXISTS `tbl_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_category` (
  `key_category` int(11) NOT NULL AUTO_INCREMENT,
  `fld_category_name` varchar(45) NOT NULL,
  PRIMARY KEY (`key_category`),
  UNIQUE KEY `fld_category_name_UNIQUE` (`fld_category_name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_category`
--

LOCK TABLES `tbl_category` WRITE;
/*!40000 ALTER TABLE `tbl_category` DISABLE KEYS */;
INSERT INTO `tbl_category` VALUES (1,'CategoryA'),(2,'CategoryB'),(3,'CategoryC'),(6,'CategoryF');
/*!40000 ALTER TABLE `tbl_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_order`
--

DROP TABLE IF EXISTS `tbl_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_order` (
  `key_order` int(11) NOT NULL AUTO_INCREMENT,
  `key_user` int(11) NOT NULL,
  `fld_amount` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`key_order`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_order`
--

LOCK TABLES `tbl_order` WRITE;
/*!40000 ALTER TABLE `tbl_order` DISABLE KEYS */;
INSERT INTO `tbl_order` VALUES (1,1,180.00),(4,3,140.00),(6,2,555.00);
/*!40000 ALTER TABLE `tbl_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_order_detail`
--

DROP TABLE IF EXISTS `tbl_order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_order_detail` (
  `key_order_detail` int(11) NOT NULL AUTO_INCREMENT,
  `key_order` int(11) NOT NULL,
  `key_product` int(11) NOT NULL,
  `fld_quantity` int(11) unsigned NOT NULL,
  PRIMARY KEY (`key_order_detail`),
  KEY `fk_tbl_order_detail_tbl_order1_idx` (`key_order`),
  KEY `fk_tbl_order_detail_tbl_product1_idx` (`key_product`),
  CONSTRAINT `fk_tbl_order_detail_tbl_order1` FOREIGN KEY (`key_order`) REFERENCES `tbl_order` (`key_order`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tbl_order_detail_tbl_product1` FOREIGN KEY (`key_product`) REFERENCES `tbl_product` (`key_product`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_order_detail`
--

LOCK TABLES `tbl_order_detail` WRITE;
/*!40000 ALTER TABLE `tbl_order_detail` DISABLE KEYS */;
INSERT INTO `tbl_order_detail` VALUES (1,1,3,10),(2,1,2,1),(10,4,6,7),(13,6,5,5),(14,6,6,4),(15,6,7,4);
/*!40000 ALTER TABLE `tbl_order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_product`
--

DROP TABLE IF EXISTS `tbl_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_product` (
  `key_product` int(11) NOT NULL AUTO_INCREMENT,
  `key_category` int(11) NOT NULL,
  `fld_product_name` varchar(45) NOT NULL,
  `fld_inventory_qty` int(11) unsigned NOT NULL DEFAULT '0',
  `fld_unit_price` decimal(10,2) NOT NULL,
  `fld_product_image` varchar(45) NOT NULL,
  PRIMARY KEY (`key_product`),
  UNIQUE KEY `fld_product_name_UNIQUE` (`fld_product_name`),
  KEY `fk_tbl_product_tbl_category1_idx` (`key_category`),
  CONSTRAINT `fk_tbl_product_tbl_category1` FOREIGN KEY (`key_category`) REFERENCES `tbl_category` (`key_category`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_product`
--

LOCK TABLES `tbl_product` WRITE;
/*!40000 ALTER TABLE `tbl_product` DISABLE KEYS */;
INSERT INTO `tbl_product` VALUES (1,1,'Apple',91,10.00,'img/product.jpg'),(2,1,'Axe',50,100.00,'img/product.jpg'),(3,2,'Banana',56,8.00,'img/product.jpg'),(4,2,'Bat',10,50.00,'img/product.jpg'),(5,3,'Crop',70,15.00,'img/product.jpg'),(6,3,'Cutter',7,20.00,'img/product.jpg'),(7,3,'Fruit Salad',50,100.00,'img/product.jpg'),(8,3,'Fruit ',0,98.75,'img/product.jpg');
/*!40000 ALTER TABLE `tbl_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_role`
--

DROP TABLE IF EXISTS `tbl_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_role` (
  `key_role` int(11) NOT NULL,
  `fld_roletype` varchar(45) NOT NULL,
  PRIMARY KEY (`key_role`),
  UNIQUE KEY `fld_roletype_UNIQUE` (`fld_roletype`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_role`
--

LOCK TABLES `tbl_role` WRITE;
/*!40000 ALTER TABLE `tbl_role` DISABLE KEYS */;
INSERT INTO `tbl_role` VALUES (1,'admin'),(2,'cust');
/*!40000 ALTER TABLE `tbl_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_user`
--

DROP TABLE IF EXISTS `tbl_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_user` (
  `key_user` int(11) NOT NULL AUTO_INCREMENT,
  `key_role` int(11) NOT NULL,
  `fld_username` varchar(45) COLLATE latin1_general_cs NOT NULL,
  `fld_password` varchar(45) COLLATE latin1_general_cs NOT NULL,
  PRIMARY KEY (`key_user`),
  UNIQUE KEY `key_user_UNIQUE` (`key_user`),
  UNIQUE KEY `fld_username_UNIQUE` (`fld_username`),
  KEY `fk_tbl_user_tbl_role1_idx` (`key_role`),
  CONSTRAINT `fk_tbl_user_tbl_role1` FOREIGN KEY (`key_role`) REFERENCES `tbl_role` (`key_role`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1 COLLATE=latin1_general_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_user`
--

LOCK TABLES `tbl_user` WRITE;
/*!40000 ALTER TABLE `tbl_user` DISABLE KEYS */;
INSERT INTO `tbl_user` VALUES (1,1,'admin','admin'),(2,2,'cus1','cus1'),(3,2,'cus2','cus2');
/*!40000 ALTER TABLE `tbl_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-05-19 11:17:41
