package BuildWeek1;

import BuildWeek1.Dao.*;
import BuildWeek1.entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Application {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerFactory");
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        boolean bool = false;
        UserDao userDao = new UserDao(em);
        User user = null;
        TicketDao ticketDao=new TicketDao(em);
        TesseraDao tesseraDao=new TesseraDao(em);
        MezzoDao mezzoDao=new MezzoDao(em);
        VenditaBigliettoDao vbdao=new VenditaBigliettoDao(em);
        Set<String> emailSet=new HashSet<>();


       /* VenditaBiglietto vb=new VenditaBiglietto(true,TipoVendita.RIVENDITORE);
        vbdao.save(vb);
        VenditaBiglietto vb1=new VenditaBiglietto(true,TipoVendita.RIVENDITORE);
        vbdao.save(vb1);
        VenditaBiglietto vb2=new VenditaBiglietto(true,TipoVendita.DISTRIBUTORE_AUTOMATICO);
        vbdao.save(vb2);
        VenditaBiglietto vb3=new VenditaBiglietto(true,TipoVendita.DISTRIBUTORE_AUTOMATICO);
        vbdao.save(vb3);*/
        emailSet.addAll(userDao.getAllUsers());
        try {
            ExitCiclo:
            while (true) {
                try{
                    System.out.println("1:SignUp 2:LogIn");
                    int risp = Integer.parseInt(scanner.nextLine());
                    switch (risp) {
                        case 1: {
                            System.out.println("inserisci email");
                            String email = scanner.nextLine();
                            System.out.println("inserisci password");
                            String password = scanner.nextLine();
                            System.out.println("sei un amministratore?(1:true 2:fasle)");
                            int risp2 = Integer.parseInt(scanner.nextLine());
                            if(emailSet.add(email)) {


                                switch (risp2) {
                                    case 1: {
                                        user = new User(email, password, true);
                                        userDao.save(user);
                                        break ExitCiclo;
                                    }
                                    case 2: {
                                        user = new User(email, password, false);
                                        userDao.save(user);
                                        break ExitCiclo;
                                    }
                                }
                            }else System.out.println("Email già utilizzata");
                            break;
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
                }catch (NoResultException e){
                    System.out.println("Nessun utente trovato");
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Non hai inserito un valore corretto");

                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
            if (user.getAdmin() == true && user != null) {
                Exit:
                while(true){
                    System.out.println("1:crea mezzo 2:gestisci mezzo");
                    int risp=Integer.parseInt(scanner.nextLine());
                    switch (risp){
                        case 1->{
                              mezzoDao.save(new Mezzo());
                        }
                        case 2->{}
                    }
                }


            }
             else if(user.getAdmin() == false && user != null) {
                ExitCiclo:
                while (true) {
                    try{
                        if (user.getTessera() == null) {
                            System.out.println("1:acquista singleride 2:acquista tessera 3: esci");
                            int risp = Integer.parseInt(scanner.nextLine());
                            if (risp == 1) {
                                Ticket t = new Ticket(LocalDate.now(), TicketType.SINGLERIDE, user, vbdao.getById(10));
                                ticketDao.save(t);
                            } else if (risp == 2) {
                                Tessera tessera = new Tessera(LocalDate.now(), user);
                                tesseraDao.save(tessera);
                                System.out.println("1:acquista singleride 2:acquista settimanale 3:acquista mensile");
                                int risp2 = Integer.parseInt(scanner.nextLine());
                                switch (risp2) {
                                    case 1 -> {
                                        Ticket t = new Ticket(LocalDate.now(), TicketType.SINGLERIDE, user, vbdao.getById(10));
                                        ticketDao.save(t);
                                    }
                                    case 2 -> {
                                        Ticket t = new Ticket(LocalDate.now(), TicketType.WEEKLY, user, vbdao.getById(11));
                                        ticketDao.save(t);
                                    }
                                    case 3 -> {
                                        Ticket t = new Ticket(LocalDate.now(), TicketType.MONTHLY, user, vbdao.getById(12));
                                        ticketDao.save(t);
                                    }
                                }
                            } else if (risp == 3) {
                                break ExitCiclo;
                            }
                        } else {
                            Tessera tessera = user.getTessera();
                            System.out.println("1:acquista singleride 2:acquista settimanale 3:acquista mensile 4:valida abbonamento 5:Esci");
                            int risp2 = Integer.parseInt(scanner.nextLine());
                            switch (risp2) {
                                case 1 -> {
                                    Ticket t = new Ticket(LocalDate.now(), TicketType.SINGLERIDE, user, vbdao.getById(10));
                                    ticketDao.save(t);
                                }
                                case 2 -> {
                                    Ticket t = new Ticket(LocalDate.now(), TicketType.WEEKLY, user, vbdao.getById(11));
                                    ticketDao.save(t);
                                }
                                case 3 -> {
                                    Ticket t = new Ticket(LocalDate.now(), TicketType.MONTHLY, user, vbdao.getById(12));
                                    ticketDao.save(t);
                                }
                                case 4 -> {
                                    System.out.println("inserisci codice biglietto");
                                    long codTicket = Integer.parseInt(scanner.nextLine());
                                    Ticket t = ticketDao.getById(codTicket);
                                    t.setDataValidazione(LocalDateTime.now());
                                    ticketDao.save(t);

                                } case 5->{
                                break ExitCiclo;
                                }
                            }

                        }
                    }catch (NoResultException e){
                        System.out.println("Nessun utente trovato");
                    }
                    catch (NumberFormatException e)
                    {
                        System.out.println("Non hai inserito un valore corretto");

                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
            }



        } catch (NumberFormatException e) {
            System.err.println("Non hai inserito un valore valido");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }finally {
            scanner.close();
            em.close();
            emf.close();
        }

    }
}
