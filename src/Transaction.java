import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Transaction {
    private int transactionId;
    private int bienImmobilierId;
    private int clientId;
    private String typeTransaction;
    private String dateTransaction;
    private double montant;
    private boolean paiementEffectue;
    private String dateEcheance;

    // Constructeur
    public Transaction(int transactionId, int bienImmobilierId, int clientId, String typeTransaction, String dateTransaction, double montant, boolean paiementEffectue, String dateEcheance) {
        this.transactionId = transactionId;
        this.bienImmobilierId = bienImmobilierId;
        this.clientId = clientId;
        this.typeTransaction = typeTransaction;
        this.dateTransaction = dateTransaction;
        this.montant = montant;
        this.paiementEffectue = paiementEffectue;
        this.dateEcheance = dateEcheance;
    }
public Transaction (){}
    // Méthode pour ajouter une transaction
    public void ajouterTransaction() {
        Scanner scanner = new Scanner(System.in);
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Initialisation de la connexion à la base de données
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "");

            // Demander à l'utilisateur d'entrer les données de la transaction
            System.out.println("Entrez l'ID de la transaction :");
            int transactionId = scanner.nextInt();
            System.out.println("Entrez l'ID du bien immobilier :");
            int bienImmobilierId = scanner.nextInt();
            // Vérifier si bienImmobilierId existe
            if (!validerBienImmobilierId(connection, bienImmobilierId)) {
                System.out.println("ID de bien immobilier incorrect.");
                return;
            }
            System.out.println("Entrez l'ID du client :");
            int clientId = scanner.nextInt();
            // Vérifier si clientId existe
            if (!validerClientId(connection, clientId)) {
                System.out.println("ID de client incorrect.");
                return;
            }
            System.out.println("Entrez le type de transaction (vente, location, gestion) :");
            String typeTransaction = scanner.next();
            // Vérifier si typeTransaction est valide
            if (!typeTransaction.equals("vente") && !typeTransaction.equals("location") && !typeTransaction.equals("gestion")) {
                System.out.println("Type de transaction incorrect.");
                return;
            }
            System.out.println("Entrez la date de transaction (format DD-MM-YYYY):");
            String dateTransaction = scanner.next();
            System.out.println("Entrez le montant :");
            double montant = scanner.nextDouble();
            System.out.println("Le paiement a-t-il été effectué ? (true/false) :");
            boolean paiementEffectue = scanner.nextBoolean();
            System.out.println("Entrez la date d'échéance (format DD-MM-YYYY):");
            String dateEcheance = scanner.next();

            // Requête SQL pour insérer une transaction dans la table
            String sql = "INSERT INTO transactions (transaction_id, bien_immobilier_id, client_id, type_transaction, date_transaction, montant, paiement_effectue, date_echeance) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, transactionId);
            statement.setInt(2, bienImmobilierId);
            statement.setInt(3, clientId);
            statement.setString(4, typeTransaction);
            statement.setString(5, dateTransaction);
            statement.setDouble(6, montant);
            statement.setBoolean(7, paiementEffectue);
            statement.setString(8, dateEcheance);

            // Exécution de la requête
            statement.executeUpdate();
            System.out.println("Transaction ajoutée avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fermeture des ressources
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Méthode pour valider si l'ID du bien immobilier existe
    private boolean validerBienImmobilierId(Connection connection, int bienImmobilierId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM biensimmobilier WHERE idBien = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, bienImmobilierId);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1) == 1;
    }

    // Méthode pour valider si l'ID du client existe
    private boolean validerClientId(Connection connection, int clientId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM client WHERE idClient = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, clientId);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1) == 1;
    }





  // Méthode pour afficher toutes les transactions avec un format tabulaire et des lignes séparatrices
