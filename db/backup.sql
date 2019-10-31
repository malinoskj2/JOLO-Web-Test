-- MySQL dump 10.13  Distrib 5.7.27, for Linux (x86_64)
--
-- Host: localhost    Database: jolo
-- ------------------------------------------------------
-- Server version	5.7.27-0ubuntu0.18.04.1

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
-- Table structure for table `AnswerAttempt`
--

DROP TABLE IF EXISTS `AnswerAttempt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AnswerAttempt` (
  `answerAttemptID` int(11) NOT NULL,
  `testSubmissionID` int(11) NOT NULL,
  `questionID` int(11) NOT NULL,
  `GuessedAngle1` int(3) DEFAULT NULL,
  `GuessedAngle2` int(3) DEFAULT NULL,
  `audioFile` varchar(2083) DEFAULT NULL,
  PRIMARY KEY (`answerAttemptID`),
  KEY `AA_CON_1` (`testSubmissionID`),
  KEY `questionID` (`questionID`),
  CONSTRAINT `AA_CON_1` FOREIGN KEY (`testSubmissionID`) REFERENCES `TestSubmission` (`testSubmissionID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `AnswerAttempt_ibfk_1` FOREIGN KEY (`questionID`) REFERENCES `Questions` (`questionID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AnswerAttempt`
--

LOCK TABLES `AnswerAttempt` WRITE;
/*!40000 ALTER TABLE `AnswerAttempt` DISABLE KEYS */;
/*!40000 ALTER TABLE `AnswerAttempt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Examiner`
--

DROP TABLE IF EXISTS `Examiner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Examiner` (
  `examID` int(11) NOT NULL,
  `fNAME` varchar(20) DEFAULT NULL,
  `lName` varchar(20) DEFAULT NULL,
  `uName` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`examID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Examiner`
--

LOCK TABLES `Examiner` WRITE;
/*!40000 ALTER TABLE `Examiner` DISABLE KEYS */;
/*!40000 ALTER TABLE `Examiner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Patients`
--

DROP TABLE IF EXISTS `Patients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Patients` (
  `patientID` int(11) NOT NULL,
  `examID` int(11) NOT NULL,
  PRIMARY KEY (`patientID`),
  KEY `examID` (`examID`),
  CONSTRAINT `Patients_ibfk_1` FOREIGN KEY (`examID`) REFERENCES `Examiner` (`examID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Patients`
--

LOCK TABLES `Patients` WRITE;
/*!40000 ALTER TABLE `Patients` DISABLE KEYS */;
/*!40000 ALTER TABLE `Patients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Questions`
--

DROP TABLE IF EXISTS `Questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Questions` (
  `questionID` int(11) NOT NULL,
  `angle1` varchar(3) DEFAULT NULL,
  `angle2` varchar(3) DEFAULT NULL,
  `line1Start` varchar(3) DEFAULT NULL,
  `line1End` varchar(3) DEFAULT NULL,
  `line2Start` varchar(3) DEFAULT NULL,
  `line2End` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`questionID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Questions`
--

LOCK TABLES `Questions` WRITE;
/*!40000 ALTER TABLE `Questions` DISABLE KEYS */;
/*!40000 ALTER TABLE `Questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Test`
--

DROP TABLE IF EXISTS `Test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Test` (
  `testID` int(11) NOT NULL,
  PRIMARY KEY (`testID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Test`
--

LOCK TABLES `Test` WRITE;
/*!40000 ALTER TABLE `Test` DISABLE KEYS */;
/*!40000 ALTER TABLE `Test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TestSubmission`
--

DROP TABLE IF EXISTS `TestSubmission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TestSubmission` (
  `testSubmissionID` int(11) NOT NULL,
  `testID` int(11) NOT NULL,
  `examID` int(11) NOT NULL,
  `patientID` int(11) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`testSubmissionID`),
  KEY `testID` (`testID`),
  KEY `examID` (`examID`),
  KEY `patientID` (`patientID`),
  CONSTRAINT `TestSubmission_ibfk_1` FOREIGN KEY (`testID`) REFERENCES `Test` (`testID`),
  CONSTRAINT `TestSubmission_ibfk_2` FOREIGN KEY (`examID`) REFERENCES `Examiner` (`examID`),
  CONSTRAINT `TestSubmission_ibfk_3` FOREIGN KEY (`patientID`) REFERENCES `Patients` (`patientID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TestSubmission`
--

LOCK TABLES `TestSubmission` WRITE;
/*!40000 ALTER TABLE `TestSubmission` DISABLE KEYS */;
/*!40000 ALTER TABLE `TestSubmission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TestToQuestion`
--

DROP TABLE IF EXISTS `TestToQuestion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TestToQuestion` (
  `testID` int(11) DEFAULT NULL,
  `questionID` int(11) DEFAULT NULL,
  KEY `testID` (`testID`),
  KEY `questionID` (`questionID`),
  CONSTRAINT `TestToQuestion_ibfk_1` FOREIGN KEY (`testID`) REFERENCES `Test` (`testID`),
  CONSTRAINT `TestToQuestion_ibfk_2` FOREIGN KEY (`questionID`) REFERENCES `Questions` (`questionID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TestToQuestion`
--

LOCK TABLES `TestToQuestion` WRITE;
/*!40000 ALTER TABLE `TestToQuestion` DISABLE KEYS */;
/*!40000 ALTER TABLE `TestToQuestion` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-10-31 21:10:12
