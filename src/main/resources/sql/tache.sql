-- Création de la table tache
CREATE TABLE IF NOT EXISTS tache (
    id_tache INT AUTO_INCREMENT PRIMARY KEY,
    titre VARCHAR(100) NOT NULL,
    description TEXT,
    date_echeance DATE,
    priorite VARCHAR(20) NOT NULL,
    statut VARCHAR(20) NOT NULL,
    id_utilisateur INT NOT NULL,
    FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id_utilisateur) ON DELETE CASCADE
);

-- Insertion de quelques tâches de test
INSERT INTO tache (titre, description, date_echeance, priorite, statut, id_utilisateur)
VALUES 
    ('Préparer la présentation', 'Préparer la présentation pour la réunion de lundi', '2023-12-15', 'Haute', 'À faire', 1),
    ('Envoyer les emails', 'Envoyer les emails de confirmation aux clients', '2023-12-10', 'Moyenne', 'En cours', 1),
    ('Mettre à jour la documentation', 'Mettre à jour la documentation du projet', '2023-12-20', 'Basse', 'À faire', 2),
    ('Corriger les bugs', 'Corriger les bugs signalés par les utilisateurs', '2023-12-05', 'Haute', 'Terminée', 2),
    ('Planifier la réunion', 'Planifier la réunion d\'équipe pour la semaine prochaine', '2023-12-08', 'Moyenne', 'En cours', 3);