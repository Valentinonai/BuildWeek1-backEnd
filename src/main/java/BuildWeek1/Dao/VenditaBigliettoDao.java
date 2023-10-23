package BuildWeek1.Dao;

import BuildWeek1.entities.VenditaBiglietto;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class VenditaBigliettoDao {

    private EntityManager em;

    public VenditaBigliettoDao(EntityManager em) {
        this.em = em;
    }


    public void save(VenditaBiglietto v) {
        try {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.persist(v);
            t.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public VenditaBiglietto getById(long id) {
        return em.find(VenditaBiglietto.class, id);
    }

    public void delete(long id) {
        try {
            EntityTransaction t = em.getTransaction();
            t.begin();
            VenditaBiglietto found = em.find(VenditaBiglietto.class, id);
            if (found != null) {
                em.remove(found);
                t.commit();
                System.out.println("Elemento eliminato");
            } else System.out.println("Elemento non trovato");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}


