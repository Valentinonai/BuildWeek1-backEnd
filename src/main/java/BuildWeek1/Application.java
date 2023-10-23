package BuildWeek1;

import BuildWeek1.Dao.UserDao;
import BuildWeek1.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class Application {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerFactory");
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        boolean bool = false;
        UserDao userDao = new UserDao(em);
        User user;

        try {
            ExitCiclo:
            while (true) {
                System.out.println("1:SignUp 2:LogIn");
                int risp = Integer.parseInt(scanner.nextLine());
                switch (risp) {
                    case 1: {
                        System.out.println("inserisci email");
                        String email = scanner.nextLine();
                        System.out.println("inserisci password");
                        String password = scanner.nextLine();
                        System.out.println("sei un amministratore?(1:true 2:fasle)");
                        System.out.println("sei un amministratore?(1:true 2:false)");
                        int risp2 = Integer.parseInt(scanner.nextLine());
                        switch (risp2) {
                            case 1: {
                                userDao.save(new User(email, password, true));
                                break;
                            }
                            case 2: {
                                userDao.save(new User(email, password, false));
                                break;
                            }
                        }

                        break ExitCiclo;
                    }
                    case 2: {
                        System.out.println("inserisci email");
                        String email = scanner.nextLine();
                        System.out.println("inserisci password");
                        String password = scanner.nextLine();
                        user = userDao.findByUserAndEmail(email, password);
                        if (user == null)
                            System.out.println("Utente non trovato");
                        else System.out.println("Accesso effettuato");
                        break ExitCiclo;
                    }
                    default:
                        break;
                }
            }
            if (user.getAdmin() == true) {

            } else {
                ExitCiclo:
                while (true) {
                    System.out.println("1:Acquista biglietto 2:Valida biglietto");
                    int risp = Integer.parseInt(scanner.nextLine());
                    switch (risp) {
                        case 1: {
                            System.out.println("Da dove vuoi acquistare il biglietto? (1:rivenditore 2:distributore automatico");
                            int risp2 = Integer.parseInt(scanner.nextLine());
                            if (risp2 == 1) {

                            } else if (risp2 == 2) {

                            }
                        }
                        default:
                            break;
                    }
                }

            }

        } catch (NumberFormatException e) {
            System.err.println("Non hai inserito un valore valido");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}
