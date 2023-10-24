package BuildWeek1;

import BuildWeek1.Dao.*;
import BuildWeek1.entities.*;
import com.github.javafaker.Faker;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.*;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Application {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerFactory");
    private static final Scanner scanner = new Scanner(System.in);
    private static final Faker fkr = new Faker();
    private static final Random rnd = new Random();

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        boolean bool = false;
        UserDao userDao = new UserDao(em);
        User user = null;
        TicketDao ticketDao = new TicketDao(em);
        TesseraDao tesseraDao = new TesseraDao(em);
        MezzoDao mezzoDao = new MezzoDao(em);
        VenditaBigliettoDao vbdao = new VenditaBigliettoDao(em);
        TrattaDAO trattaDAO=new TrattaDAO(em);


        /*VenditaBiglietto vb = new VenditaBiglietto(true, TipoVendita.RIVENDITORE);
        vbdao.save(vb);
        VenditaBiglietto vb1 = new VenditaBiglietto(true, TipoVendita.RIVENDITORE);
        vbdao.save(vb1);
        VenditaBiglietto vb2 = new VenditaBiglietto(true, TipoVendita.DISTRIBUTORE_AUTOMATICO);
        vbdao.save(vb2);
        VenditaBiglietto vb3 = new VenditaBiglietto(true, TipoVendita.DISTRIBUTORE_AUTOMATICO);
        vbdao.save(vb3); */

        try {
            //*****************LOGIN***********************
            ExitCiclo:
            while (true) {
                try {
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
                } catch (NoResultException e) {
                    System.out.println("Nessun utente trovato");
                } catch (NumberFormatException e) {
                    System.out.println("Non hai inserito un valore corretto");

                } catch (RollbackException e) {
                    System.out.println("Email già utilizzata scegline un'altra");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            //*****************ADMIN************************
            if (user.getAdmin() == true && user != null) {
                Exit:
                while (true) {
                    try {

                        System.out.println("--------------------------------------------------------------------------");
                        System.out.println("1:Numero biglietti in un intervallo di tempo"+System.lineSeparator()+"2:Numero biglietti per tipo vendita"+System.lineSeparator()+"3:Crea tratta"+System.lineSeparator()+"4:inserisci tempo effettivo tratta"+System.lineSeparator()+"5:aggiungi persona al mezzo"+System.lineSeparator()+"6:togli persona dal mezzo"+System.lineSeparator()+"7:crea mezzo"+System.lineSeparator()+"8:assegna tratta a mezzo"+System.lineSeparator()+"9:Mostra persone su un mezzo"+System.lineSeparator()+"0:Esci");
                        int risp = Integer.parseInt(scanner.nextLine());
                        switch (risp) {
                            case 1 -> {
                                System.out.println("Inserisci anno data iniziale");
                                int annoi=Integer.parseInt(scanner.nextLine());
                                System.out.println("Inserisci mese data iniziale");
                                int mesei=Integer.parseInt(scanner.nextLine());
                                System.out.println("Inserisci giorno data iniziale");
                                int giornoi=Integer.parseInt(scanner.nextLine());
                                System.out.println("Inserisci anno data finale");
                                int annof=Integer.parseInt(scanner.nextLine());
                                System.out.println("Inserisci mese data finale");
                                int mesef=Integer.parseInt(scanner.nextLine());
                                System.out.println("Inserisci giorno data finale");
                                int giornof=Integer.parseInt(scanner.nextLine());
                                List<Object[]> ticketRange = ticketDao.getTotalTicketInTimeRangeGroupedByType(LocalDate.of(annoi,mesei,giornoi), LocalDate.of(annof,mesef,giornof));
                                for (Object[] result : ticketRange) {
                                    TicketType tipo = (TicketType) result[0];
                                    Long total = (Long) result[1];
                                    System.out.println("Tipo: " + tipo.name() + ", Totale: " + total);
                                }
                            }
                            case 2 -> {
                                List<Object[]> ticketTipoVendita = ticketDao.getTicketsSoldByTipoVendita();
                                for (Object[] result : ticketTipoVendita) {
                                    TipoVendita tipoVendita = (TipoVendita) result[0];
                                    Long total = (Long) result[1];
                                    System.out.println("Tipo Vendita: " + tipoVendita + ", Totale: " + total);
                                }
                            }
                            case 3->{

                                System.out.println("Inserisci città di partenza");
                                String cittaPartenza=scanner.nextLine();
                                System.out.println("Inserisci città di arrivo");
                                String cittaArrivo=scanner.nextLine();
                                System.out.println("inserisci il tempo stimato in minuti");
                                int t=Integer.parseInt(scanner.nextLine());
                                Tratta tratta=new Tratta(cittaPartenza,cittaArrivo,t);
                                trattaDAO.save(tratta);


                            }
                            case 4->{
                                System.out.println("Inserisci codice tratta da modificare");
                                long id=Integer.parseInt(scanner.nextLine());
                                Tratta t=trattaDAO.getById(id);
                                System.out.println("Inserisci tempo in minuti effettivo tratta n: "+id);
                                int tempo=Integer.parseInt(scanner.nextLine());
                                t.setTempoEffettivo(tempo);
                                trattaDAO.save(t);

                            }
                            case 5->{
                                System.out.println("Inserisci id mezzo");
                                long idmezzo=Integer.parseInt(scanner.nextLine());
                                System.out.println("Inserisci id utente da aggiungere");
                                long idutente=Integer.parseInt(scanner.nextLine());
                               Mezzo mez= mezzoDao.getById(idmezzo);
                                em.refresh(mez);
                            int numUser= mezzoDao.getMezzoPieno(mez.getId());
                                Mezzo m=mezzoDao.getById(idmezzo);
                                System.out.println(numUser);
                              if(numUser>=  m.getNumeroPosti())
                              {
                                 throw new Exception("Non ci sono posti disponibili cambia mezzo");
                              }

                              else{
                                if(userDao.getById(idutente)!=null) {
                                    m.setUser(userDao.getById(idutente));
                                    mezzoDao.save(m);
                                }else throw new Exception("Utente non presente");
                              }

                            }
                            case 6->{
                                System.out.println("Inserisci id mezzo");
                                long idmezzo=Integer.parseInt(scanner.nextLine());
                                System.out.println("Inserisci id utente da scaricare");
                                long idutente=Integer.parseInt(scanner.nextLine());
                                Mezzo mez= mezzoDao.getById(idmezzo);
                                int numUser= mezzoDao.getMezzoPieno(mez.getId());
                                if(numUser==0)
                                {
                                    throw new Exception("Non ci sono persone da scaricare");
                                }
                                else{
                                    if(userDao.getById(idutente)!=null) {
                                        mez.eliminaUtente(userDao.getById(idutente));
                                        mezzoDao.save(mez);
                                    }else throw new Exception("Utente non presente");
                                }

                            }
                            case 7->{
                                System.out.println("Inserisci tipo mezzo 1:tram 2:autobus");
                                int ris=Integer.parseInt(scanner.nextLine());
                                TipoMezzo t;
                                if(ris==1) t=TipoMezzo.TRAM;
                                else if(ris==2) t=TipoMezzo.AUTOBUS;
                                else throw new Exception("Tipo mezzo non disponibile");
                                System.out.println("Inserisci numero di posti disponibili");
                                int posti=Integer.parseInt(scanner.nextLine());
                                mezzoDao.save(new Mezzo(t,posti));
                                System.out.println("Mezzo salvato");
                            }
                            case 8->{
                                System.out.println("Inserisci id mezzo");
                                int id_mezzo=Integer.parseInt(scanner.nextLine());
                                System.out.println("Inserisci tratta da assegnare al mezzo: "+ id_mezzo);
                                int id_tratta=Integer.parseInt(scanner.nextLine());
                                Tratta tratta=trattaDAO.getById(id_tratta);
                                Mezzo m=mezzoDao.getById(id_mezzo);
                                em.refresh(m);
                                em.refresh(tratta);
                                if(tratta!=null && m!=null){
                                    m.setTratta(tratta);
                                    mezzoDao.save(m);
                                    System.out.println("Tratta associata");
                                }else System.out.println("Il mezzo o la tratta non esistono");

                            }
                            case 9->{
                                System.out.println("Inserisci il mezzo di cui vuoi controllare i passeggeri");
                                int r=Integer.parseInt(scanner.nextLine());
                                Mezzo m=mezzoDao.getById(r);
                                Set<User> userSet=m.getUser();
                                if(userSet.size()==0) throw new Exception("Non ci sono passeggeri");
                                else userSet.forEach(elem-> System.out.println("User id: "+ elem.getId()+" user email: "+elem.getEmail()));
                            }
                            case 0->{
                                break Exit;
                            }
                        }
                    }catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                    }
                }


            }
            //*****************USER*************************
            else if (user.getAdmin() == false && user != null) {
                ExitCiclo:
                while (true) {
                    try {
                        //***************TESSERA NULL*************************
                        if (user.getTessera() == null) {
                            System.out.println("1:acquista singleride 2:acquista tessera 0: esci");
                            int risp = Integer.parseInt(scanner.nextLine());
                            if (risp == 1) {
                                Ticket t = new Ticket(LocalDate.now(), TicketType.SINGLERIDE, user, vbdao.getById(26));
                                ticketDao.save(t);
                            } else if (risp == 2) {
                                Tessera tessera = new Tessera(LocalDate.now(), user);
                                tesseraDao.save(tessera);
                                System.out.println("1:acquista singleride 2:acquista settimanale 3:acquista mensile");
                                int risp2 = Integer.parseInt(scanner.nextLine());
                                switch (risp2) {
                                    case 1 -> {
                                        Ticket t = new Ticket(LocalDate.now(), TicketType.SINGLERIDE, user, vbdao.getById(27));
                                        ticketDao.save(t);
                                    }
                                    case 2 -> {
                                        Ticket t = new Ticket(LocalDate.now(), TicketType.WEEKLY, user, vbdao.getById(28));
                                        ticketDao.save(t);
                                    }
                                    case 3 -> {
                                        Ticket t = new Ticket(LocalDate.now(), TicketType.MONTHLY, user, vbdao.getById(29));
                                        ticketDao.save(t);
                                    }
                                }
                            } else if (risp == 0) {
                                break ExitCiclo;
                            }
                            em.refresh(user);
                        }
                        //***************TESSERA PRESENTE*********************
                        else {

                            Tessera tessera = user.getTessera();
                            if(tessera.getDataScadenza().isBefore(LocalDate.now()))
                            {
                                System.out.println("1:acquista singleride 2:rinnova tessera 0: esci");
                                int risp = Integer.parseInt(scanner.nextLine());
                                if (risp == 1) {
                                    Ticket t = new Ticket(LocalDate.now(), TicketType.SINGLERIDE, user, vbdao.getById(10));
                                    ticketDao.save(t);
                                } else if (risp == 2) {
                                  tessera.setDataScadenza(LocalDate.now().plusDays(365));
                                    tesseraDao.save(tessera);
                                    System.out.println("Tessera rinnovata");
                                    em.refresh(tessera);
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
                                } else if (risp == 0) {
                                    break ExitCiclo;
                                }
                                em.refresh(user);
                            }
                            System.out.println("1:acquista singleride 2:acquista settimanale 3:acquista mensile 4:valida ticket 5:Biglietti acquistati 6:Verifica validità tessera 0:Esci");
                            int risp2 = Integer.parseInt(scanner.nextLine());
                            switch (risp2) {
                                case 1 -> {
                                    Ticket t = new Ticket(LocalDate.now(), TicketType.SINGLERIDE, user, vbdao.getById(26));
                                    ticketDao.save(t);
                                }
                                case 2 -> {
                                    Ticket t = new Ticket(LocalDate.now(), TicketType.WEEKLY, user, vbdao.getById(28));
                                    ticketDao.save(t);
                                }
                                case 3 -> {
                                    Ticket t = new Ticket(LocalDate.now(), TicketType.MONTHLY, user, vbdao.getById(29));
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


                                }
                                case 5 -> {
                                   Map<TicketType,List<Ticket>> map= ticketDao.getAllTicketForUser(user.getId());
                                    if (map.get(TicketType.SINGLERIDE) != null) {
                                        System.out.println("SINGLERIDE");
                                        map.get(TicketType.SINGLERIDE).forEach(System.out::println);
                                    }


                                    if (map.get(TicketType.WEEKLY) != null) {
                                        System.out.println("WEEKLY");
                                        map.get(TicketType.WEEKLY).forEach(System.out::println);
                                    }
                                    if (map.get(TicketType.MONTHLY) != null) {
                                        System.out.println("MONTHLY");
                                        map.get(TicketType.MONTHLY).forEach(System.out::println);
                                    }
                                }
                           case 6-> {
                               if(user.getTessera().getDataScadenza().isBefore(LocalDate.now())){
                                   System.out.println("Tessera scaduta");
                               }
                                else
                               {
                                   System.out.println("Tessera attiva-data di scadenza: " + user.getTessera().getDataScadenza());
                               }


                           }
                                case 0->{
                                break ExitCiclo;
                                }
                            }

                        }
                    } catch (NoResultException e) {
                        System.out.println("Nessun utente trovato");
                    } catch (NumberFormatException e) {
                        System.out.println("Non hai inserito un valore corretto");

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }


        } catch (NumberFormatException e) {
            System.err.println("Non hai inserito un valore valido");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            scanner.close();
            em.close();
            emf.close();
        }

    }

    public static void fillUser(UserDao userDao) throws Exception {
        User u = new User(fkr.internet().emailAddress(), fkr.internet().password(), rnd.nextInt(0, 2) == 0 ? true : false);
        userDao.save(u);

    }
}
