package BuildWeek1.Dao;

import BuildWeek1.entities.Tratta;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class TrattaDAO {
    private final EntityManager em;

    public TrattaDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Tratta t) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(t);
        transaction.commit();
        System.out.println("Tratta salvata correttamente");
    }

    public Tratta getById(long id) {
        return em.find(Tratta.class, id);
    }

    public void findAndDelete(long id) {
        Tratta found = em.find(Tratta.class, id);
        if (found != null) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(found);
            transaction.commit();
            System.out.println("Elemento eliminato correttamente!");
        } else {
            System.out.println("la tratta con l'Id: " + id + "non è stata trovata");
        }
    }

    public long countTratteByMezzo(long mezzoId, long trattaId) {
        Query count = em.createQuery("SELECT COUNT(t) FROM Tratta t JOIN t.mezzo m WHERE m.id = :mezzoId AND t.id = :trattaId");
        count.setParameter("mezzoId", mezzoId);
        count.setParameter("trattaId", trattaId);
        return (long) count.getSingleResult();
    }

}
