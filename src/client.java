import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import com.mysql.cj.xdevapi.Client;



public class client {
  

    private int idClient;
    private String NOM;
    private String PRENOM;
    private String EMAIL;
    private String type_client;

    // Constructor
    public client(int idClient, String NOM, String PRENOM, String EMAIL, String type_client) {
        this.idClient = idClient;
        this.NOM = NOM;
        this.PRENOM = PRENOM;
        this.EMAIL = EMAIL;
        this.type_client = type_client;
    }
         public client (){}










    // Méthode pour ajouter un client à la base de données
    public static void ajouterClient() {
        try (
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "");
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO client (idClient, NOM, PRENOM, EMAIL, type_client) VALUES (?, ?, ?, ?, ?)")) {
            
                Scanner scanner = new Scanner(System.in);
            System.out.println("Entrez l'ID du client:");
            int idClient = scanner.nextInt();
            scanner.nextLine(); // Pour consommer le saut de ligne

            System.out.println("Entrez le nom du client:");
            String nom = scanner.nextLine();

            System.out.println("Entrez le prénom du client:");
            String prenom = scanner.nextLine();

            System.out.println("Entrez l'email du client:");
            String email = scanner.nextLine();

            System.out.println("Entrez le type du client (acheteurs, locataires, vendeurs, bailleurs):");
            String typeClient = scanner.nextLine();
            // Vérification que le type_client est l'un des valeurs spécifiées
            if (!typeClient.equals("acheteurs") && !typeClient.equals("locataires") &&
                    !typeClient.equals("vendeurs") && !typeClient.equals("bailleurs")) {
                System.out.println("Type de client invalide !");
                return; // Sortir de la méthode si le type de client est invalide
            }

            pstmt.setInt(1, idClient);
            pstmt.setString(2, nom);
            pstmt.setString(3, prenom);
            pstmt.setString(4, email);
            pstmt.setString(5, typeClient);
            pstmt.executeUpdate();
            System.out.println("Client ajouté avec succès à la base de données.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du client à la base de données: " + e.getMessage());
        }
    }

    // Méthode pour afficher tous les clients de la base de données
    public static void afficherTousClients() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "");
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM client");
             ResultSet rs = pstmt.executeQuery()) {
            // Affichage des en-têtes du tableau
            System.out.println("ID       NOM               PRENOM            EMAIL                      TYPE CLIENT");
            System.out.println("=====================================================================================");

            // Parcourir les résultats et afficher les clients
            while (rs.next()) {
                int idClient = rs.getInt("idClient");
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String email = rs.getString("EMAIL");
                String typeClient = rs.getString("type_client");

                // Formater l'affichage avec des espaces
                System.out.printf("%-9d%-19s%-18s%-27s%s%n", idClient, nom, prenom, email, typeClient);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des clients depuis la base de données: " + e.getMessage());
        }
    }

    // Méthode pour supprimer un client de la base de données
    public static void supprimerClient() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "");
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM client WHERE idClient = ?")) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Entrez l'ID du client que vous souhaitez supprimer:");
            int idClient = scanner.nextInt();

            pstmt.setInt(1, idClient);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Le client a été supprimé avec succès de la base de données.");
            } else {
                System.out.println("Aucun client avec cet ID n'a été trouvé dans la base de données.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du client de la base de données: " + e.getMessage());
        }
    }

    // Méthode pour modifier les attributs d'un client dans la base de données
    public static void modifierClient() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "")) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Entrez l'ID du client que vous souhaitez modifier:");
            int idClient = scanner.nextInt();
            scanner.nextLine(); // Pour consommer le saut de ligne

            // Vérifier si le client existe
            try (PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM client WHERE idClient = ?")) {
                checkStmt.setInt(1, idClient);
                if (!checkStmt.executeQuery().next()) {
                    System.out.println("Aucun client avec cet ID n'a été trouvé dans la base de données.");
                    return;
                }
            }

            // Afficher les options pour les attributs à modifier
            System.out.println("Choisissez l'attribut à modifier:");
            System.out.println("N - Nom");
            System.out.println("P - Prénom");
            System.out.println("E - Email");
            System.out.println("T - Type de client");

            // Lire le choix de l'utilisateur
            char choice = Character.toUpperCase(scanner.nextLine().charAt(0));

            // Variable pour stocker la nouvelle valeur de l'attribut
            String newValue;

            // Utiliser une instruction switch pour sélectionner l'attribut à modifier
            switch (choice) {
                case 'N':
                    System.out.println("Entrez le nouveau nom du client:");
                    newValue = scanner.nextLine();
                    modifierAttribut(conn, "NOM", newValue, idClient);
                    break;
                case 'P':
                    System.out.println("Entrez le nouveau prénom du client:");
                    newValue = scanner.nextLine();
                    modifierAttribut(conn, "PRENOM", newValue, idClient);
                    break;
                case 'E':
                    System.out.println("Entrez le nouvel email du client:");
                    newValue = scanner.nextLine();
                    modifierAttribut(conn, "EMAIL", newValue, idClient);
                    break;
                case 'T':
                    System.out.println("Entrez le nouveau type du client (acheteurs, locataires, vendeurs, bailleurs):");
                    newValue = scanner.nextLine();
                    modifierAttribut(conn, "type_client", newValue, idClient);
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification des attributs du client: " + e.getMessage());
        }
    }

    // Méthode pour modifier un attribut spécifique d'un client
    private static void modifierAttribut(Connection conn, String attribut, String newValue, int idClient) throws SQLException {
        try (PreparedStatement updateStmt = conn.prepareStatement("UPDATE client SET " + attribut + " = ? WHERE idClient = ?")) {
            updateStmt.setString(1, newValue);
            updateStmt.setInt(2, idClient);
            int rowsUpdated = updateStmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("L'attribut " + attribut + " du client a été mis à jour avec succès.");
            } else {
                System.out.println("La mise à jour de l'attribut " + attribut + " du client a échoué.");
            }
        }
    }
}
