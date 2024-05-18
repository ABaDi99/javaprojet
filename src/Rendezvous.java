import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Rendezvous {

    private int idRend;
    private int id_client;
    private String date_R;
    private String statut;

    public Rendezvous(int idRend, int id_client, String date_R, String statut) {
        this.idRend = idRend;
        this.id_client = id_client;
        this.date_R = date_R;
        this.statut = statut;
    }

    public Rendezvous() {
    }

    public void ajouterRendezvousDepuisConsole() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Entrez l'id du rendezvous: ");
        this.idRend = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character left by nextInt()

        System.out.print("Entrez l'id du client: ");
        this.id_client = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character left by nextInt()

        System.out.print("Entrez la date du rendezvous (YYYY-MM-DD): ");
        this.date_R = scanner.nextLine();

        System.out.print("Entrez le statut (planifier,confirme,annuler): ");
        this.statut = scanner.nextLine();

        

        String checkClientQuery = "SELECT * FROM client WHERE idClient = ?";
        String checkRendQuery = "SELECT * FROM rendv WHERE idRend = ?";
        String insertQuery = "INSERT INTO rendv (idRend, id_client, date_R, statut) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "");
             PreparedStatement checkClientStmt = conn.prepareStatement(checkClientQuery);
             PreparedStatement checkRendStmt = conn.prepareStatement(checkRendQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

            // Check if id_client exists
            checkClientStmt.setInt(1, id_client);
            try (ResultSet rs = checkClientStmt.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("Client with id " + id_client + " does not exist.");
                    return;
                }
            }

            // Check if idRend exists
            checkRendStmt.setInt(1, idRend);
            try (ResultSet rs = checkRendStmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Rendezvous with id " + idRend + " already exists.");
                    return;
                }
            }

            // Insert the appointment
            insertStmt.setInt(1, idRend);
            insertStmt.setInt(2, id_client);
            insertStmt.setString(3, date_R);
            insertStmt.setString(4, statut);
            int affectedRows = insertStmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Rendezvous added successfully.");
            } else {
                System.out.println("Failed to add Rendezvous.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
   


     // Méthode pour supprimer un rendezvous par son id
     public void supprimerRendezvous() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Entrez l'id du rendezvous à supprimer : ");
        int idRend = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character left by nextInt()

        String checkRendQuery = "SELECT * FROM rendv WHERE idRend = ?";
        String deleteQuery = "DELETE FROM rendv WHERE idRend = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "");
             PreparedStatement checkRendStmt = conn.prepareStatement(checkRendQuery);
             PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {

            // Vérifier si idRend existe
            checkRendStmt.setInt(1, idRend);
            try (ResultSet rs = checkRendStmt.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("Rendezvous with id " + idRend + " does not exist.");
                    return;
                }
            }

            // Supprimer le rendezvous
            deleteStmt.setInt(1, idRend);

            int affectedRows = deleteStmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Rendezvous with id " + idRend + " has been successfully deleted.");
            } else {
                System.out.println("Rendezvous with id " + idRend + " could not be deleted.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } 
        
    }



    public void afficherTousRendezvous() {
        String query = "SELECT * FROM rendv";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "");
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("Liste des Rendezvous :");
            System.out.println("ID  | ID Client | Date Rendezvous | Statut");
            System.out.println("-------------------------------------------");

            while (rs.next()) {
                int idRend = rs.getInt("idRend");
                int id_client = rs.getInt("id_client");
                String date_R = rs.getString("date_R");
                String statut = rs.getString("statut");

                System.out.format("%-4d| %-10d| %-15s| %-8s%n", idRend, id_client, date_R, statut);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


 // Méthode pour modifier un rendezvous par son id
 public void modifierRendezvous() {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Entrez l'id du rendezvous à modifier : ");
    int idRend = scanner.nextInt();
    scanner.nextLine(); // Consume the newline character left by nextInt()

    System.out.print("Choisissez ce que vous voulez modifier (statut ou date) : ");
    String choix = scanner.nextLine().trim();

    switch (choix.toLowerCase()) {
        case "statut":
            System.out.print("Entrez le nouveau statut (planifier, confirme, annuler) : ");
            String newStatut = scanner.nextLine().trim();
            
            String updateStatutQuery = "UPDATE rendv SET statut = ? WHERE idRend = ?";

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "");
                 PreparedStatement updateStatutStmt = conn.prepareStatement(updateStatutQuery)) {

                updateStatutStmt.setString(1, newStatut);
                updateStatutStmt.setInt(2, idRend);

                int affectedRows = updateStatutStmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Statut du rendezvous avec id " + idRend + " modifié avec succès.");
                } else {
                    System.out.println("Le statut du rendezvous avec id " + idRend + " n'a pas pu être modifié.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            break;
        case "date":
            System.out.print("Entrez la nouvelle date (YYYY-MM-DD) : ");
            String newDate = scanner.nextLine().trim();
            
            String updateDateQuery = "UPDATE rendv SET date_R = ? WHERE idRend = ?";

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "");
                 PreparedStatement updateDateStmt = conn.prepareStatement(updateDateQuery)) {

                updateDateStmt.setString(1, newDate);
                updateDateStmt.setInt(2, idRend);

                int affectedRows = updateDateStmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Date du rendezvous avec id " + idRend + " modifiée avec succès.");
                } else {
                    System.out.println("La date du rendezvous avec id " + idRend + " n'a pas pu être modifiée.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            break;
        default:
            System.out.println("Choix invalide.");
    }

    
}

    
}