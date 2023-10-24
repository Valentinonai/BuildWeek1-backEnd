package BuildWeek1;

import BuildWeek1.Dao.*;
import BuildWeek1.entities.*;
import com.github.javafaker.Faker;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Application {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerFactory");
    private static final Scanner scanner = new Scanner(System.in);
    private static final Faker fkr=new Faker();
    private static final Random rnd=new Random();

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        boolean bool = false;
        UserDao userDao = new UserDao(em);
        User user = null;
        TicketDao ticketDao=new TicketDao(em);
        TesseraDao tesseraDao=new TesseraDao(em);
        MezzoDao mezzoDao=new MezzoDao(em);
        VenditaBigliettoDao vbdao=new VenditaBigliettoDao(em);


       /* VenditaBiglietto vb=new VenditaBiglietto(true,TipoVendita.RIVENDITORE);
        vbdao.save(vb);
        VenditaBiglietto vb1=new VenditaBiglietto(true,TipoVendita.RIVENDITORE);
        vbdao.save(vb1);
        VenditaBiglietto vb2=new VenditaBiglietto(true,TipoVendita.DISTRIBUTORE_AUTOMATICO);
        vbdao.save(vb2);
        VenditaBiglietto vb3=new VenditaBiglietto(true,TipoVendita.DISTRIBUTORE_AUTOMATICO);
        vbdao.save(vb3);*/
        try {
            //*****************LOGIN***********************
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

                }catch (RollbackException e){
                    System.out.println("Email già utilizzata scegline un'altra");
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
            //*****************ADMIN************************
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
            //*****************USER*************************
             else if(user.getAdmin() == false && user != null) {
                ExitCiclo:
                while (true) {
                    try{
                        //***************TESSERA NULL*************************
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
                            em.refresh(user);
                        }
                        //***************TESSERA PRESENTE*********************
                        else {

                            Tessera tessera = user.getTessera();
                            if(tessera.getDataScadenza().isAfter(LocalDate.now()))
                            {
                                System.out.println("1:acquista singleride 2:rinnova tessera 3: esci");
                                int risp = Integer.parseInt(scanner.nextLine());
                                if (risp == 1) {
                                    Ticket t = new Ticket(LocalDate.now(), TicketType.SINGLERIDE, user, vbdao.getById(10));
                                    ticketDao.save(t);
                                } else if (risp == 2) {
                                    Tessera tesse = new Tessera(LocalDate.now(), user);
                                    tesseraDao.save(tesse);
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
                                em.refresh(user);
                            }
                            System.out.println("1:acquista singleride 2:acquista settimanale 3:acquista mensile 4:valida ticket 5:Esci");
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
                                    if(t.getUser().getId()==user.getId()){
                                        t.setDataValidazione(LocalDateTime.now());
                                        ticketDao.save(t);
                                        System.out.println("Ticket validato");
                                    }else{
                                        System.out.println("Non hai nessun biglietto con codice "+codTicket);
                                    }


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

    public static void fillUser(UserDao userDao) throws Exception {
        User u=new User(fkr.internet().emailAddress(),fkr.internet().password(),rnd.nextInt(0,2)==0?true:false );
        userDao.save(u);

    }
}
