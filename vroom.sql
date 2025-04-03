-- Creation de la BD
CREATE DATABASE IF NOT EXISTS vroom;
USE vroom;

-- Table utilisateur:
CREATE TABLE IF NOT EXISTS utilisateur (
    idUser INT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(80) NOT NULL,
    lastName VARCHAR(80) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    motDePasse VARCHAR(255) NOT NULL,
    photo MEDIUMBLOB NOT NULL,
    numDeTele VARCHAR(255) NOT NULL UNIQUE,
    roleUser VARCHAR(12) NOT NULL, -- Soit conducteur ou passager
    isAdmin TINYINT DEFAULT 0, -- Si egal 0 donc il n'est pas admin
    dateCreation DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Table trajet:
CREATE TABLE IF NOT EXISTS trajet (
    idTrajet INT AUTO_INCREMENT PRIMARY KEY,
    idConducteur INT NOT NULL,
    pointDepart VARCHAR(100) NOT NULL, -- Coordonnees ex: 48.8584, 2.2945
    pointArrivee VARCHAR(100) NOT NULL, -- Coordonnees ex: 49.2538, 4.0330
    heureDepart DATETIME NOT NULL,
    placesDisponibles INT NOT NULL,
    prix DECIMAL(5,2) NOT NULL,
    etat VARCHAR(40) NOT NULL, -- (arrivé, en route...)
    FOREIGN KEY (idConducteur) REFERENCES utilisateur(idUser) -- Cle etrangere ds la table utilisateur
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

-- Table d'historique des trajet (utilisateur et trajet)
CREATE TABLE IF NOT EXISTS historiqueTrajet(
    idHistorique INT AUTO_INCREMENT PRIMARY KEY,
    idUser INT NOT NULL,
    idTrajet INT NOT NULL,
    FOREIGN KEY (idUser) REFERENCES utilisateur(idUser)  -- Cle etrangere ds la table utilisateur
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (idTrajet) REFERENCES trajet(idTrajet) -- Cle etrangere ds la table trajet
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

-- Table evaluations_de_conducteur:
-- pour evaluer un conducteur par un passager
CREATE TABLE IF NOT EXISTS evaluations_de_conducteur (
    idEvC INT AUTO_INCREMENT PRIMARY KEY,
    idAuteur INT NOT NULL,
    idUtilisateurEvalue INT NOT NULL,
    avis INT DEFAULT 0, -- Avis exemple (1-5): 1/5, 4/5, 5/5 ... 
    commentaire TEXT,
    dateEvaluation DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idAuteur) REFERENCES utilisateur(idUser)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (idUtilisateurEvalue) REFERENCES utilisateur(idUser)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

-- Table evaluations_de_passager:
-- pour evaluer un passager par un conducteur
CREATE TABLE IF NOT EXISTS evaluations_de_passager (
    idEvC INT AUTO_INCREMENT PRIMARY KEY,
    idAuteur INT NOT NULL,
    idUtilisateurEvalue INT NOT NULL,
    avis INT DEFAULT 0, -- Avis exemple (1-5): 1/5, 4/5, 5/5 ... 
    commentaire TEXT,
    dateEvaluation DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idAuteur) REFERENCES utilisateur(idUser)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (idUtilisateurEvalue) REFERENCES utilisateur(idUser)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

-- had 2 dernieres tables d evaluations ban lia n3awdhom ghir for a better readability

-- Table wallet:
CREATE TABLE IF NOT EXISTS wallet (
    idWallet INT AUTO_INCREMENT PRIMARY KEY,
    idUser INT NOT NULL,
    solde DECIMAL(10,2) DEFAULT 0,
    FOREIGN KEY (idUser) REFERENCES utilisateur(idUser)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

-- Table transactions:
-- methode de paiement est en ligne, en application, en chargeant la wallet
-- c'est ce qu'on a dit ⬆
CREATE TABLE IF NOT EXISTS transactions (
    idTransaction INT AUTO_INCREMENT PRIMARY KEY,
    idWallet INT NOT NULL,
    typeTransaction VARCHAR(40) NOT NULL, -- ex: dépôt, paiement...
    montant DECIMAL(5,2) NOT NULL,
    dateTransaction DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idWallet) REFERENCES wallet(idWallet)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

-- Table messages:
CREATE TABLE IF NOT EXISTS messages (
    idMessage INT AUTO_INCREMENT PRIMARY KEY,
    idExpediteur INT NOT NULL,
    idDestinataire INT NOT NULL,
    contenu TEXT NOT NULL,
    dateEnvoi DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idExpediteur) REFERENCES utilisateur(idUser)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (idDestinataire) REFERENCES utilisateur(idUser)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

-- Table notification:
-- ex: nouveau trajet, nouveau message...
CREATE TABLE IF NOT EXISTS notifications (
    idNotifications INT AUTO_INCREMENT PRIMARY KEY,
    idUser INT NOT NULL,
    titre VARCHAR(70) NOT NULL,
    message TEXT NOT NULL,
    dateNotification DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idUser) REFERENCES utilisateur(idUser)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
