package BuildWeek1.Dao;

import BuildWeek1.entities.Ticket;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class TicketDao {
    private EntityManager em;

    public TicketDao(EntityManager em) {
        this.em = em;
    }


    public void save(Ticket t) {
        try {
            EntityTransaction tr = em.getTransaction();
            tr.begin();
            em.persist(t);
            tr.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Ticket getById(long id) {
        return em.find(Ticket.class, id);
    }

    public void delete(long id) {
        try {
            EntityTransaction tr = em.getTransaction();
            tr.begin();
            Ticket found = em.find(Ticket.class, id);
            if (found != null) {
                em.remove(found);
                tr.commit();
                System.out.println("Elemento eliminato");
            } else System.out.println("Elemento non trovato");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
