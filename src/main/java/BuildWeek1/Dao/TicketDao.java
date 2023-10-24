package BuildWeek1.Dao;

import BuildWeek1.entities.Ticket;
import BuildWeek1.entities.TicketType;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.LocalDate;

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

    public List<Object[]> getTotalTicketInTimeRangeGroupedByType(LocalDate startDate, LocalDate endDate) {
        Query getByTime = em.createQuery("SELECT t.tipo, COUNT(t) as total " +
                " FROM Ticket t" +
                " WHERE t.dataEmissione BETWEEN :startDate AND :endDate " +
                " GROUP BY t.tipo " +
                " ORDER BY COUNT(t) DESC");
        getByTime.setParameter("startDate", startDate);
        getByTime.setParameter("endDate", endDate);

        return getByTime.getResultList();
    }

    public List<Object[]> getTicketsSoldByTipoVendita() {
        Query query = em.createQuery("SELECT t.venditabiglietto.tipoVendita , COUNT(t) " +
                "FROM Ticket t  " +
                "GROUP BY t.venditabiglietto.tipoVendita");
        return query.getResultList();
    }


}
