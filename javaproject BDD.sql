-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : sam. 18 mai 2024 à 18:40
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `javaproject`
--

-- --------------------------------------------------------

--
-- Structure de la table `agentimmobilier`
--

CREATE TABLE `agentimmobilier` (
  `IdAgent` int(11) NOT NULL,
  `Nom` varchar(50) DEFAULT NULL,
  `Prenom` varchar(50) DEFAULT NULL,
  `AdresseEmail` varchar(100) DEFAULT NULL,
  `NumeroTelephone` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `agentimmobilier`
--

INSERT INTO `agentimmobilier` (`IdAgent`, `Nom`, `Prenom`, `AdresseEmail`, `NumeroTelephone`) VALUES
(0, NULL, NULL, NULL, NULL),
(3, 'uahsdh', 'asdsda', 'ashudu@gmail', '12'),
(4, 'Thomas', 'Amélie', 'amelie.thomas@email.com', '0123456792'),
(5, 'Petit', 'Julien', 'julien.petit@email.com', '0123456793'),
(6, 'Robert', 'Céline', 'celine.robert@email.com', '0123456794'),
(7, 'Richard', 'Élodie', 'elodie.richard@email.com', '0123456795'),
(10, 'Dupont', 'Jean', 'jean.dupont@example.com', '0601020304');

-- --------------------------------------------------------

--
-- Structure de la table `biensimmobilier`
--

CREATE TABLE `biensimmobilier` (
  `idBien` int(11) NOT NULL,
  `TYPE` varchar(255) DEFAULT NULL,
  `ETAT` varchar(255) DEFAULT NULL,
  `DESCRIPTION` text DEFAULT NULL,
  `PRIX` int(10) DEFAULT NULL,
  `ADRESS` varchar(255) DEFAULT NULL,
  `Id_Agent` int(11) DEFAULT NULL,
  `surface` decimal(10,2) DEFAULT NULL,
  `idClient` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `biensimmobilier`
--

INSERT INTO `biensimmobilier` (`idBien`, `TYPE`, `ETAT`, `DESCRIPTION`, `PRIX`, `ADRESS`, `Id_Agent`, `surface`, `idClient`) VALUES
(1, 'Appartement', 'À vendre', '3 pièces lumineux', 250000, '123 rue de la Paix', 3, 70.00, 1),
(2, 'Maison', 'À louer', 'Maison de ville avec jardin', 350000, '456 avenue du Général', 4, 120.00, NULL),
(3, 'Studio', 'À vendre', 'Proche des commerces', 150000, '789 boulevard Liberté', 5, 30.00, NULL),
(5, 'Villa', 'À vendre', 'Villa avec piscine', 550000, '202 chemin des Oliviers', 7, 200.00, NULL),
(6, 'Duplex', 'À louer', 'Duplex avec vue sur mer', 600000, '303 allée des Mouettes', NULL, 150.00, NULL),
(16, 'maison', 'a vendre', 'en face la gare', 13000, '13 rue de la paix', 3, 120.00, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE `client` (
  `idClient` int(10) NOT NULL,
  `NOM` varchar(255) DEFAULT NULL,
  `PRENOM` varchar(255) DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `type_client` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`idClient`, `NOM`, `PRENOM`, `EMAIL`, `type_client`) VALUES
(1, 'patpot', 'deff', 'sadasf@gmail.com', 'vendeur'),
(2, 'wedwwd', 'wefwfewf', 'wfwefwef@gmail.com', 'vendeurs'),
(3, 'Dupont', 'Jean', 'jean.dupont@example.com', 'acheteurs'),
(4, 'Lefevre', 'Pierre', 'pierre.lefevre@example.com', 'vendeur'),
(5, 'Dubois', 'Sophie', 'sophie.dubois@example.com', 'bailleurs'),
(6, 'Girard', 'Paul', 'paul.girard@example.com', 'acheteurs');

-- --------------------------------------------------------

--
-- Structure de la table `contrat`
--

CREATE TABLE `contrat` (
  `idconract` int(11) NOT NULL,
  `TypeContrat` varchar(100) DEFAULT NULL,
  `BienImmobilierID` int(11) DEFAULT NULL,
  `ClientID` int(11) DEFAULT NULL,
  `AgentImmobilierID` int(11) DEFAULT NULL,
  `DateDebut` varchar(10) DEFAULT NULL,
  `DateFin` varchar(10) DEFAULT NULL,
  `Montant` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `contrat`
--

INSERT INTO `contrat` (`idconract`, `TypeContrat`, `BienImmobilierID`, `ClientID`, `AgentImmobilierID`, `DateDebut`, `DateFin`, `Montant`) VALUES
(1, 'location', 1, 1, 3, '2024-05-18', '2025-05-18', 12000.00),
(2, 'vente', 2, 2, 4, '2024-06-01', '0', 250000.00),
(3, 'gestion', 3, 3, 5, '2024-07-01', '2025-07-01', 5000.00),
(4, 'maintenance', 5, 4, 3, '2024-08-01', '2024-12-31', 3000.00),
(5, 'rénovation', 1, 5, 4, '2024-09-01', '2024-12-01', 15000.00),
(6, 'location', 3, 2, 4, '2023-09-08', '2024-09-08', 6000.00);

-- --------------------------------------------------------

--
-- Structure de la table `interactions`
--

CREATE TABLE `interactions` (
  `idinteraction` int(11) NOT NULL,
  `agent_immobilier_id` int(11) DEFAULT NULL,
  `client_id` int(11) DEFAULT NULL,
  `date_interaction` varchar(10) DEFAULT NULL,
  `type_interaction` varchar(100) DEFAULT NULL,
  `description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `interactions`
--

INSERT INTO `interactions` (`idinteraction`, `agent_immobilier_id`, `client_id`, `date_interaction`, `type_interaction`, `description`) VALUES
(1, 3, 1, '2020-09-09', 'visit', 'ijdfisdf'),
(6, 3, 1, '20-02-2021', 'test', 'test');

-- --------------------------------------------------------

--
-- Structure de la table `rendv`
--

CREATE TABLE `rendv` (
  `idRend` int(10) DEFAULT NULL,
  `id_client` int(10) DEFAULT NULL,
  `date_R` varchar(100) DEFAULT NULL,
  `Statut` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `rendv`
--

INSERT INTO `rendv` (`idRend`, `id_client`, `date_R`, `Statut`) VALUES
(23, 4, '200-09-8', 'planifier'),
(44, 3, '2000', 'annuler'),
(1, 3, '2024-09-8', 'planifier');

-- --------------------------------------------------------

--
-- Structure de la table `transactions`
--

CREATE TABLE `transactions` (
  `transaction_id` int(11) NOT NULL,
  `bien_immobilier_id` int(11) DEFAULT NULL,
  `client_id` int(11) DEFAULT NULL,
  `type_transaction` varchar(100) DEFAULT NULL,
  `date_transaction` text DEFAULT NULL,
  `montant` decimal(10,2) DEFAULT NULL,
  `paiement_effectue` text DEFAULT NULL,
  `date_echeance` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `transactions`
--

INSERT INTO `transactions` (`transaction_id`, `bien_immobilier_id`, `client_id`, `type_transaction`, `date_transaction`, `montant`, `paiement_effectue`, `date_echeance`) VALUES
(1, 1, 1, 'vente', '2024-05-18', 250000.00, '1', NULL),
(2, 2, 2, 'location', '2024-05-19', 6000000.00, '0', '2024-06-19'),
(3, 3, 3, 'achat', '2024-05-20', 350000.00, '1', '2024-12-20'),
(5, 5, 4, 'loyer', '2024-05-21', 800.00, '1', NULL),
(6, 3, 5, 'vente', '20-09-2023', 600000.00, '1', '0');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `agentimmobilier`
--
ALTER TABLE `agentimmobilier`
  ADD PRIMARY KEY (`IdAgent`);

--
-- Index pour la table `biensimmobilier`
--
ALTER TABLE `biensimmobilier`
  ADD PRIMARY KEY (`idBien`),
  ADD KEY `agentid` (`Id_Agent`),
  ADD KEY `clientid` (`idClient`);

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`idClient`);

--
-- Index pour la table `contrat`
--
ALTER TABLE `contrat`
  ADD PRIMARY KEY (`idconract`),
  ADD KEY `fk_bien_immobilier_contrat` (`BienImmobilierID`),
  ADD KEY `fk_client_contrat` (`ClientID`),
  ADD KEY `fk_agent_immobilier_contrat` (`AgentImmobilierID`);

--
-- Index pour la table `interactions`
--
ALTER TABLE `interactions`
  ADD PRIMARY KEY (`idinteraction`),
  ADD KEY `agent_immobilier_id` (`agent_immobilier_id`),
  ADD KEY `client_id` (`client_id`);

--
-- Index pour la table `rendv`
--
ALTER TABLE `rendv`
  ADD KEY `clentid` (`id_client`);

--
-- Index pour la table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`transaction_id`),
  ADD KEY `bien_immobilier_id` (`bien_immobilier_id`),
  ADD KEY `client_id` (`client_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `interactions`
--
ALTER TABLE `interactions`
  MODIFY `idinteraction` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `biensimmobilier`
--
ALTER TABLE `biensimmobilier`
  ADD CONSTRAINT `agentid` FOREIGN KEY (`Id_Agent`) REFERENCES `agentimmobilier` (`IdAgent`) ON DELETE SET NULL,
  ADD CONSTRAINT `clientid` FOREIGN KEY (`idClient`) REFERENCES `client` (`idClient`) ON DELETE SET NULL;

--
-- Contraintes pour la table `contrat`
--
ALTER TABLE `contrat`
  ADD CONSTRAINT `fk_agent_immobilier_contrat` FOREIGN KEY (`AgentImmobilierID`) REFERENCES `agentimmobilier` (`IdAgent`),
  ADD CONSTRAINT `fk_bien_immobilier_contrat` FOREIGN KEY (`BienImmobilierID`) REFERENCES `biensimmobilier` (`idBien`),
  ADD CONSTRAINT `fk_client_contrat` FOREIGN KEY (`ClientID`) REFERENCES `client` (`idClient`);

--
-- Contraintes pour la table `interactions`
--
ALTER TABLE `interactions`
  ADD CONSTRAINT `interactions_ibfk_1` FOREIGN KEY (`agent_immobilier_id`) REFERENCES `agentimmobilier` (`IdAgent`),
  ADD CONSTRAINT `interactions_ibfk_2` FOREIGN KEY (`client_id`) REFERENCES `client` (`idClient`);

--
-- Contraintes pour la table `rendv`
--
ALTER TABLE `rendv`
  ADD CONSTRAINT `clentid` FOREIGN KEY (`id_client`) REFERENCES `client` (`idClient`) ON DELETE SET NULL;

--
-- Contraintes pour la table `transactions`
--
ALTER TABLE `transactions`
  ADD CONSTRAINT `transactions_ibfk_1` FOREIGN KEY (`bien_immobilier_id`) REFERENCES `biensimmobilier` (`idBien`),
  ADD CONSTRAINT `transactions_ibfk_2` FOREIGN KEY (`client_id`) REFERENCES `client` (`idClient`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
