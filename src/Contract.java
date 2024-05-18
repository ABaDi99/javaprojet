import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Contract {
    private int idcontact;
    private String typeContrat;
    private int bienImmobilierID;
    private int clientID;
    private int agentImmobilierID;
    private String dateDebut;
    private String dateFin;
    private double montant;

    // Constructeur
    public Contract(int idcontact, String typeContrat, int bienImmobilierID, int clientID, int agentImmobilierID,
                    String dateDebut, String dateFin, double montant) {
        this.idcontact = idcontact;
        this.typeContrat = typeContrat;
        this.bienImmobilierID = bienImmobilierID;
        this.clientID = clientID;
        this.agentImmobilierID = agentImmobilierID;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.montant = montant;
    }
public Contract(){}
    // Méthode pour ajouter un contrat à la base de données
    public static void ajouterContrat() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Entrez l'ID du contrat : ");
            int contratId = scanner.nextInt();
            
            System.out.println("Entrez le type de contrat : ");
            String typeContrat = scanner.next();
            
            System.out.println("Entrez l'ID du bien immobilier : ");
            int bienImmobilierID = scanner.nextInt();
            
            System.out.println("Entrez l'ID du client : ");
            int clientID = scanner.nextInt();
            
            System.out.println("Entrez l'ID de l'agent immobilier : ");
            int agentImmobilierID = scanner.nextInt();
            
            System.out.println("Entrez la date de début (format YYYY-MM-DD) : ");
            String dateDebut = scanner.next();
            
            System.out.println("Entrez la date de fin (format YYYY-MM-DD) : ");
            String dateFin = scanner.next();
            
            System.out.println("Entrez le montant : ");
            double montant = scanner.nextDouble();
            
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "");

                // Requête SQL pour insérer le contrat dans la base de données
                String insertQuery = "INSERT INTO contrat (idconract, typeContrat, bienImmobilierID, clientID, agentImmobilierID, dateDebut, dateFin, montant) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setInt(1, contratId);
                preparedStatement.setString(2, typeContrat);
                preparedStatement.setInt(3, bienImmobilierID);
                preparedStatement.setInt(4, clientID);
                preparedStatement.setInt(5, agentImmobilierID);
                preparedStatement.setString(6, dateDebut);
                preparedStatement.setString(7, dateFin);
                preparedStatement.setDouble(8, montant);

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Le contrat a été ajouté avec succès !");
                } else {
                    System.out.println("Erreur lors de l'ajout du contrat.");
                }
            } catch (SQLException e) {
                System.out.println("Erreur SQL : " + e.getMessage());
            } finally {
                // Fermeture des ressources
                try {
                    if (resultSet != null) {
                        resultSet.close();
                    }
                    if (preparedStatement != null) {
                        preparedStatement.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    System.out.println("Erreur lors de la fermeture des ressources : " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la lecture des données : " + e.getMessage());
        }
    }


    public static void afficherTousLesContrats() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Établir la connexion à la base de données
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "");

            // Requête SQL pour récupérer tous les contrats
            String query = "SELECT * FROM contrat";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            // Afficher les en-têtes du tableau
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("| %-10s | %-20s | %-15s | %-15s | %-20s | %-15s | %-15s | %-10s |%n", "ID Contrat", "Type Contrat", "ID Bien Immobilier", "ID Client", "ID Agent Immobilier", "Date Début", "Date Fin", "Montant");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");

            // Afficher les résultats
            while (resultSet.next()) {
                int idContrat = resultSet.getInt("idconract");
                String typeContrat = resultSet.getString("TypeContrat");
                int bienImmobilierID = resultSet.getInt("BienImmobilierID");
                int clientID = resultSet.getInt("ClientID");
                int agentImmobilierID = resultSet.getInt("AgentImmobilierID");
                String dateDebut = resultSet.getString("DateDebut");
                String dateFin = resultSet.getString("DateFin");
                double montant = resultSet.getDouble("Montant");

                // Afficher chaque contrat
                System.out.printf("| %-10d | %-20s | %-15d | %-15d | %-20d | %-15s | %-15s | %-10.2f |%n", idContrat, typeContrat, bienImmobilierID, clientID, agentImmobilierID, dateDebut, dateFin, montant);
            }
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------");
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        } finally {
            // Fermeture des ressources
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la fermeture des ressources : " + e.getMessage());
            }
        }
}

}