public void afficherToutesTransactions() {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
        // Initialisation de la connexion à la base de données
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "");

        // Requête SQL pour sélectionner toutes les transactions
        String sql = "SELECT * FROM transactions";
        statement = connection.prepareStatement(sql);
        resultSet = statement.executeQuery();

        // Affichage des en-têtes
        System.out.printf("%-18s | %-18s | %-18s | %-18s | %-18s | %-18s | %-18s | %-18s%n", 
                          "Transaction ID", "Bien Immobilier ID", "Client ID", "Type Transaction",
                          "Date Transaction", "Montant", "Paiement Effectué", "Date Échéance");
        // Affichage des lignes de séparation
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");

        // Affichage des transactions
        while (resultSet.next()) {
            System.out.printf("%-18d | %-18d | %-18d | %-18s | %-18s | %-18.2f | %-18b | %-18s%n",
                              resultSet.getInt("transaction_id"),
                              resultSet.getInt("bien_immobilier_id"),
                              resultSet.getInt("client_id"),
                              resultSet.getString("type_transaction"),
                              resultSet.getString("date_transaction"),
                              resultSet.getDouble("montant"),
                              resultSet.getBoolean("paiement_effectue"),
                              resultSet.getString("date_echeance"));
            // Affichage d'une ligne de séparation après chaque transaction
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Fermeture des ressources
        try {
            if (resultSet != null)
                resultSet.close();
            if (statement != null)
                statement.close();
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
public void supprimerTransaction() {
    Scanner scanner = new Scanner(System.in);
    Connection connection = null;
    PreparedStatement statement = null;

    try {
        // Initialisation de la connexion à la base de données
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "");

        // Demander à l'utilisateur d'entrer l'ID de la transaction à supprimer
        System.out.println("Entrez l'ID de la transaction à supprimer :");
        int transactionId = scanner.nextInt();

        // Requête SQL pour supprimer la transaction avec l'ID donné
        String sql = "DELETE FROM transactions WHERE transaction_id = ?";
        statement = connection.prepareStatement(sql);
        statement.setInt(1, transactionId);

        // Exécution de la requête
        int rowsAffected = statement.executeUpdate();

        // Vérification si la transaction a été supprimée avec succès
        if (rowsAffected > 0) {
            System.out.println("Transaction avec l'ID " + transactionId + " supprimée avec succès !");
        } else {
            System.out.println("Aucune transaction trouvée avec l'ID " + transactionId);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Fermeture des ressources
        try {
            if (statement != null)
                statement.close();
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

}
 // Méthode pour modifier une transaction
 public void modifierTransaction() {
    Scanner scanner = new Scanner(System.in);
    Connection connection = null;
    PreparedStatement statement = null;

    try {
        // Initialisation de la connexion à la base de données
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "");

        // Demander à l'utilisateur d'entrer l'ID de la transaction à modifier
        System.out.println("Entrez l'ID de la transaction à modifier :");
        int transactionId = scanner.nextInt();

        // Vérifier si la transaction avec cet ID existe
        if (!validerTransactionId(connection, transactionId)) {
            System.out.println("Aucune transaction trouvée avec l'ID " + transactionId);
            return;
        }

        boolean continuer = true;
        while (continuer) {
            // Afficher le menu des options pour modifier
            System.out.println("Que voulez-vous modifier ?");
            System.out.println("1. Type de transaction");
            System.out.println("2. Date de transaction");
            System.out.println("3. Montant");
            System.out.println("4. Paiement effectué");
            System.out.println("5. Date d'échéance");
            System.out.println("0. Terminer la modification");

            // Laisser l'utilisateur choisir une option
            int choix = scanner.nextInt();
            switch (choix) {
                case 1:
                    // Modifier le type de transaction
                    System.out.println("Entrez le nouveau type de transaction (vente, location, gestion) :");
                    String nouveauTypeTransaction = scanner.next();
                    // Vérifier si le nouveau type de transaction est valide
                    if (!nouveauTypeTransaction.equals("vente") && !nouveauTypeTransaction.equals("location") && !nouveauTypeTransaction.equals("gestion")) {
                        System.out.println("Type de transaction incorrect.");
                    } else {
                        // Exécuter la mise à jour dans la base de données
                        String updateTypeTransactionSQL = "UPDATE transactions SET type_transaction = ? WHERE transaction_id = ?";
                        PreparedStatement updateTypeTransactionStatement = connection.prepareStatement(updateTypeTransactionSQL);
                        updateTypeTransactionStatement.setString(1, nouveauTypeTransaction);
                        updateTypeTransactionStatement.setInt(2, transactionId);
                        updateTypeTransactionStatement.executeUpdate();
                        System.out.println("Type de transaction mis à jour avec succès.");
                    }
                    break;
                case 2:
                    // Modifier la date de transaction
                    System.out.println("Entrez la nouvelle date de transaction (format DD-MM-YYYY) :");
                    String nouvelleDateTransaction = scanner.next();
                    // Exécuter la mise à jour dans la base de données
                    String updateDateTransactionSQL = "UPDATE transactions SET date_transaction = ? WHERE transaction_id = ?";
                    PreparedStatement updateDateTransactionStatement = connection.prepareStatement(updateDateTransactionSQL);
                    updateDateTransactionStatement.setString(1, nouvelleDateTransaction);
                    updateDateTransactionStatement.setInt(2, transactionId);
                    updateDateTransactionStatement.executeUpdate();
                    System.out.println("Date de transaction mise à jour avec succès.");
                    break;
                case 3:
                    // Modifier le montant
                    System.out.println("Entrez le nouveau montant :");
                    double nouveauMontant = scanner.nextDouble();
                    // Exécuter la mise à jour dans la base de données
                    String updateMontantSQL = "UPDATE transactions SET montant = ? WHERE transaction_id = ?";
                    PreparedStatement updateMontantStatement = connection.prepareStatement(updateMontantSQL);
                    updateMontantStatement.setDouble(1, nouveauMontant);
                    updateMontantStatement.setInt(2, transactionId);
                    updateMontantStatement.executeUpdate();
                    System.out.println("Montant mis à jour avec succès.");
                    break;
                case 4:
                    // Modifier le paiement effectué
                    System.out.println("Le paiement a-t-il été effectué ? (true/false) :");
                    boolean nouveauPaiementEffectue = scanner.nextBoolean();
                    // Exécuter la mise à jour dans la base de données
                    String updatePaiementEffectueSQL = "UPDATE transactions SET paiement_effectue = ? WHERE transaction_id = ?";
                    PreparedStatement updatePaiementEffectueStatement = connection.prepareStatement(updatePaiementEffectueSQL);
                    updatePaiementEffectueStatement.setBoolean(1, nouveauPaiementEffectue);
                    updatePaiementEffectueStatement.setInt(2, transactionId);
                    updatePaiementEffectueStatement.executeUpdate();
                    System.out.println("Paiement effectué mis à jour avec succès.");
                    break;
                case 5:
                    // Modifier la date d'échéance
                    System.out.println("Entrez la nouvelle date d'échéance (format DD-MM-YYYY) :");
                    String nouvelleDateEcheance = scanner.next();
                    // Exécuter la mise à jour dans la base de données
                    String updateDateEcheanceSQL = "UPDATE transactions SET date_echeance = ? WHERE transaction_id = ?";
                    PreparedStatement updateDateEcheanceStatement = connection.prepareStatement(updateDateEcheanceSQL);
                    updateDateEcheanceStatement.setString(1, nouvelleDateEcheance);
                    updateDateEcheanceStatement.setInt(2, transactionId);
                    updateDateEcheanceStatement.executeUpdate();
                    System.out.println("Date d'échéance mise à jour avec succès.");
                    break;
                case 0:
                    // Terminer la modification
                    continuer = false;
                    break;
                default:
                    System.out.println("Choix invalide.");
                    break;
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Fermeture des ressources
        try {
            if (statement != null)
                statement.close();
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// Méthode pour valider l'ID de la transaction
private boolean validerTransactionId(Connection connection, int transactionId) throws SQLException {
    String sql = "SELECT * FROM transactions WHERE transaction_id = ?";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setInt(1, transactionId);
    ResultSet resultSet = statement.executeQuery();
    return resultSet.next();
}

}
