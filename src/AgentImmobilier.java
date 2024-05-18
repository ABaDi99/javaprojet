
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;



public class AgentImmobilier {
    
        private int idAgent;
        private String nom;
        private String prenom;
        private String email;
        private String telephone;
    
        public AgentImmobilier(int idAgent, String nom, String prenom, String email, String telephone) {
            this.idAgent = idAgent;
            this.nom = nom;
            this.prenom = prenom;
            this.email = email;
            this.telephone = telephone;
        }

        public int getIdAgent() {
            return idAgent;
        }
        public void setIdAgent(int idAgent) {
            this.idAgent = idAgent;
        }
        public String getNom() {
            return nom;
        }
        public void setNom(String nom) {
            this.nom = nom;
        }
        public String getPrenom() {
            return prenom;
        }
        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public String getTelephone() {
            return telephone;
        }
        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }


             public AgentImmobilier(){}






             public void ajouteragent() {
                Scanner scanner = new Scanner(System.in);
        
                boolean idUnique = false;
                while (!idUnique) {
                    try {
                        System.out.print("Entrez l'ID de l'agent: ");
                        idAgent = scanner.nextInt();
                        scanner.nextLine(); // Consommer la nouvelle ligne
        
                        // Vérifier si l'ID existe déjà dans la base de données
                        if (idExiste(idAgent)) {
                            System.out.println("L'ID de l'agent existe déjà dans la base de données. Veuillez entrer un autre ID.");
                        } else {
                            idUnique = true;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Erreur de saisie: veuillez entrer un ID valide (entier).");
                        scanner.next(); // Consommer l'entrée incorrecte
                    }
                }
        
                System.out.print("Entrez le nom de l'agent: ");
                nom = scanner.nextLine();
        
                System.out.print("Entrez le prénom de l'agent: ");
                prenom = scanner.nextLine();
        
                System.out.print("Entrez l'adresse email de l'agent: ");
                email = scanner.nextLine();
        
                System.out.print("Entrez le numéro de téléphone de l'agent: ");
                telephone = scanner.nextLine();
        
                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/javaproject", "root", "");
                    String sql = "INSERT INTO agentimmobilier (idagent, nom, prenom, AdresseEmail, NumeroTelephone) VALUES (?, ?, ?, ?, ?)";
        
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, idAgent);
                    pstmt.setString(2, nom);
                    pstmt.setString(3, prenom);
                    pstmt.setString(4, email);
                    pstmt.setString(5, telephone);
        
                    pstmt.executeUpdate();
                    pstmt.close();
                    conn.close();
                    System.out.println("Agent immobilier ajouté avec succès !");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        
            private boolean idExiste(int idAgent) {
                boolean existe = false;
                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/javaproject", "root", "");
                    String sql = "SELECT idagent FROM agentimmobilier WHERE idagent = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, idAgent);
                    ResultSet rs = pstmt.executeQuery();
        
                    if (rs.next()) {
                        existe = true;
                    }
        
                    rs.close();
                    pstmt.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return existe;
            }

        
        
    public static AgentImmobilier rechercherAgent() {
    AgentImmobilier agent = null;
    try (Scanner scanner = new Scanner(System.in)) {
        // Demander à l'utilisateur d'entrer l'ID de l'agent à modifier
        System.out.print("Entrez l'ID de l'agent que vous souhaitez modifier : ");
        int idAgent = scanner.nextInt();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/javaproject", "root", "")) {
            String sql = "SELECT * FROM agentimmobilier WHERE idagent = ?";
                
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, idAgent);
                
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        agent = new AgentImmobilier(
                            rs.getInt("idagent"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("AdresseEmail"),
                            rs.getString("NumeroTelephone")
                        );
                    } else {
                        System.out.println("L'agent avec l'ID " + idAgent + " n'existe pas dans la base de données.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } catch (InputMismatchException e) {
        System.out.println("Veuillez entrer un ID d'agent valide (entier).");
    }
    return agent;
}






    public void modifierAgent() {
        try (Scanner scanner = new Scanner(System.in)) {
            // Demander à l'utilisateur d'entrer l'ID de l'agent à modifier
            System.out.print("Entrez l'ID de l'agent que vous souhaitez modifier : ");
            int idAgent = scanner.nextInt();

            boolean continuerModifications = true;
            while (continuerModifications) {
                // Menu pour choisir l'attribut à modifier
                System.out.println("Choisissez l'attribut à modifier :");
                System.out.println("1. Nom");
                System.out.println("2. Prénom");
                System.out.println("3. Adresse Email");
                System.out.println("4. Numéro de téléphone");
                System.out.println("5. Arrêter les modifications");
                System.out.print("Entrez votre choix : ");
                int choix = scanner.nextInt();

                // Variables pour stocker les nouvelles informations
                String nouveauNom = null;
                String nouveauPrenom = null;
                String nouvelEmail = null;
                String nouveauTelephone = null;

                // Demander à l'utilisateur d'entrer les nouvelles informations pour l'attribut choisi
                switch (choix) {
                    case 1:
                        System.out.print("Entrez le nouveau nom : ");
                        nouveauNom = scanner.next();
                        break;
                    case 2:
                        System.out.print("Entrez le nouveau prénom : ");
                        nouveauPrenom = scanner.next();
                        break;
                    case 3:
                        System.out.print("Entrez le nouvel email : ");
                        nouvelEmail = scanner.next();
                        break;
                    case 4:
                        System.out.print("Entrez le nouveau numéro de téléphone : ");
                        nouveauTelephone = scanner.next();
                        break;
                    case 5:
                        continuerModifications = false;
                        break;
                    default:
                        System.out.println("Choix invalide !");
                        break;
                }

                // Mettre à jour les informations dans l'objet AgentImmobilier
                if (nouveauNom != null) setNom(nouveauNom);
                if (nouveauPrenom != null) setPrenom(nouveauPrenom);
                if (nouvelEmail != null) setEmail(nouvelEmail);
                if (nouveauTelephone != null) setTelephone(nouveauTelephone);

                // Mettre à jour les informations dans la base de données si l'utilisateur n'a pas choisi d'arrêter
                if (choix >= 1 && choix <= 4) {
                    try {
                        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/javaproject", "root", "");
                        String sql = "";
                        switch (choix) {
                            case 1:
                                sql = "UPDATE agentimmobilier SET nom = ? WHERE idagent = ?";
                                break;
                            case 2:
                                sql = "UPDATE agentimmobilier SET prenom = ? WHERE idagent = ?";
                                break;
                            case 3:
                                sql = "UPDATE agentimmobilier SET AdresseEmail = ? WHERE idagent = ?";
                                break;
                            case 4:
                                sql = "UPDATE agentimmobilier SET NumeroTelephone = ? WHERE idagent = ?";
                                break;
                        }
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, choix == 1 ? nouveauNom : choix == 2 ? nouveauPrenom : choix == 3 ? nouvelEmail : nouveauTelephone);
                        pstmt.setInt(2, idAgent);
                        pstmt.executeUpdate();
                        pstmt.close();
                        conn.close();
                        System.out.println("Agent immobilier modifié avec succès !");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }



    public static void supprimerAgent() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Entrez l'ID de l'agent que vous souhaitez supprimer : ");
            if (!scanner.hasNextInt()) {
                System.out.println("Veuillez entrer un ID d'agent valide (entier).");
                return;
            }
            int idAgent = scanner.nextInt();
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/javaproject", "root", "")) {
                String checkAgentSql = "SELECT * FROM agentimmobilier WHERE idagent = ?";
                try (PreparedStatement checkAgentStmt = conn.prepareStatement(checkAgentSql)) {
                    checkAgentStmt.setInt(1, idAgent);
                    try (ResultSet rs = checkAgentStmt.executeQuery()) {
                        if (!rs.next()) {
                            System.out.println("L'agent avec l'ID " + idAgent + " n'existe pas dans la base de données.");
                            return; // Sortir de la méthode car l'agent n'existe pas
                        }
                    }
                }
    
                String deleteAgentSql = "DELETE FROM agentimmobilier WHERE idagent = ?";
                try (PreparedStatement deleteAgentStmt = conn.prepareStatement(deleteAgentSql)) {
                    deleteAgentStmt.setInt(1, idAgent);
                    int rowsAffected = deleteAgentStmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("L'agent avec l'ID " + idAgent + " a été supprimé avec succès de la base de données.");
                    } else {
                        System.out.println("Erreur lors de la suppression de l'agent avec l'ID " + idAgent + ".");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
















    

    public static void afficherTousAgents() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/javaproject", "root", "");
            String sql = "SELECT * FROM agentimmobilier";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            // Affichage de l'en-tête du tableau
            System.out.println("+------------+----------------------+----------------------+--------------------------------+---------------+");
            System.out.println("| ID Agent   | Nom                  | Prénom               | Adresse Email                  | Numéro Télé.  |");
            System.out.println("+------------+----------------------+----------------------+--------------------------------+---------------+");
            
            // Affichage des agents
            while (rs.next()) {
                System.out.printf("| %-10d | %-20s | %-20s | %-30s | %-13s |\n", 
                                  rs.getInt("idagent"), 
                                  rs.getString("nom"), 
                                  rs.getString("prenom"), 
                                  rs.getString("AdresseEmail"), 
                                  rs.getString("NumeroTelephone"));
            }
            
            // Affichage du bas du tableau
            System.out.println("+------------+----------------------+----------------------+--------------------------------+---------------+");
            
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}



