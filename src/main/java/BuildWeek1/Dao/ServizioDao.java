package BuildWeek1.Dao;

import BuildWeek1.entities.Servizio;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class ServizioDao {


    private EntityManager em;

    public ServizioDao(EntityManager em) {
        this.em = em;
    }


    public void save(Servizio s) {
        try {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.persist(s);
            t.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Servizio getById(long id) throws Exception {
        Servizio s = em.find(Servizio.class, id);
        if (s == null) throw new Exception("Il servizio inserito non esiste");
        else return s;
    }

    public void delete(long id) {
        try {
            EntityTransaction t = em.getTransaction();
            t.begin();
            Servizio found = em.find(Servizio.class, id);
            if (found != null) {
                em.remove(found);
                t.commit();
                System.out.println("Elemento eliminato");
            } else System.out.println("Elemento non trovato");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Servizio getServiceNull(long mezzo_id) {
        TypedQuery<Servizio> q = em.createQuery("SELECT s FROM Servizio s WHERE s.dataFine=null AND s.mezzo.id = :mezzo_id", Servizio.class);
        q.setParameter("mezzo_id", mezzo_id);
        return q.getSingleResult();

    }

    public long tempoTotale(long mezzo_id) throws Exception {
        long x = 0;
        try {
            TypedQuery<Long> q = em.createQuery("SELECT SUM(s.durataServizio) FROM Servizio s WHERE s.mezzo.id=:mezzo_id", Long.class);
            q.setParameter("mezzo_id", mezzo_id);
            x = q.getSingleResult();
        } catch (Exception e) {
            throw new Exception("Il mezzo selezionato non è mai stato in servizio o in manutenzione");
        }

        return x;
    }
}
