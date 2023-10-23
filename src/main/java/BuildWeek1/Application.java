package BuildWeek1;

import BuildWeek1.Dao.TesseraDao;
import BuildWeek1.Dao.TicketDao;
import BuildWeek1.Dao.UserDao;
import BuildWeek1.entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.Scanner;

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
                                user = new User(email, password, true);
                                userDao.save(user);
                                break;
                            }
                            case 2: {
                                user = new User(email, password, false);
                                userDao.save(user);
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
            if (user.getAdmin() == true && user != null) {
//ADMIN******************************************************
            } else if(user.getAdmin() == false && user != null) {
                ExitCiclo:
                while (true) {
                    System.out.println("1:Acquista biglietto 2:Valida biglietto");
                    int risp = Integer.parseInt(scanner.nextLine());
                    switch (risp) {
                        case 1: {
                            Tessera tessera=user.getTessera();
                            if(tessera==null){
                                System.out.println("1:acquista singleRide 2: acquista tessera");
                                int risp3=Integer.parseInt(scanner.nextLine());
                                if(risp3==1){
                                    System.out.println("Dove vuoi acquistare: 1:rivenditore 2:distributore automatico");
                                    int venditaBiglietto=Integer.parseInt(scanner.nextLine());
                                    TipoVendita tv=venditaBiglietto==1?TipoVendita.RIVENDITORE:TipoVendita.DISTRIBUTORE_AUTOMATICO;
                                    VenditaBiglietto vb=new VenditaBiglietto(tv==TipoVendita.RIVENDITORE?false:true,tv);
                                    Ticket ticket=new Ticket(LocalDate.now(),vb, TicketType.SINGLERIDE);
                                    ticketDao.save(ticket);
                                    ticket.setUser(user);

                                }
                                else if (risp3==2){
                                    Tessera tes=new Tessera(LocalDate.now());
                                    tesseraDao.save(tes);
                                    user.setTessera(tes);
                                    Exit:
                                    while(true){
                                        System.out.println("1:acquista singleRide 2:acquista abbonamento settimanale 3: acquista abbonamento mensile");
                                        int risp4=Integer.parseInt(scanner.nextLine());
                                        switch (risp4){
                                            case 1->{
                                                System.out.println("Dove vuoi acquistare: 1:rivenditore 2:distributore automatico");
                                                int venditaBiglietto=Integer.parseInt(scanner.nextLine());
                                                TipoVendita tv=venditaBiglietto==1?TipoVendita.RIVENDITORE:TipoVendita.DISTRIBUTORE_AUTOMATICO;
                                                VenditaBiglietto vb=new VenditaBiglietto(tv==TipoVendita.RIVENDITORE?false:true,tv);
                                                Ticket ticket=new Ticket(LocalDate.now(),vb, TicketType.SINGLERIDE);
                                                ticketDao.save(ticket);
                                                ticket.setUser(user);
                                                break Exit;
                                            }
                                            case 2->{
                                                System.out.println("Dove vuoi acquistare: 1:rivenditore 2:distributore automatico");
                                                int venditaBiglietto=Integer.parseInt(scanner.nextLine());
                                                TipoVendita tv=venditaBiglietto==1?TipoVendita.RIVENDITORE:TipoVendita.DISTRIBUTORE_AUTOMATICO;
                                                VenditaBiglietto vb=new VenditaBiglietto(tv==TipoVendita.RIVENDITORE?false:true,tv);
                                                Ticket ticket=new Ticket(LocalDate.now(),vb, TicketType.WEEKLY);
                                                ticketDao.save(ticket);
                                                ticket.setUser(user);
                                                break Exit;
                                            }
                                            case 3->{
                                                System.out.println("Dove vuoi acquistare: 1:rivenditore 2:distributore automatico");
                                                int venditaBiglietto=Integer.parseInt(scanner.nextLine());
                                                TipoVendita tv=venditaBiglietto==1?TipoVendita.RIVENDITORE:TipoVendita.DISTRIBUTORE_AUTOMATICO;
                                                VenditaBiglietto vb=new VenditaBiglietto(tv==TipoVendita.RIVENDITORE?false:true,tv);
                                                Ticket ticket=new Ticket(LocalDate.now(),vb, TicketType.MONTHLY);
                                                ticketDao.save(ticket);
                                                ticket.setUser(user);
                                                break Exit;
                                            }


                                        }
                                    }


                                }
                            }else{
                                Tessera tesseraUtente=user.getTessera();
                                if(tesseraUtente.getDataScadenza().isAfter(LocalDate.now())) {
                                    Exit:
                                    while (true) {
                                        System.out.println("1:acquista singleRide 2:acquista abbonamento settimanale 3: acquista abbonamento mensile");
                                        int risp4 = Integer.parseInt(scanner.nextLine());
                                        switch (risp4) {
                                            case 1 -> {
                                                System.out.println("Dove vuoi acquistare: 1:rivenditore 2:distributore automatico");
                                                int venditaBiglietto = Integer.parseInt(scanner.nextLine());
                                                TipoVendita tv = venditaBiglietto == 1 ? TipoVendita.RIVENDITORE : TipoVendita.DISTRIBUTORE_AUTOMATICO;
                                                VenditaBiglietto vb = new VenditaBiglietto(tv == TipoVendita.RIVENDITORE ? false : true, tv);
                                                Ticket ticket = new Ticket(LocalDate.now(), vb, TicketType.SINGLERIDE);
                                                ticketDao.save(ticket);
                                                ticket.setUser(user);
                                                break Exit;
                                            }
                                            case 2 -> {
                                                System.out.println("Dove vuoi acquistare: 1:rivenditore 2:distributore automatico");
                                                int venditaBiglietto = Integer.parseInt(scanner.nextLine());
                                                TipoVendita tv = venditaBiglietto == 1 ? TipoVendita.RIVENDITORE : TipoVendita.DISTRIBUTORE_AUTOMATICO;
                                                VenditaBiglietto vb = new VenditaBiglietto(tv == TipoVendita.RIVENDITORE ? false : true, tv);
                                                Ticket ticket = new Ticket(LocalDate.now(), vb, TicketType.WEEKLY);
                                                ticketDao.save(ticket);
                                                ticket.setUser(user);
                                                break Exit;
                                            }
                                            case 3 -> {
                                                System.out.println("Dove vuoi acquistare: 1:rivenditore 2:distributore automatico");
                                                int venditaBiglietto = Integer.parseInt(scanner.nextLine());
                                                TipoVendita tv = venditaBiglietto == 1 ? TipoVendita.RIVENDITORE : TipoVendita.DISTRIBUTORE_AUTOMATICO;
                                                VenditaBiglietto vb = new VenditaBiglietto(tv == TipoVendita.RIVENDITORE ? false : true, tv);
                                                Ticket ticket = new Ticket(LocalDate.now(), vb, TicketType.MONTHLY);
                                                ticketDao.save(ticket);
                                                ticket.setUser(user);
                                                break Exit;
                                            }


                                        }
                                    }
                                }else{
                                    System.out.println("tessera scaduta 1:Compra nuova tessera 2:Compra singleRide");
                                    int risp1=Integer.parseInt(scanner.nextLine());
                                    if(risp1==1){
                                     user.setTessera(new Tessera(LocalDate.now()));

                                    } else if (risp1 == 2) {
                                        break;
                                    }
                                }

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
        }finally {
            scanner.close();
            em.close();
            emf.close();
        }

    }
}
