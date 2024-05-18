import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import com.mysql.cj.xdevapi.Client;


public class interactions {

    private int idinteraction;
   private int  	agent_immobilier_id;
   private  int 	client_id;
   private String	date_interaction;	
   private String type_interaction;
   private String	description;

  // Constructor
  public interactions(int idinteraction, int agent_immobilier_id, int clientID, String date_interaction, String type_interaction, String description) {
    this.idinteraction = idinteraction;
    this.agent_immobilier_id = agent_immobilier_id;
    this.client_id = clientID;
    this.date_interaction = date_interaction;
    this.type_interaction = type_interaction;
    this.description = description;
}
public interactions (){}

public static void ajouterInteraction() {
    try (Scanner scanner = new Scanner(System.in)) {
        System.out.println("Entrez l'ID de l'interaction : ");
        int interactionId = scanner.nextInt();
        
        System.out.println("Entrez l'ID de l'agent immobilier : ");
        int agentId = scanner.nextInt();
        
        System.out.println("Entrez l'ID du client : ");
        int clientId = scanner.nextInt();
        
        System.out.println("Entrez la date de l'interaction (format DD-MM-YYYY) : ");
        String dateInteraction = scanner.next();
        
        System.out.println("Entrez le type d'interaction : ");
        String typeInteraction = scanner.next();
        
        System.out.println("Entrez la description de l'interaction : ");
        String description = scanner.next();
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "");

            // Requête SQL pour vérifier si l'ID de l'interaction existe déjà
            String interactionExistQuery = "SELECT COUNT(*) FROM interactions WHERE idinteraction = ?";
            preparedStatement = connection.prepareStatement(interactionExistQuery);
            preparedStatement.setInt(1, interactionId);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int interactionCount = resultSet.getInt(1);

            if (interactionCount > 0) {
                System.out.println("L'ID de l'interaction existe déjà dans la base de données.");
                return;
            }

            // Vérifier si l'ID du client existe dans la base de données
            String clientExistQuery = "SELECT COUNT(*) FROM client WHERE idClient = ?";
            preparedStatement = connection.prepareStatement(clientExistQuery);
            preparedStatement.setInt(1, clientId);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int clientCount = resultSet.getInt(1);

            if (clientCount == 0) {
                System.out.println("L'ID du client n'existe pas dans la base de données.");
                return;
            }

            // Vérifier si l'ID de l'agent immobilier existe dans la base de données
            String agentExistQuery = "SELECT COUNT(*) FROM agentimmobilier WHERE IdAgent = ?";
            preparedStatement = connection.prepareStatement(agentExistQuery);
            preparedStatement.setInt(1, agentId);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int agentCount = resultSet.getInt(1);

            if (agentCount == 0) {
                System.out.println("L'ID de l'agent immobilier n'existe pas dans la base de données.");
                return;
            }

            // Requête SQL pour insérer l'interaction dans la base de données
            String insertQuery = "INSERT INTO interactions (idinteraction,agent_immobilier_id, client_id, date_interaction, type_interaction, description) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, interactionId);
            preparedStatement.setInt(2, agentId);
            preparedStatement.setInt(3, clientId);
            preparedStatement.setString(4, dateInteraction);
            preparedStatement.setString(5, typeInteraction);
            preparedStatement.setString(6, description);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("L'interaction a été ajoutée avec succès !");
            } else {
                System.out.println("Erreur lors de l'ajout de l'interaction.");
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



 // Méthode pour afficher toutes les interactions présentes dans la base de données
 public static void afficherToutesInteractions() {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
        // Établir la connexion à la base de données
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "");

        // Requête SQL pour récupérer toutes les interactions
        String query = "SELECT * FROM interactions";
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        // Afficher les en-têtes du tableau
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-15s | %-20s | %-15s | %-15s | %-20s | %-30s |\n", "ID Interaction", "Agent immobilier ID", "Client ID", "Date interaction", "Type interaction", "Description");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");

        // Afficher les résultats
        while (resultSet.next()) {
            int idInteraction = resultSet.getInt("idinteraction");
            int agentId = resultSet.getInt("agent_immobilier_id");
            int clientId = resultSet.getInt("client_id");
            String dateInteraction = resultSet.getString("date_interaction");
            String typeInteraction = resultSet.getString("type_interaction");
            String description = resultSet.getString("description");

            // Afficher chaque interaction
            System.out.printf("| %-15d | %-20d | %-15d | %-15s | %-20s | %-30s |\n", idInteraction, agentId, clientId, dateInteraction, typeInteraction, description);
        }
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
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
