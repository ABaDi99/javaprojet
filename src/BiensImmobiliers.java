import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class BiensImmobiliers {
    private int idBien;
    private String TYPE;
    private String ETAT;
    private String DESCRIPTION;
    private int PRIX; // Prix de la propriété
    private String ADRESS;
    private int Id_Agent;
    private double surface;
    private Integer idclient;
    
    // Constructeur
    public BiensImmobiliers(int idBien, String TYPE, String ETAT, String DESCRIPTION, int PRIX, String ADRESS, int Id_Agent, double surface, int idclient) {
        this.idBien = idBien;
        this.TYPE = TYPE;
        this.ETAT = ETAT;
        this.DESCRIPTION = DESCRIPTION;
        this.PRIX = PRIX;
        this.ADRESS = ADRESS;
        this.Id_Agent = Id_Agent;
        this.surface = surface;
        this.idclient = idclient;
    }

    public BiensImmobiliers() {}

    public void afficherBiensImmobilier() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "");
            String sql = "SELECT idBien, TYPE, ETAT, PRIX, ADRESS, Id_Agent, surface, idclient FROM biensimmobilier";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            System.out.println("+------------------+--------------+----------------+--------------+---------------------------------------------+--------------+--------------+--------------+");
            System.out.println("|      ID Bien     |     Type     |     État       |     Prix     |                   Adresse                   |    ID Agent  |   Surface    |   ID Client   |");
            System.out.println("+------------------+--------------+----------------+--------------+---------------------------------------------+--------------+--------------+--------------+");

            while (resultSet.next()) {
                int idBien = resultSet.getInt("idBien");
                String type = resultSet.getString("TYPE");
                String etat = resultSet.getString("ETAT");
                double prix = resultSet.getDouble("PRIX");
                String adresse = resultSet.getString("ADRESS");
                int idAgent = resultSet.getInt("Id_Agent");
                double surface = resultSet.getDouble("surface");
                Integer idClient = (Integer) resultSet.getObject("idclient");

                System.out.printf("| %-16d | %-12s | %-14s | %-12.2f | %-45s | %-12d | %-12.2f | %-12s |\n", idBien, type, etat, prix, adresse, idAgent, surface, idClient != null ? idClient.toString() : "NULL");
            }

            System.out.println("+------------------+--------------+----------------+--------------+---------------------------------------------+--------------+--------------+--------------+");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void ajouterBienImmobilier() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO biensimmobilier (idBien, TYPE, ETAT, DESCRIPTION, PRIX, ADRESS, Id_Agent, surface, idclient) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
    
            Scanner scanner = new Scanner(System.in);
            scanner.useLocale(Locale.US); // Ensure the scanner uses the correct locale for decimal numbers
            int idAgent;
            boolean agentExists = false;
    
            // Check if agent exists
            do {
                System.out.print("Entrez l'identifiant de l'agent : ");
                idAgent = scanner.nextInt();
    
                try (PreparedStatement agentCheckStatement = connection.prepareStatement("SELECT * FROM agentimmobilier WHERE IdAgent = ?")) {
                    agentCheckStatement.setInt(1, idAgent);
                    try (ResultSet agentResultSet = agentCheckStatement.executeQuery()) {
                        agentExists = agentResultSet.next();
                    }
                }
    
                if (!agentExists) {
                    System.out.println("L'agent avec l'identifiant " + idAgent + " n'existe pas. Veuillez entrer un identifiant d'agent valide.");
                }
    
            } while (!agentExists);
    
            int idBien;
            boolean bienExists = false;
    
            // Check if property ID already exists
            do {
                System.out.print("Entrez l'ID du bien : ");
                idBien = scanner.nextInt();
    
                try (PreparedStatement bienCheckStatement = connection.prepareStatement("SELECT * FROM biensimmobilier WHERE idBien = ?")) {
                    bienCheckStatement.setInt(1, idBien);
                    try (ResultSet bienResultSet = bienCheckStatement.executeQuery()) {
                        bienExists = bienResultSet.next();
                    }
                }
    
                if (bienExists) {
                    System.out.println("Le bien avec l'ID " + idBien + " existe déjà dans la base de données. Veuillez entrer un ID de bien unique.");
                }
    
            } while (bienExists);
    
            scanner.nextLine(); // Consume the newline
    
            // Collect property details
            System.out.print("Type : ");
            String type = scanner.nextLine();
            System.out.print("État : ");
            String etat = scanner.nextLine();
            System.out.print("Description : ");
            String description = scanner.nextLine();
    
            double prix = 0;
            boolean validPrix = false;
            do {
                try {
                    System.out.print("Prix : ");
                    prix = scanner.nextDouble();
                    validPrix = true;
                } catch (InputMismatchException e) {
                    System.out.println("Veuillez entrer un nombre valide pour le prix.");
                    scanner.next(); // Clear the invalid input
                }
            } while (!validPrix);
    
            scanner.nextLine(); // Consume the newline
            System.out.print("Adresse : ");
            String adresse = scanner.nextLine();
            System.out.print("Surface : ");
            double surface = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline
    
            System.out.print("Voulez-vous entrer l'ID du client ? (oui/non) : ");
            String enterClientId = scanner.nextLine();
    
            Integer idClient = null;
    
            // Check if client ID should be entered
            if (enterClientId.equalsIgnoreCase("oui")) {
                System.out.print("Entrez l'ID du client : ");
                idClient = scanner.nextInt();
    
                try (PreparedStatement clientCheckStatement = connection.prepareStatement("SELECT * FROM client WHERE idClient = ?")) {
                    clientCheckStatement.setInt(1, idClient);
                    try (ResultSet clientResultSet = clientCheckStatement.executeQuery()) {
                        if (!clientResultSet.next()) {
                            System.out.println("Le client avec l'identifiant " + idClient + " n'existe pas dans la base de données.");
                            return;
                        }
                    }
                }
            }
    
            // Insert property into database
            preparedStatement.setInt(1, idBien);
            preparedStatement.setString(2, type);
            preparedStatement.setString(3, etat);
            preparedStatement.setString(4, description);
            preparedStatement.setDouble(5, prix);
            preparedStatement.setString(6, adresse);
            preparedStatement.setInt(7, idAgent);
            preparedStatement.setDouble(8, surface);
            preparedStatement.setObject(9, idClient);
    
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Propriété ajoutée avec succès à la base de données.");
            } else {
                System.out.println("Échec de l'ajout de la propriété à la base de données.");
            }
        } catch (SQLException e) {
            e.printStackTrace();}
        }

        
    public void supprimerBienImmobilier() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM biensimmobilier WHERE idBien = ?")) {

            Scanner scanner = new Scanner(System.in);
            System.out.print("Entrez l'ID du bien immobilier à supprimer : ");
            int idBienASupprimer = scanner.nextInt();

            preparedStatement.setInt(1, idBienASupprimer);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    System.out.println("Aucun bien immobilier trouvé avec l'ID spécifié.");
                    return;
                }
            }

            System.out.print("Êtes-vous sûr de vouloir supprimer ce bien immobilier ? (oui/non) : ");
            String confirmation = scanner.next();

            if (confirmation.equalsIgnoreCase("oui")) {
                try (PreparedStatement suppressionStatement = connection.prepareStatement("DELETE FROM biensimmobilier WHERE idBien = ?")) {
                    suppressionStatement.setInt(1, idBienASupprimer);
                    int rowsAffected = suppressionStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Le bien immobilier a été supprimé avec succès de la base de données.");
                    } else {
                        System.out.println("Échec de la suppression du bien immobilier de la base de données.");
                    }
                }
            } else {
                System.out.println("Suppression annulée.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifierBienImmobilier() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "");
             PreparedStatement verificationStatement = connection.prepareStatement("SELECT * FROM biensimmobilier WHERE idBien = ?")) {

            Scanner scanner = new Scanner(System.in);
            System.out.print("Entrez l'ID du bien immobilier à modifier : ");
            int idBienAModifier = scanner.nextInt();

            verificationStatement.setInt(1, idBienAModifier);
            try (ResultSet resultSet = verificationStatement.executeQuery()) {
                if (!resultSet.next()) {
                    System.out.println("Aucun bien immobilier trouvé avec l'ID spécifié.");
                    return;
                }
            }

            System.out.println("Choisissez l'attribut à modifier :");
            System.out.println("1. Type");
            System.out.println("2. État");
            System.out.println("3. Description");
            System.out.println("4. Prix");
            System.out.println("5. Adresse");
            System.out.println("6. Surface");
            System.out.println("7. ID Agent");
            System.out.println("8. ID Client");

            int choix = scanner.nextInt();
            String attribut = null;
            Object valeur = null;

            switch (choix) {
                case 1:
                    System.out.print("Entrez le nouveau type : ");
                    attribut = "TYPE";
                    valeur = scanner.next();
                    break;
                case 2:
                    System.out.print("Entrez le nouvel état : ");
                    attribut = "ETAT";
                    valeur = scanner.next();
                    break;
                case 3:
                    System.out.print("Entrez la nouvelle description : ");
                    attribut = "DESCRIPTION";
                    valeur = scanner.next();
                    break;
                case 4:
                    System.out.print("Entrez le nouveau prix : ");
                    attribut = "PRIX";
                    valeur = scanner.nextDouble();
                    break;
                case 5:
                    System.out.print("Entrez la nouvelle adresse : ");
                    attribut = "ADRESS";
                    valeur = scanner.next();
                    break;
                case 6:
                    System.out.print("Entrez la nouvelle surface : ");
                    attribut = "surface";
                    valeur = scanner.nextDouble();
                    break;
                case 7:
                    System.out.print("Entrez le nouvel ID Agent : ");
                    attribut = "Id_Agent";
                    valeur = scanner.nextInt();
                    break;
                case 8:
                    System.out.print("Entrez le nouvel ID Client : ");
                    attribut = "idclient";
                    valeur = scanner.nextInt();
                    break;
                default:
                    System.out.println("Choix invalide.");
                    return;
            }

            String sql = "UPDATE biensimmobilier SET " + attribut + " = ? WHERE idBien = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(sql)) {
                updateStatement.setObject(1, valeur);
                updateStatement.setInt(2, idBienAModifier);

                int rowsAffected = updateStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Le bien immobilier a été modifié avec succès.");
                } else {
                    System.out.println("Échec de la modification du bien immobilier.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



public void rechercherBiensImmobilier(double surfaceMin, double surfaceMax, String type, double prixMin, double prixMax) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "");

        // Construire la requête SQL en fonction des critères fournis
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM biensimmobilier WHERE ");
        boolean conditionAdded = false;

        if (surfaceMin > 0 && surfaceMax > 0 && surfaceMax >= surfaceMin) {
            sqlBuilder.append("surface BETWEEN ? AND ? ");
            conditionAdded = true;
        }

        if (type != null && !type.isEmpty()) {
            if (conditionAdded) {
                sqlBuilder.append("AND ");
            }
            sqlBuilder.append("TYPE = ? ");
            conditionAdded = true;
        }

        if (prixMin > 0 && prixMax > 0 && prixMax >= prixMin) {
            if (conditionAdded) {
                sqlBuilder.append("AND ");
            }
            sqlBuilder.append("PRIX BETWEEN ? AND ? ");
            conditionAdded = true;
        }

        // Préparer la requête SQL
        preparedStatement = connection.prepareStatement(sqlBuilder.toString());

        // Définir les valeurs des paramètres de la requête
        int paramIndex = 1;
        if (surfaceMin > 0 && surfaceMax > 0 && surfaceMax >= surfaceMin) {
            preparedStatement.setDouble(paramIndex++, surfaceMin);
            preparedStatement.setDouble(paramIndex++, surfaceMax);
        }

        if (type != null && !type.isEmpty()) {
            preparedStatement.setString(paramIndex++, type);
        }

        if (prixMin > 0 && prixMax > 0 && prixMax >= prixMin) {
            preparedStatement.setDouble(paramIndex++, prixMin);
            preparedStatement.setDouble(paramIndex++, prixMax);
        }

        // Exécuter la requête
        resultSet = preparedStatement.executeQuery();

        // Afficher les résultats directement depuis le ResultSet
        System.out.println("Résultats de la recherche :");
        while (resultSet.next()) {
            int idBien = resultSet.getInt("idBien");
            String bienType = resultSet.getString("TYPE");
            String etat = resultSet.getString("ETAT");
            double prix = resultSet.getDouble("PRIX");
            String adresse = resultSet.getString("ADRESS");
            int idAgent = resultSet.getInt("Id_Agent");
            double surface = resultSet.getDouble("surface");

            System.out.printf("ID: %d, Type: %s, État: %s, Prix: %.2f, Adresse: %s, ID Agent: %d, Surface: %.2f\n", idBien, bienType, etat, prix, adresse, idAgent, surface);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
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
            e.printStackTrace();
        }
    }
}















}

  