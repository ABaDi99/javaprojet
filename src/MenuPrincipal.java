import java.util.Scanner;

public class MenuPrincipal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            afficherMenu();
            int choix = scanner.nextInt();
            scanner.nextLine(); // Pour consommer la nouvelle ligne après nextInt()

            switch (choix) {
                case 1:
                    while (true) {
                        afficherMenu1();
                        int choix1 = scanner.nextInt();
                        scanner.nextLine(); // Pour consommer la nouvelle ligne après nextInt()

                        BiensImmobiliers biensImmobiliers = new BiensImmobiliers();

                        switch (choix1) {
                            case 1:
                                biensImmobiliers.afficherBiensImmobilier();
                                break;
                            case 2:
                                biensImmobiliers.ajouterBienImmobilier();
                                break;
                            case 3:
                                biensImmobiliers.supprimerBienImmobilier();
                                break;
                            case 4:
                                biensImmobiliers.modifierBienImmobilier();
                                break;
                            case 5:
                                System.out.print("Surface minimale : ");
                                double surfaceMin = scanner.nextDouble();

                                System.out.print("Surface maximale : ");
                                double surfaceMax = scanner.nextDouble();

                                scanner.nextLine(); // Pour consommer la nouvelle ligne

                                System.out.print("Type (laissez vide pour ignorer) : ");
                                String type = scanner.nextLine();

                                System.out.print("Prix minimum : ");
                                double prixMin = scanner.nextDouble();

                                System.out.print("Prix maximum : ");
                                double prixMax = scanner.nextDouble();

                                biensImmobiliers.rechercherBiensImmobilier(surfaceMin, surfaceMax, type, prixMin, prixMax);
                                break;
                            case 6:
                                break; // Sortir du sous-menu
                            default:
                                System.out.println("Choix invalide. Veuillez sélectionner une option valide.");
                        }

                        if (choix1 == 6) break; // Retourner au menu principal
                    }
                    break;
                case 2:
                    while (true) {
                        afficherMenu2();
                        int choix2 = scanner.nextInt();
                        scanner.nextLine(); // Pour consommer la nouvelle ligne après nextInt()

                        AgentImmobilier agentImmobilier = new AgentImmobilier();

                        switch (choix2) {
                            case 1:
                                agentImmobilier.afficherTousAgents();
                                break;
                            case 2:
                                agentImmobilier.ajouteragent();
                                break;
                            case 3:
                                agentImmobilier.supprimerAgent();
                                break;
                            case 4:
                                agentImmobilier.modifierAgent();
                                break;
                            case 5:
                                break; // Sortir du sous-menu
                            default:
                                System.out.println("Choix invalide. Veuillez sélectionner une option valide.");
                        }

                        if (choix2 == 5) break; // Retourner au menu principal
                    }
                    break;
                case 3:
                    while (true) {
                        afficherMenu3();
                        int choix3 = scanner.nextInt();
                        scanner.nextLine(); // Pour consommer la nouvelle ligne après nextInt()

                        client c = new client();
                        interactions c1 = new interactions();

                        switch (choix3) {
                            case 1:
                                c.afficherTousClients();
                                break;
                            case 2:
                                c.ajouterClient();
                                break;
                            case 3:
                                c.supprimerClient();
                                break;
                            case 4:
                                c.modifierClient();
                                break;
                            case 5:
                                c1.ajouterInteraction();
                                break;
                            case 6:
                                c1.afficherToutesInteractions();
                                break;
                            case 7:
                                break; // Sortir du sous-menu
                            default:
                                System.out.println("Choix invalide. Veuillez sélectionner une option valide.");
                        }

                        if (choix3 == 7) break; // Retourner au menu principal
                    }
                    break;
                case 4:
                    while (true) {
                        afficherMenu4();
                        int choix4 = scanner.nextInt();
                        scanner.nextLine(); // Pour consommer la nouvelle ligne après nextInt()

                        Transaction T = new Transaction();
                        Contract C2 = new Contract();

                        switch (choix4) {
                            case 1:
                                T.afficherToutesTransactions();
                                break;
                            case 2:
                                T.ajouterTransaction();
                                break;
                            case 3:
                                T.supprimerTransaction();
                                break;
                            case 4:
                                T.modifierTransaction();
                                break;
                            case 5:
                                C2.ajouterContrat();
                                break;
                            case 6:
                                C2.afficherTousLesContrats();
                                break;
                            case 7:
                                break; // Sortir du sous-menu
                            default:
                                System.out.println("Choix invalide. Veuillez sélectionner une option valide.");
                        }

                        if (choix4 == 7) break; // Retourner au menu principal
                    }
                    break;
                case 5:

                while (true) {


                    afficherMenu5();
                        int choix5 = scanner.nextInt();
                        scanner.nextLine(); // Pour consommer la nouvelle ligne après nextInt()
                           
                        Rendezvous R=new Rendezvous();

                        switch (choix5) {
                            case 1:
                                R.afficherTousRendezvous();
                                break;
                            case 2:
                            R.ajouterRendezvousDepuisConsole();
                                
                                break;
                            case 3:
                               
                                R.supprimerRendezvous();
                                break;
                            case 4:
                                R.modifierRendezvous();
                                break;
                            case 5:
                                
                                break;
                            
                            default:
                                System.out.println("Choix invalide. Veuillez sélectionner une option valide.");
                        }

                        if (choix5 == 5) break; // Retourner au menu principal
                    }
                              
                    break;
                case 6:
                    System.out.println("Au revoir !");
                    System.exit(0);
                default:
                    System.out.println("Choix invalide. Veuillez sélectionner une option valide.");
            }
        }
    }

    public static void afficherMenu() {
                                                      System.out.println("Bienvenue à notre logiciel de gestion ERP IMMO");
        System.out.println("=== MENU PRINCIPAL ===");
        System.out.println("1. l'espace des biens immobiliers");
        System.out.println("2. l'espace Agent");
        System.out.println("3. l'espace Clients");
        System.out.println("4. l'espace transaction");
        System.out.println("5. l'espace rendez-vous");
        System.out.println("6. Quitter");
        System.out.print("Veuillez sélectionner une option : ");
    }

    public static void afficherMenu1() {
        System.out.println("1. Afficher les biens immobiliers");
        System.out.println("2. Ajouter un bien immobilier");
        System.out.println("3. Supprimer un bien immobilier");
        System.out.println("4. Modifier un bien immobilier");
        System.out.println("5. Recherche de biens immobiliers");
        System.out.println("6. Retour");
        System.out.print("Veuillez sélectionner une option : ");
    }

    public static void afficherMenu2() {
        System.out.println("1. Afficher les agents");
        System.out.println("2. Ajouter un agent");
        System.out.println("3. Supprimer un agent");
        System.out.println("4. Modifier un agent");
        System.out.println("5. Retour");
        System.out.print("Veuillez sélectionner une option : ");
    }

    public static void afficherMenu3() {
        System.out.println("1. Afficher les clients");
        System.out.println("2. Ajouter un client");
        System.out.println("3. Supprimer un client");
        System.out.println("4. Modifier un client");
        System.out.println("5. Ajouter des interactions");
        System.out.println("6. Afficher toutes les interactions");
        System.out.println("7. Retour");
        System.out.print("Veuillez sélectionner une option : ");
    }

    public static void afficherMenu4() {
        System.out.println("1. Afficher les transactions");
        System.out.println("2. Ajouter une transaction");
        System.out.println("3. Supprimer une transaction");
        System.out.println("4. Modifier une transaction");
        System.out.println("5. Ajouter un contrat");
        System.out.println("6. Afficher tous les contrats");
        System.out.println("7. Retour");
        System.out.print("Veuillez sélectionner une option : ");
    }


    public static void afficherMenu5() {
        System.out.println("1. Afficher les Rendez-Vous");
        System.out.println("2. Ajouter un Rendez Vous ");
        System.out.println("3. Supprimer un Rendez vous ");
        System.out.println("4. Modifier un Rendez vous");
        System.out.println("7. Retour");
        System.out.print("Veuillez sélectionner une option : ");
    }
}

